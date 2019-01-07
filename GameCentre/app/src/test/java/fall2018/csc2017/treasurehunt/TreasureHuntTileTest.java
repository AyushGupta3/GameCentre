package fall2018.csc2017.treasurehunt;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * A set of tests for TreasureHuntTile.
 */
public class TreasureHuntTileTest {

    /**
     * Test whether or not the constructor of the TreasureHuntTile correctly initializes its
     * variable.
     */
    @Test
    public void testTileConstructor() {
        TreasureHuntTile blankTile = new TreasureHuntTile(TreasureHuntTile.BLANK);
        assertEquals(blankTile.getType(), TreasureHuntTile.BLANK);
        assertEquals(blankTile.getBackground(), TreasureHuntTile.BLACK);
        TreasureHuntTile brickTile = new TreasureHuntTile(TreasureHuntTile.BRICK);
        assertEquals(brickTile.getType(), TreasureHuntTile.BRICK);
        assertEquals(brickTile.getBackground(), TreasureHuntTile.BLACK);
    }

    /**
     * Test whether or not the method setType in the TreasureHuntTile correctly sets the type of the
     * tiles.
     */
    @Test
    public void testTileSetType() {
        TreasureHuntTile blankTile = new TreasureHuntTile(TreasureHuntTile.BLANK);
        assertEquals(blankTile.getType(), TreasureHuntTile.BLANK);
        blankTile.setType(TreasureHuntTile.BRICK);
        assertEquals(blankTile.getType(), TreasureHuntTile.BRICK);
    }

    /**
     * Test whether or not the method reveal in the TreasureHuntTile correctly reveals the tile's
     * background color.
     */
    @Test
    public void testTileReveal() {
        TreasureHuntTile blankTile = new TreasureHuntTile(TreasureHuntTile.BLANK);
        assertEquals(blankTile.getBackground(), TreasureHuntTile.BLACK);
        blankTile.reveal();
        assertEquals(blankTile.getBackground(), TreasureHuntTile.BLANK);
    }
}
