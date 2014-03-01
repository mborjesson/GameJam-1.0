/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.level;


import gamejam10.Main;
import gamejam10.character.Character;
import gamejam10.character.Player;
import gamejam10.physics.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


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
    //list of all characters on this map
    private ArrayList<Character> characters;
    private ArrayList<Character> enemies;

//    public Level(String level) throws SlickException {
//        // map = new TiledMap("data/levels/" + level + ".tmx", "data/img");
//        map = new TiledMap("data/maps/map04.tmx", "data/images");
//        characters = new ArrayList<Character>();
//        enemies = new ArrayList<Character>();
//
//        this.loadTileMap();
//
//        System.out.println("tiledMap.getWidth(): " + map.getWidth());
//        System.out.println("tiledMap.getTileWidth(): " + map.getTileWidth());
//        this.mapWidth = map.getWidth() * map.getTileWidth();
//        this.mapHeight = map.getHeight() * map.getTileHeight();
//        System.out.println("this.mapWidth: " + this.mapWidth);
//        System.out.println("this.mapHeight: " + this.mapHeight);
//
//
//    }

    public Level(String level, Player player, ArrayList enemies) throws SlickException {

        map = new TiledMap("data/maps/map04.tmx", "data/images");
        characters = new ArrayList<Character>();
        this.enemies = new ArrayList<Character>();
        
        this.player = player;
        addCharacter(player);
        addEnemies(enemies);
        
        
        loadTileMap();
    }

    public int getXOffset() {
        int offset_x = 0;

        //the first thing we are going to need is the half-width of the screen, to calculate if the player is in the middle of our screen
        int half_width = (int) (Main.getOptions().getWidth() / 2);

        //next up is the maximum offset, this is the most right side of the map, minus half of the screen offcourse
        int maxX = (int) (map.getWidth() * 32) - half_width*2;

        //now we have 3 cases here
        if (player.getX() < half_width) {
            //the player is between the most left side of the map, which is zero and half a screen size which is 0+half_screen
            offset_x = 0;
        } else if (player.getX() > maxX) {
            //the player is between the maximum point of scrolling and the maximum width of the map
            //the reason why we substract half the screen again is because we need to set our offset to the topleft position of our screen
            offset_x = maxX - half_width;
        } else {
            //the player is in between the 2 spots, so we set the offset to the player, minus the half-width of the screen
            offset_x = (int) (player.getX() - half_width);
        }

        return offset_x;

    }

    public int getYOffset() {
        int offset_y = 0;

        int half_heigth = (int) (Main.getOptions().getHeight() / 2);

        int maxY = (int) (map.getHeight() * 32) - half_heigth;

        if (player.getY() < half_heigth) {
            offset_y = 0;
        } else if (player.getY() > maxY) {
            offset_y = maxY - half_heigth;
        } else {
            offset_y = (int) (player.getY() - half_heigth);
        }

        return offset_y;
    }

//    public void render(int x, int y) {
//        map.render(x, y);
//
//
//
//        for (Character c : characters) {
//            c.render();
//        }
//    }
    
     public void render(){
 
        int offset_x = getXOffset();
        int offset_y = getYOffset();
 
        //render the map first
        map.render(-(offset_x%32), -(offset_y%32), offset_x/32, offset_y/32, 42, 25);
 
        //and then render the characters on top of the map
        for(Character c : characters){
            c.render(offset_x,offset_y);
        }
        
//        for(Character e : enemies){
//            e.render(offset_x,offset_y);
//        }
 
    }

    private void loadTileMap() {
        //create an array to hold all the tiles in the map
        tiles = new Tile[map.getWidth()][map.getHeight()];

        int layerIndex = map.getLayerIndex("images");

        if (layerIndex == -1) {
            //TODO we can clean this up later with an exception if we want, but because we make the maps ourselfs this will suffice for now
            System.err.println("Map does not have the layer \"images\"");
            System.exit(0);
        }

        //loop through the whole map
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {

                //get the tile
                int tileID = map.getTileId(x, y, layerIndex);

                Tile tile = null;
                //System.out.println("tile: " + map.getTileProperty(tileID, "collidable", "false"));
                //System.out.println("deadly: " + map.getTileProperty(tileID, "deadly", "false"));
                
                //and check what kind of tile it is (
                switch (map.getTileProperty(tileID, "collidable", "false")) {
                    case "true":
                  //      System.out.println("solid");
                        tile = new Tile(x, y, true, new Boolean(map.getTileProperty(tileID, "deadly", "false")).booleanValue());
                        break;
                    default:
                        //System.out.println("Non solid");
                        tile = new Tile(x, y, false, new Boolean(map.getTileProperty(tileID, "deadly", "false")).booleanValue());
                        break;
                }
                tiles[x][y] = tile;
            }
        }
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }
    
    public void addEnemies(ArrayList e) {
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
     * @param mapWidth the mapWidth to set
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
     * @param mapHeight the mapHeight to set
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
     * @param tiles the tiles to set
     */
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * @return the characters
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * @param characters the characters to set
     */
    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
}
