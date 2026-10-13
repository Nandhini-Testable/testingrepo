package seedbin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SeedBinTest {

    @Test
    public void netWeightAppliesTheAllowance() {
        assertEquals(180.0, SeedBin.netWeight(new double[] {100.0, 100.0}, 10.0), 0.0001);
    }

    @Test
    public void binsOverCountsOnlyHeavyBins() {
        assertEquals(2, SeedBin.binsOver(new double[] {40.0, 90.0, 120.0}, 80.0));
    }

    @Test
    public void gradeFollowsMoisture() {
        assertEquals("A", SeedBin.grade(5.0));
        assertEquals("B", SeedBin.grade(10.0));
        assertEquals("C", SeedBin.grade(15.0));
    }
}
