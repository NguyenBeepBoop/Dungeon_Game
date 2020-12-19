package unsw.dungeon.entities;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Observer_Subject.*;
import unsw.dungeon.entities.interaction.Movement;
import unsw.dungeon.entities.type.*;

public class Boulder extends Entity implements Movable, ObserverForDungeon, Observer, Interactable {

    private Movement movement;
    private int xNext, yNext;
    private Dungeon dungeon;

    public Boulder(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.dungeon = dungeon;
        movement = new Movement(x, y, this, dungeon);
        if (dungeon.getPlayer() != null) {
            dungeon.getPlayer().attach(this);
        }
    }
    /**
     * function to chech if the player try to overlap with blouders, 
     * which postion it will  be pushed to.
     */
    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        if (this.getX() == x) {
            if (this.getY() == (y - 1) && movement.getUp() && (y - 2) >= 0) {
                xNext = x;
                yNext = y - 2;
                return true;
            } else if (this.getY() == (y + 1) && movement.getDown() && (y + 2) < dungeon.getHeight()) {
                xNext = x;
                yNext = y + 2;
                return true;
            }
            return false;
        } else if (this.getY() == y) {
            if (this.getX() == (x - 1) && movement.getLeft() && (x - 2) >= 0) {
                xNext = x - 2;
                yNext = y;
                return true;
            } else if (this.getX() == (x + 1) && movement.getRight() && (x + 2) < dungeon.getWidth()) {
                xNext = x + 2;
                yNext = y;
                return true;
            }
            return false;
        }

        return false;
    }

    @Override
    public boolean checkListOverlap(ArrayList<Entity> entityList) {
        for (Entity i : entityList) {
            if (!i.overlaping(this.getX(), this.getY(), this)) {
                return false;
            }
        }
        return true;
    }

    public int getxNext() {
        return xNext;
    }

    public void setxNext(int xNext) {
        this.xNext = xNext;
    }

    public int getyNext() {
        return yNext;
    }

    public void setyNext(int yNext) {
        this.yNext = yNext;
    }

    public Movement getMovement() {
        return movement;
    }

    @Override
    public void update() {
        movement.upDatePosition(this.getX(), this.getY());

    }

    @Override
    public void update(Subject obj) {
        if (obj instanceof Entity) {
            Entity i = (Entity) obj;
            if (i.getX() != this.getX() || i.getY() != this.getY()) {
                movement.upDatePosition(this.getX(), this.getY());
            }
        }
    }
    /**
     * function to push the boulders
     */
    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        this.setX(xNext);
        this.setY(yNext);
        movement.upDatePosition(this.getX(), this.getY());
        player.UpdateAround();
        return null;
    }

    @Override
    public EntityType type() {
        return EntityType.BOULDER;
    } 

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}