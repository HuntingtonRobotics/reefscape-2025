package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.DashboardConstants;
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

    public Command coralToFirstPosition() {
        // Elevator only needs to rise a few inches
        return Commands.sequence(
            elevator.raiseToHeight(0.058), // 2in
            coralRamp.toggleRaise(),
            coralDoor.toggleOpen()
        );
    }

    public Command coralToSecondPosition() {
        double targetPositionMeters = SmartDashboard.getNumber(DashboardConstants.TargetElevatorPositionKey, 1);
        return Commands.sequence(
            //elevator.raiseToHeight(targetPositionMeters),
            Commands.race(elevator.raise(), Commands.waitSeconds(1.1)),
            //coralRamp.toggleRaise(),
            coralDoor.toggleOpen()
        );
    }

    public Command coralToThirdPosition() {
        return Commands.none(); 
    }

    public Command coralToTopPosition() {
        return Commands.none(); 
    }

    public Command reset() {
        return Commands.none();
    }
}
