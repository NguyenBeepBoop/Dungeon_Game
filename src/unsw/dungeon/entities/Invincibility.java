package unsw.dungeon.entities;


import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.type.*;
import java.io.File;
import javafx.scene.image.Image;

public class Invincibility extends Item implements Usable, Interactable{

    private Dungeon dungeon;
    private Image invincibilityImage;

    public Invincibility(int x, int y, Dungeon dungeon) {
        super(x, y);
        super.setUses(0);
        this.dungeon = dungeon;
        invincibilityImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
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
        if (super.getUses() > 0) {
            super.setUses(getUses() - 1);
            return true;
        }
        dungeon.getPlayer().cutoff();
        return false;
    }

    @Override
    public int getUses() {
        return super.getUses();
    }

    public void setUses(int uses) {
        super.setUses(uses);
    }

    @Override
    public boolean collect(Player player) {
        if (player.getItems(type()).size() > 0) {
            ((Invincibility) player.getItem(type())).setUses(15);
            return true;
        }
        player.addItem(this);
        setUses(15);
        player.hasEffect();
        return true;
    }

    public EntityType type() {

        return EntityType.INVINCIBILITY;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getUses();
    }

    @Override
    public Entity interaction(Player player, Dungeon dungeon) {
        collect(player);
        return this;
    }


    @Override
    public Image getImage() {
        return invincibilityImage;
    }

    
}