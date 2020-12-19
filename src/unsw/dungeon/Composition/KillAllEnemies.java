package unsw.dungeon.Composition;

import unsw.dungeon.Dungeon;

public class KillAllEnemies extends Goal {

    public KillAllEnemies(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String getString() {
        return "Kill all enemeies.";
    }

    @Override
    public boolean winCondition() {
        return super.dungeon.checkEnemies();
    }
     
    
}