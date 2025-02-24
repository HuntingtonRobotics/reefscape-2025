package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CageClimber extends SubsystemBase {
    private SparkMax rightMotor;
    private SparkMax leftMotor;

    public CageClimber() {
        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client

        rightMotor = new SparkMax(32, MotorType.kBrushless);
        leftMotor = new SparkMax(33, MotorType.kBrushless);
    }

    public Command raise() {
        return Commands.none();
    }

    public Command lower() {
        return Commands.none();
    }
}
