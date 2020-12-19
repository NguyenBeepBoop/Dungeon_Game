package unsw.dungeon.entities;

import javafx.scene.image.Image;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;
import java.io.File;

public class Sword extends Item implements Usable, Interactable {

    Image swordImage;

    public Sword(int x, int y) {
        super(x, y);
        super.setUses(5);
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if ((entity instanceof Player) || (entity instanceof Enemy)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean use() {
        if (getUses() > 0) {
            setUses(getUses() - 1);
            return true;
        }
        return false;
    }

    public void setUses(int uses) {
        super.setUses(uses);
    }

    @Override
    public int getUses() {
        return super.getUses();
    }

    @Override
    public boolean collect(Player player) {
        if (player.getItems(type()).size() > 0 && ((Usable) player.getItem(type())).getUses() > 0)
            return false;
        setX(0);
        setY(0);
        if (player.getItems(type()).size() > 0 && ((Usable) player.getItem(type())).getUses() == 0) {
            ((Sword) player.getItem(type())).setUses(5);
            return true;
        }
        player.addItem(this);
        return true;
    }

    @Override
    public EntityType type() {
        return EntityType.SWORD;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getUses();
    }

    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        if (collect(player))
            return this;
        return null;
    }

    @Override
    public Image getImage() {
        return swordImage;
    }
      
}