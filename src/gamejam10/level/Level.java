/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.level;

import gamejam10.*;
import gamejam10.camera.*;
import gamejam10.character.*;
import gamejam10.character.Character;
import gamejam10.enums.*;
import gamejam10.physics.*;

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

	public Level(String level) throws SlickException {

		map = new TiledMap("data/maps/" + level + ".tmx", "data/images");
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
						int height = map.getObjectWidth(groupID, objectID);
						System.out.println(x + ", " + y + ", " + width + ", "
								+ height);
						CharacterType ct = CharacterType.getCharacterType(name);
						switch (ct) {
						case PLAYER: {
							player = new Player(x, y);
						}
							break;
						}
					}
						break;
					}
				}
			}
		}

		addCharacter(player);
		addEnemies(enemies);

		loadTileMap();
	}

	public int getXOffset() {
		int offsetX = 0;

		// the first thing we are going to need is the half-width of the screen,
		// to calculate if the player is in the middle of our screen
		int halfWidth = (int) (Main.getOptions().getWidth() / 2);

		// next up is the maximum offset, this is the most right side of the
		// map, minus half of the screen offcourse
		int maxX = (int) (map.getWidth() * map.getTileWidth()) - halfWidth * 2;

		// now we have 3 cases here
		if (player.getX() < halfWidth) {
			// the player is between the most left side of the map, which is
			// zero and half a screen size which is 0+half_screen
			offsetX = 0;
		} else if (player.getX() > maxX) {
			// the player is between the maximum point of scrolling and the
			// maximum width of the map
			// the reason why we substract half the screen again is because we
			// need to set our offset to the topleft position of our screen
			offsetX = maxX - halfWidth;
		} else {
			// the player is in between the 2 spots, so we set the offset to the
			// player, minus the half-width of the screen
			offsetX = (int) (player.getX() - halfWidth);
		}

		return offsetX;

	}

	public int getYOffset() {
		int offsetY = 0;

		int halfHeight = (int) (Main.getOptions().getHeight() / 2);

		int maxY = (int) (map.getHeight() * map.getTileHeight()) - halfHeight;

		if (player.getY() < halfHeight) {
			offsetY = 0;
		} else if (player.getY() > maxY) {
			offsetY = maxY - halfHeight;
		} else {
			offsetY = (int) (player.getY() - halfHeight);
		}

		return offsetY;
	}

	public void render(Graphics g) {

//		int offsetX = getXOffset();
//		int offsetY = getYOffset();
		int offsetX = 0;
		int offsetY = 0;

		// render the map first
		map.render(-(offsetX % map.getTileWidth()),
				-(offsetY % map.getTileHeight()),
				offsetX / map.getTileWidth(), offsetY / map.getTileHeight(),
				42, 25);

		// and then render the characters on top of the map
		for (Character c : characters) {
			c.render(g, offsetX, offsetY);
		}
	}

	private void loadTileMap() {
		// create an array to hold all the tiles in the map
		tiles = new Tile[map.getWidth()][map.getHeight()];

		int layerIndex = map.getLayerIndex("images");

		if (layerIndex == -1) {
			// TODO we can clean this up later with an exception if we want, but
			// because we make the maps ourselfs this will suffice for now
			System.err.println("Map does not have the layer \"images\"");
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
}
