package unsw.dungeon.entities.interaction;

import java.util.ArrayList;
import unsw.dungeon.entities.type.*;

import unsw.dungeon.Dungeon;

/**
 * Movement class is created for Player Boulder and Enemy
 * to prodict whether those entity could move to it next postion.
 */
public class Movement {
    //The variable to indicate whether entiy could move to such direction
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    // The current postion of entitiy
    private int currentX;
    private int currentY;
    private Movable entity;
    private Dungeon dungeon;

    /**
     * Pass the entity and current dungon to creat movemnt class.
     * @param currentX 
     * @param currentY
     * @param entity the entiy which hould the movement vriable
     * @param dungeon the current dungone.
     */
    public Movement(int currentX, int currentY, Movable entity, Dungeon dungeon) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.entity = entity;
        this.dungeon = dungeon;
        updateAround();
    }
    /**
     * function to update the around information.
     */
    private void updateAround() {
        up = checkMoveability(0, -1);
        down = checkMoveability(0, 1);
        right = checkMoveability(1, 0);
        left = checkMoveability(-1, 0);
    } 
    /**
     * Function to chech whether such direction is reachable.
     * @param xFix the next cooridation
     * @param yFix
     * @return true for reachable
     */
    private boolean checkMoveability(int xFix, int yFix) {
        ArrayList<Entity> entityList = new ArrayList<>();
        entityList =  dungeon.getEntities((currentX + xFix), (currentY + yFix));
        if ((currentX + xFix) > dungeon.getWidth() || (currentX + xFix) < 0 ||
            (currentY + yFix) > dungeon.getHeight() || (currentY + yFix) < 0) {
                return false;
        }
        return entity.checkListOverlap(entityList);
        
    }

    public boolean getUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean getDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean getLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean getRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    /**
     * Helper function to update the around information.
     * @param x the current  x position
     * @param y the current  y position
     */
    public void upDatePosition (int x , int y) {
        currentX = x;
        currentY = y;
        updateAround();
    }   
}