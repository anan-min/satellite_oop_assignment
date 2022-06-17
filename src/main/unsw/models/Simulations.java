package unsw.models;

import unsw.utils.*;
import unsw.models.*;
import java.util.List;
import java.util.ArrayList;
import unsw.response.models.EntityInfoResponse;
import unsw.blackout.FileTransferException;


public class Simulations {
    private List<Devices> deviceList = new ArrayList<Devices>();
    private List<Satellites> satelliteList = new ArrayList<Satellites>();
    private List<String> deviceIds = new ArrayList<String>();
    private List<String> satelliteIds = new ArrayList<String>();

    /**
     * constructor for Simulations 
     */
    public Simulations(){
        super();
    }

    /**
     * create device and add device to deviceList and add its Id to deviceIDs
     * @param deviceId
     * @param type
     * @param position
     */
    public void createDevice(String deviceId, String type, Angle position) {
        switch(type){
            case "HandheldDevice":
                HandheldDevice handheldDevice = new HandheldDevice(deviceId, position, type);
                this.deviceList.add(handheldDevice);
                this.deviceIds.add(deviceId);
                break;
            case "LaptopDevice":
                LaptopDevice laptopDevice = new LaptopDevice(deviceId, position, type);
                this.deviceList.add(laptopDevice);
                this.deviceIds.add(deviceId);
                break;
            case "DesktopDevice":
                DesktopDevice desktopDevice = new DesktopDevice(deviceId, position, type);
                this.deviceList.add(desktopDevice);
                this.deviceIds.add(deviceId);
                break;
        }

    }

    /**
     * find Device in deviceList and remove from both deviceList and deviceId
     * @param deviceId
     */
    public void removeDevice(String deviceId) {
        Devices device = findDeviceWithId(deviceId);
        if(device != null){
            deviceList.remove(device);
            deviceIds.remove(deviceId);
        }
    }

    /**
     * create satellite and add it to satelliteLsit and its Id to satelltieIds
     * @param satelliteId
     * @param type
     * @param height
     * @param position
     */
    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        switch(type){
            case "StandardSatellite":
                StandardSatellite standardSatellite  = new StandardSatellite(satelliteId, type, height, position);
                this.satelliteList.add(standardSatellite);
                this.satelliteIds.add(satelliteId);
                break;
            case "ShrinkingSatellite":
                ShrinkingSatellite shrinkingSatellite = new ShrinkingSatellite(satelliteId, type, height, position);
                this.satelliteList.add(shrinkingSatellite);
                this.satelliteIds.add(satelliteId);
                break;
            case "RelaySatellite":
                RelaySatellite relaySatellite = new RelaySatellite(satelliteId, type, height, position);
                this.satelliteList.add(relaySatellite);
                this.satelliteIds.add(satelliteId);
                break;
        }
    }

    /**
     * findDevice and remove Satellite from satelliteList 
     * and remove its id from SatelliteIds
     * @param satelliteId
     */
    public void removeSatellite(String satelliteId) {
        Satellites satellite = findSatelliteWithId(satelliteId);
        if(satellite != null){
            satelliteList.remove(satellite);
            satelliteIds.remove(satelliteId);
        }

    }

    /**
     * return list for deviceIds
     * @return
     */
    public List<String> listDeviceIds() {
        return this.deviceIds;
    }

    /**
     * return List for SatelliteIds
     * @return
     */
    public List<String> listSatelliteIds() {
        return this.satelliteIds;
    }

    /**
     * create newFile and store in Device
     * @param deviceId
     * @param filename
     * @param content
     */
    public void addFileToDevice(String deviceId, String filename, String content) {
        Devices device = findDeviceWithId(deviceId);
        device.createFile(filename, content);
    }

    /**
     * find entity from its ids then call getinfo to get info respnse of 
     * that entity
     * @param id
     * @return
     */
    public EntityInfoResponse getInfo(String id) {
        // TODO: Task 1h)
        Devices device = findDeviceWithId(id);
        Satellites satellite = findSatelliteWithId(id);
        if(device != null){
            return device.getInfo();
        }
        if(satellite != null){
            return satellite.getInfo();
        }
        return null;
    }

    /**
     * move every satellite with given time by using moveSatelltie in Satellite
     * @param minutes
     */
    public void updateSatellitePosition(Integer minutes){
        for(Satellites satellite: satelliteList){
            satellite.moveSatellite(minutes);
        }
    }

    /**
     * find entity with id and return Id of every entity that able to sendFile to
     * @param id
     * @return entityIds
     */
    public List<String> communicableEntitiesInRange(String id) {
        // TODO Task 2b)
        List<String> entitieList = new ArrayList<>();

        Devices device = findDeviceWithId(id);
        Satellites satellite = findSatelliteWithId(id);

        if(device != null){
            return communicableEntitiesInRange(device);
        }
        if(satellite != null){
            return communicableEntitiesInRange(satellite);
        }
   
        return null;
    }


    /**
     * return Id of every entity that is communicable by  device
     * @param device
     * @return entityIds
     */
    public List<String> communicableEntitiesInRange(Devices device) {
        // TODO Task 2b)
        List<String> entitieList = new ArrayList<>();
        
        for(Satellites otherSatellite: this.satelliteList){
            if(device.isCommunicable(otherSatellite)){
                entitieList.add(otherSatellite.getSatelliteId());
            }
        }

        return entitieList;
    }

    /**
     * return all Ids of entity that is communicable by satellite
     * @param satellite
     * @return
     */
    public List<String> communicableEntitiesInRange(Satellites satellite) {
        // TODO Task 2b)
        List<String> entitieList = new ArrayList<>();
        
        for(Satellites otherSatellite: this.satelliteList){
            if(satellite.isCommunicable(otherSatellite)  && satellite != otherSatellite){
                entitieList.add(otherSatellite.getSatelliteId());
            }
        }
        for(Devices otherDevice: this.deviceList){
            if(satellite.isCommunicable(otherDevice)){
                entitieList.add(otherDevice.getDeviceId());
            }
        }
        return entitieList;
    }

    /**
     * find entity in fromId and toId and sendfile and find file 
     * from the sender and send it to receiver 
     * @param fileName
     * @param fromId
     * @param toId
     * @throws FileTransferException
     */
    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        Satellites fromSatellite = findSatelliteWithId(fromId);
        Devices fromDevice = findDeviceWithId(fromId);
        Satellites toSatellite = findSatelliteWithId(toId);
        Devices toDevice = findDeviceWithId(toId);

        if(fromSatellite != null && toDevice != null){
            sendFile(fileName,fromSatellite, toDevice);
        }
        if(fromSatellite != null && toSatellite != null){
            sendFile(fileName, fromSatellite, toSatellite);
        }
        if(fromDevice != null && toSatellite != null){
            sendFile(fileName, fromDevice, toSatellite);
        }
    }

    /**
     * senfile
     * - check if file is in sender 
     * - check if file is not in receiver
     * - creat partial file at reveiver
     * @param fileName
     * @param fromSatellite
     * @param toDevice
     * @throws FileTransferException
     */
    public void sendFile(String fileName, Satellites fromSatellite, Devices toDevice) throws FileTransferException {
        Files file = fromSatellite.findFile(fileName);
        if(file == null){
            // throw exception 
            throw new FileTransferException.VirtualFileNotFoundException("file not found in fromSatellite");
        }
        if(toDevice.findFile(fileName) != null){
            // throw exception 
            throw new FileTransferException.VirtualFileAlreadyExistsException("file already existed in toDevice");
        }

        PartialFile newFile = new PartialFile(file, fromSatellite, null, null, toDevice);
        toDevice.startRecievingFile(newFile);
        
    }

    /**
     * sendFile 
     * - check if file is in sender 
     * - check if file is not in reciever 
     * - create Partial file at the reciever
     * @param fileName
     * @param fromSatellite
     * @param toSatellite
     * @throws FileTransferException
     */
    public void sendFile(String fileName, Satellites fromSatellite, Satellites toSatellite) throws FileTransferException {
        Files file = fromSatellite.findFile(fileName);
        if(file == null){
            // throw exception 
            throw new FileTransferException.VirtualFileNotFoundException("file not found in fromSatellite");

        }
        if(toSatellite.findFile(fileName) != null){
            // throw exception
            throw new FileTransferException.VirtualFileAlreadyExistsException("file already existed in toSatellite");

        }
        PartialFile newFile = new PartialFile(file, fromSatellite, null, toSatellite, null);
        fromSatellite.startRecievingFile(newFile);

    }

    /**
     * sendFile
     * - check if file is in sender
     * - check if file is not in reciever
     * - create partial file at receiver
     * @param fileName
     * @param fromDevice
     * @param toSatellite
     * @throws FileTransferException
     */
    public void sendFile(String fileName, Devices fromDevice, Satellites toSatellite) throws FileTransferException {
        Files file = fromDevice.findFile(fileName);
        if(file == null){
            // throw exception 
            throw new FileTransferException.VirtualFileNotFoundException("file not found in fromDevice");

        }

        if(toSatellite.findFile(fileName) != null){
            // throw exception 
            throw new FileTransferException.VirtualFileAlreadyExistsException("file already existed in toSatellite");

        }
        PartialFile newFile = new PartialFile(file, null, fromDevice, toSatellite, null);
        toSatellite.startRecievingFile(newFile);
    }
  
    /**
     * call updatefile on every entity in simulation 
     */
    public void updateFile(){
        for(Satellites satellites: satelliteList){
            satellites.updateFile();
        }
        for(Devices device: deviceList){
            device.updateFile();
        }
    }


    ////////////////////////////////////////// HELPER FUNCTIONS //////////////////////////////////
    /**
     * search in deviceList  and find devive with associated id
     * @param deviceId
     * @return
     */
    private Devices findDeviceWithId(String deviceId){

        for(Devices device: this.deviceList){
            if(device.getDeviceId() == deviceId){
                return device;
            }
        }
        return null;
    }
    /**
     * search in satelliteList and find device with associated id
     * @param satelliteId
     * @return
     */
    private Satellites findSatelliteWithId(String satelliteId){
        for(Satellites satellite: this.satelliteList){
            if(satellite.getSatelliteId() == satelliteId){
                return satellite;
            }
        }
        return null;
    }


    ///////////////////////////////// isCommunicable helper functions /////////////////////////
    /**
     * check if satellite 1 and 2 is commnunicale by themselve or using relay
     * @param satellite1
     * @param satellite2
     * @return ifsatellitesCommunicable
     */
    private boolean isCommunicable(Satellites satellite1, Satellites satellite2){
        if(satellite1.isCommunicable(satellite2)){
            return true;
        } 


        // find relay that sat1 can communicate to and can communicate to sat2
        for(Satellites relaySatellites: satelliteList){
            if( relaySatellites != satellite1                   &&
                relaySatellites != satellite2                   &&

                relaySatellites.getType() == "RelaySatellite"   &&
                satellite1.isCommunicable(relaySatellites)      &&
                relaySatellites.isCommunicable(satellite2)
            ){
                return true;
            }
        } 

        return false;  
        
    }

    /**
     * check if satellite and device communicable by themselve or using relay
     * @param satellite
     * @param device
     * @return ifSatelliteAnddeviceCommunicabel
     */
    private boolean isCommunicable(Satellites satellite, Devices device){
        if(satellite.isCommunicable(device)){
            return true;
        }

        // find relay that satellite can communicate to and can communicate to device
        for(Satellites relaySatellites: satelliteList){
            if( relaySatellites != satellite                    &&
                relaySatellites.getType() == "RelaySatellite"   &&
                satellite.isCommunicable(relaySatellites)      &&
                relaySatellites.isCommunicable(device)
            ){
                return true;
            }
        } 
        return false;
    }

    /**
     * check if device commmunicable with satellite by themselve or using relay
     * @param device
     * @param satellite
     * @return ifDeviceCommunicableWithSattellite
     */
    private boolean isCommunicable(Devices device, Satellites satellite){
        if(device.isCommunicable(satellite)){
            return true;
        }

        // find relay that satellite can communicate to and can communicate to device
        for(Satellites relaySatellites: satelliteList){
            if( relaySatellites != satellite                    &&
                relaySatellites.getType() == "RelaySatellite"   &&
                device.isCommunicable(relaySatellites)      &&
                relaySatellites.isCommunicable(satellite)
            ){
                return true;
            }
        } 
        
        return false;
    }

    /**
     * check if Device supported by Satellite
     * @param satellite
     * @param device
     * @return
     */
    private boolean isSupported(Satellites satellite, Devices device){
        String deviceType = device.getType();
        return satellite.getSupportDevices().contains(deviceType);
    }

    /////////////////////// File transfer helper function ///////////////////
    
    


}
