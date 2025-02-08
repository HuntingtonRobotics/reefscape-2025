package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax rightMotor;
    private SparkMax leftMotor;

    public ElevatorSubsystem() {
        rightMotor = new SparkMax(-1, MotorType.kBrushless);
        leftMotor = new SparkMax(-1, MotorType.kBrushless);

        SparkMaxConfig leftConfig = new SparkMaxConfig();
        leftConfig.follow(rightMotor)
                .smartCurrentLimit(Constants.CurrentLimit);

        SparkMaxConfig rightConfig = new SparkMaxConfig();
        rightConfig.smartCurrentLimit(Constants.CurrentLimit);

        rightMotor.configure(rightConfig, null, null);
        leftMotor.configure(leftConfig, null, null);

        // Consider using Encoder class to determine its height and raise/lower until it reaches a known height
    }
    
    public Command raise() {
        return this.run(() -> rightMotor.set(1.0));
    }

    public Command lower() {
        return this.run(() -> rightMotor.set(-1.0));
    }
}
