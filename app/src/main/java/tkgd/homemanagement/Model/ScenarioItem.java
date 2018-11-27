package tkgd.homemanagement.Model;

public class ScenarioItem extends Data {
    private String name;
    private boolean isSelected;
    private String systemid;
    private String id;
    public ScenarioItem() {
    }

    ;

    public ScenarioItem(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public void setSelected(boolean selection) {
        this.isSelected = selection;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getName() {
        return name;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
