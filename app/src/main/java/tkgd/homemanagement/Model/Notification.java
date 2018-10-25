package tkgd.homemanagement.Model;

public class Notification extends Data {
    private String content;
    private int iconID;
    private String time;
    private boolean isSelected;

    public Notification(int iconid, String content, String time, boolean isSelected) {
        this.content = content;
        this.iconID = iconid;
        this.time = time;
        this.isSelected = isSelected;
    }

    public Notification(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setSelected(boolean selection) {
        this.isSelected = selection;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getContent() {
        return content;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
