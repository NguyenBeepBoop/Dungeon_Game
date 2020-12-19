package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.*;

public class EnemyTest {
    private Dungeon dungeon;
    private Player player;
    private Enemy enemy;
    
    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
        dungeon.addEntity(enemy = new Enemy(4, 4, dungeon));
    }

    @Test
    void testRunTowards() {
        // move the player
        player.moveDown();
        // assert player's location
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
        // assert the enemy has moved up closer to the player
        assertEquals(3, enemy.getX());
        assertEquals(4, enemy.getY());
    }

    @Test
    void testRunAway() {
        dungeon.addEntity(new Invincibility(1, 3, dungeon));
        assertEquals(1, dungeon.getEntities(EntityType.ENEMY).size());
        // pickup potion
        player.moveDown();
        // assert the enemy has moved up closer to the player
        assertEquals(3,enemy.getX());
        assertEquals(4,enemy.getY());
        player.moveDown();
        // assert that potion is in player's inventory
        assertEquals(1, player.getItems(EntityType.INVINCIBILITY).size());
        assertEquals(0, dungeon.getEntities(EntityType.INVINCIBILITY).size());
        // move player
        player.moveDown();
        // assert that enemy has moved away from player
        assertEquals(5,enemy.getX());
        assertEquals(4,enemy.getY());
    }
}