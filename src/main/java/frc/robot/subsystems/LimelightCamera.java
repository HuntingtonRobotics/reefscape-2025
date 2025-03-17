package frc.robot.subsystems;

import frc.robot.LimelightHelpers;
import frc.robot.util.CoralBranch;

public class LimelightCamera {
    // *** NOTE: In Limelight world, +Y is forward, +X is right ***
    //  this is different than FRC in which +X is forward and +Y is left
    private final double MaxSpeed;
    private final double MaxAngularRate;

    public LimelightCamera(double maxSpeed, double maxAngularRate) {
        MaxSpeed = maxSpeed;
        MaxAngularRate = maxAngularRate;
    }

    // Calculate proportional aim (rotation) speed
    public double aimProportional(CoralBranch coralBranch) {
        // Constant of proportionality
        double kP = 0.035;
        setPipeline(coralBranch);

        double targetingAngularVelocity = LimelightHelpers.getTX("limelight") * kP;
        targetingAngularVelocity *= MaxAngularRate / 2;
        targetingAngularVelocity *= -1.0; // -1.0 is for controller invert
        return targetingAngularVelocity;
    }

    // Calculate proportional range (drive) speed 
    public double rangeProportional(CoralBranch coralBranch) {
        // Constant of proportionality; control effective distance from tag
        double kP = 0.05;
        setPipeline(coralBranch);

        double targetingForwardSpeed = LimelightHelpers.getTY("limelight") * kP;
        targetingForwardSpeed *= MaxSpeed / 4;
        return targetingForwardSpeed;
    }

    private void setPipeline(CoralBranch coralBranch) {
        int pipelineIndex = coralBranch == CoralBranch.Right ? 1 : 0; // See Limelight config page
        if (LimelightHelpers.getCurrentPipelineIndex("limelight") != pipelineIndex) {
            LimelightHelpers.setPipelineIndex("limelight", pipelineIndex);
        }
    }
}
