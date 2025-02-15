package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// Reference: https://github.com/REVrobotics/REVLib-Examples/blob/main/Java/SPARK/Closed%20Loop%20Control/src/main/java/frc/robot/Robot.java

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax rightMotor;
    private SparkClosedLoopController rightMotorController;
    private RelativeEncoder rightMotorEncoder;

    private SparkMax leftMotor;
    private SparkClosedLoopController leftMotorController;
    private RelativeEncoder leftMotorEncoder;

    public ElevatorSubsystem() {

        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client

        rightMotor = new SparkMax(30, MotorType.kBrushless);
        // rightMotorController = rightMotor.getClosedLoopController();
        // rightMotorEncoder = rightMotor.getEncoder();

        leftMotor = new SparkMax(31, MotorType.kBrushless);
        // leftMotorController = leftMotor.getClosedLoopController();
        // leftMotorEncoder = leftMotor.getEncoder();

        // rightConfig.encoder.positionConversionFactor(1);
        // rightConfig.closedLoop
        // .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // // PID values for position control (default slot: ClosedLoopSlot.kSlot0)
        // .p(0.1)
        // .i(0)
        // .d(0)
        // .outputRange(-1,1);

        // leftConfig.encoder.positionConversionFactor(1);
        // leftConfig.closedLoop
        // .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // // PID values for position control (default slot: ClosedLoopSlot.kSlot0)
        // .p(0.1)
        // .i(0)
        // .d(0)
        // .outputRange(-1,1);
    }

    // Using controller and encoder classes to set target height

    public Command raise() {
        return this.startEnd(
            () -> rightMotor.set(-0.1), // reverse direction for 'raise'
            () -> rightMotor.set(0)
        );
    }

    public Command lower() {
        return this.startEnd(
            () -> rightMotor.set(0.1),
            () -> rightMotor.set(0)
        );
    }

    /*
     * Reset encoder to recalculate actual position
     */
    public Command resetEncoder() {
        return this.runOnce(() -> {
            rightMotorEncoder.setPosition(0);
            leftMotorEncoder.setPosition(0);
        });
    }
}
