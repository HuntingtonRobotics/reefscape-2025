package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeArm extends SubsystemBase {
    private Solenoid armSolenoid; // Or DoubleSolenoid

    public AlgaeArm() {
        armSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    }

    public Command extend() {
        return Commands.none();
    }

    public Command retract() {
        return Commands.none();
    }
}
