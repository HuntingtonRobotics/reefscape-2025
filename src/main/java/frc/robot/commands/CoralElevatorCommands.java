package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CoralDoor;
import frc.robot.subsystems.CoralRamp;
import frc.robot.subsystems.ElevatorSubsystem;

public class CoralElevatorCommands {
    private ElevatorSubsystem elevator;
    private CoralRamp coralRamp;
    private CoralDoor coralDoor;

    public CoralElevatorCommands(ElevatorSubsystem elevator, CoralRamp ramp, CoralDoor door) {
        this.elevator = elevator;
        this.coralRamp = ramp;
        this.coralDoor = door;
    }

    public Command raiseToFirstPosition() {
        return Commands.sequence(
            elevator.raise(),
            coralRamp.raise(),
            coralDoor.down()
        );
    }

    public Command raiseToSecondPosition() {
        return Commands.none(); 
    }

    public Command raiseToThirdPosition() {
        return Commands.none(); 
    }

    public Command reset() {
        return Commands.sequence(
            coralDoor.up(),
            coralRamp.lower(),
            elevator.lower()
        );
    }
}
