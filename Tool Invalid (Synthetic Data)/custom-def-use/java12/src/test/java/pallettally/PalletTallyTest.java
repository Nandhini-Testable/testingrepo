package pallettally;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PalletTallyTest {

    @Test
    public void tallySumsTheLoads() {
        assertEquals(12, PalletTally.tally(new int[] {5, 3, 4}));
    }

    @Test
    public void surchargeIsPerPallet() {
        assertEquals(15, PalletTally.surcharge(5));
        assertEquals(64, PalletTally.surcharge(21));
    }
}
