package fall2018.csc2017.memorymatrix;

import java.io.Serializable;

import fall2018.csc2017.BoardGameUtilities.Tile;
import fall2018.csc2017.R;

/**
 * A SlidingTilesTile in a sliding tiles puzzle.
 */
class MemoryMatrixTile extends Tile implements Serializable {

    /**
     * A final variable representing the background for a gray tile
     */
    static final int GRAY = R.drawable.gray;
    /**
     * A final variable representing the background for a red tile
     */
    static final int RED = R.drawable.red;
    /**
     * A final variable representing the background for a blue tile
     */
    static final int BLUE = R.drawable.blue;

    /**
     * Constructor for a Tile
     *
     * @param background takes in one of the three final values representing a background.
     */
    MemoryMatrixTile(int background) {
        super(background);
    }

}
