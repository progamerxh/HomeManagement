package tkgd.homemanagement.Model;

public class ScenarioItem extends Data {
    private String Name;
    private boolean isSelected;

    public ScenarioItem() {
    }

    ;

    public ScenarioItem(String name, boolean isSelected) {
        this.Name = name;
        this.isSelected = isSelected;
    }

    public void setSelected(boolean selection) {
        this.isSelected = selection;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getName() {
        return Name;
    }
}
