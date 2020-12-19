package unsw.dungeon.entities.type;

import java.util.ArrayList;
/**
 * For all entity that is moveable in this dunguon, it shoud has the method to check
 * whether given list of entity could overlap with itself.
 */
public interface Movable {
    public boolean checkListOverlap(ArrayList<Entity> entityList);
}