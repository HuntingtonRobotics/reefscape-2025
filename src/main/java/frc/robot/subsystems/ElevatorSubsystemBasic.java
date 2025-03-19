package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystemBasic extends SubsystemBase {
    private SparkMax rightMotor;
    @SuppressWarnings("unused")
    private SparkMax leftMotor;

    public ElevatorSubsystemBasic() {
        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client

        rightMotor = new SparkMax(30, MotorType.kBrushless);
        leftMotor = new SparkMax(31, MotorType.kBrushless); // set to 'follow' in the SparkMax
    }

    public void raise() {
        rightMotor.set(-0.7); // reverse direction for 'raise'
    }

    public void lower() {
        rightMotor.set(0.5);
    }

    public void stop() {
        rightMotor.set(0.0);
    }
}
