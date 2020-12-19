package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Observer_Subject.*;
import unsw.dungeon.entities.type.*;
import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
/**
 * Extention entity for this dungon.
 * The postion to allows the player to move within the wall in given time.
 */
public class Extention_ghostPotion extends Item implements Usable, Interactable, SubjectGhost {

    private Image ghostImage;
    // Wall is the observer for this postion, if the use is > 0, 
    // Player can move within the wall
    private ArrayList<ObserverWall> observerlist;

    public Extention_ghostPotion(int x, int y, Dungeon dungeon) {
        super(x, y);
        super.setUses(0);
        ghostImage = new Image((new File("images/bubbly.png")).toURI().toString());
        observerlist = dungeon.getAllWall();
        
    }

    @Override
    public boolean collect(Player player) {
        // If player used to piched up the postion, it will renew the time.
        if (player.getItems(type()).size() > 0) {
            ((Extention_ghostPotion) player.getItem(type())).setUses(15);
            return true;
        }
        player.addItem(this);
        setUses(15);
        notifyObservers(true);
        return true;
    }

    @Override
    public Image getImage() {
        return ghostImage;
    }

    @Override
    public EntityType type() {
        return EntityType.EXTENTION_GHOSTPOTION;
    }

    @Override
    public boolean use() {
        if (super.getUses() > 0) {
            super.setUses(getUses() - 1);
            return true;
        }
        //dungeon.getPlayer().cutoff();
        notifyObservers(false);
        return false;
    }

    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        collect(player);
        return this;
    }

    @Override
    public void attach(ObserverWall o) {
        if (!observerlist.contains(o)) {
            observerlist.add(o);
        }

    }

    @Override
    public void detach(ObserverWall o) {
        observerlist.remove(o);

    }

    @Override
    public void notifyObservers(boolean sig) {
        for (ObserverWall i : observerlist) {
            i.update(sig);
        }
    }
    
    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        //boulders cannot ovelap with postion
        if ((entity instanceof Player) || (entity instanceof Enemy)) {
            return true;
        }
        return false;
    }
    

}
