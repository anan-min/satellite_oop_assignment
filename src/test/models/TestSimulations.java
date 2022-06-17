package models;
import unsw.models.*;
import unsw.utils.Angle;
import unsw.utils.MathsHelper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;
import java.util.List;

public class TestSimulations { 
    @Test
    public void testBasics(){
        Angle deviceD_position =  Angle.fromDegrees(180);
        Angle satellite3_position = Angle.fromDegrees(160);
        Double satellite3_height = 2000 + RADIUS_OF_JUPITER;
        assertEquals(true, MathsHelper.isVisible(satellite3_height, satellite3_position, deviceD_position));
    }


}
