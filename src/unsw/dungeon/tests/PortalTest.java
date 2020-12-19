package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;

public class PortalTest {
    private Dungeon dungeon;
    private Player player;
    
    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
    }

    @Test
    void testPortalSameId() {
        // make a portal pair
        dungeon.addEntity(new Portal(1, 2, 1));
        dungeon.addEntity(new Portal(5, 7, 1));
        // move player into the portal
        player.moveDown();
        assertEquals(6, player.getX());
        assertEquals(7, player.getY());
    }

    @Test
    void testPortalDiffId() {
        // make a portal pair
        dungeon.addEntity(new Portal(1, 2, 1));
        dungeon.addEntity(new Portal(5, 7, 2));
        // move player into the portal
        player.moveDown();
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
    }
}