package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class Save implements Serializable {
    private static final long serialVersionUID = 1234L;
    TETile[][] world;
    Position player;
}
