package dao;


import profiles.CalcProfile;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import dao.SteelDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.naming.NamingException;
import javax.sql.*;

@ManagedBean
@ApplicationScoped
public class CalcDAO implements Serializable {
    
    private DataBase data;

    //Поля входящих данных
    private String steel;
    private Double intPressure;
    private Double hydroPressure;
    private Double temp;
    private Double tempT;
    private Double temp20;
    private Double diam;
    private Double thickness;
    private Double corrosion;
    private Double minusTolerance;
    private Double techno;
    private Double addThickness;
    private Double lineExpCoeff;
    private Double elasticity;
    private Double yieldStrength;
    private Double tempResist;
    private Double yieldStrength20;
    private Double weld;
    private Double bending;
    private Double shift;
    private Double force;
    //Поля результатов
    private Double resThickness;
    private String resGreaterPressure;
    private String resGreaterThickness;
    private Double resIntPressure;
    private Double resAxialForceStrength;
    private Double resAxialCompessiveForceLocal;
    private Double resFlexibility;
    private Double resAxialCompessiveForceOverallLess;
    private Double resAxialCompessiveForceOverallMore;
    private Double resAxialForceElasticity;
    private Double resAxialForcePermissible;
    private Double resStrengthConditionsThrust;
    private Double resBendingMomentStrength;
    private Double resBendingMomentElasticity;
    private Double resBendingMomentPermissible;
    private Double resStrengthConditionsBendingMoment;
    private Double resShearForceStrength;
    private Double resShearForceElasticity;
    private Double resShearForcePermissible;
    private Double resStrengthConditionsShearForce;
    
    
    
    public void setSteelCharacteristics() throws SQLException, NamingException{
        
        DataBase db = new DataBase();
        
        
        Connection conn = db.getdbConnection();
        PreparedStatement stat = conn.prepareStatement("SELECT sigma20,sigmaT FROM calculator.steels WHERE mark = ?");
        stat.setString(1, steel);
        ResultSet res =  stat.executeQuery();
        res.next();
        
        temp20 = res.getDouble("sigma20");
        tempT = res.getDouble("sigmaT");
        
        conn.close();
    }
    
    public ArrayList<CalcProfile> getCalcs() throws SQLException, NamingException {
        
        data = new DataBase();

        ArrayList<CalcProfile> calcs = new ArrayList<>();
        Connection conn = data.getdbConnection();
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM calculator.calcs");

        ResultSet result = stat.executeQuery();
        while (result.next()) {
            CalcProfile profile = new CalcProfile(result.getInt("id"), result.getDate("date"), result.getString("name"));
            calcs.add(profile);
        }

        return calcs;

    }

    /**
     * @return the steel
     */
    public String getSteel() {
        return steel;
    }

    /**
     * @param steel the steel to set
     */
    public void setSteel(String steel) {
        this.steel = steel;
    }

    /**
     * @return the intPressure
     */
    public Double getIntPressure() {
        return intPressure;
    }

    /**
     * @param intPressure the intPressure to set
     */
    public void setIntPressure(double intPressure) {
        this.intPressure = intPressure;
    }

    /**
     * @return the hydroPressure
     */
    public Double getHydroPressure() {
        return hydroPressure;
    }

    /**
     * @param hydroPressure the hydroPressure to set
     */
    public void setHydroPressure(double hydroPressure) {
        this.setHydroPressure((Double) hydroPressure);
    }

    /**
     * @return the temp
     */
    public Double getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(double temp) {
        this.setTemp((Double) temp);
    }

    /**
     * @return the tempT
     */
    public Double getTempT() {
        return tempT;
    }

    /**
     * @param tempT the tempT to set
     */
    public void setTempT(double tempT) {
        this.setTempT((Double) tempT);
    }

    /**
     * @return the temp20
     */
    public Double getTemp20() {
        return temp20;
    }

    /**
     * @param temp20 the temp20 to set
     */
    public void setTemp20(double temp20) {
        this.setTemp20((Double) temp20);
    }

    /**
     * @return the diam
     */
    public Double getDiam() {
        return diam;
    }

    /**
     * @param diam the diam to set
     */
    public void setDiam(double diam) {
        this.setDiam((Double) diam);
    }

    /**
     * @return the thickness
     */
    public Double getThickness() {
        return thickness;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(double thickness) {
        this.setThickness((Double) thickness);
    }

    /**
     * @return the corrosion
     */
    public Double getCorrosion() {
        return corrosion;
    }

    /**
     * @param corrosion the corrosion to set
     */
    public void setCorrosion(double corrosion) {
        this.setCorrosion((Double) corrosion);
    }

    /**
     * @return the minusTolerance
     */
    public Double getMinusTolerance() {
        return minusTolerance;
    }

    /**
     * @param minusTolerance the minusTolerance to set
     */
    public void setMinusTolerance(double minusTolerance) {
        this.setMinusTolerance((Double) minusTolerance);
    }

    /**
     * @return the techno
     */
    public Double getTechno() {
        return techno;
    }

    /**
     * @param techno the techno to set
     */
    public void setTechno(double techno) {
        this.setTechno((Double) techno);
    }

    /**
     * @return the addThickness
     */
    public Double getAddThickness() {
        return addThickness;
    }

    /**
     * @param addThickness the addThickness to set
     */
    public void setAddThickness(double addThickness) {
        this.setAddThickness((Double) addThickness);
    }

    /**
     * @return the lineExpCoeff
     */
    public Double getLineExpCoeff() {
        return lineExpCoeff;
    }

    /**
     * @param lineExpCoeff the lineExpCoeff to set
     */
    public void setLineExpCoeff(double lineExpCoeff) {
        this.setLineExpCoeff((Double) lineExpCoeff);
    }

    /**
     * @return the elasticity
     */
    public Double getElasticity() {
        return elasticity;
    }

    /**
     * @param elasticity the elasticity to set
     */
    public void setElasticity(double elasticity) {
        this.setElasticity((Double) elasticity);
    }

    /**
     * @return the yieldStrength
     */
    public Double getYieldStrength() {
        return yieldStrength;
    }

    /**
     * @param yieldStrength the yieldStrength to set
     */
    public void setYieldStrength(double yieldStrength) {
        this.setYieldStrength((Double) yieldStrength);
    }

    /**
     * @return the tempResist
     */
    public Double getTempResist() {
        return tempResist;
    }

    /**
     * @param tempResist the tempResist to set
     */
    public void setTempResist(double tempResist) {
        this.setTempResist((Double) tempResist);
    }

    /**
     * @return the yieldStrength20
     */
    public Double getYieldStrength20() {
        return yieldStrength20;
    }

    /**
     * @param yieldStrength20 the yieldStrength20 to set
     */
    public void setYieldStrength20(double yieldStrength20) {
        this.setYieldStrength20((Double) yieldStrength20);
    }

    /**
     * @return the weld
     */
    public Double getWeld() {
        return weld;
    }

    /**
     * @param weld the weld to set
     */
    public void setWeld(double weld) {
        this.setWeld((Double) weld);
    }

    /**
     * @return the bending
     */
    public Double getBending() {
        return bending;
    }

    /**
     * @param bending the bending to set
     */
    public void setBending(double bending) {
        this.setBending((Double) bending);
    }

    /**
     * @return the shift
     */
    public Double getShift() {
        return shift;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(double shift) {
        this.setShift((Double) shift);
    }

    /**
     * @return the force
     */
    public Double getForce() {
        return force;
    }

    /**
     * @param force the force to set
     */
    public void setForce(double force) {
        this.force = force;
    }

    /**
     * @return the calcThickness
     */
    public Double getResThickness() {
        
        resThickness = bending*force;
        resThickness = new BigDecimal(resThickness).abs().setScale(2, RoundingMode.UP).doubleValue();
        
        return resThickness;
    }

    /**
     * @param calcThickness the calcThickness to set
     */
    public void setResThickness(double resThickness) {
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
    public void setIntPressure(Double intPressure) {
        this.intPressure = intPressure;
    }

    /**
     * @param hydroPressure the hydroPressure to set
     */
    public void setHydroPressure(Double hydroPressure) {
        this.hydroPressure = hydroPressure;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(Double temp) {
        this.temp = temp;
    }

    /**
     * @param tempT the tempT to set
     */
    public void setTempT(Double tempT) {
        this.tempT = tempT;
    }

    /**
     * @param temp20 the temp20 to set
     */
    public void setTemp20(Double temp20) {
        this.temp20 = temp20;
    }

    /**
     * @param diam the diam to set
     */
    public void setDiam(Double diam) {
        this.diam = diam;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    /**
     * @param corrosion the corrosion to set
     */
    public void setCorrosion(Double corrosion) {
        this.corrosion = corrosion;
    }

    /**
     * @param minusTolerance the minusTolerance to set
     */
    public void setMinusTolerance(Double minusTolerance) {
        this.minusTolerance = minusTolerance;
    }

    /**
     * @param techno the techno to set
     */
    public void setTechno(Double techno) {
        this.techno = techno;
    }

    /**
     * @param addThickness the addThickness to set
     */
    public void setAddThickness(Double addThickness) {
        this.addThickness = addThickness;
    }

    /**
     * @param lineExpCoeff the lineExpCoeff to set
     */
    public void setLineExpCoeff(Double lineExpCoeff) {
        this.lineExpCoeff = lineExpCoeff;
    }

    /**
     * @param elasticity the elasticity to set
     */
    public void setElasticity(Double elasticity) {
        this.elasticity = elasticity;
    }

    /**
     * @param yieldStrength the yieldStrength to set
     */
    public void setYieldStrength(Double yieldStrength) {
        this.yieldStrength = yieldStrength;
    }

    /**
     * @param tempResist the tempResist to set
     */
    public void setTempResist(Double tempResist) {
        this.tempResist = tempResist;
    }

    /**
     * @param yieldStrength20 the yieldStrength20 to set
     */
    public void setYieldStrength20(Double yieldStrength20) {
        this.yieldStrength20 = yieldStrength20;
    }

    /**
     * @param weld the weld to set
     */
    public void setWeld(Double weld) {
        this.weld = weld;
    }

    /**
     * @param bending the bending to set
     */
    public void setBending(Double bending) {
        this.bending = bending;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(Double shift) {
        this.shift = shift;
    }

    /**
     * @param force the force to set
     */
    public void setForce(Double force) {
        this.force = force;
    }


    /**
     * @param resThickness the resThickness to set
     */
    public void setResThickness(Double resThickness) {
        
        this.resThickness = resThickness;
    }

    /**
     * @return the resIntPressure
     */
    public Double getResIntPressure() {
        return resIntPressure;
    }

    /**
     * @param resIntPressure the resIntPressure to set
     */
    public void setResIntPressure(Double resIntPressure) {
        this.resIntPressure = resIntPressure;
    }

    /**
     * @return the resAxialForceStrength
     */
    public Double getResAxialForceStrength() {
        return resAxialForceStrength;
    }

    /**
     * @param resAxialForceStrength the resAxialForceStrength to set
     */
    public void setResAxialForceStrength(Double resAxialForceStrength) {
        this.resAxialForceStrength = resAxialForceStrength;
    }

    /**
     * @return the resAxialCompessiveForceLocal
     */
    public Double getResAxialCompessiveForceLocal() {
        return resAxialCompessiveForceLocal;
    }

    /**
     * @param resAxialCompessiveForceLocal the resAxialCompessiveForceLocal to set
     */
    public void setResAxialCompessiveForceLocal(Double resAxialCompessiveForceLocal) {
        this.resAxialCompessiveForceLocal = resAxialCompessiveForceLocal;
    }

    /**
     * @return the resFlexibility
     */
    public Double getResFlexibility() {
        return resFlexibility;
    }

    /**
     * @param resFlexibility the resFlexibility to set
     */
    public void setResFlexibility(Double resFlexibility) {
        this.resFlexibility = resFlexibility;
    }

    /**
     * @return the resAxialCompessiveForceOverallLess
     */
    public Double getResAxialCompessiveForceOverallLess() {
        return resAxialCompessiveForceOverallLess;
    }

    /**
     * @param resAxialCompessiveForceOverallLess the resAxialCompessiveForceOverallLess to set
     */
    public void setResAxialCompessiveForceOverallLess(Double resAxialCompessiveForceOverallLess) {
        this.resAxialCompessiveForceOverallLess = resAxialCompessiveForceOverallLess;
    }

    /**
     * @return the resAxialCompessiveForceOverallMore
     */
    public Double getResAxialCompessiveForceOverallMore() {
        return resAxialCompessiveForceOverallMore;
    }

    /**
     * @param resAxialCompessiveForceOverallMore the resAxialCompessiveForceOverallMore to set
     */
    public void setResAxialCompessiveForceOverallMore(Double resAxialCompessiveForceOverallMore) {
        this.resAxialCompessiveForceOverallMore = resAxialCompessiveForceOverallMore;
    }

    /**
     * @return the resAxialForceElasticity
     */
    public Double getResAxialForceElasticity() {
        return resAxialForceElasticity;
    }

    /**
     * @param resAxialForceElasticity the resAxialForceElasticity to set
     */
    public void setResAxialForceElasticity(Double resAxialForceElasticity) {
        this.resAxialForceElasticity = resAxialForceElasticity;
    }

    /**
     * @return the resAxialForcePermissible
     */
    public Double getResAxialForcePermissible() {
        return resAxialForcePermissible;
    }

    /**
     * @param resAxialForcePermissible the resAxialForcePermissible to set
     */
    public void setResAxialForcePermissible(Double resAxialForcePermissible) {
        this.resAxialForcePermissible = resAxialForcePermissible;
    }

    /**
     * @return the resStrengthConditionsThrust
     */
    public Double getResStrengthConditionsThrust() {
        return resStrengthConditionsThrust;
    }

    /**
     * @param resStrengthConditionsThrust the resStrengthConditionsThrust to set
     */
    public void setResStrengthConditionsThrust(Double resStrengthConditionsThrust) {
        this.resStrengthConditionsThrust = resStrengthConditionsThrust;
    }

    /**
     * @return the resBendingMomentStrength
     */
    public Double getResBendingMomentStrength() {
        return resBendingMomentStrength;
    }

    /**
     * @param resBendingMomentStrength the resBendingMomentStrength to set
     */
    public void setResBendingMomentStrength(Double resBendingMomentStrength) {
        this.resBendingMomentStrength = resBendingMomentStrength;
    }

    /**
     * @return the resBendingMomentElasticity
     */
    public Double getResBendingMomentElasticity() {
        return resBendingMomentElasticity;
    }

    /**
     * @param resBendingMomentElasticity the resBendingMomentElasticity to set
     */
    public void setResBendingMomentElasticity(Double resBendingMomentElasticity) {
        this.resBendingMomentElasticity = resBendingMomentElasticity;
    }

    /**
     * @return the resBendingMomentPermissible
     */
    public Double getResBendingMomentPermissible() {
        return resBendingMomentPermissible;
    }

    /**
     * @param resBendingMomentPermissible the resBendingMomentPermissible to set
     */
    public void setResBendingMomentPermissible(Double resBendingMomentPermissible) {
        this.resBendingMomentPermissible = resBendingMomentPermissible;
    }

    /**
     * @return the resStrengthConditionsBendingMoment
     */
    public Double getResStrengthConditionsBendingMoment() {
        return resStrengthConditionsBendingMoment;
    }

    /**
     * @param resStrengthConditionsBendingMoment the resStrengthConditionsBendingMoment to set
     */
    public void setResStrengthConditionsBendingMoment(Double resStrengthConditionsBendingMoment) {
        this.resStrengthConditionsBendingMoment = resStrengthConditionsBendingMoment;
    }

    /**
     * @return the resShearForceStrength
     */
    public Double getResShearForceStrength() {
        return resShearForceStrength;
    }

    /**
     * @param resShearForceStrength the resShearForceStrength to set
     */
    public void setResShearForceStrength(Double resShearForceStrength) {
        this.resShearForceStrength = resShearForceStrength;
    }

    /**
     * @return the resShearForceElasticity
     */
    public Double getResShearForceElasticity() {
        return resShearForceElasticity;
    }

    /**
     * @param resShearForceElasticity the resShearForceElasticity to set
     */
    public void setResShearForceElasticity(Double resShearForceElasticity) {
        this.resShearForceElasticity = resShearForceElasticity;
    }

    /**
     * @return the resShearForcePermissible
     */
    public Double getResShearForcePermissible() {
        return resShearForcePermissible;
    }

    /**
     * @param resShearForcePermissible the resShearForcePermissible to set
     */
    public void setResShearForcePermissible(Double resShearForcePermissible) {
        this.resShearForcePermissible = resShearForcePermissible;
    }

    /**
     * @return the resStrengthConditionsShearForce
     */
    public Double getResStrengthConditionsShearForce() {
        return resStrengthConditionsShearForce;
    }

    /**
     * @param resStrengthConditionsShearForce the resStrengthConditionsShearForce to set
     */
    public void setResStrengthConditionsShearForce(Double resStrengthConditionsShearForce) {
        this.resStrengthConditionsShearForce = resStrengthConditionsShearForce;
    }

}
