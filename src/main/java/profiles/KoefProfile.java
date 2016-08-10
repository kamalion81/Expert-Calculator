package profiles;

/**
 *
 * @author Kamalion
 */
public class KoefProfile {
    
    private int id;
    private String matname;
    private int temp_min;
    private int temp_max;
    private float value;

    public KoefProfile(int id, String matname, int temp_min, int temp_max, float value) {
        this.id = id;
        this.matname = matname;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.value = value;
    }


    /**
     * @return the matname
     */
    public String getMatname() {
        return matname;
    }

    /**
     * @param matname the matname to set
     */
    public void setMatname(String matname) {
        this.matname = matname;
    }

    /**
     * @return the temp_min
     */
    public int getTemp_min() {
        return temp_min;
    }

    /**
     * @param temp_min the temp_min to set
     */
    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    /**
     * @return the temp_max
     */
    public int getTemp_max() {
        return temp_max;
    }

    /**
     * @param temp_max the temp_max to set
     */
    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
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
    
    
    
}
