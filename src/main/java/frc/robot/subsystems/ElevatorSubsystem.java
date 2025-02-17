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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// Reference: https://github.com/REVrobotics/REVLib-Examples/blob/main/Java/SPARK/Closed%20Loop%20Control/src/main/java/frc/robot/Robot.java

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax rightMotor;
    private SparkMaxConfig rightMotorConfig;
    private SparkClosedLoopController rightMotorController;
    private RelativeEncoder rightMotorEncoder;

    private SparkMax leftMotor;
    private SparkMaxConfig leftMotorConfig;
    private SparkClosedLoopController leftMotorController;
    private RelativeEncoder leftMotorEncoder;

    public ElevatorSubsystem() {

        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client

        rightMotor = new SparkMax(30, MotorType.kBrushless);
        // rightMotorController = rightMotor.getClosedLoopController();
        // rightMotorEncoder = rightMotor.getEncoder();
        // rightMotorConfig = new SparkMaxConfig();

        leftMotor = new SparkMax(31, MotorType.kBrushless);
        //leftMotorController = leftMotor.getClosedLoopController();
        //leftMotorEncoder = leftMotor.getEncoder();
        //leftMotorConfig = new SparkMaxConfig();

        // rightMotorConfig.encoder.positionConversionFactor(1);
        // rightMotorConfig.closedLoop
        // .feedbackSensor(FeedbackSensor.kPrimaryEncoder)//.velocityFF(-0.5)
        // // PID values for position control (default slot: ClosedLoopSlot.kSlot0)
        // .p(0.1)
        // .i(0)
        // .d(0)
        // .outputRange(-1,1);

        // leftMotorConfig.encoder.positionConversionFactor(1);
        // leftMotorConfig.closedLoop
        // .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // // PID values for position control (default slot: ClosedLoopSlot.kSlot0)
        // .p(0.1)
        // .i(0)
        // .d(0)
        // .outputRange(-1,1);

        //rightMotor.configure(rightMotorConfig, null, null);
        //leftMotor.configure(leftMotorConfig,  ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        // Initialize dashboard values
        SmartDashboard.setDefaultNumber("Target Position", 0);
        SmartDashboard.setDefaultBoolean("Reset Encoder", false);
  
    }

    // Using controller and encoder classes to set target height

    // public Command raiseToFirstPosition() {
    //   //  Get the target position from SmartDashboard and set it as the setpoint for the closed loop controller.
    //   double targetPosition = SmartDashboard.getNumber("Target Position", 0);
    //   return this.startEnd(() ->
    //     rightMotorController.setReference(targetPosition, ControlType.kPosition, ClosedLoopSlot.kSlot0),
    //     () -> rightMotor.set(0)
    //   );
    // }

    public Command raise() {
        return this.startEnd(
            () -> rightMotor.set(-0.5), // reverse direction for 'raise'
            () -> rightMotor.set(0)
        );
        
    }

    public Command lower() {
        return this.startEnd(
            () -> rightMotor.set(0.3),
            () -> rightMotor.set(0)
        );
    }

    // public double getCurrentPosition() {
    //     return rightMotorEncoder.getPosition();
    // }

    /*
     * Reset encoder to recalculate actual position
     */
    // public Command resetEncoder() {
    //     return this.runOnce(() -> {
    //         rightMotorEncoder.setPosition(0);
    //     });
    // }
}
