/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassesDAO;

import ClassesPrincipais.Usuarios;
import ConexaoBD.ConBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class UsuariosDAO{

        public boolean login(int id, String pass) {
           String sql = "select us_password from usuarios where us_id = "+id;
           Statement st = null;
           Connection conn = null;
           String consultaSenha = "";
            
           try {
                conn = ConBD.getConnection();
                st = conn.createStatement();
                
                ResultSet rs = st.executeQuery(sql);
                
                while(rs.next()){
                    consultaSenha = rs.getString("us_password");
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erro de SQLException.");
                JOptionPane.showMessageDialog(null, "Erro de SQLException." );
            }
           
           
           return consultaSenha.equals(pass);
           
           
        
        }
        public boolean idExiste(int id) {
           int count = 0;
           String sql = "select count(*)qtd from usuarios where us_id = "+id;
           Statement st = null;
           Connection conn = null;
           
            
           try {
                conn = ConBD.getConnection();
                st = conn.createStatement();
                
                ResultSet rs = st.executeQuery(sql);
                
                while(rs.next()){
                    count = rs.getInt("qtd");
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erro de SQLException.");
                JOptionPane.showMessageDialog(null, "Erro de SQLException." );
            }
           
           
           return count == 1;
           
           
        
        }
        public Usuarios pesquisaID(int id){ 
            Usuarios u = null;
          String sql = "select * from usuarios where us_id = "+id;
           Statement st = null;
           Connection conn = null;
           
            
           try {
                conn = ConBD.getConnection();
                st = conn.createStatement();
                
                ResultSet rs = st.executeQuery(sql);
                
                while(rs.next()){
                    u = new Usuarios(null, null, 0);
                    u.setId(rs.getInt("us_id"));
                    u.setNome(rs.getString("us_nome"));
                    u.setAcesso(rs.getInt("us_tipo_usuario"));
                    u.setSenha(rs.getString("us_password"));
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erro de SQLException.");
                JOptionPane.showMessageDialog(null, "Erro de SQLException." );
            }
           
            
            return u;
        } 
}
