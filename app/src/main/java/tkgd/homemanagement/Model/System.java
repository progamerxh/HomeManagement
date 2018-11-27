package tkgd.homemanagement.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class System {
    @SerializedName("name")
    private String name;
    @SerializedName("photoUrl")
    private int photoID;
    @SerializedName("roomcount")
    private int roomcount;
    @SerializedName("id")
    private String id;
    private ArrayList<Room> rooms;

    public System() {
    }

    ;

    public System(String name, int roomcount,int photoID) {
        this.name = name;
        this.roomcount = roomcount;
        rooms = new ArrayList<>();
        this.photoID = photoID;
    }

    ;

    public System(String name, ArrayList<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public int getPhotoID() {
        return photoID;
    }

    public int getRoomcount() {
        return roomcount;
    }

    public void setRoomcount(int roomcount) {
        this.roomcount = roomcount;
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
