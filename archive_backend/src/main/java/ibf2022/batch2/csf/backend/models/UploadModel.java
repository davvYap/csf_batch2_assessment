package ibf2022.batch2.csf.backend.models;

public class UploadModel {
    private String filename;
    private String contentType;
    private long contentSize;
    private byte[] content;

    public UploadModel() {
    }

    public UploadModel(String filename, String contentType, long contentSize, byte[] content) {
        this.filename = filename;
        this.contentType = contentType;
        this.contentSize = contentSize;
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getContentSize() {
        return contentSize;
    }

    public void setContentSize(long contentSize) {
        this.contentSize = contentSize;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
