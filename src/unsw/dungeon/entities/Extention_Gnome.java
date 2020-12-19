package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.Entity;
import unsw.dungeon.entities.type.EntityType;
import unsw.dungeon.entities.type.Interactable;
import unsw.dungeon.entities.type.Item;
/**
 * The Extention entity for this dungon, if the player hold a treasure
 * and overlap with this entity, the usage of player's current sword will become to 99.
 */
public class Extention_Gnome extends Entity implements Interactable {


    public Extention_Gnome(int x, int y) {
        super(x, y);
    }
    
    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if ((entity instanceof Player) || (entity instanceof Enemy)) {
            return true;
        }
        return false;
    }

    @Override
    public EntityType type() {
        return EntityType.EXTENTION_GNOME;
    }

	@Override
	public Entity interaction(Player player, Dungeon dungeon) {
        if (player.getItems(EntityType.SWORD).size() > 0 && player.getItems(EntityType.TREASURE).size() > 0) {
            Item i = player.getItem(EntityType.TREASURE);
            Treasure e = (Treasure) i;
            if (e.getUses() >= 1) {
                player.getItem(EntityType.SWORD).setUses(99);
                e.reduce();
                return this;
            }
        }
		return null;
	}
}