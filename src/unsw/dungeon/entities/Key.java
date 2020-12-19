package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;
import java.io.File;
import javafx.scene.image.Image;

public class Key extends Item implements Usable, Interactable {

    private int id;
    private Image keyImage;

    public Key(int x, int y, int id) {
        super(x, y);
        super.setUses(1);
        this.id = id;
        keyImage = new Image((new File("images/key.png")).toURI().toString());
    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if ((entity instanceof Player) || (entity instanceof Enemy)) {
            return true;
        }
        return false;
    }

    @Override
    public int getUses() {
        return super.getUses();
    }

    public void setUses(int uses) {
       super.setUses(uses);
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    @Override
    public boolean use() {
        if (getUses() <= 0) {
            return false;
        }
        setUses(0);
        return true;
    }

    @Override
    public boolean collect(Player player) {
        //Player can only hold one key at a time.
        if (player.getItems(type()).size() > 0)
            return false;
        player.addItem(this);
        return true;
    }

    @Override
    public EntityType type() {
        return EntityType.KEY;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getUses();
    }

    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        boolean check = collect(player);
        if (check) {
            return this;
        } else {
            return null;
        }

    }

    @Override
    public Image getImage() {
        return keyImage;
    }
    
}