package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.*;


public class InvincibleTest {
    private Dungeon dungeon;
    private Player player;
    private Invincibility invincibility;
    
    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
        dungeon.addEntity(new Enemy(3, 3, dungeon));
        // make a invincibility potion
        dungeon.addEntity(invincibility = new Invincibility(1, 2, dungeon));
    }

    @Test
    void pickUpInvincible() {
        // assert potion is in dungeon
        assertEquals(1, dungeon.getEntities(EntityType.INVINCIBILITY).size());
        assertEquals(invincibility.getX(), 1);
        assertEquals(invincibility.getY(), 2);
        // move player down to pick up key
        player.moveDown();
        // assert that potion is in player's inventory
        assertEquals(1, player.getItems(EntityType.INVINCIBILITY).size());
        assertEquals(0, dungeon.getEntities(EntityType.INVINCIBILITY).size());
    }

    @Test
    void testPotionDurability() {
        // pickup potion
        player.moveDown();
        // assert that potion is in player's inventory
        assertEquals(1, player.getItems(EntityType.INVINCIBILITY).size());
        assertEquals(0, dungeon.getEntities(EntityType.INVINCIBILITY).size());
        assertEquals(9, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        // assert potion uses decreases with player movement
        player.moveDown();
        assertEquals(8, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveLeft();
        assertEquals(7, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveUp();
        assertEquals(6, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveRight();
        assertEquals(5, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveDown();
        assertEquals(4, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveLeft();
        assertEquals(3, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveUp();
        assertEquals(2, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveRight();
        assertEquals(1, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
        player.moveDown();
        assertEquals(0, ((Usable) player.getItem(EntityType.INVINCIBILITY)).getUses());
    }

    @Test
    void testKillEnemy() {
        assertEquals(1, dungeon.getEntities(EntityType.ENEMY).size());
        // pickup potion
        player.moveDown();
        // assert that potion is in player's inventory
        assertEquals(1, player.getItems(EntityType.INVINCIBILITY).size());
        assertEquals(0, dungeon.getEntities(EntityType.INVINCIBILITY).size());
        // encapsulate wall
        dungeon.addEntity(new Wall(3, 5, dungeon));
        // try kill the enemy
        player.setX(4);
        player.setY(2);
        player.moveDown();
        player.moveDown();
        // assert enemy has been killed 
        assertEquals(0, dungeon.getEntities(EntityType.ENEMY).size()); 
    }
}