package unsw.dungeon.Composition;

import unsw.dungeon.Dungeon;

public class ORgoal extends Goal {

    public ORgoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String getString() {
        return "OR";
    }

    @Override
    public boolean winCondition() {
        return super.getFritSubGoal().winCondition() || super.getSecondSubGoal().winCondition();
    }
    
    
}