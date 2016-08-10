package profiles;

/**
 *
 * @author Kamalion
 */
public class Napr_Tekuch_Soprotiv_Profile {

    private int id;
    private String material;
    private int thickness_min;
    private int thickness_max;
    private int temp;
    private float value;

    public Napr_Tekuch_Soprotiv_Profile(int id, String material, int thickness_min, int thickness_max, int temp, float value) {
        this.id = id;
        this.material = material;
        this.thickness_min = thickness_min;
        this.thickness_max = thickness_max;
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
     * @return the material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * @return the thickness_min
     */
    public int getThickness_min() {
        return thickness_min;
    }

    /**
     * @param thickness_min the thickness_min to set
     */
    public void setThickness_min(int thickness_min) {
        this.thickness_min = thickness_min;
    }

    /**
     * @return the thickness_max
     */
    public int getThickness_max() {
        return thickness_max;
    }

    /**
     * @param thickness_max the thickness_max to set
     */
    public void setThickness_max(int thickness_max) {
        this.thickness_max = thickness_max;
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

