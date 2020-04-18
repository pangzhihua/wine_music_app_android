package cn.pang.winemusic.models;

public class Song {

    private Integer id;
    private String name;
    private Integer audioId;
    private Integer imageId;
    private String imageUrl;
    private Integer sheet;
    private String type;
    private Integer fileSize;
    private String remark; //出品
    private String title;
    private Integer price; //价格
    private Integer albumId;
    private String extName; //后缀名
    private Integer inLine;  //是否听到

    public Integer getInLine() {
        return inLine;
    }

    public void setInLine(Integer inLine) {
        this.inLine = inLine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAudioId() {
        return audioId;
    }

    public void setAudioId(Integer audioId) {
        this.audioId = audioId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSheet() {
        return sheet;
    }

    public void setSheet(Integer sheet) {
        this.sheet = sheet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", audioId=" + audioId +
                ", imageId=" + imageId +
                ", imageUrl='" + imageUrl + '\'' +
                ", sheet=" + sheet +
                ", type='" + type + '\'' +
                ", fileSize=" + fileSize +
                ", remark='" + remark + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", albumId=" + albumId +
                ", extName='" + extName + '\'' +
                ", inLine=" + inLine +
                '}';
    }
}
