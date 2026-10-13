package hopperbatch;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HopperBatchTest {

    @Test
    public void blendIsPositive() {
        assertTrue(HopperBatch.blend(new double[] {1.0, 2.0, 3.0}) > 0.0);
    }
}
