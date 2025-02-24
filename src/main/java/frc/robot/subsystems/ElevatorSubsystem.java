package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase implements AutoCloseable {
    private SparkMax rightMotor;
    private SparkMaxConfig rightMotorConfig;
    private RelativeEncoder rightMotorEncoder;
    
    @SuppressWarnings("unused")
    private SparkMax leftMotor;

    private final double gearRadiusMeters = 0.037;
    private double gearCircumferenceMeters;

    public ElevatorSubsystem() {

        //rightMotor = right;
        //leftMotor = left;

        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client

        rightMotor = new SparkMax(30, MotorType.kBrushless);
        rightMotorEncoder = rightMotor.getEncoder();
        rightMotorConfig = new SparkMaxConfig();

        leftMotor = new SparkMax(31, MotorType.kBrushless); // set to 'follow' using the REV Hardware Client

        rightMotorConfig.encoder.positionConversionFactor(1);
        rightMotor.configure(rightMotorConfig, null, null);

        // Initialize dashboard values
        SmartDashboard.setDefaultNumber("Target Position", 0);

        gearCircumferenceMeters = (gearRadiusMeters * 2) * Math.PI;

    }

    // private static ElevatorSubsystem Create() {
    //     SparkMax rightSparkMax = new SparkMax(30, MotorType.kBrushless);
    //     SparkMax leftSparkMax = new SparkMax(31, MotorType.kBrushless);
    //     return new ElevatorSubsystem(rightSparkMax, leftSparkMax);
    // }

    public Command raiseToHeight(double heightMeters) {
        double targetPositionMeters = SmartDashboard.getNumber("Target Position", 0);
        double targetRotations = targetPositionMeters / gearCircumferenceMeters;
        return Commands.race(raise(), this.runOnce(() -> {
            while(true) {
                double currentPosition = rightMotorEncoder.getPosition();
                if (currentPosition >= targetRotations) {
                    break;
                }
            }
        }));
    }

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

    @Override
    public void close(){
        leftMotor.close();
        rightMotor.close();
    }
}
