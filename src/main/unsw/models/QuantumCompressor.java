package unsw.models;



public class QuantumCompressor {

    /**
     * constructor for quantum compressor
     */
    public QuantumCompressor(){
        super();
    }

    /**
     * compress file and return new file size
     * @param file
     * @return
     */
    protected Integer quantumCompression(Files file){
        if(file.getContent().toLowerCase().contains("quantum")){
            return (Integer) Math.round(file.getFileSize() * 2/3) + 1;
        }
        return file.getFileSize();
    }
}
