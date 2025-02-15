package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralDoor extends SubsystemBase {
    private Solenoid doorSolenoid;

    public CoralDoor() {
        doorSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 2);
    }

    public Command toggleOpen() {
        return this.run(() -> doorSolenoid.toggle());
    }   
}

