package dao;


import java.io.Serializable;
import java.math.BigDecimal;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.sql.*;
import java.util.HashMap;
import javax.xml.bind.JAXBException;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import profiles.CalcProfile;
import java.util.Date;

@ManagedBean
@ApplicationScoped
public class CalcDAO implements Serializable {
    
    private DataSource ds;
    
    private Integer id;
    private Date date;
    private String name;
    //Поля входящих данных
    private String material;
    private Integer id_open;
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
    
    public String getToday(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String data = format.format(cal.getTime());
        return data;
    }

    public CalcDAO() {
        
        
        
        this.temp = 20;
        this.koef = (float)2.4;
        
         DataBase db = new DataBase();
         ds = db.getDs();
        
         
    }
    
    public List<CalcProfile> getCalcs(){
        
        List<CalcProfile> calcs = new ArrayList<>();
        try {
            try (Connection conn = ds.getConnection()) {
                Statement stat = conn.createStatement();
                String query = "SELECT `calcs`.`id`,"
                        + "    `calcs`.`name`,"
                        + "    `calcs`.`date`,"
                        + "    `materials`.`matname`,"
                        + "    `calcs`.`intPressure`,"
                        + "    `calcs`.`temp`,"
                        + "    `calcs`.`tempT`,"
                        + "    `calcs`.`diam`,"
                        + "    `calcs`.`thickness`,"
                        + "    `calcs`.`corrosion`,"
                        + "    `calcs`.`minusTolerance`,"
                        + "    `calcs`.`techno`,"
                        + "    `calcs`.`addThickness`,"
                        + "    `calcs`.`elasticity`,"
                        + "    `calcs`.`weld`,"
                        + "    `calcs`.`koef`,"
                        + "    `calcs`.`length`,"
                        + "    `calcs`.`length_pr`,"
                        + "    `calcs`.`bending`,"
                        + "    `calcs`.`shift`,"
                        + "    `calcs`.`force`,"
                        + "    `calcs`.`resThickness`,"
                        + "    `calcs`.`resIntPressure`,"
                        + "    `calcs`.`resGreaterPressure`,"
                        + "    `calcs`.`resGreaterThickness`,"
                        + "    `calcs`.`resAxialForceStrength`,"
                        + "    `calcs`.`resAxialCompessiveForceLocal`,"
                        + "    `calcs`.`resFlexibility`,"
                        + "    `calcs`.`resAxialCompessiveForce`,"
                        + "    `calcs`.`resAxialForceElasticity`,"
                        + "    `calcs`.`resAxialForcePermissible`,"
                        + "    `calcs`.`resStrengthConditionsThrust`,"
                        + "    `calcs`.`resBendingMomentStrength`,"
                        + "    `calcs`.`resBendingMomentElasticity`,"
                        + "    `calcs`.`resBendingMomentPermissible`,"
                        + "    `calcs`.`resStrengthConditionsBendingMoment`,"
                        + "    `calcs`.`resShearForceStrength`,"
                        + "    `calcs`.`resShearForceElasticity`,"
                        + "    `calcs`.`resShearForcePermissible`,"
                        + "    `calcs`.`resStrengthConditionsShearForce`"
                        + "FROM `calculator`.`calcs` INNER JOIN `calculator`.`materials` ON `calcs`.`materials_id` = `materials`.`id`";
                ResultSet result = stat.executeQuery(query);
                while (result.next()) {
                    CalcProfile profile = new CalcProfile(result.getInt("id"),result.getDate("date"), result.getString("name"));
                    calcs.add(profile);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calcs;
        
        
        
    }
    
    public void fillCalc(){
        
        try {
            try (Connection conn = ds.getConnection()) {
                String query = "SELECT calcs.id,"
                        + "    `calcs`.`name`,"
                        + "    `calcs`.`date`,"
                        + "    `materials`.`matname`,"
                        + "    `calcs`.`intPressure`,"
                        + "    `calcs`.`temp`,"
                        + "    `calcs`.`tempT`,"
                        + "    `calcs`.`diam`,"
                        + "    `calcs`.`thickness`,"
                        + "    `calcs`.`corrosion`,"
                        + "    `calcs`.`minusTolerance`,"
                        + "    `calcs`.`techno`,"
                        + "    `calcs`.`addThickness`,"
                        + "    `calcs`.`elasticity`,"
                        + "    `calcs`.`weld`,"
                        + "    `calcs`.`koef`,"
                        + "    `calcs`.`length`,"
                        + "    `calcs`.`length_pr`,"
                        + "    `calcs`.`bending`,"
                        + "    `calcs`.`shift`,"
                        + "    `calcs`.`force`,"
                        + "    `calcs`.`resThickness`,"
                        + "    `calcs`.`resIntPressure`,"
                        + "    `calcs`.`resGreaterPressure`,"
                        + "    `calcs`.`resGreaterThickness`,"
                        + "    `calcs`.`resAxialForceStrength`,"
                        + "    `calcs`.`resAxialCompessiveForceLocal`,"
                        + "    `calcs`.`resFlexibility`,"
                        + "    `calcs`.`resAxialCompessiveForce`,"
                        + "    `calcs`.`resAxialForceElasticity`,"
                        + "    `calcs`.`resAxialForcePermissible`,"
                        + "    `calcs`.`resStrengthConditionsThrust`,"
                        + "    `calcs`.`resBendingMomentStrength`,"
                        + "    `calcs`.`resBendingMomentElasticity`,"
                        + "    `calcs`.`resBendingMomentPermissible`,"
                        + "    `calcs`.`resStrengthConditionsBendingMoment`,"
                        + "    `calcs`.`resShearForceStrength`,"
                        + "    `calcs`.`resShearForceElasticity`,"
                        + "    `calcs`.`resShearForcePermissible`,"
                        + "    `calcs`.`resStrengthConditionsShearForce`"
                        + " FROM `calculator`.`calcs` INNER JOIN `calculator`.`materials` ON `calcs`.`materials_id` = `materials`.`id`"
                        + " WHERE calcs.id = ?";
               PreparedStatement pstat = conn.prepareStatement(query);
               pstat.setInt(1, id_open);
               ResultSet result = pstat.executeQuery();
               
               result.next();
               
                setId(result.getInt("id"));
                setDate(result.getDate("date"));
                setName(result.getString("name"));
                setMaterial(result.getString("matname"));
                setIntPressure(result.getFloat("intPressure"));
                setTemp(result.getInt("temp"));
                setTempT(result.getFloat("tempT"));
                setDiam(result.getInt("diam"));
                setThickness(result.getInt("thickness"));
                setCorrosion(result.getFloat("corrosion"));
                setMinusTolerance(result.getFloat("minusTolerance"));
                setTechno(result.getFloat("techno"));
                setAddThickness(result.getFloat("addThickness"));
                setElasticity(result.getFloat("elasticity"));
                setWeld(result.getFloat("weld"));
                setKoef(result.getFloat("koef"));
                setLength(result.getFloat("length"));
                setLength_pr(result.getFloat("length_pr"));
                setBending(result.getFloat("bending"));
                setShift(result.getFloat("shift"));
                setForce(result.getFloat("force"));
                setResThickness(result.getFloat("resThickness"));
                setResIntPressure(result.getFloat("resIntPressure"));
                setResGreaterPressure(result.getString("resGreaterPressure").matches("null")?"":result.getString("resGreaterPressure"));
                setResGreaterThickness(result.getString("resGreaterThickness").matches("null")?"":result.getString("resGreaterThickness"));
                setResAxialForceStrength(result.getFloat("resAxialForceStrength"));
                setResAxialCompessiveForceLocal(result.getFloat("resAxialCompessiveForceLocal"));
                setResFlexibility(result.getFloat("resFlexibility"));
                setResAxialCompessiveForce(result.getFloat("resAxialCompessiveForce"));
                setResAxialForceElasticity(result.getFloat("resAxialForceElasticity"));
                setResAxialForcePermissible(result.getFloat("resAxialForcePermissible"));
                setResStrengthConditionsThrust(result.getString("resStrengthConditionsThrust").matches("null")?"":result.getString("resStrengthConditionsThrust"));
                setResBendingMomentStrength(result.getFloat("resBendingMomentStrength"));
                setResBendingMomentElasticity(result.getFloat("resBendingMomentElasticity"));
                setResBendingMomentPermissible(result.getFloat("resBendingMomentPermissible"));
                setResStrengthConditionsBendingMoment(result.getString("resStrengthConditionsBendingMoment").matches("null")?"":result.getString("resStrengthConditionsBendingMoment"));
                setResShearForceStrength(result.getFloat("resShearForceStrength"));
                setResShearForceElasticity(result.getFloat("resShearForceElasticity"));
                setResShearForcePermissible(result.getFloat("resShearForcePermissible"));
                setResStrengthConditionsShearForce(result.getString("resStrengthConditionsShearForce").matches("null")?"":result.getString("resStrengthConditionsShearForce"));
               
                }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
   public void exportWord() throws Docx4JException, JAXBException {
       
        
        //org.docx4j.wml.ObjectFactory foo = Context.getWmlObjectFactory();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("export.docx").getFile());

        //String inputfilepath = "d:\\Java\\Задания\\Калькулятор расчетов\\export.docx";
        boolean save = true;
        String property = System.getProperty("user.home") + "\\Documents";
        
        String data;
        data = date == null?getToday():date.toString();
        
        String outputfilepath = property + "\\" + name + "_" + data + ".docx";
//		String outputfilepath = "d:\\Java\\Задания\\Калькулятор расчетов\\"+name+"_"+date.toString()+".docx";

//		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
//				.load(new File(inputfilepath));
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                .load(file);
        
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        
        HashMap<String, String> mappings = new HashMap<>();
        
        try{
           mappings.put("c4", material);
           mappings.put("press", intPressure.toString());
           mappings.put("temp", temp.toString());
           mappings.put("diam", diam.toString());
           mappings.put("thikness", thickness.toString());
           mappings.put("c6", tempT.toString());
           mappings.put("module", elasticity.toString());
           mappings.put("c1", corrosion.toString());
           mappings.put("c2", minusTolerance.toString());
           mappings.put("c3", techno.toString());
           mappings.put("c7", addThickness.toString());
           mappings.put("c8", weld.toString());
           mappings.put("c9", koef.toString());
           mappings.put("c10", length.toString());
           mappings.put("c11", length_pr.toString());
           mappings.put("c12", resThickness.toString());
           mappings.put("c13", resIntPressure.toString());
           mappings.put("c14", resGreaterPressure);
           Float c15 = resThickness + addThickness;
           mappings.put("c15", c15.toString());
           mappings.put("c16", resGreaterThickness);
           mappings.put("c20", resAxialForceStrength.toString());
           mappings.put("c21", resAxialCompessiveForceLocal.toString());
           mappings.put("c22", resFlexibility.toString());
           Float param = length / diam;
           if (param < 10) {
               mappings.put("c23", resAxialCompessiveForce.toString());
               mappings.put("c24", "=" + param.toString());
               mappings.put("c25", "");
               mappings.put("c26", "");
           } else {
               mappings.put("c23", "");
               mappings.put("c24", "");
               mappings.put("c25", resAxialCompessiveForce.toString());
               mappings.put("c26", "=" + param.toString());
           }
           mappings.put("c27", resAxialForceElasticity.toString());
           mappings.put("c28", resAxialForcePermissible.toString());
           mappings.put("c29", bending.toString());
           mappings.put("c30", shift.toString());
           mappings.put("c31", force.toString());
           mappings.put("c33", resStrengthConditionsThrust);
           mappings.put("c34", resBendingMomentStrength.toString());
           mappings.put("c35", resBendingMomentElasticity.toString());
           mappings.put("c36", resBendingMomentPermissible.toString());
           mappings.put("c37", resShearForceStrength.toString());
           mappings.put("c38", resShearForceElasticity.toString());
           mappings.put("c39", resShearForcePermissible.toString());

       } catch (NullPointerException ex) {
           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Документ не создан", "Не хватает данных для успешного создания документа");
           FacesContext.getCurrentInstance().addMessage(null, msg);
           
           Logger.getLogger(CalcDAO.class.getName()).log(Level.SEVERE, null, ex);
           return;
       }
        

        // Approach 1 (from 3.0.0; faster if you haven't yet caused unmarshalling to occur):
        documentPart.variableReplace(mappings);

        /*		// Approach 2 (original)
		
			// unmarshallFromTemplate requires string input
			String xml = XmlUtils.marshaltoString(documentPart.getJaxbElement(), true);
			// Do it...
			Object obj = XmlUtils.unmarshallFromTemplate(xml, mappings);
			// Inject result into docx
			documentPart.setJaxbElement((Document) obj);
         */
        // Save it
        if (save) {
            SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
            saver.save(outputfilepath);
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Выгрузка завершена", "Файл сохранен : " + outputfilepath);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            System.out.println(XmlUtils.marshaltoString(documentPart.getJaxbElement(), true,
                    true));
        }
        
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
                    
                    BigDecimal x = new BigDecimal(res2.getFloat("value"));
                    x = x.setScale(2, BigDecimal.ROUND_HALF_UP);
                    
                    setTempT(x.floatValue());

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
        }
        
        writeResults();

    }
    
    public void changeC(){
        
        setCorrosion(corrosion == null ? 0 : corrosion);
        setMinusTolerance(minusTolerance == null ? 0 : minusTolerance);
        setTechno(techno == null ? 0 : techno);
        Float result = corrosion + minusTolerance + techno;
        
        BigDecimal x = new BigDecimal(result);
        x = x.setScale(2, BigDecimal.ROUND_HALF_UP);
        
        setAddThickness(result == 0 ? null : x.floatValue());
        
        writeResults();
        
    }
    
    public void writeResults(){
        
        Float res;
        
        try {
            res = intPressure * diam / (2 * tempT * weld - intPressure);
            //setResThickness(intPressure * diam / (2 * tempT * weld - intPressure));
            if(res.isNaN()||res.isInfinite()||res == 0){
                setResThickness(null);
            } else {
                setResThickness(res);
            }
        } catch (NullPointerException ex) {
            setResThickness(null);
        }

        try {
            res = (2 * weld * tempT * (thickness - addThickness)) / (diam + thickness - addThickness);
            if(res.isNaN()||res.isInfinite()||res == 0){
                setResIntPressure(null);
            } else {
                setResIntPressure(res);
            }
            //setResIntPressure((2 * weld * tempT * (thickness - addThickness)) / (diam + thickness - addThickness));
        } catch (NullPointerException ex) {
            setResIntPressure(null);
        }
       
        if(!(resIntPressure == null)){
            compareP();
            compareS();
            
        }
        
        try{
            res = (float)Math.PI*(diam+thickness-addThickness)*(thickness-addThickness)*tempT;
           // setResAxialForceStrength((float)Math.PI*(diam+thickness-addThickness)*(thickness-addThickness)*tempT);
           if(res.isNaN()||res.isInfinite()||res == 0){
                setResAxialForceStrength(null);
            } else {
                setResAxialForceStrength(res);
            }
        }catch(NullPointerException ex){
           setResAxialForceStrength(null); 
        }
        
        try{
            float part1 = (float) (0.000360*elasticity/ koef * Math.pow(diam, 2));
            float part2 = (float) (Math.pow((100 * (thickness - addThickness) / diam), (float) 2.5));
            res = part1*part2;
            
            if(res.isNaN()||res.isInfinite()||res == 0){
                setResAxialCompessiveForceLocal(null);
            } else {
                setResAxialCompessiveForceLocal(part1 * part2);
            }
                
            
        } catch (NullPointerException ex) {
            setResAxialCompessiveForceLocal(null);
        }
        
        try{
            //float part = (float)((2.83*length_pr)/(diam+thickness-addThickness));
            res = (float)((2.83*length_pr)/(diam+thickness-addThickness));
            if(res.isNaN()||res.isInfinite()||res == 0){
                setResFlexibility(null);
            } else {
                setResFlexibility(res);
            }
            
            //setResFlexibility(part);
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
                res = part1 * part2;
                if (res.isNaN() || res.isInfinite()||res == 0) {
                    setResAxialCompessiveForce(null);
                } else {
                    setResAxialCompessiveForce(res);
                }

                //setResAxialCompessiveForce(part1 * part2);
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
            float part1 = (float) Math.pow((resAxialForceStrength / resAxialForceElasticity), 2);
            float part2 = (float) Math.sqrt(1 + part1);

            res = (float) resAxialForceStrength / part2;

            //setResAxialForcePermissible((float)resAxialForceStrength/part2);
            if (res.isNaN() || res.isInfinite()||res == 0) {
                setResAxialForcePermissible(null);
            } else {
                setResAxialForcePermissible(res);
            }

        } catch (NullPointerException ex) {
            setResAxialForcePermissible(null);
        }
        
        try{
           resStrengthConditionsThrust = resAxialForcePermissible>= force? "Выполнено":"Не выполнено";
           
            
        }catch(NullPointerException ex){
            setResStrengthConditionsThrust(null);
        }
        
        try{
            res = (float)((Math.PI/4)*(diam+thickness-addThickness)*(thickness-addThickness)*tempT);
            //setResBendingMomentStrength(part);
             if (res.isNaN() || res.isInfinite()||res == 0) {
                setResBendingMomentStrength(null);
            } else {
                setResBendingMomentStrength(res);
            }
            
        }catch(NullPointerException ex){
            setResBendingMomentStrength(null);
        }
        
        try{
            
            float part1 = (float)(((0.000089*elasticity)/koef)*Math.pow(diam, 3));
            float part2 = (float)(Math.pow(((100*(thickness-addThickness))/diam),2.5));
            
            res = part1*part2;
            if (res.isNaN() || res.isInfinite()||res == 0) {
                setResBendingMomentElasticity(null);
            } else {
                setResBendingMomentElasticity(res);
            }
            
            //setResBendingMomentElasticity(part1*part2);
            
        }catch(NullPointerException ex){
            setResBendingMomentElasticity(null);
        }
        
        try{
            
            float part = (float) Math.sqrt(1+ Math.pow(resBendingMomentStrength/resBendingMomentElasticity, 2));
            res = resBendingMomentStrength/part;
            //setResBendingMomentPermissible(resBendingMomentStrength/part);
             if (res.isNaN() || res.isInfinite()||res == 0) {
                setResBendingMomentPermissible(null);
            } else {
                setResBendingMomentPermissible(res);
            }
            
        }catch(NullPointerException ex){
          setResBendingMomentPermissible(null);  
        }
        
        try{
            resStrengthConditionsBendingMoment = resBendingMomentPermissible >= bending?"Выполнено":"Не выполнено";
        }catch(NullPointerException ex){
           setResStrengthConditionsBendingMoment(null); 
        }
        
        try{
            
            res = (float) (0.25*Math.PI*diam*(thickness-addThickness)*tempT);
            if (res.isNaN() || res.isInfinite()||res == 0) {
                setResShearForceStrength(null);
            } else {
                setResShearForceStrength(res);
            }
            //setResShearForceStrength(part);        
            
        }catch(NullPointerException ex){
            setResShearForceStrength(null);        
        }
        
        try{
            float part1 = (float)((2.4*elasticity*(thickness-addThickness)*tempT)/koef);
            float part2 = (float)Math.abs(0.18+3.3*((diam*(thickness-addThickness))/(Math.pow(length, 2))));
            res = part1*part2;
            if (res.isNaN() || res.isInfinite()||res == 0) {
                setResShearForceElasticity(null);
            } else {
                setResShearForceElasticity(res);
            }
            
            //setResShearForceElasticity(part1*part2);
            
        }catch(NullPointerException ex){
            setResShearForceElasticity(null);
        }
        
        try{
            float part = (float) Math.sqrt(1+ Math.pow(resShearForceStrength/resShearForceElasticity, 2));
            res = resShearForceStrength/part;
            if (res.isNaN() || res.isInfinite()||res == 0) {
                setResShearForcePermissible(null);
            } else {
                setResShearForcePermissible(res);
            }
            //setResShearForcePermissible(resShearForceStrength/part);
            
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
    
    public void save(ActionEvent actionEvent) {

        String data = getToday();

        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;
                
                
                PreparedStatement stat = conn.prepareStatement("SELECT id FROM calculator.materials where matname = ?");
                        stat.setString(1, material);
                        ResultSet res = stat.executeQuery();

                        res.next();
                        int material_id = res.getInt("id");
                
                
                
                try{
                
                String query = "INSERT INTO `calculator`.`calcs`"
                        + "(`name`,"
                        + "`date`,"
                        + "`materials_id`,"
                        + "`intPressure`,"
                        + "`temp`,"
                        + "`tempT`,"
                        + "`diam`,"
                        + "`thickness`,"
                        + "`corrosion`,"
                        + "`minusTolerance`,"
                        + "`techno`,"
                        + "`addThickness`,"
                        + "`elasticity`,"
                        + "`weld`,"
                        + "`koef`,"
                        + "`length`,"
                        + "`length_pr`,"
                        + "`bending`,"
                        + "`shift`,"
                        + "`force`,"
                        + "`resThickness`,"
                        + "`resIntPressure`,"
                        + "`resGreaterPressure`,"
                        + "`resGreaterThickness`,"
                        + "`resAxialForceStrength`,"
                        + "`resAxialCompessiveForceLocal`,"
                        + "`resFlexibility`,"
                        + "`resAxialCompessiveForce`,"
                        + "`resAxialForceElasticity`,"
                        + "`resAxialForcePermissible`,"
                        + "`resStrengthConditionsThrust`,"
                        + "`resBendingMomentStrength`,"
                        + "`resBendingMomentElasticity`,"
                        + "`resBendingMomentPermissible`,"
                        + "`resStrengthConditionsBendingMoment`,"
                        + "`resShearForceStrength`,"
                        + "`resShearForceElasticity`,"
                        + "`resShearForcePermissible`,"
                        + "`resStrengthConditionsShearForce`)"
                        + "VALUES"
                        + "('"+name+"',"
                        + "'"+data+"',"
                        + material_id+","
                        + intPressure+","
                        + temp+","
                        + tempT+","
                        + diam+","
                        + thickness+","
                        + corrosion+","
                        + minusTolerance+","
                        + techno+","
                        + addThickness+","
                        + elasticity+","
                        + weld+","
                        + koef+","
                        + length+","
                        + length_pr+","
                        + bending+","
                        + shift+","
                        + force+","
                        + resThickness+","
                        + resIntPressure+","
                        + "'"+resGreaterPressure+"',"
                        + "'"+resGreaterThickness+"',"
                        + resAxialForceStrength+","
                        + resAxialCompessiveForceLocal+","
                        + resFlexibility+","
                        + resAxialCompessiveForce+","
                        + resAxialForceElasticity+","
                        + resAxialForcePermissible+","
                        + "'"+resStrengthConditionsThrust+"',"
                        + resBendingMomentStrength+","
                        + resBendingMomentElasticity+","
                        + resBendingMomentPermissible+","
                        + "'"+resStrengthConditionsBendingMoment+"',"
                        + resShearForceStrength+","
                        + resShearForceElasticity+","
                        + resShearForcePermissible+","
                        + "'"+resStrengthConditionsShearForce+"')";
                
                String query2 = "UPDATE `calculator`.`calcs`"
                            + " SET"
                            + "name = "+name+","
                            + "`materials_id` = "+material_id+","
                            + "`intPressure` = "+intPressure+","
                            + "`temp` = "+temp+" ,"
                            + "`tempT` = "+tempT+" ,"
                            + "`diam` = "+diam+" ,"
                            + "`thickness` = "+thickness+" ,"
                            + "`corrosion` = "+corrosion+" ,"
                            + "`minusTolerance` = "+minusTolerance+" ,"
                            + "`techno` = "+techno+" ,"
                            + "`addThickness` = "+addThickness+" ,"
                            + "`elasticity` = "+elasticity+" ,"
                            + "`weld` = "+weld+" ,"
                            + "`koef` = "+koef+" ,"
                            + "`length` = "+length+" ,"
                            + "`length_pr` = "+length_pr+" ,"
                            + "`bending` = "+bending+" ,"
                            + "`shift` = "+shift+" ,"
                            + "`force` = "+force+" ,"
                            + "`resThickness` = "+resThickness+" ,"
                            + "`resIntPressure` = "+resIntPressure+" ,"
                            + "`resGreaterPressure` = '"+resGreaterPressure+"' ,"
                            + "`resGreaterThickness` = '"+resGreaterThickness+"' ,"
                            + "`resAxialForceStrength` = "+resAxialForceStrength+" ,"
                            + "`resAxialCompessiveForceLocal` = "+resAxialCompessiveForceLocal+" ,"
                            + "`resFlexibility` = "+resFlexibility+" ,"
                            + "`resAxialCompessiveForce` = "+resAxialCompessiveForce+" ,"
                            + "`resAxialForceElasticity` = "+resAxialForceElasticity+" ,"
                            + "`resAxialForcePermissible` = "+resAxialForcePermissible+" ,"
                            + "`resStrengthConditionsThrust` = '"+resStrengthConditionsThrust+"' ,"
                            + "`resBendingMomentStrength` = "+resBendingMomentStrength+" ,"
                            + "`resBendingMomentElasticity` = "+resBendingMomentElasticity+" ,"
                            + "`resBendingMomentPermissible` = "+resBendingMomentPermissible+" ,"
                            + "`resStrengthConditionsBendingMoment` = '"+resStrengthConditionsBendingMoment+"' ,"
                            + "`resShearForceStrength` = "+resShearForceStrength+" ,"
                            + "`resShearForceElasticity` = "+resShearForceElasticity+" ,"
                            + "`resShearForcePermissible` = "+resShearForcePermissible+" ,"
                            + "`resStrengthConditionsShearForce` = '"+resStrengthConditionsShearForce+"' "
                            + "WHERE `id` = "+id+";";
                
                
                
                if(!(id==null)){
                    query = query2;
                    
                }
                
                PreparedStatement stat2 = conn.prepareStatement(query);

                        stat2.executeUpdate();
                        
                        conn.commit();
                        committed = true;
                        
                }finally{
                        if (!committed) {
                            conn.rollback();
                        }
                    
                }
            }
        } catch (SQLException ex) {
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные не сохранены", "Недостаточно данных для успешного сохранения");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            Logger.getLogger(CalcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        
        addMessage("Данные сохранены");
        
        
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    /**
     * @return the id_open
     */
    public Integer getId_open() {
        return id_open;
    }

    /**
     * @param id_open the id_open to set
     */
    public void setId_open(Integer id_open) {
        this.id_open = id_open;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
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

}
