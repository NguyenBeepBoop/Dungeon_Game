package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Enemy;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Sword;
import unsw.dungeon.entities.type.*;

public class SwordTest {
        
    private Dungeon dungeon;
    private Player player;
    private Sword sword;
    private Sword sword2;

    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
        dungeon.addEntity(new Enemy(3, 3, dungeon));
    }

    @Test
    void swordPickup() {
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);

        dungeon.addEntity(sword = new Sword(1, 2));
        assertEquals(sword.getX(), 1);
        assertEquals(sword.getY(), 2);

        player.moveDown();

        assertEquals(1, player.getItems(EntityType.SWORD).size());
        assertEquals(0, dungeon.getEntities(EntityType.SWORD).size());      // check item has been removed from dungeon
    }

    @Test
    void swordDurability() {
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);

        dungeon.addEntity(sword = new Sword(1, 2));
        assertEquals(sword.getX(), 1);
        assertEquals(sword.getY(), 2);

        player.moveDown();
        // check item has been removed from dungeon
        assertEquals(1, player.getItems(EntityType.SWORD).size());
        assertEquals(0, dungeon.getEntities(EntityType.SWORD).size());     
       
        // try kill the enemy
        player.setX(3);
        player.setY(2);
        player.moveDown();
        // assert enemy has been killed 
        assertEquals(0, dungeon.getEntities(EntityType.ENEMY).size());  
        // assert item usage has decreased 
        Item i = player.getItem(EntityType.SWORD);
        assertEquals(4, ((Usable) i).getUses());
    }

    @Test
    void checkDoubleSwordPickup() {
        // create two swords
        dungeon.addEntity(sword = new Sword(1, 2));         
        dungeon.addEntity(sword2 = new Sword(1, 3));
        assertEquals(sword2.getX(), 1);
        assertEquals(sword2.getY(), 3);
        // check that both are there
        assertEquals(2, dungeon.getEntities(EntityType.SWORD).size());
        // collect a sword
        player.moveDown();
        // assert that sword has been picked up
        assertEquals(1, player.getItems(EntityType.SWORD).size());
        assertEquals(1, dungeon.getEntities(EntityType.SWORD).size());
        // try to pick up another sword
        player.moveDown();
        // assert sword cannot be picked up 
        assertEquals(1, player.getItems(EntityType.SWORD).size());
        assertEquals(1, dungeon.getEntities(EntityType.SWORD).size());
        // move player down
        player.moveDown();
        ((Sword)player.getItem(EntityType.SWORD)).setUses(0);
        // set sword uses to 0 (break sword)
        assertEquals(0, ((Usable)player.getItem(EntityType.SWORD)).getUses());
        // try to pick up the sword again
        player.moveUp();
        // assert sword uses has been refreshd
        assertEquals(5, ((Usable)player.getItem(EntityType.SWORD)).getUses());
        // assert item has been removed from dungeon
        assertEquals(0, dungeon.getEntities(EntityType.SWORD).size());
    }
}