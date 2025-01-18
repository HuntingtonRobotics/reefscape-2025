package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LauncherArmConstants;
import frc.robot.Constants.LauncherConstants;

public class LauncherArm extends SubsystemBase {

  private SparkMax m_left;
  private SparkMax m_right;

  //where sC stands for speed constant
  private double sC = 1.0;

  public LauncherArm() {

    m_left = new SparkMax(LauncherArmConstants.kLeftLifterID, MotorType.kBrushless);
    m_right = new SparkMax(LauncherArmConstants.kRightLifterID, MotorType.kBrushless);

    SparkMaxConfig leftMotorConfig = new SparkMaxConfig();
    leftMotorConfig
      .follow(m_right)
      .smartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);

    SparkMaxConfig rightMotorConfig = new SparkMaxConfig();
    rightMotorConfig
      .inverted(false)
      .smartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);

    m_left.configure(leftMotorConfig, null, null);
    m_right.configure(rightMotorConfig, null, null);
  }

  public Command getLauncherUpCommand() {
    return this.startEnd(
        () -> {
          m_right.set(sC);
        },
        () -> {
          m_right.set(0);
        });
  }

  public Command getLauncherDownCommand() {
    return this.startEnd(
        () -> {
          m_right.set(-sC);
        },
        () -> {
          m_right.set(0);
        });
  }
}
