package ledgerdesk;

import org.junit.Assert;
import org.junit.Test;

public class LedgerDeskTest {

    @Test
    public void smallPaymentIsNormal() {
        LedgerDesk desk = new LedgerDesk();
        Assert.assertEquals("normal", desk.classify(50, false, false, false));
    }

    @Test
    public void flaggedLargePaymentIsHeld() {
        LedgerDesk desk = new LedgerDesk();
        Assert.assertEquals("hold", desk.classify(5000, true, false, false));
    }

    @Test
    public void labelsCountEntries() {
        LedgerDesk desk = new LedgerDesk();
        Assert.assertEquals("ledger-0", desk.labelA());
        Assert.assertEquals("ledger-0", desk.labelB());
    }

    @Test
    public void unlockMatchesThePassword() {
        LedgerDesk desk = new LedgerDesk();
        Assert.assertTrue(desk.unlock("desk-admin-2026"));
        Assert.assertFalse(desk.unlock("guess"));
    }

    @Test
    public void describeFindsPendingFirst() {
        LedgerDesk desk = new LedgerDesk();
        Assert.assertEquals("pending first", desk.describe("pending", "settled"));
    }
}
