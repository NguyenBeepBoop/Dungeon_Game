package unsw.dungeon.tests;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.EntityType;
public class KeyTest {
    private Dungeon dungeon;
    private Player player;
    private Key key;
    private Door door;

    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
        dungeon.addEntity(new Enemy(3, 3, dungeon));
        // make a key
        dungeon.addEntity(key = new Key(1, 2, 1));
    }

    @Test
    void keyPickUp() {
        // assert key is in dungeon
        assertEquals(1, dungeon.getEntities(EntityType.KEY).size());  
        assertEquals(key.getX(), 1);
        assertEquals(key.getY(), 2);
        // move player down to pick up key
        player.moveDown();
        // assert that key is in player's inventory
        assertEquals(1, player.getItems(EntityType.KEY).size());
        assertEquals(0, dungeon.getEntities(EntityType.KEY).size());  
    }

    @Test
    void testKeyDoor() {
        assertEquals(1, dungeon.getEntities(EntityType.KEY).size());
        // pickup the key
        player.moveDown();
        // create a door with matching id 
        dungeon.addEntity(door = new Door(9, 9, 1));
        // assert that door is initially closed
        assertEquals("Closed", door.getStatus());
        // move player to door
        player.setX(9);
        player.setY(8);
        player.moveDown();
        // assert that door is now open and key is used
        assertEquals("Open", door.getStatus());
        assertEquals(0, player.getItems(EntityType.KEY).size());
    }

    @Test
    void testInvalidKey() {
        assertEquals(1, dungeon.getEntities(EntityType.KEY).size());
        // pickup the key
        player.moveDown();
        // create a door with matching id 
        dungeon.addEntity(door = new Door(9, 9, 2));
        // assert that door is initially closed
        assertEquals("Closed", door.getStatus());
        // move player to door
        player.setX(9);
        player.setY(9);
        // assert that door is now open and key is used
        assertEquals("Closed", door.getStatus());
        assertEquals(1, player.getItems(EntityType.KEY).size());
    }

    @Test
    void testNoKey() {
        // assert that key is in dungeon
        assertEquals(1, dungeon.getEntities(EntityType.KEY).size());
        // create a door with matching id 
        dungeon.addEntity(door = new Door(9, 9, 2));
        // assert that door is initially closed
        assertEquals("Closed", door.getStatus());
        // move player to door
        player.setX(9);
        player.setY(9);
        // assert that door is now open and key is used
        assertEquals("Closed", door.getStatus());
        // assert that key is not in inventory
        assertEquals(0, player.getItems(EntityType.KEY).size());
    }
}