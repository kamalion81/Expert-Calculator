package profiles;

import java.util.Date;

/**
 *
 * @author Kamalion
 */
public class CalcProfile {
    
    private int id;
    private Date date;
    private String name;
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

    public CalcProfile(int id, Date date, String name, String material, 
            Float intPressure, Integer temp, Float tempT, Integer diam,
            Integer thickness, Float corrosion, Float minusTolerance,
            Float techno, Float addThickness, Float elasticity,
            Float weld, Float bending, Float shift, Float force, Float koef,
            Float length, Float length_pr, Float resThickness, String resGreaterPressure,
            String resGreaterThickness, Float resIntPressure, Float resAxialForceStrength,
            Float resAxialCompessiveForceLocal, Float resFlexibility, Float resAxialCompessiveForce,
            Float resAxialForceElasticity, Float resAxialForcePermissible, String resStrengthConditionsThrust,
            Float resBendingMomentStrength, Float resBendingMomentElasticity, Float resBendingMomentPermissible,
            String resStrengthConditionsBendingMoment, Float resShearForceStrength, Float resShearForceElasticity,
            Float resShearForcePermissible, String resStrengthConditionsShearForce) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.material = material;
        this.intPressure = intPressure;
        this.temp = temp;
        this.tempT = tempT;
        this.diam = diam;
        this.thickness = thickness;
        this.corrosion = corrosion;
        this.minusTolerance = minusTolerance;
        this.techno = techno;
        this.addThickness = addThickness;
        this.elasticity = elasticity;
        this.weld = weld;
        this.bending = bending;
        this.shift = shift;
        this.force = force;
        this.koef = koef;
        this.length = length;
        this.length_pr = length_pr;
        this.resThickness = resThickness;
        this.resGreaterPressure = resGreaterPressure;
        this.resGreaterThickness = resGreaterThickness;
        this.resIntPressure = resIntPressure;
        this.resAxialForceStrength = resAxialForceStrength;
        this.resAxialCompessiveForceLocal = resAxialCompessiveForceLocal;
        this.resFlexibility = resFlexibility;
        this.resAxialCompessiveForce = resAxialCompessiveForce;
        this.resAxialForceElasticity = resAxialForceElasticity;
        this.resAxialForcePermissible = resAxialForcePermissible;
        this.resStrengthConditionsThrust = resStrengthConditionsThrust;
        this.resBendingMomentStrength = resBendingMomentStrength;
        this.resBendingMomentElasticity = resBendingMomentElasticity;
        this.resBendingMomentPermissible = resBendingMomentPermissible;
        this.resStrengthConditionsBendingMoment = resStrengthConditionsBendingMoment;
        this.resShearForceStrength = resShearForceStrength;
        this.resShearForceElasticity = resShearForceElasticity;
        this.resShearForcePermissible = resShearForcePermissible;
        this.resStrengthConditionsShearForce = resStrengthConditionsShearForce;
    }
    
    public CalcProfile(int id, Date date, String name){
        
        this.id = id;
        this.date = date;
        this.name = name;
        
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the intPressure
     */
    public Float getIntPressure() {
        return intPressure;
    }

    /**
     * @param intPressure the intPressure to set
     */
    public void setIntPressure(Float intPressure) {
        this.intPressure = intPressure;
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
     * @param diam the diam to set
     */
    public void setDiam(Integer diam) {
        this.diam = diam;
    }

    /**
     * @return the thickness
     */
    public Integer getThickness() {
        return thickness;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(Integer thickness) {
        this.thickness = thickness;
    }

    /**
     * @return the corrosion
     */
    public Float getCorrosion() {
        return corrosion;
    }

    /**
     * @param corrosion the corrosion to set
     */
    public void setCorrosion(Float corrosion) {
        this.corrosion = corrosion;
    }

    /**
     * @return the minusTolerance
     */
    public Float getMinusTolerance() {
        return minusTolerance;
    }

    /**
     * @param minusTolerance the minusTolerance to set
     */
    public void setMinusTolerance(Float minusTolerance) {
        this.minusTolerance = minusTolerance;
    }

    /**
     * @return the techno
     */
    public Float getTechno() {
        return techno;
    }

    /**
     * @param techno the techno to set
     */
    public void setTechno(Float techno) {
        this.techno = techno;
    }

    /**
     * @return the addThickness
     */
    public Float getAddThickness() {
        return addThickness;
    }

    /**
     * @param addThickness the addThickness to set
     */
    public void setAddThickness(Float addThickness) {
        this.addThickness = addThickness;
    }

    /**
     * @return the elasticity
     */
    public Float getElasticity() {
        return elasticity;
    }

    /**
     * @param elasticity the elasticity to set
     */
    public void setElasticity(Float elasticity) {
        this.elasticity = elasticity;
    }

    /**
     * @return the weld
     */
    public Float getWeld() {
        return weld;
    }

    /**
     * @param weld the weld to set
     */
    public void setWeld(Float weld) {
        this.weld = weld;
    }

    /**
     * @return the bending
     */
    public Float getBending() {
        return bending;
    }

    /**
     * @param bending the bending to set
     */
    public void setBending(Float bending) {
        this.bending = bending;
    }

    /**
     * @return the shift
     */
    public Float getShift() {
        return shift;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(Float shift) {
        this.shift = shift;
    }

    /**
     * @return the force
     */
    public Float getForce() {
        return force;
    }

    /**
     * @param force the force to set
     */
    public void setForce(Float force) {
        this.force = force;
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

    
    
    
}
