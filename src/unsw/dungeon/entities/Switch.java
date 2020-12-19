package unsw.dungeon.entities;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;

public class Switch extends Entity {


    public Switch(int x, int y) {
        super(x, y);
    }


    public boolean checkBoulder(Dungeon dungeon) {
        ArrayList<Entity> list = dungeon.getEntities(this.getX(), this.getY());
        for (Entity i : list) {
            if (i instanceof Boulder) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        return true;
    }

    @Override
    public EntityType type() {
        return EntityType.SWITCH;
    }
    
}