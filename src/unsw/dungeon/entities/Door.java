package unsw.dungeon.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;


public class Door extends Entity implements Interactable {

   

    private BooleanProperty status = new SimpleBooleanProperty(false);
    private int id;

    public Door(int x, int y, int id) {
        super(x, y);
        this.id = id;

    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        // if the door is opened it
        if (status.get()) {
            return true;
        }
        // if the player try to open the door.
        if (entity instanceof Player ) {
            Player player = (Player) entity;
            Entity k = player.getItem(EntityType.KEY);
            if (k != null) {
                Key key = (Key) k;
                if (key.getID() == id) {
                    return true;
                }
            }
        }
        return false;
        
    }

    @Override
    public EntityType type() {
        return EntityType.DOOR;
    }

    public void setStatus(Boolean status) {
        this.status.set(status);
    }

    public String getStatus() {
        return status.getName();
    }

    public BooleanProperty status() {
        return status;
    }
    /**
     * IF the player is overlaped with door.
     */
    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        if (!status.get()) {
            Entity k = player.getItem(EntityType.KEY);
            if (k != null) {
                Key key = (Key) k;
                if (key.getID() == id) {
                    setStatus(true);
                    player.removeItem((Item)key);
                }
            }    
        }
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}