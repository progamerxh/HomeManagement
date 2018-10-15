package tkgd.homemanagement.Model;

import java.util.ArrayList;

public class Room extends Data {
    private String Name;
    private int photoID;
    private ArrayList<Device> devices;

    public Room() {
    }

    ;

    public Room(String name, int photoID) {
        this.Name = name;
        devices = new ArrayList<>();
        this.photoID = photoID;
    }

    ;

    public Room(String name, ArrayList<Device> devices) {
        this.Name = name;
        this.devices = devices;
    }

    public String getName() {
        return Name;
    }

    public int getPhotoID() {
        return photoID;
    }
}
