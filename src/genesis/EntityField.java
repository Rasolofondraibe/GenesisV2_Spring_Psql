package genesis;

public class EntityField {
    private String name;
    private String type;
    private boolean primary;
    private boolean foreign;
    private String referencedField;
    private String referencedView;
    private String referencedInput;

    public String getReferencedInput() {
        return referencedInput;
    }
    public void setReferencedInput(String referencedInput) {
        this.referencedInput = referencedInput;
    }
    public String getReferencedView() {
        return referencedView;
    }
    public void setReferencedView(String referencedView) {
        this.referencedView = referencedView;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isPrimary() {
        return primary;
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
    public boolean isForeign() {
        return foreign;
    }
    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }
    public String getReferencedField() {
        return referencedField;
    }
    public void setReferencedField(String referencedField) {
        this.referencedField = referencedField;
    }

    public Entity getEntityForeignKey(Entity[] entities){
        for (Entity entity : entities) {
            if(entity.getTableName().equals(this.getName())){
                return entity;
            }
        }
        return null;
    }
    
}
