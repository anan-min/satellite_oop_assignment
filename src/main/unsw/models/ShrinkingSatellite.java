package unsw.models;
import unsw.utils.*;
import java.util.Arrays;

public class ShrinkingSatellite extends Satellites{


    private Integer partialFileNum = 0;
    private QuantumCompressor quantumCompressor;

    /**
     * constructir for ShrikingSatellite
     */
    public ShrinkingSatellite() {
        super();
    }

    /**
     * constructor for ShrinkingSatellite
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public ShrinkingSatellite(String satelliteId, String type, double height, Angle position){
        super(satelliteId, type, height, position);
        this.linearSpeed = 1000.00;
        this.maxRange = 200_000.00;
        this.supportDevices = Arrays.asList("HandheldDevice", "LaptopDevice", "DesktopDevice");

        this.storageSize = 150;
        this.sendBandwidth = 10;
        this.recieveBandwidth = 15;
    }


  
 
}
