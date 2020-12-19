package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.entities.*;
import unsw.dungeon.entities.type.*;
import unsw.dungeon.Composition.*;
/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        JSONObject jsonGoal = json.getJSONObject("goal-condition");
        Goal goal = addGoal(dungeon, jsonGoal);
        dungeon.setGoal(goal);
        
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y, dungeon);
            onLoad(wall);
            entity = wall;
            break;
        case "boulder":
            Boulder boulder = new Boulder(x, y, dungeon);
            onLoad(boulder);
            entity = boulder;
            break;
        case "enemy":
            Enemy enemy = new Enemy(x, y, dungeon);
            onLoad(enemy);
            entity = enemy;
            break;
        case "switch":
            Switch floor_switch = new Switch(x, y);
            onLoad(floor_switch);
            entity = floor_switch;
            break;
        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "invincibility":
            Invincibility invincibility = new Invincibility(x, y,dungeon);
            onLoad(invincibility);
            entity = invincibility;
            break;
        case "door":
            int id = json.getInt("id");
            Door door = new Door(x, y, id);
            onLoad(door);
            entity = door;
            break;
        case "exit":
            Exit exit = new Exit(x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "portal":
            id = json.getInt("id");
            Portal portal = new Portal(x, y, id);
            onLoad(portal);
            entity = portal;
            break;
        case "key":
            id = json.getInt("id");
            Key key = new Key(x, y, id);
            onLoad(key);
            entity = key;
            break;
        case "ghost":
            Extention_ghostPotion potion = new Extention_ghostPotion(x, y, dungeon);
            onLoad(potion);
            entity = potion;
            break;
        case "gnome":
            Extention_Gnome gnome = new Extention_Gnome(x, y);
            onLoad(gnome);
            entity = gnome;
            break;
        default:
            throw new Error("Could not load JSON for object type " + type);
        }
        dungeon.addEntity(entity);
    }

    private Goal addGoal(Dungeon dungeon, JSONObject json) {
        String type = json.getString("goal");
        Goal goal = null;
        JSONArray subgoal = null;
        switch (type) {
            case "AND":
                goal = new ANDGoal(dungeon);
                break;
            case "OR":
                goal = new ORgoal(dungeon);
                break;
            case "exit":
                goal = new ReachExit(dungeon);
                break;
            case "enemies":
                goal = new KillAllEnemies(dungeon);
                break;
            case "treasure":
                goal = new CollectTreasure(dungeon);
                break;
            case "boulders":
                goal = new TrigerSwitch(dungeon);
                break;
            case "gnome":
                goal = new ExtentionGoal(dungeon);
                break;
        }
        try {
            subgoal = json.getJSONArray("subgoals");
            goal.addSubGoal(addGoal(dungeon, subgoal.getJSONObject(0)));
            goal.addSubGoal(addGoal(dungeon, subgoal.getJSONObject(1)));
            return goal;
        } catch(org.json.JSONException exception){
            return goal;
        } 
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Switch floor_switch);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Invincibility invincibility);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Extention_ghostPotion ghostPotion);

    public abstract void onLoad(Extention_Gnome gnome);

}
