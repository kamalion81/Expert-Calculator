package dao;

import profiles.Napr_Tekuch_Soprotiv_Profile;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ApplicationScoped
public class VremSoprotivDAO extends DopuskNaprDAO implements Serializable {

    @Override
    public ArrayList<Napr_Tekuch_Soprotiv_Profile> getValues() {

        ArrayList<Napr_Tekuch_Soprotiv_Profile> values = new ArrayList<>();
        try {
            try (Connection conn = getDs().getConnection()) {
                PreparedStatement stat = conn.prepareStatement("SELECT vremennoe_soprotivlenie.id, materials.matname, "
                        + "vremennoe_soprotivlenie.thickness_min, vremennoe_soprotivlenie.thickness_max, vremennoe_soprotivlenie.temp,"
                        + " vremennoe_soprotivlenie.value "
                        + "FROM calculator.vremennoe_soprotivlenie INNER JOIN calculator.materials ON vremennoe_soprotivlenie.material_id = materials.id");

                ResultSet result = stat.executeQuery();
                while (result.next()) {
                    Napr_Tekuch_Soprotiv_Profile profile = new Napr_Tekuch_Soprotiv_Profile(result.getInt("id"), result.getString("matname"), result.getInt("thickness_min"),
                            result.getInt("thickness_max"), result.getInt("temp"), result.getInt("value"));
                    values.add(profile);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VremSoprotivDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

    @Override
    public void saveValue() {
        try {
            try (Connection conn = super.getDs().getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, super.getMatname());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int material_id = res.getInt("id");
                    String query = "INSERT INTO `calculator`.`vremennoe_soprotivlenie`\n"
                            + "(`material_id`,\n"
                            + "`thickness_min`,\n"
                            + "`thickness_max`,\n"
                            + "`temp`,\n"
                            + "`value`)\n"
                            + "VALUES\n"
                            + "(" + material_id + ",\n"
                            + super.getThickness_min() + ",\n"
                            + super.getThickness_max() + ",\n"
                            + super.getTemp() + ",\n"
                            + super.getValue() + ");";
                    PreparedStatement stat2 = conn.prepareStatement(query);

                    stat2.executeUpdate();

                    FacesMessage message = null;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные сохранены", "Значение напряжения " + super.getValue() + " добавлено");
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
            Logger.getLogger(VremSoprotivDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editValue(Napr_Tekuch_Soprotiv_Profile selectedValue) {

        try {
            try (Connection conn = getDs().getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {
                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, selectedValue.getMaterial());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int material = res.getInt("id");
                    Statement stat = conn.createStatement();
                    String querry = "UPDATE `calculator`.`vremennoe_soprotivlenie` "
                            + "SET `material_id`='" + material + "', `thickness_min`='" + selectedValue.getThickness_min() + "',"
                            + "`thickness_max`='" + selectedValue.getThickness_max() + "',"
                            + "`temp`='" + selectedValue.getTemp() + "', `value`='" + selectedValue.getValue() + "' WHERE `id`='" + selectedValue.getId() + "';";
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
            Logger.getLogger(VremSoprotivDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @Override
    public void deleteValue(Napr_Tekuch_Soprotiv_Profile selectedValue) {

        try {
            try (Connection conn = getDs().getConnection()) {

                Statement stat = conn.createStatement();

                stat.executeUpdate("DELETE FROM `calculator`.`vremennoe_soprotivlenie` WHERE `id`='" + selectedValue.getId() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VremSoprotivDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
