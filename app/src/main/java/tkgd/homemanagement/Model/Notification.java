package tkgd.homemanagement.Model;

public class Notification extends Data {
    private boolean isSelected;

    public Notification(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setSelected(boolean selection) {
        this.isSelected = selection;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
