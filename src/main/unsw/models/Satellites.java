package unsw.models;
import unsw.utils.*;
import unsw.response.models.FileInfoResponse;
import unsw.response.models.EntityInfoResponse;


import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class Satellites {
    protected String satelliteId; 
    protected String type;
    protected double height; 
    protected Angle position;

    
    protected Double linearSpeed;
    protected Double maxRange;
    protected List<String> supportDevices;
    protected List<Files> files; 

    protected Integer storageNumber = 0;// 3 files
    protected Integer storageSize = 0;// 80 bytes
    protected Integer sendBandwidth = 0; 
    protected Integer recieveBandwidth = 0;

    protected Integer sendingFileNum = 0;
    protected Integer receivingFileNum = 0;


    /**
     * constructor for satellites
     */
    public Satellites() {
        super();
    }


    /**
     * constructor for satellites
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public Satellites(String satelliteId, String type, double height, Angle position) {
        this.satelliteId = satelliteId;
        this.type = type;
        this.height = height;
        this.position = position;
        this.files = new ArrayList<Files>();
    }

    /**
     * getter for satelliteId
     * @return
     */
    public String getSatelliteId() {
        return this.satelliteId;
    }

    /**
     * setter for satelliteID
     * @param satelliteId
     */
    public void setSatelliteId(String satelliteId) {
        this.satelliteId = satelliteId;
    }

    /**
     * getter type
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * setter type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for height
     * @return
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * setter for height
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }


    /**
     * getter for position 
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
     * getter for linear position 
     * @return
     */
    public Double getLinearSpeed() {
        return this.linearSpeed;
    }

    /**
     * setter for linear speed 
     * @param linearSpeed
     */
    public void setLinearSpeed(Double linearSpeed) {
        this.linearSpeed = linearSpeed;
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
     * getter for support device
     * @return
     */
    public List<String> getSupportDevices() {
        return this.supportDevices;
    }

    /**
     * settter for support Device
     * @param supportDevices
     */
    public void setSupportDevices(List<String> supportDevices) {
        this.supportDevices = supportDevices;
    }

    /**
     * getter for Files
     * @return
     */
    public List<Files> getFiles() {
        return this.files;
    }

    /**
     * setter for Files
     * @param files
     */
    public void setFiles(List<Files> files) {
        this.files = files;
    }

    /**
     * add file to the files in Satellite
     * @param file
     */
    public void addFile(Files file){
        this.files.add(file);
    }

    /**
     * getter for storage number
     * @return
     */
    public Integer getStorageNumber() {
        return this.storageNumber;
    }

    /**
     * setter for storage number
     * @param storageNumber
     */
    public void setStorageNumber(Integer storageNumber) {
        this.storageNumber = storageNumber;
    }

    /**
     * getter for storage number
     * @return
     */
    public Integer getStorageSize() {
        return this.storageSize;
    }

    /**
     * setter for storage size
     * @param storageSize
     */
    public void setStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
    }

    /**
     * getter for send bandwidth
     * @return
     */
    public Integer getSendBandwidth() {
        return this.sendBandwidth;
    }

    /**
     * setter for send bandwidth 
     * @param sendBandwidth
     */
    public void setSendBandwidth(Integer sendBandwidth) {
        this.sendBandwidth = sendBandwidth;
    }

    /**
     * getter for recieve bandwidth
     * @return
     */
    public Integer getRecieveBandwidth() {
        return this.recieveBandwidth;
    }

    /**
     * setter for receieve bandwidth 
     * @param recieveBandwidth
     */
    public void setRecieveBandwidth(Integer recieveBandwidth) {
        this.recieveBandwidth = recieveBandwidth;
    }


    /**
     * store file info reponse in hash map
     * create new EntityInfoResponse
     * - satlliteId
     * - position 
     * - height 
     * - hashmap store fileinfor responmse of all files
     * 
     * @return
     */
    public EntityInfoResponse getInfo(){
                                                                        
        Map<String, FileInfoResponse> fileInfoResponses = new HashMap<>();
        for(Files file: files){
            fileInfoResponses.put(file.getFileName(), file.getInfo());
        }
        
        EntityInfoResponse entityInfoResponse = new EntityInfoResponse( this.satelliteId, 
                                                                        this.position, 
                                                                        this.height, 
                                                                        this.type,           
                                                                        fileInfoResponses );

        return entityInfoResponse;
    }
               
    /**
     * move Satellite depending on the linear speed, height, time in minutes
     * @param minutes
     */
    public void moveSatellite(Integer minutes){
        Double angularVelocity = this.linearSpeed / this.height;
        this.position = this.position.add(Angle.fromRadians(angularVelocity * minutes));
    }

    /**
     * check if this satellite able to send file to other satellite 
     * - isInrange(other) check if other satelite is in this satellite range
     * - isVisible(other) check if other satellite is visible by this satellite
     * @param other toSatellite
     * @return if this satellite able to send file to other satellite
     */
    public boolean isCommunicable(Satellites other){
        if( this.isInRange(other)  && this.isVisible(other)){
            return true;
        }
        return false;
    }   

    /**
     * check if this satellite able to sendfile to device
     * - check if device is in satellite max rane 
     * - check if satellite support device type
     * - check if device is visible by satelluite
     * @param device
     * @return
     */
    public boolean isCommunicable(Devices device){
        if(this.isInRange(device) && this.isVisible(device) && this.isSupported(device)){
            return true;
        }
        return false;
    }

    /**
     * find file with associated filename in the files(storage)
     * - return null if file not existed
     * @param fileName
     * @return Files file 
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
     * call download file on every file in files(storage) to update file 
     * depending on the bandwidth 
     */
    public void updateFile(){
        for(Files file: files){
            file.downloadFile();
        }
    }

    /**
     * start receicing file 
     * - increse number of file receiving by this satellite
     * - add partial file to storage
     * @param file
     */
    public void startRecievingFile(Files file){
        this.receivingFileNum += 1;
        this.files.add(file);
    }

    /**
     * completer receuving file
     * - decrement number of sending file from this satellite 
     * - set status of this satellite to hasTransferred
     * @param file
     */
    public void completeReceivingFile(Files file){
        this.receivingFileNum -= 1;
        file.setHasTranfered(true);
    }

    /**
     * start sending File
     * - increment number of file sending by this device
     * @param file
     */
    public void startSendingFile(Files file){
        this.receivingFileNum += 1;
    }

    /**
     * complete the sending file 
     * - decrement number of file sending by this device
     * @param file
     */
    public void completeSendingFile(Files file){
        this.sendingFileNum -= 1;
    }

    /**
     * get sendbandwith per file by seperate bandwidth between files
     * @return
     */
    public Integer getUnitsendBandwidth(){
        return (Integer) Math.round(this.sendBandwidth/ this.sendingFileNum);
    }

    /**
     * get recieve bandwidth per file by seperate bandwidth between file
     * @return
     */
    public Integer getUnitreceiveBandwidth(){
        return (Integer) Math.round(this.recieveBandwidth/ this.receivingFileNum);
    }
    /////////////////////////////////// HELPER FUNCTIONS ///////////////////////////////////

    /**
     * check if other satellite is inRange of this Satellite maxRange
     * @param other
     * @return ifOtherSatelliteInRange
     */
    private boolean isInRange(Satellites other){
        Double distance = MathsHelper.getDistance(this.height, this.position, other.getHeight(), other.position);
        if(distance <= this.maxRange){
            return true;
        }
        return false;
    }
    
    /**
     * check if other satellite is visible by this satellite
     * @param other
     * @return ifOtherSatelliteVisible
     */
    private boolean isVisible(Satellites other){
        return MathsHelper.isVisible(this.height, this.position, other.height, other.position);
    }

    /**
     * check if Device is in range of Satellite
     * @param device
     * @return
     */
    private boolean isInRange(Devices device){
        Double distance = MathsHelper.getDistance(this.height, this.position, device.getPosition());
        if(distance <=  this.maxRange){
            return true;
        }
        return false;
    }
    
    /**
     * check if satellite is visible by device
     * @param device
     * @return ifDeviceVisible
     */
    private boolean isVisible(Devices device){
        if(MathsHelper.isVisible(this.height, this.position, device.getPosition())){
            return true;
        }
        return false;
    }

    /**
     * check if satellite support device
     * @param device
     * @return ifDeviceIsSupported
     */
    private boolean isSupported(Devices device){
        String deviceType = device.getType();
        if(this.supportDevices.contains(deviceType)){
            return true;
        }
        return false;
    }
}
