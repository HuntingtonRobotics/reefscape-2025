package frc.robot.commands;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CoralDoor;
import frc.robot.subsystems.ElevatorSubsystem;

public class Countdrive {
    private SparkMax motor1;
    private SparkMax motor2;
    private SparkMax motor3;
    private SparkMax motor4;
    

    public Countdrive (){
        motor1 = new SparkMax(12, MotorType.kBrushless);
        motor2 = new SparkMax(14, MotorType.kBrushless);
        motor3 = new SparkMax(13, MotorType.kBrushless);
        motor4 = new SparkMax(15, MotorType.kBrushless);
        //left is on top right on the bottom
        //3&4 are the back motors
        double ticks = 0;
        //find a way to count ticks of a motor
    }
    

    public Command moveforward (){
        double tick = 0.0;
        while (tick != 10){
            return this.startEnd(() -> motor1.set(1) () -> motorq.set(0));
            tick+=.01;
        }
    }
}