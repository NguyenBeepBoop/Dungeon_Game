package unsw.dungeon.Composition;

import unsw.dungeon.Dungeon;

public class ExtentionGoal extends Goal {

    public ExtentionGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String getString() {
        return "Find Gome and upgrade your Sword!(Cost: 1 Tearsure)";
    }

    @Override
    public boolean winCondition() {
        return super.dungeon.checkGome();
    }
    
    
}