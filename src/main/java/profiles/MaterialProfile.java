package profiles;

/**
 *
 * @author Kamalion
 */
public class MaterialProfile {
    
    private int id;
    private String matname;
    private String matgroup;

    public MaterialProfile(int id, String matname, String matgroup) {
        this.id = id;
        this.matname = matname;
        this.matgroup = matgroup;
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

    
    
    
}
