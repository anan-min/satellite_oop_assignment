package unsw.models;
import unsw.response.models.EntityInfoResponse;
import unsw.utils.*;
import unsw.response.models.FileInfoResponse;
import unsw.response.models.EntityInfoResponse;



import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;


public abstract class Devices {
    protected String deviceId;
    protected Angle position;
    protected String type; 


    protected Double maxRange;
    protected List<Files> files = new ArrayList<Files>();

    /**
     * constructor for Devices
     */
    public Devices() {
        super();
    }

    /**
     * Constructor for devices
     * @param deviceId 
     * @param position
     * @param type
     */
    
    public Devices(String deviceId, Angle position, String type) {
        this.deviceId = deviceId;
        this.position = position;
        this.type = type;
        this.files = new ArrayList<Files>();
    }

    /**
     * getter for deviceId
     * @return
     */
    public String getDeviceId() {
        return this.deviceId;
    }

    /**
     * setter for device Id
     * @param deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     *  getter for position
     * @return
     */
    public Angle getPosition() {
        return this.position;
    }

    /**
     * setter for position 
     * @param position
     */
    public void setPosition(Angle position) {
        this.position = position;
    }

    /**
     * getter for type
     * @return
     */
    public String getType() {
        return this.type;
    }


    /**
     * getter for maxRange
     * @return
     */
    public Double getMaxRange() {
        return this.maxRange;
    }

    /**
     * setter for maxRange
     * @param maxRange
     */
    public void setMaxRange(Double maxRange) {
        this.maxRange = maxRange;
    }

    /**
     * getter for Files
     * @return
     */
    public List<Files> getFiles() {
        return this.files;
    }
    /**
     * add file to Device 
     * @param file file to add to device
     */
    public void addFile(Files file){
        this.files.add(file);
    }

    /**
     * remove file from devices
     * @param file target file to remove
     */
    public void removeFile(Files file){
        this.files.remove(file);
    }

    

    /**
     * create new file and addfile to device
     * @param filename
     * @param content
     */
    public void createFile(String filename, String content){
        Files newFile = new Files(filename, content);
        this.files.add(newFile);
    }

    /**
     * create entityInfoResponse
     * create hashmap that store all fileInfoResponse of file 
     * - add attributes of this device to the entityInfoResponse
     * - add hashmap that store fileinfos to entityInfoResponse
     * @return entityInfoResponse 
     */
    public EntityInfoResponse getInfo(){
        Map<String, FileInfoResponse> fileInfoResponses = new HashMap<>();
        for(Files file: files){
            fileInfoResponses.put(file.getFileName(), file.getInfo());
        }
        
        EntityInfoResponse entityInfoResponse = new EntityInfoResponse( this.deviceId, 
                                                                        this.position, 
                                                                        RADIUS_OF_JUPITER,
                                                                        this.type,
                                                                        fileInfoResponses   );
        return entityInfoResponse;
    }

    /**
     *  check if device able to sendFile to satellite
     * - check if visible 
     * - check if distance <= maxRange
     * - check if device is supported by satellite 
     * @param satellite
     * @return
     */
    public boolean isCommunicable(Satellites satellite){
        if(isVisible(satellite) && isInRange(satellite) && isSupported(satellite)){
            return true; 
        } 
        return false;
    }

    /**
     * find Files that have associated fileName 
     * @param fileName
     * @return
     */
    public Files findFile(String fileName){
        for(Files file: this.files){
            if(file.getFileName() == fileName){
                return file;
            }
        }
        return null;
    }

    /**
     * start file transferring at device
     * @param newFile
     */
    public void startRecievingFile(Files newFile){
        this.files.add(newFile);
    }
    /**
     *  stop receiving file and remove file from the recepient
     * @param newFile
     */
    public void stopReceivingFile(Files newFile){
        this.files.remove(newFile);
    }
    /**
     *  complete file transferring and change file status to hasTransferred
     * @param newFile
     */
    public void completeReceivingFile(Files newFile){
        newFile.setHasTranfered(true);
    }

    /**
     * downnload all file stored in the Devices
     */
    public void updateFile(){
        for(Files file: this.files){
            file.downloadFile();
        }
    }



    ////////////////////////////////// HELPER FUNCTIONS ////////////////////////////////////////////////

    /**
     * check if satellite is in range of device to send file 
     * - compare the distance with maxRange of device
     * @param satellite
     * @return ifSatelliteInRange
     */
    private boolean isInRange(Satellites satellite){
        Double distance = MathsHelper.getDistance(satellite.getHeight(), satellite.getPosition(), this.position);
        if(distance <= this.maxRange){
            return true;
        }
        return false;
    }

    /**
     * use function from mathhelper to check if satellite is visible by device
     * @param satellite
     * @return ifSatellitevisibleByDevice
     */
    private boolean isVisible(Satellites satellite){
        return MathsHelper.isVisible(satellite.getHeight(), satellite.getPosition(), this.position);
    }

    /**
     * // check if satellite support sending file from this device type
     * @param satellite
     * @return ifSatelliteSupportSendingFileFromDeviceType
     */
    private boolean isSupported(Satellites satellite){
        return satellite.getSupportDevices().contains(this.type);
    }

}
