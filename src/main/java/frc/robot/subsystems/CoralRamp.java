package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralRamp extends SubsystemBase {
    private Solenoid rampSolenoid; // Or DoubleSolenoid

    public CoralRamp() {
        rampSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 7);
    }

    public Command toggleRaise() {
        return this.run(()->rampSolenoid.toggle());
    }

   /*  public Command lower() {
        return Commands.none();
    } 
    */
}