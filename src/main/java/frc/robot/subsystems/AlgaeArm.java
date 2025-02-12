package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeArm extends SubsystemBase {

    private Solenoid LeftSolenoid;
    private Solenoid RightSolenoid;

    public AlgaeArm() {

        LeftSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 1);
        RightSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 2);

    }

    public Command extend() {

        return this.run(() -> {
            RightSolenoid.set(true);
            LeftSolenoid.set(true);
        });
    }

    public Command retract() {

        return this.run(() -> {
            RightSolenoid.set(false);
            LeftSolenoid.set(false);
        });
    }
}
