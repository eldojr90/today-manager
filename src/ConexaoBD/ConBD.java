/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ConexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ConBD {
    	
	public static Connection getConnection(){
           
                Connection conn = null;
                String drive = "org.postgresql.Driver";
                String db = "app-today";
                String url = "jdbc:postgresql://localhost:5432/" + db;
                String user = "postgres";
                String psw = "elisa010612";
                
                
                try {
                     Class.forName(drive);
               
                    
                    conn = DriverManager.getConnection(url,user,psw);
                    
                } catch (SQLException e) {
                    
                    System.out.println("Falha na conexão com o banco de dados");
                    JOptionPane.showMessageDialog(null,"Falha na conexão com o banco de dados. \n BD: "+db+" Usuário: "+user);
                    e.printStackTrace();
                    
                } catch (ClassNotFoundException ex) {
                    System.out.println("Driver não carregado");
                    ex.printStackTrace();
                    
            }
                
                
                
                return conn;
            
	}
}
