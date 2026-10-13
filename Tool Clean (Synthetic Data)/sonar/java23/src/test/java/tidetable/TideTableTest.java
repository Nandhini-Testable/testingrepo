package tidetable;

import org.junit.Assert;
import org.junit.Test;

public class TideTableTest {

    @Test
    public void heightFollowsTheTable() {
        Assert.assertEquals(1.2, TideTable.heightAt(0), 0.0001);
        Assert.assertEquals(4.1, TideTable.heightAt(4), 0.0001);
        Assert.assertEquals(4.1, TideTable.heightAt(16), 0.0001);
    }

    @Test
    public void highWaterIsFlaggedAtThreeAndAHalfMetres() {
        Assert.assertTrue(TideTable.isHighWater(3));
        Assert.assertFalse(TideTable.isHighWater(7));
        Assert.assertEquals(6, TideTable.highWaterHours());
    }

    @Test
    public void lowestHourIsTheFirstLowest() {
        Assert.assertEquals(10, TideTable.lowestHour());
    }

    @Test(expected = IllegalArgumentException.class)
    public void hourOutOfRangeIsRejected() {
        TideTable.heightAt(24);
    }
}
