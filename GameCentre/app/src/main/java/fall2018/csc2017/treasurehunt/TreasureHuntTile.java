package fall2018.csc2017.treasurehunt;

import java.io.Serializable;

import fall2018.csc2017.BoardGameUtilities.Tile;
import fall2018.csc2017.R;

/**
 * A tile in the game Treasure Hunt.
 */
class TreasureHuntTile extends Tile implements Serializable {

    /**
     * An integer that refers to image of a black tile.
     */
    static final int BLACK = R.drawable.treasure_0;

    /**
     * An integer that refers to the image of a person.
     */
    static final int PERSON = R.drawable.treasure_1;

    /**
     * An integer that refers to the image of a treasure.
     */
    static final int TREASURE = R.drawable.treasure_2;

    /**
     * An integer that refers to a blank tile.
     */
    static final int BLANK = R.drawable.treasure_blank;

    /**
     * An integer that refers to a brick tile.
     */
    static final int BRICK = R.drawable.treasure_3;

    /**
     * This tile's type. A tile's type can either be PERSON, TREASURE, BLANK, OR BLACK.
     */
    private int type;

    /**
     * Initialize this tile.
     *
     * @param type the type that this tile is.
     */
    TreasureHuntTile(int type) {
        super(BLACK);
        this.type = type;
    }

    /**
     * Set the background image of this tile to this tile's type.
     */
    void reveal() {
        setBackground(type);
    }

    /**
     * Returns this tile's type.
     *
     * @return this tile's type.
     */
    int getType() {
        return type;
    }

    /**
     * Set the this tile's type.
     *
     * @param type the type to set this tile's type to.
     */
    void setType(int type) {
        this.type = type;
    }
}
