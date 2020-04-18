package cn.pang.winemusic.models;

public class SongSheet {

    private String name;
    private int imageId;
    private int number;

    public SongSheet(String name, int imageId, int number) {
        this.name = name;
        this.imageId = imageId;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
