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
        return RaiseByTime(0.2);
    }

    public Command coralToSecondPosition() {
        return RaiseByTime(0.75);
    }    

    public Command coralToThirdPosition() {
        return RaiseByTime(1.7);
    }

    public Command coralToTopPosition() {
        return RaiseByTime(2.8);
    }

    private Command RaiseByTime(double seconds){
        return Commands.sequence(
            Commands.race(elevator.raiseAuto(), Commands.waitSeconds(seconds)),
            coralDoor.toggleOpen(),
            Commands.waitSeconds(2.5),
            coralDoor.toggleOpen()
        );

    }

    public Command lowerTopToThirdPositionIsh() {
        return lowerByTime(0.9*1.11842105);
    }

    public Command lowerToBottomIshFromSecondPosition() {
        return lowerByTime(0.75*1.11842105);
    }

    public Command lowerToBottomIshFromTop() {
        return lowerByTime(2.7*1.11842105);
    }

    public Command reset() {
        return Commands.none();
    }

    private Command raiseByTime(double seconds) {
        return  Commands.race(elevator.raise(), Commands.waitSeconds(seconds));
    }




    private Command lowerByTime(double seconds) {
        return Commands.race(elevator.lower(), Commands.waitSeconds(seconds));
    }

    public Command raiseSequence(double raise , double lower){
        return Commands.sequence(
            raiseByTime(raise),
            coralDoor.toggleOpen(),
            Commands.waitSeconds(0.75),
            lowerByTime(lower),
            coralDoor.toggleOpen()
        );
    }


    
}
