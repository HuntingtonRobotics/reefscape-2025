package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.AlgaeArm;
import frc.robot.subsystems.AlgaeIntake;

public class AlgaeCommands {
    private AlgaeArm algaeArm;
    private AlgaeIntake algaeIntake;

    public AlgaeCommands(AlgaeArm algaeArm, AlgaeIntake algaeIntake) {
        this.algaeArm = algaeArm;
        this.algaeIntake = algaeIntake;
    }

    public Command getAlgae() {
        return Commands.sequence(
            algaeArm.extend(),
            algaeIntake.intake()
        );
    }

    public Command depositAlgae() {
        return Commands.sequence(
            algaeArm.retract(),
            algaeIntake.outtake()
        );
    }
}
