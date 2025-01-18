// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;

/** Subsystem for the "climber" lifter-in-a-box component */
public class Claw extends SubsystemBase {
  private SparkMax m_right;
  private SparkMax m_left;

  public Claw() {
     
    m_right = new SparkMax(ClawConstants.kRightClaw, MotorType.kBrushless);
    m_left = new SparkMax(ClawConstants.kLeftClaw, MotorType.kBrushless);

    SparkMaxConfig leftConfig = new SparkMaxConfig();
    leftConfig.follow(m_right)
              .smartCurrentLimit(ClawConstants.CurrentLimit);

    SparkMaxConfig rightConfig = new SparkMaxConfig();
    rightConfig.smartCurrentLimit(ClawConstants.CurrentLimit);

    m_right.configure(rightConfig, null, null);
    m_left.configure(leftConfig, null, null);
  }
  
  public Command getClawUp() {
    return this.startEnd(
        () -> {
          m_right.set(1.0);
        },
        () -> {
          m_right.set(0);
        });
  }

  public Command getClawDown() {
    return this.startEnd(
        () -> {
          m_right.set(-1.0);
        },
        () -> {
          m_right.set(0);
        });
  }
}
