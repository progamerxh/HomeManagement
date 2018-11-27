package tkgd.homemanagement.Model;

public class Device extends Data {
    private String id;
    private String type;
    private String name;
    private String roomid;
    private Boolean isActive;
    private String config;
    public Device() {
    }

    public Device(String type, String name, String roomid, Boolean isActive) {
        this.type = type;
        this.name = name;
        this.roomid = roomid;
        this.isActive = false;
        this.config = "";
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
