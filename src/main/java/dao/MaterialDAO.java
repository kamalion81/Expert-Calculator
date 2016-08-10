package dao;

import profiles.MaterialProfile;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ApplicationScoped
public class MaterialDAO implements Serializable {

    private String matname;
    private String groupname;
    private DataSource ds;

    public MaterialDAO() {

        DataBase db = new DataBase();
        ds = db.getDs();

    }

    public ArrayList<MaterialProfile> getMaterials() {

        ArrayList<MaterialProfile> materials = new ArrayList<>();
        try {
            try (Connection conn = ds.getConnection()) {
                PreparedStatement stat = conn.prepareStatement("SELECT materials.id, materials.matname, material_group.groupname FROM calculator.materials INNER JOIN calculator.material_group ON materials.material_group_id = material_group.id");

                ResultSet result = stat.executeQuery();
                while (result.next()) {
                    MaterialProfile profile = new MaterialProfile(result.getInt("id"), result.getString("matname"), result.getString("groupname"));
                    materials.add(profile);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return materials;
    }
    
    public Map<String, String> getMaterialMap() {
        ArrayList<MaterialProfile> materials = getMaterials();
        Map<String, String> materialMap = new LinkedHashMap<>();
        for (MaterialProfile material : materials) {
            materialMap.put(material.getMatname(), material.getMatname());
        }
        return materialMap;
    }
    
    public List<String> getNames(){
        ArrayList<MaterialProfile> materials = getMaterials();
        List<String> names = new ArrayList<>();
        for (MaterialProfile material : materials){
            names.add(material.getMatname());
        }
        
        return names;
    }

    public List<String> getGroups() {

        List<String> groups = new ArrayList<>();
        try {
            try (Connection conn = ds.getConnection()) {
                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT groupname FROM calculator.material_group");

                while (result.next()) {
                    String group = result.getString("groupname");
                    groups.add(group);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;

    }

    public void saveMaterial() {
        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.material_group WHERE groupname = ?");
                    pstat.setString(1, groupname);
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int group = res.getInt("id");
                    String query = "INSERT INTO `calculator`.`materials` (`matname`, `material_group_id`) VALUES "
                            + "('" + matname + "','" + group + "')";
                    PreparedStatement stat2 = conn.prepareStatement(query);

                    stat2.executeUpdate();

                    FacesMessage message = null;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные сохранены", "Материал " + matname + " добавлен");
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    conn.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editMaterial(MaterialProfile selectedMaterial) {

        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {
                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.material_group WHERE groupname = ?");
                    pstat.setString(1, selectedMaterial.getMatgroup());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int group = res.getInt("id");
                    Statement stat = conn.createStatement();
                    String querry = "UPDATE `calculator`.`materials` SET `matname`='" + selectedMaterial.getMatname() + "', `material_group_id`='" + group + "' WHERE `id`='" + selectedMaterial.getId() + "';";
                    stat.executeUpdate(querry);

                    conn.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void deleteMaterial(MaterialProfile selectedMaterial) {

        try {
            try (Connection conn = ds.getConnection()) {

                Statement stat = conn.createStatement();

                stat.executeUpdate("DELETE FROM `calculator`.`materials` WHERE `id`='" + selectedMaterial.getId() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

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
     * @return the groupname
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * @param groupname the groupname to set
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

}
