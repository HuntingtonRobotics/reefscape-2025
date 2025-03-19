package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CoralDoor;
import frc.robot.subsystems.ElevatorSubsystemBasic;

public class ScoreCoralCommands {
    private ElevatorSubsystemBasic elevator;
    private DigitalInput bottomElevatorSensor;
    private CoralDoor coralDoor;
    
    public ScoreCoralCommands(ElevatorSubsystemBasic elevator, DigitalInput bottomElevatorSensor, CoralDoor coralDoor) {
        this.elevator = elevator;
        this.bottomElevatorSensor = bottomElevatorSensor;
        this.coralDoor = coralDoor;
    }

    private Command raiseElevatorByTime(double seconds) {
        return Commands.race(new RaiseElevatorCommand(elevator), Commands.waitSeconds(seconds));
    }

    private Command scoreCoral() {
        return Commands.sequence(
            coralDoor.toggleOpen(),
            Commands.waitSeconds(2.5),
            coralDoor.toggleOpen()
        );
    }

    private Command coralToPositionByTime(double seconds) {
        return Commands.sequence(
            raiseElevatorByTime(seconds),
            scoreCoral(),
            new LowerElevatorCommand(elevator, bottomElevatorSensor)
        );
    }

    public Command coralToTopPosition() {
        return coralToPositionByTime(2.6);
    }
    
    public Command coralToThirdPosition() {
        return coralToPositionByTime(1.7);
    }

    public Command coralToSecondPosition() {
        return coralToPositionByTime(0.75);
    }

    public Command coralToFirstPosition() {
        // Elevator only needs to rise a few inches
        return coralToPositionByTime(0.2);
    }
}
