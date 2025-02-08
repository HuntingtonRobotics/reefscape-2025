package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralRamp extends SubsystemBase {
    private Solenoid rampSolenoid; // Or DoubleSolenoid

    public CoralRamp() {
        rampSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    }

    public Command raise() {
        return Commands.none();
    }

    public Command lower() {
        return Commands.none();
    }
}
