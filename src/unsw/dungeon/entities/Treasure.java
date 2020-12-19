package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;
import java.io.File;
import javafx.scene.image.Image;


public class Treasure extends Item implements Interactable {

    private Image treasureImage;

    public Treasure(int x, int y) {
        super(x, y);
        super.setUses(0);
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
    }

    @Override
    public boolean overlaping(int x, int y, Entity entity) {
        if ((entity instanceof Player) || (entity instanceof Enemy)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean collect(Player player) {
        if (player.getItems(type()).size() == 0)
            player.addItem(this);
        player.getItem(type()).setUses(getUses() + 1);
        return true;
    }

    @Override
    public EntityType type() {
        return EntityType.TREASURE;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + super.getUses();
    }

    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        collect(player);
        return this;
    }

    @Override
    public Image getImage() {
        return treasureImage;
    }

    public void reduce() {
        if (super.getUses() > 1) {
            super.setUses(super.getUses() - 1);
        }
    }

}   