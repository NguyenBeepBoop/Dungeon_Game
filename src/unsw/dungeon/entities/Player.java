package unsw.dungeon.entities;

import unsw.dungeon.entities.Observer_Subject.Observer;
import unsw.dungeon.entities.Observer_Subject.ObserverForDungeon;
import unsw.dungeon.entities.Observer_Subject.Subject;
import unsw.dungeon.entities.interaction.Movement;
import unsw.dungeon.entities.type.*;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.*;

import java.util.ArrayList;
import unsw.dungeon.Dungeon;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements Movable, Subject, ObserverForDungeon {

    private Dungeon dungeon;

    private Movement movement;

    private ArrayList<Observer> observersList;

    private ListProperty<Item> inventory;

    private boolean alive = true;
    private BooleanProperty effect; 
    /**
     * Create a player positioned in square (x,y)
     * 
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        movement = new Movement(x, y, this, dungeon);
        observersList = new ArrayList<>();
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        inventory = new SimpleListProperty<Item>(observableList);
        effect = new SimpleBooleanProperty(false);
        addObservers(observersList);
        notifyObservers();
    }


    public ListProperty<Item> inventory() {
        return inventory;
    }

    private void addObservers(ArrayList<Observer> observerList) {
        ArrayList<Observer> enemies = dungeon.getAllObservers();
        for (Observer i : enemies) {
            if (!observerList.contains(i)) {
                observerList.add(i);
            }
        }
    }

    public void death() {
        alive = false;
    }
    //Function to check whether the player is alive after  enemy's moving.
    public Entity checkAlive() {
        ArrayList<Entity> list = dungeon.getEntities(this.getX(), this.getY());
        Entity s = getItem(EntityType.SWORD);
        Sword sword = (Sword) s;
        for (Entity i : list) {
            if (i instanceof Enemy){
                Enemy e = (Enemy) i;
                if (s != null && sword.getUses() > 0 && e.getAlive()) {
                    sword.use();;
                    e.kill();
                    return e;
                }
                if (e.getAlive()) {
                    death();
                }
                return null;
            }
        }
        return null;

    }

    public BooleanProperty effect() {
        return effect;
    }

    public void hasEffect() {
        effect.set(true);
    }

    public void cutoff() {
        effect.set(false);
    }

    public void moveUp() {
        if (getY() > 0 && movement.getUp() && alive)
            y().set(getY() - 1);
        movement.upDatePosition(this.getX(), this.getY());
        notifyObservers();
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1 && movement.getDown() && alive)
            y().set(getY() + 1);
        movement.upDatePosition(this.getX(), this.getY());
        notifyObservers();
    }

    public void moveLeft() {
        if (getX() > 0 && movement.getLeft() && alive)
            x().set(getX() - 1);
        movement.upDatePosition(this.getX(), this.getY());
        notifyObservers();
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && movement.getRight() && alive)
            x().set(getX() + 1);
        movement.upDatePosition(this.getX(), this.getY());
        notifyObservers();
    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if ((entity instanceof Enemy)) {
            return true;
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

    @Override
    public void attach(Observer o) {
        if (!observersList.contains(o)) {
            observersList.add(o);
        }
    }

    @Override
    public void detach(Observer o) {
        observersList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer i : observersList) {
            i.update(this);
        }
    }

    @Override
    public void update() {
        movement.upDatePosition(this.getX(), this.getY());
    }
    /**
     * get player's inventory.
     * @return
     */
    public ObservableList<Item> getInventory() {
        return inventory.get();
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void UpdateAround() {
        movement.upDatePosition(this.getX(), this.getY());
    }

    @Override
    public EntityType type() {
        return EntityType.PLAYER;
    }

    public List<Entity> getItems(EntityType type) {
        return inventory.stream().filter(entity -> entity.type() == type).collect(Collectors.toList());
    }
    
    public Item getItem(EntityType type) {
        for (Item i : inventory) {
            if (i.type().equals(type)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Function to reduce the time remain of the potion.
     */
    public void reduceInvicibility() {
        ObservableList<Item> list = getInventory();
        for (Item i : list) {
            if (i instanceof Invincibility) {
                Invincibility potion = (Invincibility) i;
                potion.use();
            }
            if (i instanceof Extention_ghostPotion) {
                Extention_ghostPotion p = (Extention_ghostPotion) i;
                p.use();
            }
        }
    }
}
