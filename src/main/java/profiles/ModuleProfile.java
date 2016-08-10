package profiles;

/**
 *
 * @author Kamalion
 */
public class ModuleProfile {
    
    private int id;
    private String matgroup;
    private int temp;
    private float value;

    public ModuleProfile(int id, String matgroup, int temp, float value) {
        this.id = id;
        this.matgroup = matgroup;
        this.temp = temp;
        this.value = value;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the matgroup
     */
    public String getMatgroup() {
        return matgroup;
    }

    /**
     * @param matgroup the matgroup to set
     */
    public void setMatgroup(String matgroup) {
        this.matgroup = matgroup;
    }

    /**
     * @return the temp
     */
    public int getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(int temp) {
        this.temp = temp;
    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
        this.value = value;
    }


    
    
    
}
