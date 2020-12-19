/*package unsw.dungeon.tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.EntityType;
import unsw.dungeon.Composition.Composition;
public class GoalsTest {

    private Dungeon dungeon;
    private Player player;
    private Boulder boulder;
    private Composition composition;

    @BeforeEach
    void init() {
        dungeon = new Dungeon(10, 10);
        dungeon.addEntity((player = new Player(dungeon, 1, 1)));
        dungeon.setPlayer(player);
    }

    @Test
    void testExitGoal() {
        dungeon.setGoal(new Composition("Exit", dungeon));
        // create an exit
        dungeon.addEntity(new Exit(8,8));
        // move player to the exit
        player.setX(8);
        player.setY(7);
        player.moveDown();
        // assert that goal has been met
        assertEquals(true, dungeon.isWin());
    }

    @Test
    void testTreasureGoal() {
        dungeon.setGoal(new Composition("treasure", dungeon));
        // create treasures
        dungeon.addEntity(new Treasure(8,8));
        dungeon.addEntity(new Treasure(1,2));
        // move player to the treasure
        player.moveDown();
        player.setX(8);
        player.setY(7);
        player.moveDown();
        // assert that goal has been met
        assertEquals(true, dungeon.isWin());
    }

    @Test
    void testEnemiesGoal() {
        dungeon.setGoal(new Composition("enemies", dungeon));
        // create enemy
        dungeon.addEntity(new Enemy(1,3, dungeon));
        // create weapon for player
        dungeon.addEntity(new Sword(1,2));
        // move player to the weapon
        player.moveDown();
        // assert player has weapon
        assertEquals(1, player.getItems(EntityType.SWORD).size());
        assertEquals(0, dungeon.getEntities(EntityType.SWORD).size());
        // move player to the enemy
        player.moveDown();
        // assert enemy has been killed
        assertEquals(0, dungeon.getEntities(EntityType.ENEMY).size());  
        // assert that goal has been met
        assertEquals(true, dungeon.isWin());
    }

    @Test
    void testBoulderGoal() {
        // create new goal
        dungeon.setGoal(new Composition("boulders", dungeon));

        // create an exit
        dungeon.addEntity(new Exit(8,8));
        // add boulders
        dungeon.addEntity(boulder = (new Boulder(1, 3, dungeon)));
        dungeon.addEntity(new Switch(1, 4));
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
        // assert goal condition has been met
        assertEquals(true, dungeon.isWin());
    }

    @Test
    void testANDGoal() {
         // create new goal
        composition = new Composition ("AND", dungeon);
        composition.addSubGoal(new Composition("boulders", dungeon));
        composition.addSubGoal(new Composition("treasure", dungeon));
        dungeon.setGoal(composition);
        // create boulder
        dungeon.addEntity(boulder = (new Boulder(1, 3, dungeon)));
        // create treasures
        dungeon.addEntity(new Treasure(7,7));
        // create switch
        dungeon.addEntity(new Switch(1, 4));
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
        // assert that game state is not win yet
        assertEquals(false, dungeon.isWin());
        // move player to the treasure
        player.setX(7);
        player.setY(6);
        player.moveDown();
        // assert that game goal is fulfilled
        assertEquals(true, dungeon.isWin());
    }

    @Test
    void testORGoal() {
        // create new goal
       composition = new Composition ("OR", dungeon);
       composition.addSubGoal(new Composition("boulders", dungeon));
       composition.addSubGoal(new Composition("treasure", dungeon));
       dungeon.setGoal(composition);
       // create boulder
       dungeon.addEntity(boulder = (new Boulder(1, 3, dungeon)));
       // create treasures
       dungeon.addEntity(new Treasure(7,7));
       // create switch
       dungeon.addEntity(new Switch(1, 4));
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
       // assert that game goal is fulfilled
       assertEquals(true, dungeon.isWin());
   }
}*/