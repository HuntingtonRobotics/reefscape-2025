package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeIntake extends SubsystemBase {
    private SparkFlex motor;

    public AlgaeIntake() {
        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client
        motor = new SparkFlex(40, MotorType.kBrushless);
    }

    public Command outtake() {
        return this.startEnd(() -> motor.set(1), () -> motor.set(0));
    }

    public Command intake() {
        return this.startEnd(() -> motor.set(-0.5), () -> motor.set(0));
    }

   

}
