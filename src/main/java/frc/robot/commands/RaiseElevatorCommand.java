package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystemBasic;

public class RaiseElevatorCommand extends Command {
    private final ElevatorSubsystemBasic elevator;

    public RaiseElevatorCommand(ElevatorSubsystemBasic elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.raise();
    }

    @Override
    public void end(boolean isInterrupted) {
        elevator.stop();
    }
}
