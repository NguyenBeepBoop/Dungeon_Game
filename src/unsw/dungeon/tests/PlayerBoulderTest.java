package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

//import java.sql.Blob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;
//import unsw.dungeon.entities.interaction.Movement;

class PlayerBoulderTest {
    private Dungeon dungeon;
    private Player player;
    private Enemy enemy;
    private Boulder boulder;
    
    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
    }

    @Test
    void playerMoveToBoulder() {
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        dungeon.addEntity(boulder = new Boulder(2, 1, dungeon));
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);
        assertEquals(boulder.getX(), 3);
        assertEquals(boulder.getY(), 1);
        assertEquals(boulder.getxNext(),4);
        assertEquals(boulder.getyNext(),1);
        Boulder test2 = new Boulder(0, 1, dungeon);

        dungeon.addEntity(test2);
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        //Test adding the blouder adjacent to player.
        Boulder test3 = new Boulder(0, 1, dungeon);
        dungeon.addEntity(test3);
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
    }

    @Test
    void EnemyBlockByBoulder() {
        enemy = new Enemy(3, 1, dungeon);
        dungeon.addEntity(enemy);
        dungeon.addEntity(boulder = new Boulder(2, 1, dungeon));
        player.moveLeft();
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 1);
        assertEquals(boulder.getX(), 2);
        assertEquals(boulder.getY(), 1);
        assertEquals(enemy.getX(), 3);
        assertEquals(enemy.getY(), 1);
    }
}