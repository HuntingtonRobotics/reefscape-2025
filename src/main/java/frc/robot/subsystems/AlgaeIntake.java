package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeIntake extends SubsystemBase {
    private SparkMax motor;

    public AlgaeIntake() {
        motor = new SparkMax(-1, MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(Constants.CurrentLimit);

        motor.configure(config, null, null);
    }

    public Command intake() {
        return Commands.none();
    }

    public Command outtake() {
        return Commands.none();
    }
}
