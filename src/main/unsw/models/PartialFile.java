package unsw.models;

public class PartialFile extends Files {
    Satellites fromSatellite;
    Devices fromDevice;
    Satellites toSatellite;
    Devices toDevice;
    Integer currentSize;


    /**
     * constuctor for PartialFile
     * 
     * create partial file with complete file 
     * from and to attribute store sender and reciever of the file
     * - null if none
     * - curreneSize -> download size of the file 
     * @param file
     * @param fromSatellite
     * @param fromDevice
     * @param toSatellite
     * @param toDevice
     */
    public PartialFile(Files file, Satellites fromSatellite, Devices fromDevice, Satellites toSatellite, Devices toDevice){
        this.fileName = file.getFileName();
        this.content = file.getContent();
        this.fileSize = file.getFileSize();
        this.hasTranfered = false;
        this.currentSize = 0;


        this.fromSatellite = fromSatellite;
        this.fromDevice = fromDevice;
        this.toSatellite = toSatellite;
        this.toDevice =  toDevice;
    }


    /** 
     * call download file with different parameter depending on 
     * the sender and reciever type 
     */
    @Override
    public void downloadFile() {

        if(this.fromSatellite != null && this.toDevice != null){
            downloadFile(this.fromSatellite, this.toDevice);
        }
        if(this.fromSatellite != null && this.toSatellite != null){
            downloadFile(this.fromSatellite, this.toDevice);
        }
        if(this.fromDevice != null && this.toSatellite != null){
            downloadFile(this.fromDevice, this.toSatellite);
        }




    }

    /**
     * download file by getteting bandwidth from the fromSatellite and 
     * increment the downloadedSize. stop if not communicable. complete 
     * if currentSize equal or morethan the filesize 
     * @param fromSatellite
     * @param toDevice
     */
    public void downloadFile(Satellites fromSatellite, Devices toDevice){

        this.currentSize += fromSatellite.getSendBandwidth();
        if(this.currentSize >=  this.fileSize){
            fromSatellite.completeSendingFile(this);
            toDevice.completeReceivingFile(this);
        }
    }

    /**
     * download file by comparing sending bandwidth fromSatellite 
     * and recieving bandwidth toSatellite use the smaller bandwidth as a capped 
     * - stop transferring if not communicable 
     * - complete transferring if  current size equal or morethan fileSize
     * @param fromSatellite
     * @param toSatellite
     */
    public void downloadFile(Satellites fromSatellite, Satellites toSatellite){

        this.currentSize += 1;
        if(this.currentSize >=  this.fileSize){
            fromSatellite.completeReceivingFile(this);
            toSatellite.completeReceivingFile(this);
        }
    }

    /**
     * download satellite by get receiving bandwidth from toSatellite 
     * - stop transferring if not communicable 
     * - complete transferrig if current size higher or equal to fileSize
     * @param fromDevice
     * @param toSatellites
     */
    public void downloadFile(Devices fromDevice, Satellites toSatellites){

        this.currentSize += toSatellites.getRecieveBandwidth();
        if(this.currentSize >=  this.fileSize){
            toSatellites.completeReceivingFile(this);
        }
    }


}
