import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.Test;
//import edu.wpi.first.wpilibj.*;

class ElevatorTests {
    // static {
    //     HLUsageReporting.SetImplementation(new HLUsageReporting.Null());
    // }

    @Test
    void raise_success() {
        assertEquals(2, getValue());
    }

    int getValue() {
        return 2;
    }
}
