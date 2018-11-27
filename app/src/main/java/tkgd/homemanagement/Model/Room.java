package tkgd.homemanagement.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Room extends Data {
    private String name;
    private int photoID;
    @SerializedName("id")
    private String id;
    private ArrayList<Device> devices;

    public Room() {
    }

    ;

    public Room(String name, int photoID) {
        this.name = name;
        devices = new ArrayList<>();
        this.photoID = photoID;
    }

    ;

    public Room(String name, ArrayList<Device> devices) {
        this.name = name;
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
