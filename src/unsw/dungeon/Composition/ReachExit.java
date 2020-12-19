package unsw.dungeon.Composition;

import unsw.dungeon.Dungeon;

public class ReachExit extends Goal {

    public ReachExit(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String getString() {
        return "Reach exit(finish other goal first).";
    }

    @Override
    public boolean winCondition() {
        return super.dungeon.checkExit();
    }

    
    
    
}