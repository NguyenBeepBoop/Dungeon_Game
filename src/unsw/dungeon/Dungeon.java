/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import unsw.dungeon.Composition.*;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.*;
import unsw.dungeon.entities.Observer_Subject.*;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements SubjectDungeon, Observer {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private Goal goal;
    private boolean win = false;

    private ArrayList<ObserverForDungeon> observerList;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.observerList = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setPlayer(Player player) {
        this.player = player;
        ObserverForDungeon p = (ObserverForDungeon) player;
        Subject ply = (Subject) player;
        ply.attach(this);
        attach(p);
    }

    public void addEntity(Entity entity) {
        if (entity instanceof ObserverForDungeon) {
            ObserverForDungeon e = (ObserverForDungeon) entity;
            attach(e);
        }
        entities.add(entity);
        notifyObservers();
    }

    public ArrayList<Entity> getEntities() {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity i : entities) {
            if (!(i instanceof Wall))
                result.add(i);
        }
        return result;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        entity.deletEntity();
        notifyObservers();
    }

    public ArrayList<Entity> getEntities(int x, int y) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity i : entities) {
            if (i.getX() == x && i.getY() == y) {
                result.add(i);
            }
        }
        return result;
    }

    public boolean checkEnemies() {
        for (Entity i : entities) {
            if (i instanceof Enemy) {
                return false;
            }
        }
        return true;
    }

    public boolean checkTreasure() {
        for (Entity i : entities) {
            if (i instanceof Treasure) {
                return false;
            }
        }
        return true;
    }

    public boolean checkBoulder() {
        for (Entity i : entities) {
            if (i instanceof Switch) {
                Switch s = (Switch) i;
                if (!s.checkBoulder(this))
                    return false; 
            }
        }
        return true;
    }

    public boolean checkExit() {
        ArrayList<Entity> list = getEntities(player.getX(), player.getY());
        for (Entity i : list) {
            if (i instanceof Exit) {
                return true;
            }
        }
        return false;

    }

    public boolean checkGome() {
        Item s =  player.getItem(EntityType.SWORD);
        if (s != null) {
            Sword sword = (Sword) s;
            if (sword.getUses() > 5) {
                return true;
            }
        }
        return false;
    }

    public Entity getPortal(int id, int x, int y) {
        for (Entity i : entities) {
            if (i instanceof Portal) {
                Portal p = (Portal) i;  
                if (p.getId() == id && p.getX() != x && p.getY() != y) {
                    return p;
                }
            }
        }
        return null;
    }

    public ArrayList<Observer> getAllObservers() {
        ArrayList<Observer> result = new ArrayList<>();
        for (Entity i : entities) {
            if (i instanceof Boulder || i instanceof Enemy) {
                Observer k = (Observer) i;
                result.add(k);
            }
        }
        return result;
    }

    //return all wall
    public ArrayList<ObserverWall> getAllWall() {
        ArrayList<ObserverWall> result = new ArrayList<>();
        for (Entity i : entities) {
            if (i instanceof Wall) {
                ObserverWall k = (ObserverWall) i;
                result.add(k);
            }
        }
        return result;
    }

    //return all ghost potion
    public ArrayList<SubjectGhost> getAllGhost() {
        ArrayList<SubjectGhost> result = new ArrayList<>();
        for (Entity i : entities) {
            if (i instanceof Extention_ghostPotion) {
                SubjectGhost k = (SubjectGhost) i;
                result.add(k);
            }
        }
        return result;
    }

    @Override
    public void attach(ObserverForDungeon o) {
        if (!observerList.contains(o)) {
            observerList.add(o);
        }

    }

    @Override
    public void detach(ObserverForDungeon o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (ObserverForDungeon i : observerList) {
            i.update();
        }
    }

    @Override
    public void update(Subject obj) {
        Entity remove = null;
        for (Entity i : entities) {
            if (i instanceof Interactable && i.SamePosition(player)) {
                Interactable e = (Interactable) i;
                Entity temp= e.interaction(player, this);
                if (temp != null) {
                    remove = temp;
                }             
            }
        }
        if (remove != null) {
            removeEntity(remove);
        }
        if (goal.winCondition()) {
            win = true;
        }
        
    }

    /**
     * Function to make Enemy move.
     */
    public void EnemyMove() {
        Entity remove = null;
        for (Entity i : entities){
            if (i instanceof Enemy) {
                Enemy e = (Enemy) i;
                e.update();
                Entity temp = e.moving();
                if (temp != null) {
                    remove = temp;
                }
            }
        }
        if (remove != null) {
            removeEntity(remove);
        }
        player.UpdateAround();
        for (Entity i : entities) {
            if (i instanceof Boulder) {
                Boulder e = (Boulder) i;
                e.update();
            }

        }

    }


    public List<Entity> getEntities(EntityType type) {
        return entities.stream().filter(entity -> entity.type() == type).collect(Collectors.toList());
    }

    public boolean isWin() {
        return this.win;
    }


}
