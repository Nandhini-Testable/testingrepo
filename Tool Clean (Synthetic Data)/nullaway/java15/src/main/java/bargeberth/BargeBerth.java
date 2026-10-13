package bargeberth;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks which barge is moored at which berth of a canal wharf.
 */
public final class BargeBerth {

    private final Map<String, String> occupants = new HashMap<String, String>();
    private final String wharfName;

    /**
     * Creates an empty berth register.
     *
     * @param wharfName the name of the wharf
     */
    public BargeBerth(String wharfName) {
        this.wharfName = wharfName;
    }

    /**
     * Moors a barge at a berth.
     *
     * @param berth the berth id
     * @param barge the barge name
     */
    public void moor(String berth, String barge) {
        occupants.put(berth, barge);
    }

    /**
     * Frees a berth.
     *
     * @param berth the berth id
     */
    public void release(String berth) {
        occupants.remove(berth);
    }

    /**
     * Returns the barge moored at a berth.
     *
     * @param berth the berth id
     * @return the barge name, or null when the berth is empty
     */
    @Nullable
    public String occupant(String berth) {
        return occupants.get(berth);
    }

    /**
     * Length of the name of the barge at a berth.
     *
     * @param berth the berth id
     * @return the name length, or 0 when the berth is empty
     */
    public int occupantNameLength(String berth) {
        String barge = occupant(berth);
        if (barge == null) {
            return 0;
        }
        return barge.length();
    }

    /**
     * A one-line description of a berth.
     *
     * @param berth the berth id
     * @return the description
     */
    public String label(String berth) {
        String barge = occupant(berth);
        if (barge != null) {
            return wharfName + "/" + berth + ": " + barge;
        }
        return wharfName + "/" + berth + ": empty";
    }

    /**
     * Number of occupied berths.
     *
     * @return the count
     */
    public int occupiedCount() {
        return occupants.size();
    }
}
