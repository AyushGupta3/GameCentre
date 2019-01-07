package fall2018.csc2017.BoardGameUtilities;

import android.widget.Button;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

/**
 * Test the CustomAdapter
 */

public class CustomAdapterTest {

    /**
     * Test the constructor for CustomAdapter
     */
    @Test
    public void testConstructor() {
        CustomAdapter customAdapter = new CustomAdapter(new ArrayList<Button>(), 1, 1);
        assertEquals(0, customAdapter.getCount());
    }
}
