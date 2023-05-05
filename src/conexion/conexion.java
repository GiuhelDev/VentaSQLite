/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author giuse
 */
public class conexion {
    
    String strconexion="jdbc:sqlite:src/db/base.s3db";
    Connection conn=null; 
    
     public conexion(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection(strconexion);
            //System.out.println("conexion");
        } catch (Exception e) {
            System.out.println("no conexion "+e);
        }
    }
    
    public Connection conexionreporte(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection(strconexion);
            //System.out.println("conexion");
        } catch (Exception e) {
            System.out.println("no conexion "+e);
        }
        return conn;
    }
    
    public int ejecutarSentencia(String sql){
    
        try {
            PreparedStatement pstm=conn.prepareStatement(sql);
            pstm.execute();
            return 1;
            
        } catch (SQLException e) {
            return 0;
        }
        
    }
    
    
    public ResultSet consultar(String sql){
        
        try {
            PreparedStatement pstm=conn.prepareStatement(sql);
            ResultSet respuesta=pstm.executeQuery();
            return respuesta;
        } catch (Exception e) {
            return null;
        }
    }
}
