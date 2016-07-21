package dao;

/**
 *
 * @author Kamalion
 */
public class UserProfile {
    
    private String username;
    private String rolename;
    private int id;

    public UserProfile(String username, String rolename, int id) {
        this.username = username;
        this.rolename = rolename;
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param name the name to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the role
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param role the role to set
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
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
