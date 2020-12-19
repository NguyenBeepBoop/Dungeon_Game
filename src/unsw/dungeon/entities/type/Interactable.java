package unsw.dungeon.entities.type;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;

/**
 * interface for all entity that could interact with player
 */
public interface Interactable {
    public Entity interaction(Player player, Dungeon dungeon);
}