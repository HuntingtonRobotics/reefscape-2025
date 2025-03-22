// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import frc.robot.subsystems.CoralDoor;
// import frc.robot.subsystems.ElevatorSubsystemWithEncoder;

// public class ElevatorPIDCommands {
//     private ElevatorSubsystemWithEncoder elevator;
//     private CoralDoor coralDoor;

//     public ElevatorPIDCommands(ElevatorSubsystemWithEncoder elevator, CoralDoor coralDoor) {
//         this.elevator = elevator;
//         this.coralDoor = coralDoor;
//     }

//     public Command scoreTop() {
//         return Commands.sequence(
//             elevator.setPosition(5),
//             coralDoor.toggleOpen(),
//             Commands.waitSeconds(0.75),
//             coralDoor.toggleOpen(),
//             elevator.setPosition(-5)
//         );
//     }
// }
