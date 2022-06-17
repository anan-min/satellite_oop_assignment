package unsw.models;
import unsw.utils.*;
import java.util.Arrays;

public class RelaySatellite extends Satellites{
    private static final Integer FORWARD =   1;
    private static final Integer BACKWARD = -1;
    private Integer direction = FORWARD;
    

    /**
     * contructor for relay satellite
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public RelaySatellite(String satelliteId, String type, double height, Angle position){
        super(satelliteId, type, height, position);
        this.linearSpeed = 1500.00;
        this.supportDevices = Arrays.asList("HandheldDevice", "LaptopDevice", "DesktopDevice");
    }

    /**
     * move Relay satellite between region[140, 190] toggle the direction if out of range
     */
    @Override
    public void moveSatellite(Integer minutes) {

        if(this.position.compareTo(Angle.fromDegrees(190)) >= 0 && direction != BACKWARD){
            this.direction = BACKWARD;
        }else if(this.position.compareTo(Angle.fromDegrees(140)) <= 0 && direction != FORWARD){
            this.direction = FORWARD;
        }
        
        Double angularVelocity = this.linearSpeed / this.height;
        this.position = this.position.add(Angle.fromRadians(direction * angularVelocity * minutes));
    }

}
