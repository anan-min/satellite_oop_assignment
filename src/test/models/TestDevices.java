package models;
import unsw.models.*;
import unsw.utils.Angle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDevices {
    @Test
    public void testBasics(){
        HandheldDevice handheld = new HandheldDevice(   "device a", 
                                                        Angle.fromDegrees(20), 
                                                        "HandheldDevice"        );
        assertEquals("device a", handheld.getDeviceId());
        assertEquals(Angle.fromDegrees(20), handheld.getPosition());
        assertEquals("HandheldDevice", handheld.getType());
        assertEquals(50_000.00, handheld.getMaxRange());
    }

    @Test 
    public void testSubclass(){
        HandheldDevice handheldDevice = new HandheldDevice("device a", Angle.fromDegrees(30), "HandheldDevice");
        LaptopDevice laptopDevice = new LaptopDevice("device b", Angle.fromDegrees(30), "LaptopDevice");
        DesktopDevice desktopDevice = new DesktopDevice("device c", Angle.fromDegrees(30), "DesktopDevice");


        assertEquals(50_000.00, handheldDevice.getMaxRange());
        assertEquals(100_000.00, laptopDevice.getMaxRange());
        assertEquals(200_000.00, desktopDevice.getMaxRange());
    }
}
