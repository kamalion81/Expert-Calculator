package dao;


import profiles.CalcProfile;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import dao.MaterialDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.sql.*;
import profiles.MaterialProfile;

@ManagedBean
@ApplicationScoped
public class CalcDAO implements Serializable {
    
    private DataSource ds;

    //Поля входящих данных
    private String material;
    private Float intPressure;
    private Float hydroPressure;
    private Integer temp;
    private Float tempT;
    private Float temp20;
    private Integer diam;
    private Integer thickness;
    private Float corrosion;
    private Float minusTolerance;
    private Float techno;
    private Float addThickness;
    private Float lineExpCoeff;
    private Float elasticity;
    private Float yieldStrengthT;
    private Float tempResist;
    private Float yieldStrength20;
    private Float weld;
    private Float bending;
    private Float shift;
    private Float force;
    //Поля результатов
    private Float resThickness;
    private String resGreaterPressure;
    private String resGreaterThickness;
    private Float resIntPressure;
    private Float resAxialForceStrength;
    private Float resAxialCompessiveForceLocal;
    private Float resFlexibility;
    private Float resAxialCompessiveForceOverallLess;
    private Float resAxialCompessiveForceOverallMore;
    private Float resAxialForceElasticity;
    private Float resAxialForcePermissible;
    private Float resStrengthConditionsThrust;
    private Float resBendingMomentStrength;
    private Float resBendingMomentElasticity;
    private Float resBendingMomentPermissible;
    private Float resStrengthConditionsBendingMoment;
    private Float resShearForceStrength;
    private Float resShearForceElasticity;
    private Float resShearForcePermissible;
    private Float resStrengthConditionsShearForce;

    public CalcDAO() {
       // this.resThickness = new Float(0);
        this.temp = 20;
        
         DataBase db = new DataBase();
         ds = db.getDs();
        
    }
    
    
    
    public void insertTemp(){
        
        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, material);
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int material_id = res.getInt("id");
                    PreparedStatement stat = conn.prepareStatement("SELECT value FROM calculator.koeficient_lineinogo_rashireniya WHERE material_id = ? AND (?<= temp_max AND ?>= temp_min)");
                    stat.setInt(1, material_id);
                    stat.setInt(2, getTemp());
                    stat.setInt(3, getTemp());
                    ResultSet res2 = stat.executeQuery();
                    if(!res2.next()){
                        setLineExpCoeff(null);
                    }

                    setLineExpCoeff((Float) res2.getFloat("value"));
                    
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
        if (!(thickness == null)) {
            insertThickness();
        }
        
        if(!(temp == null)){
            writeModule();
            
        }
//        PreparedStatement stat = conn.prepareStatement("SELECT sigma20,sigmaT FROM calculator.steels WHERE mark = ?");
//        stat.setString(1, material);
//        ResultSet res =  stat.executeQuery();
//        res.next();
        
//        temp20 = res.getDouble("sigma20");
//        tempT = res.getDouble("sigmaT");
        
        //conn.close();
    }
    
    public void insertThickness(){
        
        writeNapryazh_Predel_Soprotiv("dopuskaemoe_napryazhenie");
        writeNapryazh_Predel_Soprotiv("predel_tekuchesti");
        writeNapryazh_Predel_Soprotiv("vremennoe_soprotivlenie");
        
    }
    
    public void insertC(){
        
        setCorrosion(getCorrosion() == null ? 0 : getCorrosion());
        setMinusTolerance(getMinusTolerance() == null ? 0 : getMinusTolerance());
        setTechno(getTechno() == null ? 0 : getTechno());
        
        setAddThickness(getCorrosion() + getMinusTolerance() + getTechno());
        
        if(getAddThickness().equals(0)){
            setAddThickness(null);
        }
    }
    
    public void insertPressure(){
        
//        resThickness = (intPressure*diam)/(2*tempT*weld-intPressure);
        resThickness = new Float(10.3) ;
        
    }
    
    public void writeNapryazh_Predel_Soprotiv(String base){
       try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, material);
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int material_id = res.getInt("id");
                    PreparedStatement stat = conn.prepareStatement("SELECT value FROM "+base+" WHERE material_id = ? AND temp = ? AND (?<= thickness_max AND ?>= thickness_min)");
                    stat.setInt(1, material_id);
                    stat.setInt(2, getTemp());
                    stat.setInt(3, getThickness());
                    stat.setInt(4, getThickness());
                    ResultSet res2 = stat.executeQuery();
                    
                    if (!res2.next()) {
                        switch (base) {
                            case "dopuskaemoe_napryazhenie":
                                if (getTemp() == 20) {
                                    setTemp20(null);
                                } else {
                                    setTempT(null);
                                }
                                break;
                            case "predel_tekuchesti":
                                if (getTemp() == 20) {
                                    setYieldStrength20(null);
                                } else {
                                    setYieldStrengthT(null);
                                }
                                break;
                            case "vremennoe_soprotivlenie":
                                setTempResist(null);
                            default:
                                break;
                        }
                    }

                    if (base.equals("vremennoe_soprotivlenie")) {
                        setTempResist((Float) res2.getFloat("value"));
                    }

                    if (getTemp() == 20) {
                        switch (base) {
                            case "dopuskaemoe_napryazhenie":
                                setTemp20((Float) res2.getFloat("value"));
                                setTempT(null);
                                break;
                            case "predel_tekuchesti":
                                setYieldStrength20((Float) res2.getFloat("value"));
                                setYieldStrengthT(null);
                                break;
                            default:
                                break;
                        }

                    } else {
                        switch (base) {
                            case "dopuskaemoe_napryazhenie":
                                setTempT((Float) res2.getFloat("value"));
                                setTemp20(null);
                                break;
                            case "predel_tekuchesti":
                                setYieldStrengthT((Float) res2.getFloat("value"));
                                setYieldStrength20(null);
                                break;
                            default:
                                break;
                        }
                    }

                    

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
    
    public void writeModule(){
        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement pstat = conn.prepareStatement("SELECT material_group_id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, material);
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int group_id = res.getInt("material_group_id");
                    PreparedStatement stat = conn.prepareStatement("SELECT value FROM calculator.modul_prodolnoi_uprugosti WHERE temp = ? AND material_group_id = ?");
                    stat.setInt(1, getTemp());
                    stat.setInt(2, group_id);
                    ResultSet res2 = stat.executeQuery();
                    if(!res2.next()){
                        setElasticity(null);
                    }

                    setElasticity((Float) res2.getFloat("value"));
                    
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
    
    public ArrayList<CalcProfile> getCalcs() throws SQLException, NamingException {
        
//        data = new DataBase();
//
        ArrayList<CalcProfile> calcs = new ArrayList<>();
//        //Connection conn = data.getdbConnection();
//        PreparedStatement stat = conn.prepareStatement("SELECT * FROM calculator.calcs");
//
//        ResultSet result = stat.executeQuery();
//        while (result.next()) {
//            CalcProfile profile = new CalcProfile(result.getInt("id"), result.getDate("date"), result.getString("name"));
//            calcs.add(profile);
//        }
//
        return calcs;

    }


    /**
     * @return the intPressure
     */
    public Float getIntPressure() { 
        return intPressure;
    }

    

    /**
     * @param diam the diam to set
     */
    public void setDiam(Integer diam) {
        this.diam = diam;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(Integer thickness) {
        this.thickness = thickness;
    }

    /**
     * @param corrosion the corrosion to set
     */
    public void setCorrosion(Float corrosion) {
        this.corrosion = corrosion;
    }

    /**
     * @param minusTolerance the minusTolerance to set
     */
    public void setMinusTolerance(Float minusTolerance) {
        this.minusTolerance = minusTolerance;
    }

    /**
     * @param techno the techno to set
     */
    public void setTechno(Float techno) {
        this.techno = techno;
    }

    /**
     * @param addThickness the addThickness to set
     */
    public void setAddThickness(Float addThickness) {
        this.addThickness = addThickness;
    }

    /**
     * @param lineExpCoeff the lineExpCoeff to set
     */
    public void setLineExpCoeff(Float lineExpCoeff) {
        this.lineExpCoeff = lineExpCoeff;
    }

    /**
     * @param elasticity the elasticity to set
     */
    public void setElasticity(Float elasticity) {
        this.elasticity = elasticity;
    }

    /**
     * @param yieldStrength the yieldStrengthT to set
     */
    public void setYieldStrengthT(Float yieldStrength) {
        this.yieldStrengthT = yieldStrength;
    }

    /**
     * @param tempResist the tempResist to set
     */
    public void setTempResist(Float tempResist) {
        this.tempResist = tempResist;
    }

    /**
     * @param yieldStrength20 the yieldStrength20 to set
     */
    public void setYieldStrength20(Float yieldStrength20) {
        this.yieldStrength20 = yieldStrength20;
    }

    /**
     * @param weld the weld to set
     */
    public void setWeld(Float weld) {
        this.weld = weld;
    }

    /**
     * @param bending the bending to set
     */
    public void setBending(Float bending) {
        this.bending = bending;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(Float shift) {
        this.shift = shift;
    }

    /**
     * @param force the force to set
     */
    public void setForce(Float force) {
        this.force = force;
    }



    /**
     * @return the resIntPressure
     */
    public Float getResIntPressure() {
        return resIntPressure;
    }

    /**
     * @param resIntPressure the resIntPressure to set
     */
    public void setResIntPressure(Float resIntPressure) {
        this.resIntPressure = resIntPressure;
    }

    /**
     * @return the resAxialForceStrength
     */
    public Float getResAxialForceStrength() {
        return resAxialForceStrength;
    }

    /**
     * @param resAxialForceStrength the resAxialForceStrength to set
     */
    public void setResAxialForceStrength(Float resAxialForceStrength) {
        this.resAxialForceStrength = resAxialForceStrength;
    }

    /**
     * @return the resAxialCompessiveForceLocal
     */
    public Float getResAxialCompessiveForceLocal() {
        return resAxialCompessiveForceLocal;
    }

    /**
     * @param resAxialCompessiveForceLocal the resAxialCompessiveForceLocal to set
     */
    public void setResAxialCompessiveForceLocal(Float resAxialCompessiveForceLocal) {
        this.resAxialCompessiveForceLocal = resAxialCompessiveForceLocal;
    }

    /**
     * @return the resFlexibility
     */
    public Float getResFlexibility() {
        return resFlexibility;
    }

    /**
     * @param resFlexibility the resFlexibility to set
     */
    public void setResFlexibility(Float resFlexibility) {
        this.resFlexibility = resFlexibility;
    }

    /**
     * @return the resAxialCompessiveForceOverallLess
     */
    public Float getResAxialCompessiveForceOverallLess() {
        return resAxialCompessiveForceOverallLess;
    }

    /**
     * @param resAxialCompessiveForceOverallLess the resAxialCompessiveForceOverallLess to set
     */
    public void setResAxialCompessiveForceOverallLess(Float resAxialCompessiveForceOverallLess) {
        this.resAxialCompessiveForceOverallLess = resAxialCompessiveForceOverallLess;
    }

    /**
     * @return the resAxialCompessiveForceOverallMore
     */
    public Float getResAxialCompessiveForceOverallMore() {
        return resAxialCompessiveForceOverallMore;
    }

    /**
     * @param resAxialCompessiveForceOverallMore the resAxialCompessiveForceOverallMore to set
     */
    public void setResAxialCompessiveForceOverallMore(Float resAxialCompessiveForceOverallMore) {
        this.resAxialCompessiveForceOverallMore = resAxialCompessiveForceOverallMore;
    }

    /**
     * @return the resAxialForceElasticity
     */
    public Float getResAxialForceElasticity() {
        return resAxialForceElasticity;
    }

    /**
     * @param resAxialForceElasticity the resAxialForceElasticity to set
     */
    public void setResAxialForceElasticity(Float resAxialForceElasticity) {
        this.resAxialForceElasticity = resAxialForceElasticity;
    }

    /**
     * @return the resAxialForcePermissible
     */
    public Float getResAxialForcePermissible() {
        return resAxialForcePermissible;
    }

    /**
     * @param resAxialForcePermissible the resAxialForcePermissible to set
     */
    public void setResAxialForcePermissible(Float resAxialForcePermissible) {
        this.resAxialForcePermissible = resAxialForcePermissible;
    }

    /**
     * @return the resStrengthConditionsThrust
     */
    public Float getResStrengthConditionsThrust() {
        return resStrengthConditionsThrust;
    }

    /**
     * @param resStrengthConditionsThrust the resStrengthConditionsThrust to set
     */
    public void setResStrengthConditionsThrust(Float resStrengthConditionsThrust) {
        this.resStrengthConditionsThrust = resStrengthConditionsThrust;
    }

    /**
     * @return the resBendingMomentStrength
     */
    public Float getResBendingMomentStrength() {
        return resBendingMomentStrength;
    }

    /**
     * @param resBendingMomentStrength the resBendingMomentStrength to set
     */
    public void setResBendingMomentStrength(Float resBendingMomentStrength) {
        this.resBendingMomentStrength = resBendingMomentStrength;
    }

    /**
     * @return the resBendingMomentElasticity
     */
    public Float getResBendingMomentElasticity() {
        return resBendingMomentElasticity;
    }

    /**
     * @param resBendingMomentElasticity the resBendingMomentElasticity to set
     */
    public void setResBendingMomentElasticity(Float resBendingMomentElasticity) {
        this.resBendingMomentElasticity = resBendingMomentElasticity;
    }

    /**
     * @return the resBendingMomentPermissible
     */
    public Float getResBendingMomentPermissible() {
        return resBendingMomentPermissible;
    }

    /**
     * @param resBendingMomentPermissible the resBendingMomentPermissible to set
     */
    public void setResBendingMomentPermissible(Float resBendingMomentPermissible) {
        this.resBendingMomentPermissible = resBendingMomentPermissible;
    }

    /**
     * @return the resStrengthConditionsBendingMoment
     */
    public Float getResStrengthConditionsBendingMoment() {
        return resStrengthConditionsBendingMoment;
    }

    /**
     * @param resStrengthConditionsBendingMoment the resStrengthConditionsBendingMoment to set
     */
    public void setResStrengthConditionsBendingMoment(Float resStrengthConditionsBendingMoment) {
        this.resStrengthConditionsBendingMoment = resStrengthConditionsBendingMoment;
    }

    /**
     * @return the resShearForceStrength
     */
    public Float getResShearForceStrength() {
        return resShearForceStrength;
    }

    /**
     * @param resShearForceStrength the resShearForceStrength to set
     */
    public void setResShearForceStrength(Float resShearForceStrength) {
        this.resShearForceStrength = resShearForceStrength;
    }

    /**
     * @return the resShearForceElasticity
     */
    public Float getResShearForceElasticity() {
        return resShearForceElasticity;
    }

    /**
     * @param resShearForceElasticity the resShearForceElasticity to set
     */
    public void setResShearForceElasticity(Float resShearForceElasticity) {
        this.resShearForceElasticity = resShearForceElasticity;
    }

    /**
     * @return the resShearForcePermissible
     */
    public Float getResShearForcePermissible() {
        return resShearForcePermissible;
    }

    /**
     * @param resShearForcePermissible the resShearForcePermissible to set
     */
    public void setResShearForcePermissible(Float resShearForcePermissible) {
        this.resShearForcePermissible = resShearForcePermissible;
    }

    /**
     * @return the resStrengthConditionsShearForce
     */
    public Float getResStrengthConditionsShearForce() {
        return resStrengthConditionsShearForce;
    }

    /**
     * @param resStrengthConditionsShearForce the resStrengthConditionsShearForce to set
     */
    public void setResStrengthConditionsShearForce(Float resStrengthConditionsShearForce) {
        this.resStrengthConditionsShearForce = resStrengthConditionsShearForce;
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
     * @return the hydroPressure
     */
    public Float getHydroPressure() {
        return hydroPressure;
    }

    /**
     * @param hydroPressure the hydroPressure to set
     */
    public void setHydroPressure(Float hydroPressure) {
        this.hydroPressure = hydroPressure;
    }

    /**
     * @return the temp
     */
    public Integer getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    /**
     * @return the tempT
     */
    public Float getTempT() {
        return tempT;
    }

    /**
     * @param tempT the tempT to set
     */
    public void setTempT(Float tempT) {
        this.tempT = tempT;
    }

    /**
     * @return the temp20
     */
    public Float getTemp20() {
        return temp20;
    }

    /**
     * @param temp20 the temp20 to set
     */
    public void setTemp20(Float temp20) {
        this.temp20 = temp20;
    }

    /**
     * @return the diam
     */
    public Integer getDiam() {
        return diam;
    }

    /**
     * @return the thickness
     */
    public Integer getThickness() {
        return thickness;
    }

    /**
     * @return the corrosion
     */
    public Float getCorrosion() {
        return corrosion;
    }

    /**
     * @return the minusTolerance
     */
    public Float getMinusTolerance() {
        return minusTolerance;
    }

    /**
     * @return the techno
     */
    public Float getTechno() {
        return techno;
    }

    /**
     * @return the addThickness
     */
    public Float getAddThickness() {
        return addThickness;
    }

    /**
     * @return the lineExpCoeff
     */
    public Float getLineExpCoeff() {
        return lineExpCoeff;
    }

    /**
     * @return the elasticity
     */
    public Float getElasticity() {
        return elasticity;
    }

    /**
     * @return the yieldStrengthT
     */
    public Float getYieldStrengthT() {
        return yieldStrengthT;
    }

    /**
     * @return the tempResist
     */
    public Float getTempResist() {
        return tempResist;
    }

    /**
     * @return the yieldStrength20
     */
    public Float getYieldStrength20() {
        return yieldStrength20;
    }

    /**
     * @return the weld
     */
    public Float getWeld() {
        return weld;
    }

    /**
     * @return the bending
     */
    public Float getBending() {
        return bending;
    }

    /**
     * @return the shift
     */
    public Float getShift() {
        return shift;
    }

    /**
     * @return the force
     */
    public Float getForce() {
        return force;
    }

    /**
     * @return the resThickness
     */
    public Float getResThickness() {
        return resThickness;
    }

    /**
     * @param resThickness the resThickness to set
     */
    public void setResThickness(Float resThickness) {
        this.resThickness = resThickness;
    }

    /**
     * @return the resGreaterPressure
     */
    public String getResGreaterPressure() {
        return resGreaterPressure;
    }

    /**
     * @param resGreaterPressure the resGreaterPressure to set
     */
    public void setResGreaterPressure(String resGreaterPressure) {
        this.resGreaterPressure = resGreaterPressure;
    }

    /**
     * @return the resGreaterThickness
     */
    public String getResGreaterThickness() {
        return resGreaterThickness;
    }

    /**
     * @param resGreaterThickness the resGreaterThickness to set
     */
    public void setResGreaterThickness(String resGreaterThickness) {
        this.resGreaterThickness = resGreaterThickness;
    }

    /**
     * @param intPressure the intPressure to set
     */
    public void setIntPressure(Float intPressure) {
        this.intPressure = intPressure;
    }

}
