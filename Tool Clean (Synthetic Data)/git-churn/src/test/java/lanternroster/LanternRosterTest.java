package lanternroster;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LanternRosterTest {

    @Test
    public void dutyRotatesThroughTheKeepers() {
        LanternRoster roster = new LanternRoster();
        roster.enrol("Imre");
        roster.enrol("Sol");
        assertEquals("Imre", roster.onDuty(0));
        assertEquals("Sol", roster.onDuty(1));
        assertEquals("Imre", roster.onDuty(2));
    }

    @Test
    public void newRosterIsEmpty() {
        LanternRoster roster = new LanternRoster();
        assertEquals(true, roster.isEmpty());
        roster.enrol("Imre");
        assertEquals(false, roster.isEmpty());
    }
}
