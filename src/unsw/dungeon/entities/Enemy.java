package unsw.dungeon.entities;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import unsw.dungeon.entities.Observer_Subject.*;
import unsw.dungeon.entities.interaction.Movement;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;

public class Enemy extends Entity implements Movable, Observer, ObserverForDungeon, Interactable{

    private Dungeon dungeon;
    private Movement movement;
    private Subject player;
    private boolean alive = true;

    public Enemy(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.dungeon = dungeon;
        movement = new Movement(x, y, this, dungeon);
        player = dungeon.getPlayer();
        if (player != null) {
            player.attach(this);
        }
    }
    /**
     * Function to move toward player
     * @param x postion of player
     * @param y positon of player
     */
    private void moverTowardPlayer(int x, int y) {
        if (this.getX() == x && this.getY() == y) {
            return;
        }
        Boolean check = false;
        if (this.getX() > x) {
            check = this.moveLeft();
        } else if (this.getX() < x) {
            check = this.moveRight();
        }
        if (check) {
            return;
        }
        if (this.getY() > y) {
            this.moveUp();
        } else if(this.getY() < y) {
            this.moveDown();
        }
    }
     /**
     * Function to move way from player
     * @param x postion of player
     * @param y positon of player
     */
    private void moverAwayPlayer(int x, int y) {
        if (this.getX() == x && this.getY() == y) {
            return;
        }
        Boolean check = false;
        if (this.getX() > x) {
            check = this.moveRight();
        } else if (this.getX() < x) {
            check = this.moveLeft();
        }
        if (check) {
            return;
        }
        if (this.getY() > y) {
            this.moveDown();
        } else if(this.getY() < y) {
            this.moveUp();
        }
    }

    private boolean moveUp() {
        if (movement.getUp() && (this.getY() - 1) >= 0) {
            this.setY(this.getY() - 1);
            movement.upDatePosition(this.getX(), this.getY());
            return true;
        }
        return false;
    }

    private boolean moveDown() {
        if (movement.getDown() && (this.getY() + 1) <= dungeon.getHeight()) {
            this.setY(this.getY() + 1);
            movement.upDatePosition(this.getX(), this.getY());
            return true;
        }
        return false;
    }

    private boolean moveLeft() {
        if (movement.getLeft() && (this.getX() - 1) >= 0) {
            this.setX(this.getX() - 1);
            movement.upDatePosition(this.getX(), this.getY());
            return true;
        }
        return false;
    }

    private boolean moveRight() {
        if (movement.getRight() && (this.getX() + 1) <= dungeon.getWidth()) {
            this.setX(this.getX() + 1);
            movement.upDatePosition(this.getX(), this.getY());
            return true;
        }
        return false;
    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkListOverlap(ArrayList<Entity> entityList) {
        for (Entity i : entityList) {
            if (!i.overlaping(this.getX(), this.getY(), this) || i instanceof Enemy 
            || i instanceof Boulder) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(Subject obj) {
       movement.upDatePosition(this.getX(), this.getY());
    }
    /**
     * Function to trigger the enemy to move.
     * @return
     */
    public Entity moving() {
        Player i = (Player) player;
        if (i.getItems(EntityType.INVINCIBILITY).size() > 0
                && ((Usable) i.getItem(EntityType.INVINCIBILITY)).getUses() > 0) {
            moverAwayPlayer(i.getX(), i.getY());
        } else {
            moverTowardPlayer(i.getX(), i.getY());
        }
        Entity remove = i.checkAlive();
        return remove;

    }

    @Override
    public void update() {
        movement.upDatePosition(this.getX(), this.getY());
    }

    @Override
    public EntityType type() {
        return EntityType.ENEMY;
    }

    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        ObservableList<Item> inv = player.getInventory();
        for (Item i : inv) {
            if (i.type() == EntityType.INVINCIBILITY && (((Usable) i).getUses() > 0)) {
                ((Usable) i).use();
                kill();
                return this;
            }
        }
        for (Item i : inv) {
            if (i.type() == EntityType.SWORD && (((Usable) i).getUses() > 0)) {
                ((Usable) i).use();
                kill();
                return this;
            }
        }
        player.death();
        return null;

    }
    
    public void kill() {
        this.alive = false;
    }  

    public boolean getAlive() {
        return alive;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
