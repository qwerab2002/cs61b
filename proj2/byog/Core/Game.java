package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Set;

public class Game {
    static TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private Random rand;
    private Room[] rooms;
    private Position player;
    private WeightedQuickUnionUF links;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = null;
        input = input.toLowerCase();
        String movement = "";
        boolean save = false;
        if (input.charAt(0) == 'n') {
            String sd = parseForSeed(input);
            movement = parseForMovement(input);
            save = parseForSave(input);
            long seed = Long.parseLong(sd);
            rand = new Random(seed);
            finalWorldFrame = generateWorld();
        } else if (input.charAt(0) == 'l') {
            finalWorldFrame = loadGame();
            movement = parseForMovement(input);
            save = parseForSave(input);
        }
        movePlayer(finalWorldFrame, movement);
        if (save) {
            saveGame(finalWorldFrame);
        }
        return finalWorldFrame;
    }

    //save/load functions
    public void saveGame(TETile[][] world) {
        Save sv = new Save();
        sv.player = this.player;
        sv.world = world;
        File f = new File("./world.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(sv);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }

    }
    public TETile[][] loadGame() {
        ter.initialize(WIDTH, HEIGHT);
        File f = new File("./world.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Save loadWorld = (Save) os.readObject();
                os.close();
                player = loadWorld.player;
                System.out.println(loadWorld.player.x);
                return loadWorld.world;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        throw new IllegalArgumentException("No save file!");
    }

    //movement
    private void movePlayer(TETile[][] world, String movement) {
        Set<Character> moves = Set.of('a', 's', 'd', 'w');
        for (int i = 0; i < movement.length(); i++) {
            char move = movement.charAt(i);
            if (moves.contains(move)) {
                moveOnce(world, move);
            }
        }
    }
    private void moveOnce(TETile[][] world, char move) {
        switch (move) {
            case('a'):
                movePlayer(world, player.x - 1, player.y);
                break;
            case('d'):
                movePlayer(world, player.x + 1, player.y);
                break;
            case('w'):
                movePlayer(world, player.x, player.y + 1);
                break;
            case('s'):
                movePlayer(world, player.x, player.y - 1);
                break;
            default:
        }
    }
    private void movePlayer(TETile[][] world, int x, int y) {
        if (world[x][y].equals(Tileset.FLOOR)) {
            world[player.x][player.y] = Tileset.FLOOR;
            world[x][y] = Tileset.PLAYER;
            player = new Position(x, y);
        }
    }

    //parse the input
    private String parseForSeed(String input) {
        String sd = "";
        for (int i = 1; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                sd += input.charAt(i);
            } else {
                break;
            }
        }
        return sd;
    }
    private String parseForMovement(String input) {
        int start;
        int end;
        if (input.charAt(0) == 'n') {
            start = input.indexOf('s') + 1; // case of new game
        } else {
            start = 1; // case of loading
        }
        end = input.indexOf(":q");
        if (end == -1) {
            end = input.length();
        }
        return input.substring(start, end);
    }
    private boolean parseForSave(String input) {
        return input.contains(":q");
    }

    //generate map
    private TETile[][] generateWorld() {
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int N = RandomUtils.uniform(rand,5, 10);
        generateRoom(N);
        links = new WeightedQuickUnionUF(N);
        for (Room r : rooms) {
            r.floorUp(world);
        }
        linkRooms(world);
        wallUp(world);
        generatePlayer(world);
        return world;
    }
    private void generatePlayer(TETile[][] world) {
        int xPlayer = RandomUtils.uniform(rand, 1, WIDTH - 1);
        int yPlayer = RandomUtils.uniform(rand, 1, HEIGHT - 1);
        while (!world[xPlayer][yPlayer].equals(Tileset.FLOOR)) {
            xPlayer = RandomUtils.uniform(rand, 1, WIDTH - 1);
            yPlayer = RandomUtils.uniform(rand, 1, HEIGHT - 1);
        }
        player = new Position(xPlayer, yPlayer);
        world[xPlayer][yPlayer] = Tileset.PLAYER;
    }
    private void generateRoom(int N) {
        rooms = new Room[N];
        for (int i = 0; i < N; i++) {
            while (true) {
                int width = RandomUtils.uniform(rand, 5, WIDTH / 5);
                int height = RandomUtils.poisson(rand, HEIGHT / 3);
                while (height >= HEIGHT) {
                    height = RandomUtils.poisson(rand, HEIGHT / 3);
                }
                int x = rand.nextInt(WIDTH - width);
                int y = rand.nextInt(HEIGHT - height);
                Position p = new Position(x, y);
                Room r = new Room(width, height, p);
                //check if room is out of bound or overlapping
                if (!r.legal()) {
                    continue;
                }
                boolean overlap = false;
                for (int j = 0; j < i; j++) {
                    if (r.overlap(rooms[j])) {
                        overlap = true;
                        break;
                    }
                }
                if (overlap) {
                    continue;
                }

                rooms[i] = r;
                break;
            }
        }
    }
    private boolean needWall(TETile[][] world, Position p) {
        //if the tile is NOTHING and is next to a FLOOR
        if (!world[p.x][p.y].equals(Tileset.NOTHING)) {
            return false;
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (p.x + i >= 0 && p.x + i < WIDTH && p.y + j >= 0 && p.y + j < HEIGHT) {
                    if (world[p.x + i][p.y + j].equals(Tileset.FLOOR)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void wallUp(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Position p = new Position(x, y);
                if (needWall(world, p)) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
        boolean lockedDoor = false;
        int loc = 0;
        while (!lockedDoor) {
            loc = rand.nextInt(WIDTH * HEIGHT);
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (x * y > loc && world[x][y].equals(Tileset.WALL)) {
                        world[x][y] = Tileset.LOCKED_DOOR;
                        lockedDoor = true;
                        break;
                    }
                }
                if (lockedDoor) {
                    break;
                }
            }
        }
    }
    private void linkRooms(TETile[][] world) {
        while (links.count() > 1) {
            generateCorridor(world);
        }
    }
    private void generateCorridor(TETile[][] world) {
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        Position p = new Position(x, y);
        while (isWall(p) == -1) {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
            p = new Position(x, y);
        }
        int start = isWall(p);
        Position q = corridorBuilder(world, p);
        int end = isWall(q);
        links.union(start, end);
    }
    private Position corridorBuilder(TETile[][] world, Position p) {
        int dir = getDirection(world, p);
        layFloor(world, p);
        p = walk(p, dir);
        while (isWall(p) == -1) {
            layFloor(world, p);
            int roll = rand.nextInt(10); // turn left or right by chance
            if (roll == 0) {
                dir = (dir + 90) % 360;
            }
            if (roll == 1) {
                dir = (dir + 270) % 360;
            }
            p = walk(p, dir);
        }
        layFloor(world, p);
        return p;
    }
    private void layFloor(TETile[][] world, Position p) {
        if (p.x != 0 && p.y != 0 && p.x != WIDTH - 1 && p.y != HEIGHT - 1) {
            world[p.x][p.y] = Tileset.FLOOR;
        }
    }
    private Position walk(Position p, int dir) {
        if (dir == 0 && p.x + 1 < WIDTH - 1) {
            return new Position(p.x + 1, p.y);
        }
        if (dir == 90 && p.y + 1 < HEIGHT - 1) {
            return new Position(p.x, p.y + 1);
        }
        if (dir == 180 && p.x - 1 > 0) {
            return new Position(p.x - 1, p.y);
        }
        if (dir == 270 && p.y - 1 > 0) {
            return new Position(p.x, p.y - 1);
        }
        return walk(p, (dir + 90) % 360);
    }
    private int getDirection(TETile[][] world, Position p) {
        if (world[p.x + 1][p.y].equals(Tileset.NOTHING)) {
            return 0;
        } else if (world[p.x][p.y + 1].equals(Tileset.NOTHING)) {
            return 90;
        } else if (world[p.x - 1][p.y].equals(Tileset.NOTHING)) {
            return 180;
        } else {
            return 270;
        }
    } // return direction perpendicular outwards the room; E: 0, N: 90, W: 180, S: 270
    private int isWall(Position p) { //is wall of which room
        for (int i = 0; i < rooms.length; i++) {
            if (p.x == rooms[i].p.x || p.x == rooms[i].p.x + rooms[i].width - 1) {
                if (p.y > rooms[i].p.y && p.y < rooms[i].p.y + rooms[i].height - 1) {
                    return i;
                }
            }
            if (p.y == rooms[i].p.y || p.y == rooms[i].p.y + rooms[i].height - 1) {
                if (p.x > rooms[i].p.x && p.x < rooms[i].p.x + rooms[i].width - 1) {
                    return i;
                }
            }
        }
        return -1;
    }
    private static class Room {
        private int height;
        private int width;
        private Position p; //bottom left corner
        //the dimensions include walls and floor
        Room(int width, int height, Position p) {
            this.width = width;
            this.height = height;
            this.p = p;
        }
        public boolean legal() {
            if (this.p.x < 0 || this.p.x >= WIDTH || this.p.y < 0 || this.p.y >= HEIGHT) {
                return false;
            }
            if (this.p.x + this.width >= WIDTH || this.p.y + this.height >= HEIGHT) {
                return false;
            }
            return true;
        }

        public void floorUp(TETile[][] world) {
            int x = this.p.x;
            int y = this.p.y;
            for (int i = x + 1; i < x + this.width - 1; i++) {
                for (int j = y + 1; j < y + this.height - 1; j++) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }

        public boolean overlap(Room r) {
            int x1 = this.p.x;
            int y1 = this.p.y;
            int x2 = x1 + this.width;
            int y2 = y1 + this.height;
            int x3 = r.p.x;
            int y3 = r.p.y;
            int x4 = x3 + r.width;
            int y4 = y3 + r.height;
            boolean xSeparate = (x1 > x4) || (x2 < x3);
            boolean ySeparate = (y1 > y4) || (y2 < y3);
            return (!xSeparate) && (!ySeparate);
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        TETile[][] world = game.playWithInputString("n1234s");
        ter.renderFrame(world);
    }
}
