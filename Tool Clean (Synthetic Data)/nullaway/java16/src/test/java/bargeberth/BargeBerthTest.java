package bargeberth;

import org.junit.Assert;
import org.junit.Test;

public class BargeBerthTest {

    @Test
    public void mooredBargeIsFound() {
        BargeBerth wharf = new BargeBerth("North");
        wharf.moor("B1", "Heron");
        Assert.assertEquals("Heron", wharf.occupant("B1"));
        Assert.assertEquals(5, wharf.occupantNameLength("B1"));
    }

    @Test
    public void emptyBerthHasNoOccupant() {
        BargeBerth wharf = new BargeBerth("North");
        Assert.assertNull(wharf.occupant("B2"));
        Assert.assertEquals(0, wharf.occupantNameLength("B2"));
        Assert.assertEquals("North/B2: empty", wharf.label("B2"));
    }

    @Test
    public void releaseFreesTheBerth() {
        BargeBerth wharf = new BargeBerth("North");
        wharf.moor("B1", "Heron");
        wharf.moor("B3", "Otter");
        Assert.assertEquals(2, wharf.occupiedCount());
        wharf.release("B1");
        Assert.assertEquals(1, wharf.occupiedCount());
        Assert.assertEquals("North/B3: Otter", wharf.label("B3"));
    }
}
