package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CoralDoor;
import frc.robot.subsystems.ElevatorSubsystem;

public class CoralElevatorCommands {
    private ElevatorSubsystem elevator;
    private CoralDoor coralDoor;

    public CoralElevatorCommands(ElevatorSubsystem elevator, CoralDoor door) {
        this.elevator = elevator;
        this.coralDoor = door;
    }

    public Command coralToFirstPosition() {
        // Elevator only needs to rise a few inches
        return raiseByTime(0.25);
    }

    public Command coralToSecondPosition() {
        return raiseByTime(1.1);
    }    

    public Command coralToThirdPosition() {
        return raiseByTime(2.3);
    }

    public Command coralToTopPosition() {
        return raiseByTime(3.8);
    }

    public Command reset() {
        return Commands.none();
    }

    private Command raiseByTime(double seconds) {
        return Commands.sequence(
            //elevator.raiseToHeight(targetPositionMeters),
            Commands.race(elevator.raise(), Commands.waitSeconds(seconds)),
            //coralRamp.toggleRaise(),
            coralDoor.toggleOpen(),
            Commands.waitSeconds(4),
            coralDoor.toggleOpen()
        );
    }
}
