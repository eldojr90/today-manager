/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ConexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ConBDServ {
    	
	public static Connection getConnection(){
           
                Connection conn = null;
                String drive = "org.postgresql.Driver";
                String db = "fs-manager";
                String url = "jdbc:postgresql://192.168.0.3:5432/" + db;
                String user = "postgres";
                String psw = "fan_951753";
                
                
                try {
                     Class.forName(drive);
               
                    
                    conn = DriverManager.getConnection(url,user,psw);
                    
                } catch (SQLException e) {
                    
                    System.out.println("Falha na conexão com o banco de dados");
                    e.printStackTrace();
                    
                } catch (ClassNotFoundException ex) {
                    System.out.println("Driver não carregado");
                    ex.printStackTrace();
                    
            }
                
                
                
                return conn;
            
	}
}
