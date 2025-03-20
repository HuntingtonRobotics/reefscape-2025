import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ElevatorSubsystemBasic;

public class CommandTests {

    @BeforeAll
    static void setupAll() {
        CommandScheduler.getInstance().run();
    }
    @BeforeEach
    void setup() {
        assert HAL.initialize(500, 0);
    }

    @Test
    void commandOutputTest() {
        Command raceCommand = new ParallelRaceGroup(
            new WaitCommand(10),
            new RunCommand(() -> System.out.println("waiting..."), new ElevatorSubsystemBasic())
        );
        raceCommand.setName("raceCommand");

        CommandScheduler.getInstance().schedule(raceCommand);
        CommandScheduler.getInstance().onCommandFinish(cmd -> {
            System.out.printf("command '{0}' finished", cmd.getName());
            assertTrue(true);
        } );
    }
    
    void raceCommand_finishesAsExpected() throws InterruptedException {
        double target = 10.0;
        AtomicInteger current = new AtomicInteger(0);
        Command raceCommand = Commands.race(
            Commands.runOnce(() -> raise()),
            Commands.runOnce(() -> {
                while (true) {
                    if (current.get() >= target) {
                        break;
                    }
                    current.addAndGet(1);
                }
            }),
            Commands.print(String.format("current=[{0}] target=[{1}]", current.get(), target))
        );
        raceCommand.setName("race");

        CommandScheduler.getInstance().schedule(raceCommand);
        CommandScheduler.getInstance().onCommandFinish(cmd -> {
            System.out.printf("command '{0}' finished", cmd.getName());
            assertEquals(target, current.get());
        } );
        assertTrue(raceCommand.isFinished());
        assertEquals(target, current.get());
    }

    private void raise() {
        while(true) {
            try {
                System.out.println("raise loop");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("raise() thread interrupted");
            }
        }
    }

    @AfterAll
    static void teardownAll() {
        CommandScheduler.getInstance().cancelAll();
    }
}
