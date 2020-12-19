package unsw.dungeon.entities.type;
import javafx.scene.image.Image;
import unsw.dungeon.entities.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public abstract class Item extends Entity {

    IntegerProperty uses;

    public Item(int x, int y) {
       super(x,y);
       uses = new SimpleIntegerProperty();
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public IntegerProperty getUseProperty() {
        return uses;

    }

    public int getUses() {
        return uses.get();
    }

    public void setUses(int uses) {
        this.uses.set(uses);
    }
    
    // implment the method to inidcathe whether given entity could overlaped with this item.
    public abstract boolean overlaping(int x, int y, Entity entity);
    // how does the item collected by player
    public abstract boolean collect(Player player);
    // return the image of the item
    public abstract Image getImage();

}   