package models;
import unsw.models.*;
import unsw.utils.Angle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSatellites {
    @Test
    public void testConsturctor(){
        StandardSatellite standardSatellite = new StandardSatellite("satellite A", "StandardSatellite", 200000.00, Angle.fromDegrees(30));
        assertEquals("satellite A", standardSatellite.getSatelliteId());
        assertEquals("StandardSatellite", standardSatellite.getType());
        assertEquals(200000.00, standardSatellite.getHeight());
        assertEquals(Angle.fromDegrees(30), standardSatellite.getPosition());
    }
    @Test 
    public void testSubclassCharacteristics(){
        StandardSatellite standardSatellite = new StandardSatellite("satellite A", "StandardSatellite", 200000.00, Angle.fromDegrees(30));
        RelaySatellite relaySatellite = new RelaySatellite("satellite B", "RelaySatellite", 200000.00, Angle.fromDegrees(30));
        ShrinkingSatellite shrinkingSatellite = new ShrinkingSatellite("satellite C", "ShrinkingSatellite", 200000.00, Angle.fromDegrees(30));

        assertEquals(2500, standardSatellite.getLinearSpeed());
        assertEquals(1000, relaySatellite.getLinearSpeed());
        assertEquals(1000, shrinkingSatellite.getLinearSpeed());

        assertEquals(150_000, standardSatellite.getMaxRange());
        assertEquals(200_000, shrinkingSatellite.getMaxRange());
        
        assertEquals(1, standardSatellite.getSendBandwidth());
        assertEquals(1, standardSatellite.getRecieveBandwidth());

        assertEquals(10, shrinkingSatellite.getSendBandwidth());
        assertEquals(15, shrinkingSatellite.getRecieveBandwidth());


        assertEquals(3, standardSatellite.getStorageNumber());
        assertEquals(80, standardSatellite.getStorageSize());
        
        assertEquals(150, shrinkingSatellite.getStorageSize());

    }
}
