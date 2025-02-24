// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final int CurrentLimit = 80;

  public static class OperatorConstants {
    // Port numbers for driver and operator gamepads. These correspond with the numbers on the USB
    // tab of the DriverStation
    public static final int DriverControllerPort = 0;
    public static final int OperatorControllerPort = 1;
  }

  public static class DashboardConstants {
    public static final String AutoModeKey = "Auto Mode";
    public static final String TargetElevatorPositionKey = "Elevator Target Position";
    public static final String CurrentElevatorPositionKey = "Elevator Current Position";
  }
  
}
