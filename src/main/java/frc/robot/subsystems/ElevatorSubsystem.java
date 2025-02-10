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
        rightMotor = new SparkMax(-1, MotorType.kBrushless);
        rightMotorController = rightMotor.getClosedLoopController();
        rightMotorEncoder = rightMotor.getEncoder();

        leftMotor = new SparkMax(-1, MotorType.kBrushless);
        leftMotorController = rightMotor.getClosedLoopController();
        leftMotorEncoder = rightMotor.getEncoder();

        SparkMaxConfig globalConfig = new SparkMaxConfig();
        globalConfig.smartCurrentLimit(Constants.CurrentLimit)
                    .idleMode(IdleMode.kBrake);

        SparkMaxConfig rightConfig = new SparkMaxConfig();
        rightConfig.apply(globalConfig);
        rightConfig.encoder.positionConversionFactor(1);
        rightConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // PID values for position control (default slot: ClosedLoopSlot.kSlot0)
            .p(0.1)
            .i(0)
            .d(0)
            .outputRange(-1,1);

        SparkMaxConfig leftConfig = new SparkMaxConfig();
        leftConfig.apply(globalConfig)
                  .follow(rightMotor);
        leftConfig.encoder.positionConversionFactor(1);
        leftConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // PID values for position control (default slot: ClosedLoopSlot.kSlot0)
            .p(0.1)
            .i(0)
            .d(0)
            .outputRange(-1,1);

        rightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        leftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    
    // Using controller and encoder classes to set target height

    public Command raise() {
        return this.run(() -> {
            rightMotorController.setReference(0.5, ControlType.kPosition, ClosedLoopSlot.kSlot0);
            leftMotorController.setReference(0.5, ControlType.kPosition, ClosedLoopSlot.kSlot0);
        });
    }

    public Command lower() {
        return this.run(() -> rightMotor.set(-1.0));
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
