package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax rightMotor;
    private SparkMaxConfig rightMotorConfig;
    private RelativeEncoder rightMotorEncoder;

    @SuppressWarnings("unused")
    private SparkMax leftMotor;

    public static final double GearRadiusMeters = 0.037;
    public static final double GearCircumferenceMeters = (GearRadiusMeters * 2) * Math.PI;

    public ElevatorSubsystem() {
        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client

        rightMotor = new SparkMax(30, MotorType.kBrushless);
        rightMotorEncoder = rightMotor.getEncoder();
        rightMotorConfig = new SparkMaxConfig();

        leftMotor = new SparkMax(31, MotorType.kBrushless); // set to 'follow' in the SparkMax

        rightMotorConfig.encoder.positionConversionFactor(1);
        rightMotor.configure(rightMotorConfig, null, null);
    }

    public Command raiseToHeight(double targetHeightMeters) {
        double targetRotations = targetHeightMeters / GearCircumferenceMeters;
        return Commands.race(raise(), Commands.runOnce(() -> {
            while (true) {
                double currentPositionRotations = rightMotorEncoder.getPosition();
                if (currentPositionRotations >= targetRotations) {
                    break;
                }
            }
        }),
        Commands.print(String.format("targetRotations=[{0}] encoderPosition=[{1}] rotations", targetRotations, rightMotorEncoder.getPosition())));
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
                () -> rightMotor.set(-0.5), // reverse direction for 'raise'
                () -> rightMotor.set(0));

    }

    public Command lower() {
        return this.startEnd(
                () -> rightMotor.set(0.3),
                () -> rightMotor.set(0));
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
