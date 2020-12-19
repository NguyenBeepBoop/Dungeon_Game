package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Enemy;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Sword;
import unsw.dungeon.entities.type.EntityType;
//import unsw.dungeon.entities.type.Entity;
class PlayerTest {
    
    private Dungeon dungeon;
    private Player player;
    private Enemy enemy;
    private Sword sword;

    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
        dungeon.addEntity(enemy = new Enemy(3, 3, dungeon));
    }

    @Test
    void playerMovement() {
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        assertEquals(enemy.getX(), 3);
        assertEquals(enemy.getY(), 3);
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);
        assertEquals(enemy.getX(), 2);
        assertEquals(enemy.getY(), 3);
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 3);
        assertEquals(enemy.getX(), 1);
        assertEquals(enemy.getY(), 3);
        
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
    }

    
    
}