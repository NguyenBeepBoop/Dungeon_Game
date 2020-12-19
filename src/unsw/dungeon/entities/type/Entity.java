package unsw.dungeon.entities.type;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;

    private BooleanProperty exit = new SimpleBooleanProperty();

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        exit.set(true);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public BooleanProperty exit() {
        return exit;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public boolean SamePosition(Entity entity) {
        if (entity.getX() == this.getX() && entity.getY() == this.getY()) {
            return true;
        }
        return false;
    }

    public abstract boolean overlaping(int x, int y, Entity entity);

    public abstract EntityType type();

    public void deletEntity() {
        exit.set(false);
    }
}
