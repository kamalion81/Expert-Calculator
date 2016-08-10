/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Kamalion
 */
public class DataBase {
    
    private DataSource ds;
    

    public DataBase(){

        InitialContext initContext = null;
        try {
            initContext = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ds = (DataSource) initContext.lookup("java:comp/env/jdbc/calcdb");
        } catch (NamingException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ds == null) {
            try {
                throw new SQLException("No data source");
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * @return the ds
     */
    public DataSource getDs() {
        return ds;
    }

    /**
     * @param ds the ds to set
     */
    public void setDs(DataSource ds) {
        this.ds = ds;
    }
    


}
