/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassesDAO;

import ClassesPrincipais.Atendimentos;
import ClassesPrincipais.Cliente;
import ConexaoBD.ConBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class AtendimentosDAO{
         
         public int NovoAtendimento(Atendimentos at){
         int linhasAlteradas = 0;
         String sql = "insert into atendimentos(cli_id, atd_desc, atd_status, atd_data) " +
            "values (?, ?, ?, CURRENT_DATE);";
         Connection conn = ConBD.getConnection();
         PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, at.getCli_id());
                ps.setString(2, at.getDescricao());
                ps.setInt(3, at.getStatus());
                
                linhasAlteradas = ps.executeUpdate();
                
                ps.close();
                
            } catch (SQLException ex) {
                System.out.println("Falha ao cadastrar Atendimento. BD:SQLException");
                JOptionPane.showMessageDialog(null,"Falha ao cadastrar Atendimento. BD:SQLException");
               
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (NovoAtendimento)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (NovoAtendimento)");
                    ex.printStackTrace();
                }
            }

         
         return linhasAlteradas;
         }
         
         public int AlterarAtendimentoMtData(Atendimentos at){
             
             int la = 0;
             
             String  sqlMantemData = "update atendimentos set "
                     + "atd_desc = ? "
                     + "where atd_id = ?";
             
                       
           
             Connection con = ConBD.getConnection();
             PreparedStatement ps = null;
            try {
                
                        ps = con.prepareStatement(sqlMantemData);
                        ps.setString(1, at.getDescricao());
                        ps.setInt(2, at.getId());
                        la = ps.executeUpdate();
                
                
                
                ps.close();            
                
                
            } catch (SQLException ex) {
                System.out.println("Erro ao alterar atendimento. BD:SQLException. (AlterarAtendimentoMtData)");
                JOptionPane.showMessageDialog(null, "Erro ao alterar atendimento. BD:SQLException. (AlterarAtendimentoMtData)");
                ex.printStackTrace();
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (AlterarAtendimentoMtData)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (AlterarAtendimentoMtData)");
                    ex.printStackTrace();
                }
            }
             
             
             
             
             return la;
         }
         
         public int AlterarAtendimentoModData(Atendimentos at){
             
             int la = 0;
             
            
             
             String sqlModificaData = "update atendimentos set "
                     + "atd_desc = ?, "
                     + "atd_data = CURRENT_DATE "
                    + "where atd_id = ?";
             
           
             Connection con = ConBD.getConnection();
             PreparedStatement ps = null;
            try {
                
                        ps = con.prepareStatement(sqlModificaData);
                        ps.setString(1, at.getDescricao());
                        ps.setInt(2, at.getId());
                        la = ps.executeUpdate();
                
                
                
                ps.close();            
                
                
            } catch (SQLException ex) {
                System.out.println("Erro ao alterar atendimento. BD:SQLException. (AlterarAtendimentoModData)");
                JOptionPane.showMessageDialog(null, "Erro ao alterar atendimento. BD:SQLException. (AlterarAtendimentoModData)");
                ex.printStackTrace();
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (AlterarAtendimentoModData)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (AlterarAtendimentoModData)");
                    ex.printStackTrace();
                }
            }
             
             
             
             
             
             return la;
         }
         
         public int AlterarStatus(int idAtd, int status){
              
             int la = 0;
             
             String  sqlMantemData = "update atendimentos set "
                     + "atd_status = ? "
                     + "where atd_id = ?";
             
                       
           
             Connection con = ConBD.getConnection();
             PreparedStatement ps = null;
            try {
                
                        ps = con.prepareStatement(sqlMantemData);
                        
                        ps.setInt(1, status  );
                        ps.setInt(2, idAtd );
                        la = ps.executeUpdate();
                
                
                
                ps.close();            
                
                
            } catch (SQLException ex) {
                System.out.println("Erro ao alterar status atendimento. BD:SQLException. (AlterarStatus)");
                JOptionPane.showMessageDialog(null, "Erro ao alterar status atendimento. BD:SQLException. (AlterarStatus)");
                ex.printStackTrace();
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (AlterarStatus)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (AlterarStatus)");
                    ex.printStackTrace();
                }
            }
             
             
             
             
             return la;
         }
         
         public Vector[] exibePendentes(){
            int count = countAtendPend();
             Connection conn = null;
            PreparedStatement ps = null;
            
            String sql = "select atd_id, cli_id, atd_desc, to_char(atd_data , 'DD/MM/YYYY') atd_data, atd_status from atendimentos where atd_status > 0 order by atd_status desc;";
            int i = 0;

                Vector vc[] = new Vector[count];

            try {
                conn = ConBD.getConnection();
                ps = conn.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Vector row = new Vector();
                        row.add(rs.getInt("atd_id"));
                        row.add(rs.getString("atd_data"));
                        ClienteDAO cd = new ClienteDAO();
                        AtendimentosDAO ad = new AtendimentosDAO();
                        Cliente c = cd.PesquisaID(rs.getInt("cli_id"));
                        row.add(c.getRSocial()+" - "+c.getNFantasia());
                        row.add(rs.getString("atd_desc"));
                        row.add(ad.retornaStatus(rs.getInt("atd_status")));

                    vc[i] = row;
                    i++;

                }

                ps.close();



            } catch (SQLException ex) {
                System.out.println("Erro no metodo exibePend. BD:SQLException (exibePendentes)");
                JOptionPane.showMessageDialog(null, "Erro no metodo exibePend. BD:SQLException (exibePendentes)");

            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (exibePendentes)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (exibePendentes)");
                    ex.printStackTrace();
                }
            }


            return vc;}
         
         public Vector[] exibeHistórico(){
            int count = countAtendimentos();
             Connection conn = null;
            PreparedStatement ps = null;
            String sql = "select * from atendimentos order by atd_id desc;";
                   int i = 0;

                Vector vc[] = new Vector[count];

            try {
                conn = ConBD.getConnection();
                ps = conn.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Vector row = new Vector();
                        row.add(rs.getInt("atd_id"));
                        row.add(rs.getString("atd_data"));
                        Cliente c = new ClienteDAO().PesquisaID(rs.getInt("cli_id"));
                        row.add(c.getRSocial()+" - "+c.getNFantasia());
                        row.add(rs.getString("atd_desc"));
                        row.add(retornaStatus(rs.getInt("atd_status")));

                    vc[i] = row;
                    i++;

                }

                ps.close();



            } catch (SQLException ex) {
                System.out.println("Erro no metodo exibeHistório. BD:SQLException(exibeHistórico)");
                JOptionPane.showMessageDialog(null, "Erro no metodo exibeHistório. BD:SQLException(exibeHistórico)");

            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (exibeHistórico)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (exibeHistórico)");
                    ex.printStackTrace();
                }
            }



            return vc;}
         
         public Atendimentos PesquisaAtd(int id){
            Atendimentos atd = null;
            Connection con = ConBD.getConnection();
            PreparedStatement ps = null;
           
            try {
                ps = con.prepareStatement("select atd_id, cli_id, atd_desc, to_char(atd_data , 'DD/MM/YYYY') atd_data, atd_status"
                        + " from atendimentos where atd_id = ?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    atd = new Atendimentos();
                    atd.setId(rs.getInt("atd_id"));
                    atd.setCli_id(rs.getInt("cli_id"));
                    atd.setDescricao(rs.getString("atd_desc"));
                    atd.setData(rs.getString("atd_data"));
                    atd.setStatus(rs.getInt("atd_status"));
                  }
                
                
                ps.close();
                
                
            } catch (SQLException ex) {
                System.out.println("Erro na pesquisa do id. (Método PesquisaAtd)");
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (PesquisaAtd)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (PesquisaAtd)");
                    ex.printStackTrace();
                }
            }


            
            
            
            
            return atd;
        }
         
         public int countAtendimentos(){            
            int count = -1;
            
            Connection conn = ConBD.getConnection();
            PreparedStatement ps = null;
           
            
            try {
                ps = conn.prepareStatement("select count(*) qtd from atendimentos");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count = rs.getInt("qtd");
                }
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Erro CountAtendimentos. BD: SQLException");
                JOptionPane.showMessageDialog(null, "Erro CountAtendimentos. BD: SQLException");
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (countAtendimentos)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (countAtendimentos)");
                    ex.printStackTrace();
                }
            }
          
            
            return count;
        }
         
         public int countAtendPend(){            
            int count = -1;
            Connection conn = ConBD.getConnection();
            PreparedStatement ps = null;
           
            
            try {
                ps = conn.prepareStatement("select count(*) qtd from atendimentos where atd_status > 0");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count = rs.getInt("qtd");
                }
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Erro CountAtendimentosPendentes. BD: SQLException");
                JOptionPane.showMessageDialog(null, "Erro CountAtendimentos. BD: SQLException");
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (countAtendPend)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (countAtendPend)");
                    ex.printStackTrace();
                }
            }
          return count;
        }
         
         public int countExibePendentes(){
            Connection conn = null;
            PreparedStatement ps = null;
            String sql = "select count(*)qtd from atendimentos where atd_status > 0 order by atd_data;";
            int count = 0;       
           
            try {
                conn = ConBD.getConnection();
                ps = conn.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                  count = rs.getInt("qtd");

                }

                ps.close();



            } catch (SQLException ex) {
                System.out.println("Erro no metodo exibeHistório. BD:SQLException");
                JOptionPane.showMessageDialog(null, "Erro no metodo exibeHistório. BD:SQLException");

            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (countExibePendentes())");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (countExibePendentes())");
                    ex.printStackTrace();
                }
            }


            return count;}
         
          public String retornaStatus(int cod){
            String status = "";
        
            switch(cod){
                case 0:
                    status = "Finalizado";
                    break;
                case 1:
                    status = "Aguardando Cliente";
                    break;
                case 2:
                    status = "Aguardando Solução";
                    break;
                case 3:
                    status = "Em Execução";
                    break;
            }
            
            
            return status;
        }
            
          public int retornaStatusID(String status){
           int id = 0;
        
           if(status.equals("Finalizado")){
               id = 0;
           }else if(status.equals("Aguardando Cliente")){
               id = 1;
           }else if(status.equals("Aguardando Solução")){
               id = 2;
           }else if(status.equals("Em Execução")){
               id = 3;     
            }
            
            
            return id;
        }

    public int ExcluiAtendimento(int cod) {
         int linhasAlteradas = 0;
         String sql = "delete from atendimentos where atd_id = "+cod;
         Connection conn = ConBD.getConnection();
         Statement st = null;
            try {
                st = conn.createStatement();
               
                
                linhasAlteradas = st.executeUpdate(sql);
                
                st.close();
                
            } catch (SQLException ex) {
                System.out.println("Falha ao excluir Atendimento. BD:SQLException");
                JOptionPane.showMessageDialog(null,"Falha ao excluir Atendimento. BD:SQLException");
               
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (ExcluiAtendimento)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (ExcluiAtendimento)");
                    ex.printStackTrace();
                }
            }

         
         return linhasAlteradas;
         }
}
