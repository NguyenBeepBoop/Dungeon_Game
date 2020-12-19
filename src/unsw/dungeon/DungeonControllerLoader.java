package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.*;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    // Images
    private Image playerImage;
    private Image wallImage;
    private Image boulderImage;
    private Image enemyImage;
    private Image switchImage;
    private Image swordImage;
    private Image treasureImage;
    private Image invincibilityImage;
    private Image doorImage;
    private Image keyImage;
    private Image exitImage;
    private Image portalImage;
    private Image doorImage_open;
    private Image playerEffect;
    private Image potion;
    private Image gnome;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        invincibilityImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        doorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        doorImage_open = new Image((new File("images/open_door.png")).toURI().toString());
        playerEffect = new Image((new File("images/hound.png")).toURI().toString());
        potion = new Image((new File("images/bubbly.png")).toURI().toString());
        gnome = new Image((new File("images/gnome.png")).toURI().toString());
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        Player me = (Player) player;
        me.effect().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    view.setImage(playerEffect);
                } else {
                    view.setImage(playerImage);
                }
            }

        });
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }

    @Override
    public void onLoad(Switch floor_switch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floor_switch, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Invincibility invincibility) {
        ImageView view = new ImageView(invincibilityImage);
        addEntity(invincibility, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        addEntity(door, view);
        door.status().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    view.setImage(doorImage_open);
                }
            }

        });
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Extention_ghostPotion ghostPotion) {
        ImageView view = new ImageView(potion);
        addEntity(ghostPotion, view);
    }

    @Override
    public void onLoad(Extention_Gnome gnome) {
        ImageView view = new ImageView(this.gnome);
        addEntity(gnome, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
        //IF the entity is not exit, we make the image not visiable.
        entity.exit().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                node.setVisible(newValue);
            }

        });

    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * 
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(DungeonScreen dungeonScreen) throws FileNotFoundException {
        return new DungeonController(load(), entities, dungeonScreen);
    }
}
