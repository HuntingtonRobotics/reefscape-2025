package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CoralDoor;
import frc.robot.subsystems.ElevatorSubsystem;



public class CoralElevatorCommands {
    private ElevatorSubsystem elevator;
    private CoralDoor coralDoor;
    



    public CoralElevatorCommands(ElevatorSubsystem elevator, CoralDoor door){
        this.elevator = elevator;
        this.coralDoor = door;
        
    }

    public Command coralToFirstPosition() {
        // Elevator only needs to rise a few inches
        return raiseByTime(0.2);
    }

    public Command coralToSecondPosition() {
        return raiseByTime(0.75);
    }    

    public Command coralToThirdPosition() {
        return raiseByTime(1.7);
    }

    public Command coralToTopPosition() {
        return raiseByTime(2.6);
    }

    public Command lowerTopToThirdPositionIsh() {
        return lowerByTime(1.1);
    }

    public Command lowerToBottomIshFromSecondPosition() {
        return lowerByTime(0.35);
    }

    public Command lowerToBottomIshFromTop() {
        return lowerByTime(2);
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
            Commands.waitSeconds(2.5),
            coralDoor.toggleOpen()
        );
    }
    

    private Command lowerByTime(double seconds) {
        return Commands.race(elevator.lower(), Commands.waitSeconds(seconds));
    }

    public Command fullrun4Pos (){
        return Commands.sequence(
            coralToTopPosition(),
            coralDoor.toggleOpen(),
            Commands.waitSeconds(2.5),
            coralDoor.toggleOpen(),
            lowerByTime(5.2)
        );        
    }

    public Command fullrun3Pos() {
        return Commands.sequence(
            coralToThirdPosition(),
            coralDoor.toggleOpen(),
            Commands.waitSeconds(2.5),
            coralDoor.toggleOpen(),
            lowerByTime(2.4));
    }

    public Command fullrun2Pos() {
        return Commands.sequence(
            coralToSecondPosition(),
            coralDoor.toggleOpen(),
            Commands.waitSeconds(2.5),
            coralDoor.toggleOpen(),
            lowerByTime(1.5));
    }

    public Command fullrun1Pos() {
        return Commands.sequence(
            coralToFirstPosition(),
            coralDoor.toggleOpen(),
            Commands.waitSeconds(2.5),
            coralDoor.toggleOpen(),
            lowerByTime(0.4));
    }
}
