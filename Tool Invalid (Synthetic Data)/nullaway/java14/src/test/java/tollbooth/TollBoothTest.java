package tollbooth;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TollBoothTest {

    @Test
    public void recordedPlateIsPricedPerAxle() {
        TollBooth booth = new TollBooth();
        booth.record("KT-4471", 3);
        assertEquals(450, booth.toll("KT-4471"));
    }

    @Test
    public void receiptNamesPlateAndAxles() {
        TollBooth booth = new TollBooth();
        booth.record("KT-4471", 2);
        assertEquals("PLATE KT-4471 AXLES 2", booth.receipt("KT-4471"));
    }

    @Test
    public void lastPlateLengthFollowsTheLatestRecord() {
        TollBooth booth = new TollBooth();
        booth.record("AB-12", 2);
        assertEquals(5, booth.lastPlateLength());
    }
}
