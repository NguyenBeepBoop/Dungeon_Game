package unsw.dungeon.Composition;

import unsw.dungeon.Dungeon;

public class TrigerSwitch extends Goal {

    public TrigerSwitch(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String getString() {
        return "Trigger all floor switch with boulders.";
    }

    @Override
    public boolean winCondition() {
        return super.dungeon.checkBoulder();
    }    
}