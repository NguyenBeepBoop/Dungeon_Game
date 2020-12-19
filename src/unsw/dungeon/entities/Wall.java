package unsw.dungeon.entities;


import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Observer_Subject.ObserverWall;
import unsw.dungeon.entities.Observer_Subject.SubjectGhost;
import unsw.dungeon.entities.type.*;

public class Wall extends Entity implements ObserverWall {

    private Boolean ghost = false;
    private ArrayList<SubjectGhost> subjects;

    public Wall(int x, int y, Dungeon dungeon) {
        super(x, y);
        subjects = dungeon.getAllGhost();
        for (SubjectGhost i : subjects) {
            i.attach(this);
        }
    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if (entity instanceof Player) {
            return ghost;
        }
        return false;
    }

    @Override
    public EntityType type() {
        return EntityType.WALL;
    }

    @Override
    public void update(boolean i) {
        ghost = i;
    }

    
    
}
