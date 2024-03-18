package genesis;

import java.util.HashMap;

public class ViewConfig {
    private HashMap<String, String> types;
    private HashMap<String, String> typesInput;

    public HashMap<String, String> getTypes() {
        return types;
    }
    public void setTypes(HashMap<String, String> types) {
        this.types = types;
    }
    public HashMap<String, String> getTypesInput() {
        return typesInput;
    }
    public void setTypesInput(HashMap<String, String> typesInput) {
        this.typesInput = typesInput;
    }

}
