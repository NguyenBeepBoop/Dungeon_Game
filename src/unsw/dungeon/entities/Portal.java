package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;
import java.util.ArrayList;


public class Portal extends Entity implements Interactable {

    private int id;

    public Portal(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }
    
    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if ((entity instanceof Player)) {
            return true;
        }
        return false;
    }

    @Override
    public EntityType type() {
        return EntityType.PORTAL;
    }

    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        Portal p = (Portal) dungeon.getPortal(id, this.getX(), this.getY());
        if (p != null) {
            ArrayList<Integer> coords = checkTele(p.getX(), p.getY(), dungeon);
            player.setX(coords.get(0));
            player.setY(coords.get(1));
            player.UpdateAround();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public ArrayList<Integer> checkTele(int x, int y, Dungeon dungeon) {
        ArrayList<Integer> result = new ArrayList<>();
        if ((dungeon.getEntities(x + 1, y).isEmpty())) {
            result.add(x+1);
            result.add(y);
            return result;
        } else if ((dungeon.getEntities(x - 1, y).isEmpty())) {
            result.add(x - 1);
            result.add(y);
            return result;
        } else if ((dungeon.getEntities(x, y + 1).isEmpty())) {
            result.add(x);
            result.add(y + 1);
            return result;
        } else if ((dungeon.getEntities(x, y - 1).isEmpty())) {
            result.add(x);
            result.add(y - 1);
            return result;
        }
        return result;
    }
}