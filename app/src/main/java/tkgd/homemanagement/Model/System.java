package tkgd.homemanagement.Model;

import java.util.ArrayList;

public class System {
    private String Name;
    private int photoID;
    private ArrayList<Room> rooms;

    public System() {
    }

    ;

    public System(String name, int photoID) {
        this.Name = name;
        rooms = new ArrayList<>();
        this.photoID = photoID;
    }

    ;

    public System(String name, ArrayList<Room> rooms) {
        this.Name = name;
        this.rooms = rooms;
    }

    public String getName() {
        return Name;
    }

    public int getPhotoID() {
        return photoID;
    }
}
