package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CageClimber extends SubsystemBase {
    private SparkMax motor;
    private Solenoid solenoid;
    private final double maxSpeed = 0.05;

    public CageClimber() {
        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client
        motor = new SparkMax(32, MotorType.kBrushless);
        solenoid = new Solenoid(50, PneumaticsModuleType.REVPH, 6);
    }

    public Command raise() {
        return this.startEnd(() -> motor.set(maxSpeed), () -> motor.set(0));

    }

    public Command lower() {
        return this.startEnd(() -> motor.set(-maxSpeed), () -> motor.set(0));
    }

    public Command toggle() {
        return this.run(() -> solenoid.toggle());
    }
}
