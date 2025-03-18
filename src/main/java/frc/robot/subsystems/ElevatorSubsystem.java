package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DashboardConstants;

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax rightMotor;
    private SparkMaxConfig rightMotorConfig;
    private RelativeEncoder rightMotorEncoder;
    private DigitalInput sensor;
    @SuppressWarnings("unused")
    private SparkMax leftMotor;
    

    public static final double GearRadiusMeters = 0.047;
    public static final double GearCircumferenceMeters = (GearRadiusMeters * 2) * Math.PI;

    public ElevatorSubsystem() {
        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client
        
        rightMotor = new SparkMax(30, MotorType.kBrushless);
        rightMotorEncoder = rightMotor.getEncoder();
        rightMotorConfig = new SparkMaxConfig();
        sensor = new DigitalInput(4);
        leftMotor = new SparkMax(31, MotorType.kBrushless); // set to 'follow' in the SparkMax

        rightMotorConfig.encoder.positionConversionFactor(0.028); // 1/36th
        rightMotor.configure(rightMotorConfig, null, null);
    }

    public Command raiseToHeightAlt(double targetHeightMeters) {
        return this.runOnce(
                () -> {
                    double targetRotations = targetHeightMeters / GearCircumferenceMeters;
                    rightMotor.set(-0.25);
                    while (rightMotorEncoder.getPosition() <= targetRotations) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            // nothing
                        }
                    }
                    rightMotor.set(0);
                });
    }

    public Command raiseToHeight(double targetHeightMeters) {
        double targetRotations = targetHeightMeters / GearCircumferenceMeters;
        return Commands.race(
            raise(),
            Commands.runOnce(() -> {
                while (true) {
                    double currentPositionRotations = rightMotorEncoder.getPosition();
                    if (currentPositionRotations >= targetRotations) {
                        break;
                    }
                }
            }),
            Commands.print(String.format("targetRotations=[{0}] encoderPosition=[{1}] rotations", targetRotations, rightMotorEncoder.getPosition())),
            Commands.run(() -> SmartDashboard.putNumber(DashboardConstants.CurrentElevatorPositionKey, rightMotorEncoder.getPosition())));
    }

    public Command lowerToHeight(double targetHeightMeters) {
        double targetRotations = targetHeightMeters / GearCircumferenceMeters;
        return Commands.race(raise(), Commands.runOnce(() -> {
            while (true) {
                double currentPositionRotations = rightMotorEncoder.getPosition();
                if (currentPositionRotations <= targetRotations) {
                    break;
                }
            }
        }),
        Commands.print(String.format("targetRotations=[{0}] encoderPosition=[{1}] rotations", targetRotations, rightMotorEncoder.getPosition())));
    }

    public Command raise() {
        return this.startEnd(
                () -> rightMotor.set(-0.7), // reverse direction for 'raise'
                () -> rightMotor.set(0));

    }

    public Command lower() {
        return this.startEnd(
            () -> {
                while (sensor.get()) {
                    rightMotor.set(0.5);
                }

                rightMotor.set(0.0);
            },
            () -> rightMotor.set(0));
    }

    public Command stop() {
        return this.run(() -> rightMotor.set(0));
    }

    public double getCurrentPosition() {
        return rightMotorEncoder.getPosition();
    }

    /*
     * Reset encoder to recalculate actual position
     */
    public Command resetEncoder() {
        return this.runOnce(() -> {
            rightMotorEncoder.setPosition(0);
        });
    }
}
