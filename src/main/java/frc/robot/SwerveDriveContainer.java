package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class SwerveDriveContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public SwerveDriveContainer() {
        // N/A
    }

    public void configureBindings(CommandXboxController controller) {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-controller.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-controller.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-controller.getRightX() * MaxAngularRate / 2) // Drive counterclockwise with negative X (left)
            )
        );

        // Fine motor control
        fineMotorControlBindings(controller);

        controller.a().whileTrue(drivetrain.applyRequest(() -> brake));
        controller.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-controller.getLeftY(), -controller.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        controller.back().and(controller.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        controller.back().and(controller.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        controller.start().and(controller.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        controller.start().and(controller.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        controller.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    private void fineMotorControlBindings(CommandXboxController controller) {
        double speedX = 0.5;
        double speedY = 0.5;

        // Forward
        controller.povUp().whileTrue(
            drivetrain.applyRequest(() ->
                drive.withVelocityX(speedX)
            )
        );

        // Backward
        controller.povDown().whileTrue(
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-speedX)
            )
        );

        // Left
        controller.povLeft().whileTrue(
            drivetrain.applyRequest(() ->
                drive.withVelocityY(speedY)
            )
        );

        // Right
        controller.povRight().whileTrue(
            drivetrain.applyRequest(() ->
                drive.withVelocityY(-speedY)
            )
        );

        // Forward-Left
        controller.povUpLeft().whileTrue(
            drivetrain.applyRequest(() -> 
                drive.withVelocityX(speedX)
                     .withVelocityY(speedY)    
            )
        );

        // Forward-Right
        controller.povUpRight().whileTrue(
            drivetrain.applyRequest(() -> 
                drive.withVelocityX(speedX)
                     .withVelocityY(-speedY)    
            )
        );

        // Backward-Left
        controller.povDownLeft().whileTrue(
            drivetrain.applyRequest(() -> 
                drive.withVelocityX(-speedX)
                     .withVelocityY(speedY)    
            )
        );

        // Backward-Right
        controller.povDownRight().whileTrue(
            drivetrain.applyRequest(() -> 
                drive.withVelocityX(-speedX)
                     .withVelocityY(-speedY)
            )
        );
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
