import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.Test;
//import edu.wpi.first.wpilibj.*;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkMax;

import frc.robot.subsystems.ElevatorSubsystem;

class ElevatorTests {
    // static {
    //     HLUsageReporting.SetImplementation(new HLUsageReporting.Null());
    // }
    private ElevatorSubsystem elevator;

    @BeforeEach
    void Setup() {
        assert HAL.initialize(500);
        elevator = new ElevatorSubsystem();
    }

    @Test
    void raise_success() {
        SparkMax m = mock(SparkMax.class);
        verify(m, times(1).)
    }
}
