package fall2018.csc2017.BoardGameUtilities;

import java.io.Serializable;

import fall2018.csc2017.R;

/**
 * A tile that can be displayed on the screen.
 */
public class Tile implements Serializable {
    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * The constructor for Tile to set the background
     */
    protected Tile() {
        background = R.drawable.tile_blank;
    }

    /**
     * Set the background of the current Tile
     *
     * @param background the background
     */
    public void setBackground(int background) {
        this.background = background;
    }

    /**
     * The constructor for a Tile with a determined background
     *
     * @param background the background
     */
    public Tile(int background) {
        this.background = background;
    }
}
