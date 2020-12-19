package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;

public class WallTest {
    private Dungeon dungeon;
    private Player player;
    private Boulder boulder;
    
    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
    }

    @Test
    void testPlayerMove() {
        // create a wall
        dungeon.addEntity((new Wall(1, 3, dungeon)));
        // try move player down
        player.moveDown();
        // assert player has moved
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
        // try move player down
        player.moveDown();
        // assert player cannot move downwards
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    void testBoulderCollidesWithWall() {
        // create a wall
        dungeon.addEntity((new Wall(1, 5, dungeon)));
        dungeon.addEntity(boulder = (new Boulder(1, 3, dungeon)));
        // try move player down
        player.moveDown();
        // assert player has moved
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
        // try move player down
        player.moveDown();
        // assert player move downwards
        assertEquals(1, player.getX());
        assertEquals(3, player.getY());
        // assert boulder has moved downwards
        assertEquals(1, boulder.getX());
        assertEquals(4, boulder.getY());
        player.moveDown();
        // assert player cannot move downwards
        assertEquals(1, player.getX());
        assertEquals(3, player.getY());
        // assert boulder has not moved downwards
        assertEquals(1, boulder.getX());
        assertEquals(4, boulder.getY());

    }
}