package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralDoor extends SubsystemBase {
    private Solenoid doorSolenoid; // Or DoubleSolenoid

    public CoralDoor() {
        doorSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 5);
    }

    public Command toggleSolenoid() {
        return this.run(() -> doorSolenoid.toggle());
    }

   
}

