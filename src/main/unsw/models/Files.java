package unsw.models;
import java.util.ArrayList;

import unsw.response.models.FileInfoResponse;

public class Files {
    
    protected String fileName;
    protected String content;
    protected Integer fileSize; 
    protected Boolean hasTranfered;


    /**
     * constructor for Files
     */
    public Files(){
        super();
    }

    /**
     * constructor for Files
     * @param fileName 
     * @param content
     */
    public Files(String fileName, String content){
        this.fileName = fileName; 
        this.content = content;
        this.fileSize = content.length();
        this.hasTranfered = true;
    }

    /**
     * getter for fileName
     * @return
     */
    public String getFileName(){
        return this.fileName;
    }
    /**
     * getter for content
     * @return
     */
    public String getContent(){
        return this.content;
    }

    /**
     * getter for fileSize
     * @return
     */
    public Integer getFileSize(){
        return this.fileSize;
    }

    /**
     * getter for hasTransferred
     * @return
     */
    public boolean hasTranfered(){
        return this.hasTranfered;
    }
    
    /**
     * setter for hasTransfered
     * @param hasTranfered
     */
    public void setHasTranfered(boolean hasTranfered){
        this.hasTranfered = hasTranfered;
    }

    /**
     * create file info response that store fileName, content, fileSize, hasTransferred 
     * if device is hasNotTransfered hide the content as ""
     * @return
     */
    public FileInfoResponse getInfo(){
        if(!this.hasTranfered){
            return new FileInfoResponse(this.fileName, "", this.fileSize, this.hasTranfered);
        }
        return new FileInfoResponse(this.fileName, this.content, this.fileSize, this.hasTranfered);
    }

    /**
     * download partial file by checking bandwidth and complete if size reach the fileSize
     */
    public void downloadFile(){
        
    }
 


}
