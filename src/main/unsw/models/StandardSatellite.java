package unsw.models;

import unsw.utils.*;
import java.util.Arrays;

public class StandardSatellite extends Satellites{
    

    /**
     * constructor for Standard Satellite
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public StandardSatellite(String satelliteId, String type, double height, Angle position){
        super(satelliteId, type, height, position);        
        this.linearSpeed = 2500.00;
        this.maxRange = 150_000.00;

        this.storageNumber = 3;
        this.storageSize = 80;
        this.sendBandwidth = 1;
        this.recieveBandwidth = 1;
        
        this.supportDevices = Arrays.asList("HandheldDevice", "LaptopDevice");
       
    }

    
        



}
