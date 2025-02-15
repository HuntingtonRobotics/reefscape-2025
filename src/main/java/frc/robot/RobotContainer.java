// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AlgaeCommands;
import frc.robot.commands.CoralElevatorCommands;
import frc.robot.subsystems.AlgaeArm;
import frc.robot.subsystems.AlgaeIntake;
import frc.robot.subsystems.CageClimber;
import frc.robot.subsystems.CoralDoor;
import frc.robot.subsystems.CoralRamp;
import frc.robot.subsystems.ElevatorSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final SwerveDriveContainer swerve = new SwerveDriveContainer();
  private final AlgaeArm algaeArm = new AlgaeArm();
  private final AlgaeIntake algaeIntake = new AlgaeIntake();
  private final ElevatorSubsystem elevator = new ElevatorSubsystem();
  private final CoralRamp coralRamp = new CoralRamp();
  private final CoralDoor coralDoor = new CoralDoor();
  private final CageClimber climber = new CageClimber();

  private final CoralElevatorCommands coralElevatorCommands = new CoralElevatorCommands(elevator, coralRamp, coralDoor);
  private final AlgaeCommands algaeCommands = new AlgaeCommands(algaeArm, algaeIntake);

  private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();

  Alliance assignedAlliance;

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.DriverControllerPort);
  private final CommandXboxController operatorController =
      new CommandXboxController(OperatorConstants.OperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    assignedAlliance = getAlliance();

    configureBindings();
    
    CameraServer.startAutomaticCapture(); // adds to dashboard

  }

  private Alliance getAlliance() {
    Optional<Alliance> ally = DriverStation.getAlliance();
    if (ally.isPresent()) {
      return ally.get();
    }

    return Alliance.Blue;
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    swerve.configureBindings(driverController);
    configureGameplayBindings();
  }

  /**
   * Define trigger->command bindings for gameplay actions (raise/lower, on/off, etc.)
   */
  private void configureGameplayBindings() {
    // Example: operatorController.a().whileTrue(climber.raise())
    operatorController.a().onTrue(coralElevatorCommands.raiseToFirstPosition());
    operatorController.x().onTrue(coralElevatorCommands.raiseToSecondPosition());
    operatorController.y().onTrue(coralElevatorCommands.raiseToThirdPosition());
    operatorController.b().onTrue(coralElevatorCommands.reset());
    operatorController.povUp().whileTrue(coralRamp.toggleRaise());
    operatorController.povDown().whileTrue(coralDoor.toggleSolenoid());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return autonomousChooser.getSelected();
    return Commands.none();
  }
}
