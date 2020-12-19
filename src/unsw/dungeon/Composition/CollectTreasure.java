package unsw.dungeon.Composition;

import unsw.dungeon.Dungeon;

public class CollectTreasure extends Goal {

    public CollectTreasure(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String getString() {
        return "Collect all Treasure.";
    }

    @Override
    public boolean winCondition() {
        return super.dungeon.checkTreasure();
    }

    
    
    
}