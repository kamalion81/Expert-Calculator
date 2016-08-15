package dao;


import profiles.CalcProfile;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import dao.MaterialDAO;
import java.math.MathContext;
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
    private Integer temp;
    private Float tempT;
    private Integer diam;
    private Integer thickness;
    private Float corrosion;
    private Float minusTolerance;
    private Float techno;
    private Float addThickness;
    private Float elasticity;
    private Float weld;
    private Float bending;
    private Float shift;
    private Float force;
    private Float koef;
    private Float length;
    private Float length_pr;
    //Поля результатов
    private Float resThickness;
    private String resGreaterPressure;
    private String resGreaterThickness;
    private Float resIntPressure;
    private Float resAxialForceStrength;
    private Float resAxialCompessiveForceLocal;
    private Float resFlexibility;
    private Float resAxialCompessiveForce;
    private Float resAxialForceElasticity;
    private Float resAxialForcePermissible;
    private String resStrengthConditionsThrust;
    private Float resBendingMomentStrength;
    private Float resBendingMomentElasticity;
    private Float resBendingMomentPermissible;
    private String resStrengthConditionsBendingMoment;
    private Float resShearForceStrength;
    private Float resShearForceElasticity;
    private Float resShearForcePermissible;
    private String resStrengthConditionsShearForce;

    public CalcDAO() {
        this.temp = 20;
        this.koef = (float)2.4;
        this.length_pr = (float)1;
        
        
         DataBase db = new DataBase();
         ds = db.getDs();
        
    }
    
    
    
    public void changeTemp(){
            
            if(!(thickness==null)){
            changeThickness();    
            }
            
            
            if(!(temp == null)){
                writeModule();
            }
            
    }
    
    public void changeThickness() {

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
                    PreparedStatement stat = conn.prepareStatement("SELECT value FROM calculator.dopuskaemoe_napryazhenie WHERE material_id = ? AND temp = ? AND (?<= thickness_max AND ?>= thickness_min)");
                    stat.setInt(1, material_id);
                    stat.setInt(2, getTemp());
                    stat.setInt(3, getThickness());
                    stat.setInt(4, getThickness());
                    ResultSet res2 = stat.executeQuery();
                    res2.next();

//                    if (!res2.next()) {
//                        setTempT(null);
//                    }

                    setTempT(res2.getFloat("value"));

                    conn.commit();
                    committed = true;
                }catch(NullPointerException ex){
                    setTempT(null);
                    
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }catch(NullPointerException ex){
//            setTempT(null);
        }
        
        writeResults();

    }
    
    public void changeC(){
        
        setCorrosion(corrosion == null ? 0 : corrosion);
        setMinusTolerance(minusTolerance == null ? 0 : minusTolerance);
        setTechno(techno == null ? 0 : techno);
        Float result = corrosion + minusTolerance + techno;
        
        setAddThickness(result == 0 ? null : result);
        
        writeResults();
        
    }
    
    public void writeResults(){
        
        
        try {
            setResThickness(intPressure * diam / (2 * tempT * weld - intPressure));
        } catch (NullPointerException ex) {
            setResThickness(null);
        }

        try {
            setResIntPressure((2 * weld * tempT * (thickness - addThickness)) / (diam + thickness - addThickness));
        } catch (NullPointerException ex) {
            setResIntPressure(null);
        }
       
        if(!(resIntPressure == null)){
            compareP();
            compareS();
            
        }
        
        try{
            setResAxialForceStrength((float)Math.PI*(diam+thickness-addThickness)*(thickness-addThickness)*tempT);
        }catch(NullPointerException ex){
           setResAxialForceStrength(null); 
        }
        
        try{
            float part1 = (float) (0.000310 / koef * Math.pow(diam, 2));
            float part2 = (float) (Math.pow((100 * (thickness - addThickness) / diam), (float) 2.5));
            setResAxialCompessiveForceLocal(part1 * part2);
        } catch (NullPointerException ex) {
            setResAxialCompessiveForceLocal(null);
        }
        
        try{
            float part = (float)((2.83*length_pr)/(diam+thickness-addThickness));
            setResFlexibility(part);
        }catch(NullPointerException ex){
            setResFlexibility(null);
        }
        
        try {
            float condition = length / diam;

            if (condition < 10) {
                setResAxialCompessiveForce(resAxialCompessiveForceLocal);
            } else if (condition >= 10) {
                float part1 = (float) ((Math.PI * (diam + thickness - addThickness) * (thickness - addThickness) * elasticity) / koef);
                float part2 = (float) (Math.pow(Math.PI, resFlexibility));

                setResAxialCompessiveForce(part1 * part2);
            }

        } catch (NullPointerException ex) {
            setResAxialCompessiveForce(null);
        }
        
        try{
            setResAxialForceElasticity(Math.min(resAxialCompessiveForceLocal, resAxialCompessiveForce)); 
        }catch(NullPointerException ex){
           setResAxialForceElasticity(null); 
        }
            
        try{
            float part1 = (float) Math.pow((resAxialForceStrength/resAxialForceElasticity),2);
            float part2 = (float) Math.sqrt(1+part1);
            
            setResAxialForcePermissible((float)resAxialForceStrength/part2);
            
        }catch(NullPointerException ex){
            setResAxialForcePermissible(null);
        }
        
        try{
           resStrengthConditionsThrust = resAxialForcePermissible>= force? "Выполнено":"Не выполнено";
           
            
        }catch(NullPointerException ex){
            setResStrengthConditionsThrust(null);
        }
        
        try{
            float part = (float)((Math.PI/4)*(diam+thickness-addThickness)*(thickness-addThickness)*tempT);
            setResBendingMomentStrength(part);
            
        }catch(NullPointerException ex){
            setResBendingMomentStrength(null);
        }
        
        try{
            
            float part1 = (float)(((0.000089*elasticity)/koef)*Math.pow(diam, 3));
            float part2 = (float)(Math.pow(((100*(thickness-addThickness))/diam),2.5));
            
            setResBendingMomentElasticity(part1*part2);
            
        }catch(NullPointerException ex){
            setResBendingMomentElasticity(null);
        }
        
        try{
            
            float part = (float) Math.sqrt(1+ Math.pow(resBendingMomentStrength/resBendingMomentElasticity, 2));
            setResBendingMomentPermissible(resBendingMomentStrength/part);
            
        }catch(NullPointerException ex){
          setResBendingMomentPermissible(null);  
        }
        
        try{
            resStrengthConditionsBendingMoment = resBendingMomentPermissible >= bending?"Выполнено":"Не выполнено";
        }catch(NullPointerException ex){
           setResStrengthConditionsBendingMoment(null); 
        }
        
        try{
            
            float part = (float) (0.25*Math.PI*diam*(thickness-addThickness)*tempT);
            setResShearForceStrength(part);        
            
        }catch(NullPointerException ex){
            setResShearForceStrength(null);        
        }
        
        try{
            float part1 = (float)((2.4*elasticity*(thickness-addThickness)*tempT)/koef);
            float part2 = (float)Math.abs(0.18+3.3*((diam*(thickness-addThickness))/(Math.pow(length, 2))));
            setResShearForceElasticity(part1*part2);
            
        }catch(NullPointerException ex){
            setResShearForceElasticity(null);
        }
        
        try{
            float part = (float) Math.sqrt(1+ Math.pow(resShearForceStrength/resShearForceElasticity, 2));
            setResBendingMomentPermissible(resShearForceStrength/part);
            
            setResShearForcePermissible(part);
            
        }catch(NullPointerException ex){
            setResShearForcePermissible(null);
        }
        
        try{
            resStrengthConditionsShearForce = resShearForcePermissible >= shift?"Выполнено":"Не выполнено";
            
        }catch(NullPointerException ex){
            setResStrengthConditionsShearForce(null);
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
                    res2.next();
//                    if(!res2.next()){
//                        setElasticity(null);
//                    }

                    setElasticity(res2.getFloat("value"));
                    
                    conn.commit();
                    committed = true;
                }catch(NullPointerException ex){
                    setElasticity(null);
                    
                    
                    
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
//        catch(NullPointerException ex){
//            setElasticity(null);
//        }
        
        
    }
    
    public void compareP(){
        try{
        resGreaterPressure = resIntPressure >= intPressure?"Выполнено":"Не выполнено";
        setResGreaterPressure(resGreaterPressure);
        }catch(NullPointerException ex){
           setResGreaterPressure(null); 
        }
    }
    
    public void compareS(){
        try{
        resGreaterThickness = thickness >= (resThickness+addThickness)?"Выполнено":"Не выполнено";
        setResGreaterThickness(resGreaterThickness);
        }catch(NullPointerException ex){
          setResGreaterThickness(null);  
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
        
        return intPressure ;
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
         
         //calculateThickness();
        
        
        
        
        
       
    }
    
    public void calculateThickness(){
        
        
        
        float corrosion = this.corrosion == null ? 0 : this.corrosion;
        
        float MinusTolerance = getMinusTolerance() == null ? 0 : getMinusTolerance();
        float Techno = (getTechno() == null ? 0 : getTechno()); 
        
        float sum = corrosion + MinusTolerance + Techno;
        
        if(sum == 0){
            setAddThickness(null);
        }
        else{
            addThickness = sum;
        }
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
     * @param elasticity the elasticity to set
     */
    public void setElasticity(Float elasticity) {
        this.elasticity = elasticity;
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
    public String getResStrengthConditionsThrust() {
        return resStrengthConditionsThrust;
    }

    /**
     * @param resStrengthConditionsThrust the resStrengthConditionsThrust to set
     */
    public void setResStrengthConditionsThrust(String resStrengthConditionsThrust) {
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
    public String getResStrengthConditionsBendingMoment() {
        return resStrengthConditionsBendingMoment;
    }

    /**
     * @param resStrengthConditionsBendingMoment the resStrengthConditionsBendingMoment to set
     */
    public void setResStrengthConditionsBendingMoment(String resStrengthConditionsBendingMoment) {
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
    public String getResStrengthConditionsShearForce() {
        return resStrengthConditionsShearForce;
    }

    /**
     * @param resStrengthConditionsShearForce the resStrengthConditionsShearForce to set
     */
    public void setResStrengthConditionsShearForce(String resStrengthConditionsShearForce) {
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
     * @return the elasticity
     */
    public Float getElasticity() {
        return elasticity;
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

    /**
     * @return the koef
     */
    public Float getKoef() {
        return koef;
    }

    /**
     * @param koef the koef to set
     */
    public void setKoef(Float koef) {
        this.koef = koef;
    }

    /**
     * @return the length
     */
    public Float getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Float length) {
        this.length = length;
    }

    /**
     * @return the length_pr
     */
    public Float getLength_pr() {
        return length_pr;
    }

    /**
     * @param length_pr the length_pr to set
     */
    public void setLength_pr(Float length_pr) {
        this.length_pr = length_pr;
    }

    /**
     * @return the resAxialCompessiveForce
     */
    public Float getResAxialCompessiveForce() {
        return resAxialCompessiveForce;
    }

    /**
     * @param resAxialCompessiveForce the resAxialCompessiveForce to set
     */
    public void setResAxialCompessiveForce(Float resAxialCompessiveForce) {
        this.resAxialCompessiveForce = resAxialCompessiveForce;
    }

}
