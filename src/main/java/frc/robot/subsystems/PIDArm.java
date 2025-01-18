package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/* This code is to test PID-drive movement of the Launcher Arm */
public class PIDArm extends SubsystemBase {

  private SparkMax m_rightLead;
  private SparkMax m_leftFollow;

  // new stuff
  private SparkClosedLoopController m_rightPIDController;
  private SparkClosedLoopController m_leftPIDController;
  private RelativeEncoder m_rightEncoder;
  private RelativeEncoder m_leftEncoder;

  public PIDArm() {

    m_rightLead = new SparkMax(30, MotorType.kBrushless);
    m_leftFollow = new SparkMax(31, MotorType.kBrushless);

    // test stuff

    // set the PID coefficients for the right arm motor
    SparkMaxConfig rightConfig = new SparkMaxConfig();
    rightConfig.closedLoop
        .pid(0.01, 0, 0);

    SparkMaxConfig leftConfig = new SparkMaxConfig();
    leftConfig.closedLoop
        .pid(0.01, 0, 0);

    m_rightLead.configure(rightConfig, null, null);
    m_leftFollow.configure(leftConfig, null, null);

    // set the encoders for the right and left arm motors
    m_rightEncoder = m_rightLead.getEncoder();
    m_leftEncoder = m_leftFollow.getEncoder();

    // reset the encoder positions
    m_rightEncoder.setPosition(0);
    m_leftEncoder.setPosition(0);

    m_rightPIDController = m_rightLead.getClosedLoopController();
    m_leftPIDController = m_leftFollow.getClosedLoopController();
  }

  public Command getLauncherUpCommand() {
    return this.startEnd(
        () -> {
          m_leftFollow.set(.9);
          m_rightLead.set(-.9);
        },
        () -> {
          m_leftFollow.set(0);
          m_rightLead.set(0);
        });
  }

  public Command getLauncherDownCommand() {
    return this.startEnd(
        () -> {
          m_leftFollow.set(-.9);
          m_rightLead.set(.9);
        },
        () -> {
          m_leftFollow.set(0);
          m_rightLead.set(0);
        });
  }

  public void setAngle(double angle) {
    double rightSpeed = (angle - m_rightEncoder.getPosition()) / 50.0;
    double leftSpeed = (angle - m_leftEncoder.getPosition()) / 50.0;

    m_rightPIDController.setReference(angle, SparkMax.ControlType.kPosition);
    m_leftPIDController.setReference(angle, SparkMax.ControlType.kPosition);

    m_rightLead.set(rightSpeed);
    m_leftFollow.set(leftSpeed);

    SmartDashboard.putNumber("Right encoder position", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Left encoder position", m_leftEncoder.getPosition());
  }

}
