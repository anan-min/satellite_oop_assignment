package unsw.models;
import unsw.utils.*;


public class DesktopDevice extends Devices{
    /**
     * 
     * @param deviceId string represent device
     * @param position angle in degree or radian 
     * @param type strin represent type 
     */
    public DesktopDevice(String deviceId, Angle position, String type){
        super(deviceId, position, type);
        this.maxRange = 200_000.00;
    }
}
