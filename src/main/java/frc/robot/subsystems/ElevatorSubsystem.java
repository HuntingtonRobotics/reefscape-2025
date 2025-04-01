package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DashboardConstants;

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax rightMotor;
    private SparkMaxConfig rightMotorConfig;
    private RelativeEncoder rightMotorEncoder;
    private DigitalInput sensor;
    @SuppressWarnings("unused")
    private SparkMax leftMotor;
    

    public static final double GearRadiusMeters = 0.047;
    public static final double GearCircumferenceMeters = (GearRadiusMeters * 2) * Math.PI;

    public ElevatorSubsystem() {
        // Motor configuration (brake mode, follow, etc) are configured using Rev Hardware Client
        sensor = new DigitalInput(4);
        
        rightMotor = new SparkMax(30, MotorType.kBrushless);
        rightMotorEncoder = rightMotor.getEncoder();
        rightMotorConfig = new SparkMaxConfig();
        sensor = new DigitalInput(4);
        leftMotor = new SparkMax(31, MotorType.kBrushless); // set to 'follow' in the SparkMax

        rightMotorConfig.encoder.positionConversionFactor(0.028); // 1/36th
        rightMotor.configure(rightMotorConfig, null, null);
    }

    public Command raiseToHeightAlt(double targetHeightMeters) {
        return this.runOnce(
                () -> {
                    double targetRotations = targetHeightMeters / GearCircumferenceMeters;
                    rightMotor.set(-0.25);
                    while (rightMotorEncoder.getPosition() <= targetRotations) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            // nothing
                        }
                    }
                    rightMotor.set(0);
                });
    }

    public Command raiseToHeight(double targetHeightMeters) {
        double targetRotations = targetHeightMeters / GearCircumferenceMeters;
        return Commands.race(
            raise(),
            Commands.runOnce(() -> {
                while (true) {
                    double currentPositionRotations = rightMotorEncoder.getPosition();
                    if (currentPositionRotations >= targetRotations) {
                        break;
                    }
                }
            }),
            Commands.print(String.format("targetRotations=[{0}] encoderPosition=[{1}] rotations", targetRotations, rightMotorEncoder.getPosition())),
            Commands.run(() -> SmartDashboard.putNumber(DashboardConstants.CurrentElevatorPositionKey, rightMotorEncoder.getPosition())));
    }

    public Command lowerToHeight(double targetHeightMeters) {
        double targetRotations = targetHeightMeters / GearCircumferenceMeters;
        return Commands.race(raise(), Commands.runOnce(() -> {
            while (true) {
                double currentPositionRotations = rightMotorEncoder.getPosition();
                if (currentPositionRotations <= targetRotations) {
                    break;
                }
            }
        }),
        Commands.print(String.format("targetRotations=[{0}] encoderPosition=[{1}] rotations", targetRotations, rightMotorEncoder.getPosition())));
    }

    public Command raise() {
        
        return this.startEnd(
                () -> rightMotor.set(-0.85), // reverse direction for 'raise'
                () -> rightMotor.set(0));
                

    }

    public Command raiseAuto(){
        return this.startEnd(
                () -> rightMotor.set(-0.7), // reverse direction for 'raise'
                () -> rightMotor.set(0));
    }

    public Command lower() {
        
       /*  return this.run(() -> {
            if(sensor.get()){
                rightMotor.set(0);
            }
            else{
                rightMotor.set(0.7);
                }
        
        
        }); */
        
        return this.startEnd(
            () -> {
                while (sensor.get()) {
                    rightMotor.set(0.5);
                }

                rightMotor.set(0.0);
            },
            () -> rightMotor.set(0));
    }

    

    public Command stop() {
        System.out.print(sensor);
        return this.run(() -> rightMotor.set(0));
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

    /*public Command Accelerate ( double Currentpower, double timetomaxspeed , double currenttime){
        return this.runOnce(() -> {{double direction = 1;
            double timepassed = 0.0;
            double totalPower = 0; 
            double count = 0;
            double outputpower = 0;
            double tp = timepassed;
            while (timepassed < timetomaxspeed){
                outputpower = Math.pow(timepassed/timetomaxspeed,2);
                timepassed ++;
                totalPower+= outputpower;
                System.out.println("outputpower " + outputpower * direction);
                
            }
    
            double totalaccelpower = totalPower;
            while(totalPower + 1.6 < (Currentpower * currenttime) - totalaccelpower){
                if (count <= 16){
                    outputpower = 0.99;
                    timepassed ++;
                    totalPower += outputpower;
                }
                else{
                    outputpower = 1;
                    timepassed ++;
                    totalPower += outputpower;
    
                }
    
                count += 0.1;
                System.out.println("outputpower " + outputpower * direction);
            }
            while(outputpower> 0){
                outputpower = 1 - Math.pow((timepassed - tp)/timetomaxspeed,0.5);
                timepassed++;
                totalPower += outputpower;
                System.out.println("outputpower " + outputpower * direction);
            }
            
            System.out.println("the average power used was " + totalPower / timepassed + " over " + timepassed);
            System.out.println("total power used was " + totalPower);
    
        }
        
        
  
    
        });
       

}*/
    
}
