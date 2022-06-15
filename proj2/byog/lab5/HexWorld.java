package byog.lab5;
import org.junit.Test;

import static byog.lab5.RandomWorldDemo.RANDOM;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    static class Position {
        int x;
        int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        char fil = 'a';
        //Upper half
        for (int i = 0; i < s; i++) {
            for (int j = s - 1 - i; j < 2 * s - 1 + i; j++) {
                world[p.x + j][p.y + i] = t;
            }
        }
        //Lower half
        for (int i = s; i < 2 * s; i++) {
            for (int j = i - s; j < 4 * s - 2 - i; j++) {
                world[p.x + j][p.y + i] = t;
            }
        }
    }

    public static Position[] startingPositions (Position p, int s) {
        int offsetX = 2 * s - 1;
        int offsetY = s;
        Position[] positions = new Position[19];
        for (int i = 0; i < 19; i++) {
            positions[i] = new Position(p.x, p.y);
        }

        positions[0].x = p.x + 2 * offsetX;
        positions[0].y = p.y;
        for (int i = 1; i < 18; i++) {
            int layer = (i - 1) / 5;
            int pos = (i - 1) % 5;

            positions[i].y = p.y + offsetY + 2 * layer * offsetY;
            if (pos > 1) { positions[i].y += offsetY; }

            if (pos < 2) {
                positions[i].x = p.x + offsetX + 2 * pos * offsetX;
            } else {
                positions[i].x = p.x + (pos - 2) * 2 * offsetX;
            }
        }
        positions[18].x = p.x + 2 * offsetX;
        positions[18].y = p.y + 8 * offsetY;

        return positions;
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.SAND;
            case 5: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);
        TETile[][] world = new TETile[50][50];

        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Position p0 = new Position(0, 0);
        int size = 2;
        Position[] ps = startingPositions(p0, size);

        for (int i = 0; i < 19; i++){
            addHexagon(world, ps[i], size, randomTile());
        }
        ter.renderFrame(world);
    }

}
