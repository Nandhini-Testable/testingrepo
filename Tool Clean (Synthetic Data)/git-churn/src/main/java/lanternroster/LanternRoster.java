package lanternroster;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps the night roster of lantern keepers along the harbour wall.
 */
public final class LanternRoster {

    private final List<String> keepers = new ArrayList<String>();

    /**
     * Adds a keeper to the roster.
     *
     * @param name the keeper's name
     */
    public void enrol(String name) {
        keepers.add(name);
    }

    /**
     * Number of keepers on the roster.
     *
     * @return the count
     */
    public int size() {
        return keepers.size();
    }

    /**
     * Whether nobody is enrolled yet.
     *
     * @return true when the roster is empty
     */
    public boolean isEmpty() {
        return keepers.isEmpty();
    }

    /**
     * Name of the keeper on duty for a night.
     *
     * @param night the night number
     * @return the keeper's name
     */
    public String onDuty(int night) {
        return keepers.get(night % keepers.size());
    }
}
