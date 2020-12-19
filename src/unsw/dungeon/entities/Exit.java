package unsw.dungeon.entities;

import unsw.dungeon.entities.type.*;

public class Exit extends Entity {

    public Exit(int x, int y) {
        super(x, y);
    }
    
    @Override
    public boolean overlaping(int x, int y, Entity entity){
        if (entity instanceof Player) {
            return true;
        }
        return false;
    }

    @Override
    public EntityType type() {
        return EntityType.EXIT;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}