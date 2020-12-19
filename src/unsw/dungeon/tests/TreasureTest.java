package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Enemy;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Treasure;
import unsw.dungeon.entities.type.*;


public class TreasureTest {
    private Dungeon dungeon;
    private Player player;
    private Treasure treasure;
    
    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
        dungeon.addEntity(new Enemy(3, 3, dungeon));
    }

    @Test
    void treasurePickup() {
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);

        dungeon.addEntity(treasure = new Treasure(1, 2));
        assertEquals(treasure.getX(), 1);
        assertEquals(treasure.getY(), 2);

        player.moveDown();
        // check item has been removed from dungeon and is in player inventory

        assertEquals(1, player.getItems(EntityType.TREASURE).size());
        assertEquals(0, dungeon.getEntities(EntityType.TREASURE).size());
    }

    @Test
    void pickupDoubleTreasure() {
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);

        dungeon.addEntity(treasure = new Treasure(1, 2));
        dungeon.addEntity(new Treasure(1, 3));
        assertEquals(treasure.getX(), 1);
        assertEquals(treasure.getY(), 2);

        player.moveDown();
        // check item has been removed from dungeon and is in player inventory

        assertEquals(1, player.getItems(EntityType.TREASURE).size());
        assertEquals(1, dungeon.getEntities(EntityType.TREASURE).size());

        player.moveDown();
        // assert treasure has been picked up
        assertEquals(1, player.getItems(EntityType.TREASURE).size());
        assertEquals(0, dungeon.getEntities(EntityType.TREASURE).size());
    }
}