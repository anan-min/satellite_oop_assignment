package unsw.blackout;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;
import java.util.ArrayList;
import java.util.List;


import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;
import unsw.models.*;

public class BlackoutController {
    
    Simulations simulation = new Simulations();


    public void createDevice(String deviceId, String type, Angle position) {
        this.simulation.createDevice(deviceId, type, position);
    }

    public void removeDevice(String deviceId) {
        this.simulation.removeDevice(deviceId);
    }

    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        this.simulation.createSatellite(satelliteId, type, height, position);
    }

    public void removeSatellite(String satelliteId) {
        this.simulation.removeSatellite(satelliteId);
    }

    public List<String> listDeviceIds() {
        return this.simulation.listDeviceIds();
    }

    public List<String> listSatelliteIds() {
        return this.simulation.listSatelliteIds();
    }

    public void addFileToDevice(String deviceId, String filename, String content) {
        this.simulation.addFileToDevice(deviceId, filename, content);
    }

    public EntityInfoResponse getInfo(String id) {
        return this.simulation.getInfo(id);
    }

    public void simulate() {
        simulation.updateSatellitePosition(1);
        simulation.updateFile();
    }

    /**
     * Simulate for the specified number of minutes.
     * You shouldn't need to modify this function.
     */
    public void simulate(int numberOfMinutes) {
        for(int i = 0; i < numberOfMinutes; i++){
            simulate();
        }
    }

    public List<String> communicableEntitiesInRange(String id) {
        // TODO Task 2b)
        return this.simulation.communicableEntitiesInRange(id);
    }

    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        // TODO Task 2c)
        this.simulation.sendFile(fileName, fromId, toId);
    }

    public static void main(String[] args) {

        BlackoutController controller = new BlackoutController();

        controller.createSatellite("Satellite1", "StandardSatellite", 100 + RADIUS_OF_JUPITER, Angle.fromDegrees(340));
        controller.createDevice("DeviceA", "HandheldDevice", Angle.fromDegrees(30));
        controller.createDevice("DeviceB", "LaptopDevice", Angle.fromDegrees(180));
        controller.createDevice("DeviceC", "DesktopDevice", Angle.fromDegrees(330));
        controller.listSatelliteIds();
    }

}
