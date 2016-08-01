/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.annotation.Resource;

/**
 *
 * @author Kamalion
 */
public class DataBase {
    
//    @Resource(name="jdbc/calcdb")
    private DataSource ds;
    
    private Connection dbConnection;

    public DataBase() throws NamingException, SQLException {

        InitialContext initContext = new InitialContext();
        ds = (DataSource) initContext.lookup("java:comp/env/jdbc/calcdb");
        if (ds == null) {
            throw new SQLException("No data source");
        }

    }

    /**
     * @return the dbConnection
     * @throws java.sql.SQLException
     */
    public Connection getdbConnection() throws SQLException {
        dbConnection = ds.getConnection();
        return dbConnection;
    }

}
