package unsw.dungeon.Composition;

import unsw.dungeon.Dungeon;
import java.util.ArrayList;

/**
 * The Goal class is the parent of all kind of goal avaibale in this dungeon.
 */
public abstract class Goal {

    public Dungeon dungeon;
    private ArrayList<Goal> subGoal;

    public Goal (Dungeon dungeon) {
        this.dungeon = dungeon;
        subGoal = new ArrayList<Goal>();
    }

    public void addSubGoal(Goal c) {
        subGoal.add(c);

    }
    
    public void removeSubGoal(Goal c) {
        subGoal.remove(c);
    }

    public abstract String getString(); 

    public Goal getFritSubGoal() {
        return subGoal.get(0);
    }

    public Goal getSecondSubGoal() {
        return subGoal.get(1);
    }

    public abstract boolean winCondition();

    public int getSubSize() {
        return subGoal.size();
    }
}