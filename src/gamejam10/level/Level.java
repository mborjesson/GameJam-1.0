/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.level;

import gamejam10.*;
import gamejam10.ai.*;
import gamejam10.camera.*;
import gamejam10.character.*;
import gamejam10.character.Character;
import gamejam10.enums.*;
import gamejam10.physics.*;
import gamejam10.sun.*;

import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.*;

/**
 * 
 * @author gregof
 */
public class Level {

	private TiledMap map;
	private int mapWidth;
	private int mapHeight;
	private Tile[][] tiles;
	private Player player;
	private Camera camera;
	// list of all characters on this map
	private List<Character> characters = new ArrayList<Character>();
	private List<Character> enemies = new ArrayList<Character>();
	private List<EndOfLevelObject> endOfWorldObjects = new ArrayList<EndOfLevelObject>();
	private List<StaticAnimatedObject> staticObjects = new ArrayList<StaticAnimatedObject>();
	 
	
	private Sun sun = null;

	public Level(String level) throws SlickException {

		map = new TiledMap("data/maps/" + level + ".tmx", "data/images");
		for (int i = 0; i < map.getTileSetCount(); ++i) {
			map.getTileSet(i).tiles.setFilter(Image.FILTER_NEAREST);
		}
		for (int groupID = 0; groupID < map.getObjectGroupCount(); ++groupID) {
			for (int objectID = 0; objectID < map.getObjectCount(groupID); ++objectID) {
				LevelType obj = LevelType.getLevelObject(map.getObjectType(
						groupID, objectID));
				if (obj != null) {
					switch (obj) {
					case SPAWN: {
						String name = map.getObjectProperty(groupID, objectID,
								"name", null);

						int x = map.getObjectX(groupID, objectID);
						int y = map.getObjectY(groupID, objectID);
						int width = map.getObjectWidth(groupID, objectID);
						int height = map.getObjectHeight(groupID, objectID);
						System.out.println(x + ", " + y + ", " + width + ", "
								+ height);
						CharacterType ct = CharacterType.getCharacterType(name);
						System.out.println("name: " + name);
						switch (ct) {
							case PLAYER: {
								player = new Player(x, y);
								break;
							}
							case WINDMILL: {
								staticObjects.add(new WindmillObject(x, y));
							}break;
							case ENEMY_FLOAT_EASY: {
								FloatEnemy en = new FloatEnemy(x, y, 0, 150);
								en.setMaximumSpeed(0.20f);
								en.setMaximumFallSpeed(0);
								BasicAI ai = new BasicAI(en, player);
								
								int deltaTilesMin = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmin", "0"));
								int deltaTilesMax = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmax", "0"));
								
								PatrollingAIAction.Parameters parameters = new PatrollingAIAction.Parameters();
								parameters.minX = x - map.getTileWidth() * deltaTilesMin;
								parameters.maxX = x + map.getTileWidth() * deltaTilesMax;
								PatrollingAIAction aiAction = new PatrollingAIAction(en, parameters);
								
								ai.addAIAction(aiAction);
								
								en.setAI(ai);
								
								enemies.add(en);
								} break;
							case ENEMY_FLOAT_HARD:  {
								FloatEnemy en = new FloatEnemy(x, y, 0, 150);
								en.setMaximumSpeed(0.90f);
								en.setMaximumFallSpeed(0);
								
								BasicAI ai = new BasicAI(en, player);
								
								int deltaTilesMin = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmin", "0"));
								int deltaTilesMax = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmax", "0"));
								
								PatrollingAIAction.Parameters parameters = new PatrollingAIAction.Parameters();
								parameters.minX = x - map.getTileWidth() * deltaTilesMin;
								parameters.maxX = x + map.getTileWidth() * deltaTilesMax;
								PatrollingAIAction aiAction = new PatrollingAIAction(en, parameters);
								
								ai.addAIAction(aiAction);
								
								en.setAI(ai);
								
								enemies.add(en);
							} break;
							case ENEMY_EASY: {
								AIEnemy en = new AIEnemy(x, y);
								BasicAI ai = new BasicAI(en, player);
								
								int deltaTilesMin = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmin", "0"));
								int deltaTilesMax = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmax", "0"));
								
								RandomMovementAIAction.Parameters moveParameters = new RandomMovementAIAction.Parameters();
								moveParameters.maxMovementDuration = 1000;
								moveParameters.minMovementDuration = 100;
								moveParameters.minX = x - map.getTileWidth() * deltaTilesMin;
								moveParameters.maxX = x + map.getTileWidth() * deltaTilesMax;
								
								ai.addAIAction(new RandomMovementAIAction(en, moveParameters));
								
								en.setAI(ai);
								
								enemies.add(en);
								break;
							}
							case ENEMY_JUMPING: {
								AIEnemy en = new AIEnemy(x, y);
								BasicAI ai = new BasicAI(en, player);
								
								int deltaTilesMin = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmin", "0"));
								int deltaTilesMax = Integer.parseInt(map.getObjectProperty(groupID, objectID, "deltatilesmax", "0"));

								RandomMovementAIAction.Parameters moveParameters = new RandomMovementAIAction.Parameters();
								moveParameters.maxMovementDuration = 1000;
								moveParameters.minMovementDuration = 100;
								moveParameters.minX = x - map.getTileWidth() * deltaTilesMin;
								moveParameters.maxX = x + map.getTileWidth() * deltaTilesMax;
								
								RandomJumpAIAction.Parameters jumpParameters = new RandomJumpAIAction.Parameters();
								jumpParameters.maxTimeBetweenJumps = 5000;
								jumpParameters.minTimeBetweenJumps = 1000;
								
								ai.addAIAction(new RandomJumpAIAction(en, jumpParameters));
								ai.addAIAction(new RandomMovementAIAction(en, moveParameters));
								
								en.setAI(ai);
								
								enemies.add(en);
								break;
							}
							
						}
						break;
					} 
					case TRIGGER: {
						System.out.println(" ");
						
						System.out.println("Trigger Found!");
						
						System.out.println("LEVEL_END FOUND");
						String name = map.getObjectProperty(groupID, objectID, "name", null);
						int x = map.getObjectX(groupID, objectID);
						int y = map.getObjectY(groupID, objectID);
						
						int width = map.getObjectWidth(groupID, objectID);
						int height = map.getObjectHeight(groupID, objectID);
						System.out.println(x + ", " + y + ", " + width + ", "	+ height);
						
						System.out.println("Name: " + name);
						CharacterType ct = CharacterType.getCharacterType(name);
						LevelType lt = LevelType.getLevelObject(name);
						System.out.println("lt: " + lt);
						
						EndOfLevelObject endOfWorldObject = new EndOfLevelObject(x, y, width, height);
						endOfWorldObjects.add(endOfWorldObject);
						
						break;
						
					}
						
					}
				}
			}
		}
		
		String suntime = map.getMapProperty("suntime", "60");
		System.out.println("suntime: " + suntime);
		int sunint = Integer.parseInt(suntime);
		
		sun = new Sun(sunint*1000);
		
		//sun = new Sun(160*1000);
		// TEST
		

		addCharacter(player);
		//addEnemies(enemies);
		
//		Brainemy beny = new Brainemy(50, 370, 50, 150);
//		enemies.add(en);
		
		//FloatEnemy fenemy = new FloatEnemy(50, 370, 50, 150);
		//enemies.add(fenemy);
		
		for (Object e : enemies) {
			addCharacter((Character)e);
		}

		loadTileMap();
	}
	
	public Sun getSun() {
		return sun;
	}

	public void render(Graphics g) {

		g.pushTransform();
		g.resetTransform();
		g.setColor( sun.getRealColor() );
		
		float sunRadius = sun.getRadius();
		g.fillOval(sun.getSunPositionX() * Main.getOptions().getWidth() - sunRadius, (1f-sun.getSunPositionY())*Main.getOptions().getHeight() - sunRadius, 2*sunRadius, 2*sunRadius);
		g.popTransform();

		g.pushTransform();
		
		float x = Math.min(map.getWidth()*map.getTileWidth()-camera.getWidth(), Math.max(0, camera.getX()-camera.getWidth()*0.5f));
		float y = Math.min(map.getHeight()*map.getTileHeight()-camera.getHeight(), Math.max(0, camera.getY()-camera.getHeight()*0.5f));
		
		g.translate(-x, -y);
		

		// render the map first
		map.render(0, 0);
//		System.out.println(getXOffset() + ", " + camera.getX() + ", " + camera.getMinX());
//		map.render(-(offsetX % map.getTileWidth()),
//				-(offsetY % map.getTileHeight()),
//				offsetX / map.getTileWidth(), offsetY / map.getTileHeight(),
//				(int)camera.getWidth()/map.getTileWidth(), (int)camera.getHeight()/map.getTileHeight());

		// Render static objects
		for(StaticAnimatedObject ao : staticObjects) {
			ao.render(g);
		}
		
		// and then render the characters on top of the map
		for (Character c : characters) {
			c.render(g, 0, 0);
		}

		g.popTransform();
		
		
	}
	
	private void loadTileMap() {
		// create an array to hold all the tiles in the map
		tiles = new Tile[map.getWidth()][map.getHeight()];

		int layerIndex = map.getLayerIndex("mapplayer");

		if (layerIndex == -1) {
			// TODO we can clean this up later with an exception if we want, but
			// because we make the maps ourselfs this will suffice for now
			System.err.println("Map does not have the layer \"mapplayer\"");
			System.exit(0);
		}

		// loop through the whole map
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {

				// get the tile
				int tileID = map.getTileId(x, y, layerIndex);

				Tile tile = null;
				// System.out.println("tile: " + map.getTileProperty(tileID,
				// "collidable", "false"));
				// System.out.println("deadly: " + map.getTileProperty(tileID,
				// "deadly", "false"));

				// and check what kind of tile it is (
				switch (map.getTileProperty(tileID, "collidable", "false")) {
				case "true":
					// System.out.println("solid");
					tile = new Tile(x, y, true,
							new Boolean(map.getTileProperty(tileID, "deadly",
									"false")).booleanValue());
					break;
				default:
					// System.out.println("Non solid");
					tile = new Tile(x, y, false,
							new Boolean(map.getTileProperty(tileID, "deadly",
									"false")).booleanValue());
					break;
				}
				tiles[x][y] = tile;
			}
		}
	}
	
	public void update(int delta) {
		sun.update(delta);
//		player.update(delta);
	}

	public void addCharacter(Character c) {
		characters.add(c);
	}

	public void addEnemies(List<Character> e) {
		System.out.println("enemies: " + enemies);
		enemies.addAll(e);
	}

	public TiledMap getMap() {
		return map;
	}

	/**
	 * @return the mapWidth
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * @param mapWidth
	 *            the mapWidth to set
	 */
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	/**
	 * @return the mapHeight
	 */
	public int getMapHeight() {
		return mapHeight;
	}

	/**
	 * @param mapHeight
	 *            the mapHeight to set
	 */
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	/**
	 * @return the tiles
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 * @param tiles
	 *            the tiles to set
	 */
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	/**
	 * @return the characters
	 */
	public List<Character> getCharacters() {
		return characters;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * @param characters
	 *            the characters to set
	 */
	public void setCharacters(ArrayList<Character> characters) {
		this.characters = characters;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public List<EndOfLevelObject> getEndOfWorldObjects() {
		return endOfWorldObjects;
	}

	public void setEndOfWorldObjects(List<EndOfLevelObject> endOfWorldObjects) {
		this.endOfWorldObjects = endOfWorldObjects;
	}

	
	
}
