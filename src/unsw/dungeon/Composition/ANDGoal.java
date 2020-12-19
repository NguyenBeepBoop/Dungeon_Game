package unsw.dungeon.Composition;

import unsw.dungeon.*;

public class ANDGoal extends Goal {

    public ANDGoal(Dungeon dungeon) {
        super(dungeon);
    }
    
    @Override
    public String getString() {
        return "And";
    }

    @Override
    public boolean winCondition() {
        return super.getFritSubGoal().winCondition() && super.getSecondSubGoal().winCondition();
    }

    
    
    
}