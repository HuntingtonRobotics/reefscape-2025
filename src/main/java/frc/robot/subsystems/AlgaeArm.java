package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeArm extends SubsystemBase {

    private Solenoid solenoid;

    public AlgaeArm() {
        solenoid = new Solenoid(50, PneumaticsModuleType.REVPH, 13);
    }

    public Command toggleExtend() {
        // Starts "off" or retracted
        return this.runOnce(() -> solenoid.toggle());
    }
}
