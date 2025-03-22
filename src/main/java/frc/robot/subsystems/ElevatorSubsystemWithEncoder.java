// package frc.robot.subsystems;

// import com.revrobotics.spark.SparkLowLevel.MotorType;

// import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import com.ctre.phoenix6.hardware.CANcoder;
// import com.revrobotics.spark.SparkMax;

// public class ElevatorSubsystemWithEncoder extends SubsystemBase {
//     private SparkMax rightMotor;
//     @SuppressWarnings("unused")
//     private SparkMax leftMotor;
//     private CANcoder encoder;

//     private double desiredPosition;
//     private ProfiledPIDController pid;

//     public ElevatorSubsystemWithEncoder() {
//         rightMotor = new SparkMax(30, MotorType.kBrushless);
//         leftMotor = new SparkMax(31, MotorType.kBrushless); // set to 'follow' in the SparkMax
//         encoder = new CANcoder(51);

//         encoder.setPosition(0.0);

//         desiredPosition = 0.0;
//         pid = new ProfiledPIDController(0.1, 0.0, 0.0, new TrapezoidProfile.Constraints(10.0, 5.0));
//     }

//     public void setPosition(double pos) {
//         desiredPosition = pos;
//         pid.setGoal(pos);
//     }

//     // public Command raise() {

//     // }

//     // public Command lower() {

//     // }

//     @Override
//     public void periodic() {
//         SmartDashboard.putNumber("Elevator Position", encoder.getPosition().getValueAsDouble());
    
//         double gravityFF = 3.85 / 40.0;
//         double positionPIDOut = pid.calculate(encoder.getPosition().getValueAsDouble());

//         double motorSet = gravityFF + positionPIDOut;

//         rightMotor.set(motorSet);
//         // leftMotor.set(motorSet);
//     }
// }
