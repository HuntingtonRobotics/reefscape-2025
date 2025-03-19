package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystemBasic;

public class LowerElevatorCommand extends Command {
    private final ElevatorSubsystemBasic elevator;
    private final DigitalInput sensor;

    public LowerElevatorCommand(ElevatorSubsystemBasic elevator, DigitalInput sensor) {
        this.elevator = elevator;
        this.sensor = sensor;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        if (!sensor.get()) {
            elevator.lower();
        }
        else {
            elevator.stop();
        }
    }

    @Override
    public void end(boolean isInterrupted) {
        elevator.stop();
    }
}
