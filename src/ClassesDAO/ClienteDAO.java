package ClassesDAO;

import ClassesPrincipais.Cliente;
import ConexaoBD.ConBD;
import ConexaoBD.ConBDServ;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Admin
 */
public class ClienteDAO{
        
        public int NovoCliente(Cliente c){
            int linhasAlteradas = 0;
            Connection con = ConBD.getConnection();
            PreparedStatement ps = null;
            
            try {
                ps = con.prepareStatement("insert into clientes"
                        + " (cli_cnpj, cli_rsocial, cli_nfant, cli_resp, cli_DDD_tel, cli_tel, cli_DDD_cel, cli_cel) "
                        + " values (?,?,?,?,?,?,?,?)");
                ps.setString(1,c.getCNPJ());
                ps.setString(2,c.getRSocial());
                ps.setString(3,c.getNFantasia());
                ps.setString(4,c.getRsp());
                ps.setString(5,c.getDDDTel());
                ps.setString(6,c.getTel());
                ps.setString(7,c.getDDDCel());
                ps.setString(8,c.getTel2());
                
                
                    linhasAlteradas = ps.executeUpdate();
                    
                ps.close();
                              
                
            } catch (SQLException ex) {
                System.out.println("Falha ao cadastrar cliente. BD:SQLException (NovoCliente)");
                JOptionPane.showMessageDialog(null,"Falha ao cadastrar cliente. BD:SQLException (NovoCliente)");
                ex.printStackTrace();
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (NovoCliente)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (NovoCliente)");
                    ex.printStackTrace();
                }
            }
            
            return linhasAlteradas;
        }
        
        public int AlterarCliente(Cliente c){
            int linhasAlteradas = 0;
            Connection con = ConBD.getConnection();
            PreparedStatement ps = null;
            String sql = "update clientes "
                        + "set cli_rsocial = ?, "
                        + "cli_nfant = ?, "
                        + "cli_resp = ?, "
                        + "cli_ddd_tel = ?, "
                        + "cli_tel = ?, "
                        + "cli_ddd_cel = ?, "
                        + "cli_cel = ? "
                        + "where cli_id = ?;";
            try {
                ps = con.prepareStatement(sql);
                System.out.println(sql);
                
                ps.setString(1,c.getRSocial());
                System.out.println(c.getRSocial());
                ps.setString(2,c.getNFantasia());
                System.out.println(c.getNFantasia());
                ps.setString(3,c.getRsp());
                System.out.println(c.getRsp());
                ps.setString(4,c.getDDDTel());
                System.out.println(c.getDDDTel());
                ps.setString(5,c.getTel());
                System.out.println(c.getDDDTel());
                ps.setString(6,c.getDDDCel());
                System.out.println(c.getDDDCel());
                ps.setString(7,c.getTel2());
                System.out.println(c.getTel2());
                ps.setInt(8,c.getId());
                System.out.println(c.getId());
                
                linhasAlteradas = ps.executeUpdate();
                ps.close();
                              
                
            } catch (SQLException ex) {
                System.out.println("Falha ao alterar cliente.  BD:SQLException (AlterarCliente)");
                JOptionPane.showMessageDialog(null,"Falha ao alterar cliente.  BD:SQLException (AlterarCliente)");
                ex.printStackTrace();
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (AlterarCliente)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (AlterarCliente)");
                    ex.printStackTrace();
                }
            }
            
            return linhasAlteradas;
            
        }
        
        public Cliente PesquisaCNPJ(String CNPJ){
            Cliente c = null;
            Connection con = ConBD.getConnection();
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement("select * from clientes where cli_cnpj = ?");
                ps.setString(1, CNPJ);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    c = new Cliente();
                    c.setId(rs.getInt("cli_id"));
                    c.setCNPJ(rs.getString("cli_cnpj"));
                    c.setRSocial(rs.getString("cli_rsocial"));
                    c.setNFantasia(rs.getString("cli_nfant"));
                    c.setRsp(rs.getString("cli_resp"));
                    c.setDDDTel(rs.getString("cli_ddd_tel"));
                    c.setTel(rs.getString("cli_tel"));
                    c.setTel2(rs.getString("cli_cel"));
                    c.setDDDCel(rs.getString("cli_ddd_cel"));
                }
                
                
                ps.close();
                
                
            } catch (SQLException ex) {
                System.out.println("Erro na pesquisa do cnpj. BD:SQLException (PesquisaCNPJ)");
                ex.printStackTrace();
            
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (PesquisaCNPJ)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (PesquisaCNPJ)");
                    ex.printStackTrace();
                }
            }
            
            
            
            
            return c;
        }
        
        public Vector[] PesquisaRSocial(String rsocial){
            Vector[] registros = null;
            
            int countpesqsocial = countPesqRSocial(rsocial);
             if(countpesqsocial>0){
            
                    Connection con = ConBD.getConnection();
                    Statement st = null;

           
                registros = new Vector[countpesqsocial];
                                try {
                                    st = con.createStatement();
                                  
                                    ResultSet rs = st.executeQuery("select * from clientes where upper(cli_rsocial) like upper('%"+rsocial+"%') order by cli_rsocial");
                                    int i = 0;
                                    
                                    System.out.println("rs.getRow(): "+rs.getRow());
                                    
                                    while(rs.next()){
                                       Vector linha = new Vector();


                                        linha.add(rs.getString("cli_cnpj"));
                                        linha.add(rs.getString("cli_rsocial"));
                                        linha.add(rs.getString("cli_nfant"));
                                        linha.add(rs.getString("cli_resp"));
                                        linha.add("("+rs.getString("cli_DDD_tel")+") "+rs.getString("cli_tel"));

                                        registros[i] = linha;
                                        i++;
                                    }


                                    st.close();
                                    //continua

                                } catch (SQLException ex) {
                                    System.out.println("Erro na pesquisa pela Razão Social. BD:SQLException (PesquisaRSocial)");
                                    ex.printStackTrace();

                                }finally{
                                    try {
                                        st.close();
                                    } catch (SQLException ex) {
                                        System.out.println("Não foi possível fechar a conexão. BD:SQLException (PesquisaRSocial)");
                                        JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (PesquisaRSocial)");
                                        ex.printStackTrace();
                                    }
                                }
            
            }
            
            
            return registros;
        }
        
        public Vector[] PesquisaNFantasia(String nfant){
            
            int countpesqnf = countPesqNFant(nfant);
            Vector[] registros = new Vector[countpesqnf];
            if(countpesqnf>0){
                            Connection con = ConBD.getConnection();
                            Statement st = null;

                                
                                    try {
                                    st = con.createStatement();
                                    
                                    ResultSet rs = st.executeQuery("select * "
                                            + "from clientes "
                                            + "where upper(cli_nfant) like upper('%"+nfant+"%') "
                                            + "order by cli_nfant");
                                    
                                    int i = 0;

                                    while(rs.next()){
                                       Vector linha = new Vector();


                                        linha.add(rs.getString("cli_cnpj"));
                                        linha.add(rs.getString("cli_rsocial"));
                                        linha.add(rs.getString("cli_nfant"));
                                        linha.add(rs.getString("cli_resp"));
                                        linha.add("("+rs.getString("cli_DDD_tel")+") "+rs.getString("cli_tel"));

                                        registros[i] = linha;
                                        i++;
                                    }


                                    st.close();
                                    //continua

                                } catch (SQLException ex) {
                                    System.out.println("Erro na pesquisa pelo Nome Fantasia. BD:SQLException (PesquisaNFantasia)");
                                    ex.printStackTrace();

                                }finally{
                                    try {
                                        st.close();
                                    } catch (SQLException ex) {
                                        System.out.println("Não foi possível fechar a conexão. BD:SQLException (PesquisaNFantasia)");
                                        JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (PesquisaNFantasia)");
                                        ex.printStackTrace();
                                    }
                                }
            
            }
            
            
            return registros;
        }
        
        public Vector[] PesquisaResponsavel(String resp){
            int countPesqResp = countPesqResp(resp);
            Vector[] registros = new Vector[countPesqResp];
            Connection con = ConBD.getConnection();
            Statement st = null;
            try {
                st = con.createStatement();
               
                ResultSet rs = st.executeQuery("select * from clientes where upper(cli_resp) like upper('%"+resp+"%') order by cli_resp");
                int i = 0;
                
                while(rs.next()){
                   Vector linha = new Vector();
                
                
                    linha.add(rs.getString("cli_cnpj"));
                    linha.add(rs.getString("cli_rsocial"));
                    linha.add(rs.getString("cli_nfant"));
                    linha.add(rs.getString("cli_resp"));
                    linha.add("("+rs.getString("cli_DDD_tel")+") "+rs.getString("cli_tel"));

                    registros[i] = linha;
                    i++;
                }
                
                
                st.close();
                //continua
                
            } catch (SQLException ex) {
                System.out.println("Erro na pesquisa pelo Responsável l. BD:SQLException (PesquisaResponsavel)");
                ex.printStackTrace();
            
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (PesquisaResponsavel)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (PesquisaResponsavel)");
                    ex.printStackTrace();
                }
            }
            
            
            
            
            return registros;
    }
        
        public Vector[] PesquisaTelefone(String tel){
            int count = countPesqTel(tel);
            Vector[] registros = new Vector[count];
            Connection con = ConBD.getConnection();
            Statement st = null;
            try {
                st = con.createStatement();
                
                ResultSet rs = st.executeQuery("select * from clientes where upper(cli_tel) like upper('%"+tel+"%') order by cli_resp");
                int i = 0;
                
                while(rs.next()){
                   Vector linha = new Vector();
                
                
                    linha.add(rs.getString("cli_cnpj"));
                    linha.add(rs.getString("cli_rsocial"));
                    linha.add(rs.getString("cli_nfant"));
                    linha.add(rs.getString("cli_resp"));
                    linha.add("("+rs.getString("cli_DDD_tel")+") "+rs.getString("cli_tel"));

                    registros[i] = linha;
                    i++;
                }
                
                
                st.close();
                //continua
                
            } catch (SQLException ex) {
                System.out.println("Erro na pesquisa pelo Telefone. BD:SQLException (PesquisaTelefone)");
                ex.printStackTrace();
            
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (PesquisaTelefone)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (PesquisaTelefone)");
                    ex.printStackTrace();
                }
            }
            
            
            
            
            return registros;
    }
        
        public Cliente PesquisaID(int id){
            Cliente c = null;
            Connection con = ConBD.getConnection();
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement("select * from clientes where cli_id = ?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    c = new Cliente();
                    c.setId(rs.getInt("cli_id"));
                    c.setCNPJ(rs.getString("cli_cnpj"));
                    c.setRSocial(rs.getString("cli_rsocial"));
                    c.setNFantasia(rs.getString("cli_nfant"));
                    c.setRsp(rs.getString("cli_resp"));
                    c.setDDDTel(rs.getString("cli_ddd_tel"));
                    c.setTel(rs.getString("cli_tel"));
                    c.setTel2(rs.getString("cli_cel"));
                    c.setDDDCel(rs.getString("cli_ddd_cel"));
                }
                
                
                ps.close();
                
                
            } catch (SQLException ex) {
                System.out.println("Erro na pesquisa do id. (Método PesquisaID)");
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (PesquisaID)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (PesquisaID)");
                    ex.printStackTrace();
                }
            }
            
            
            
            
            
            return c;
        }
        
        public void DeletarCliente(String CNPJ){
        int linhasAlteradas = 0;
            Connection con = ConBD.getConnection();
            PreparedStatement ps = null;
            
            try {
                ps = con.prepareStatement("delete from clientes where cli_id = (select cli_id from clientes where cli_cnpj = ?);");
                System.out.println("'"+CNPJ+"'");
                ps.setString(1, CNPJ);
              
                linhasAlteradas = ps.executeUpdate();
                
                if(linhasAlteradas == 1){
                    JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
                }
                
                ps.close();
                              
                
            } catch (SQLException ex) {
                System.out.println("Falha ao excluir cliente.  BD:SQLException (DeletarCliente)");
                JOptionPane.showMessageDialog(null,"Falha ao excluir cliente cliente.  BD:SQLException (DeletarCliente)");
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (DeletarCliente)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (DeletarCliente)");
                    ex.printStackTrace();
                }
            }
        }
        
        public Vector[] ExibirTodosClientes(){
        int count = countClientes();
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "select * from clientes order by cli_rsocial;";
               int i = 0;
        
            Vector vc[] = new Vector[ count ];
             
        try {
            conn = ConBD.getConnection();
            ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                    Vector row = new Vector();
                    row.add(rs.getString("cli_cnpj"));
                    row.add(rs.getString("cli_rsocial"));
                    row.add(rs.getString("cli_nfant"));
                    row.add(rs.getString("cli_resp"));
                    row.add("("+rs.getString("cli_DDD_tel")+") "+rs.getString("cli_tel"));

                    vc[i] = row;
                    i++;
             
            }
            
            ps.close();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (ExibirTodos)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (ExibirTodos)");
                    ex.printStackTrace();
                }
            }
        
        
        return vc;}
        
        public void ImportarClientes(int id_inicio, int id_final){
            
            int countImport = 0;
            Cliente[] cs = ExportarCliServ(id_inicio, id_final);
            int countcli = countCliServ(id_inicio, id_final);
            System.out.println("Metodo2 mandando pro meu banco");
            
            for(int i = 0; i<countcli; i++){
               
                Cliente c = cs[i];
                if(!ExisteCNPJ(c.getCNPJ()) && !c.equals(null)){
                    NovoCliente(c);
                    countImport ++;
                }
                  }
            
            JOptionPane.showMessageDialog(null,"Importação concluída com sucesso! Clientes importados: " +countImport);
        
        }
        
        public Cliente[] ExportarCliServ(int id_ini, int id_fin){ 
                int countcli = countCliServ(id_ini,id_fin);
                Cliente[] cs = new Cliente[countcli];
                Connection conn = ConBDServ.getConnection();
                int i = 0;
                    System.out.println("Metodo1 exportar ... count cli: "+countcli);

                if(countcli>0){
                Statement st = null;
                    try {
                        st = conn.createStatement();
                        String sql = "select cli_cnpj_cpf cnpj, cli_razao_social rs, cli_nome_fantasia nf, " +
        "cli_responsavel rp, cli_ddd ddd, cli_telefone_1 tel1, cli_telefone_2 tel2 " +
        "from cad_clientes where cli_id between "+id_ini+" and "+id_fin+";";

                        ResultSet rs = st.executeQuery(sql);

                        while(rs.next()){
                            Cliente c = new Cliente();
                            c.setCNPJ(rs.getString("cnpj"));
                            c.setRSocial(rs.getString("rs"));
                            c.setNFantasia(rs.getString("nf"));
                            c.setRsp(rs.getString("rp"));
                            c.setDDDTel(rs.getString("ddd"));
                            c.setDDDCel(rs.getString("ddd"));
                            c.setTel(rs.getString("tel1"));
                            c.setTel2(rs.getString("tel2"));

                            cs[i] = c;
                            i++;
                        }



                    } catch (SQLException ex) {
                        Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{
                    try {
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);

                    }
                    }





                }
            
            
            
            return cs;}
        
        public int countCliServ(int id_ini, int id_fin){
            int count = -1;
            
            Connection conn = ConBDServ.getConnection();
            Statement st = null;
           
            
            try {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("select count(*) qtd from cad_clientes where cli_id between "+id_ini+" and "+id_fin+";");
                while(rs.next()){
                    count = rs.getInt("qtd");
                }
                
            } catch (SQLException ex) {
                System.out.println("Erro CountCliente. BD: SQLException");
                JOptionPane.showMessageDialog(null, "Erro CountCliente. BD: SQLException");
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (countClientes)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (countClientes)");
                    ex.printStackTrace();
                }
            }
          
            
            return count;
        }
        
        public void apagarTodosOsClientes(){
            int count = -1;
            
            Connection conn = ConBD.getConnection();
            PreparedStatement ps = null;
           
            
            try {
                ps = conn.prepareStatement("delete from clientes");
                int i = ps.executeUpdate();
                System.out.println("Registros deletados: "+i);
                    if(i > 0){
                        JOptionPane.showMessageDialog(null, "Tabela limpa com sucesso");
                    }
                
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Erro apagarTodosOsClientes(). BD: SQLException");
                
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (apagarTodosOsClientes())");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (apagarTodosOsClientes())");
                    ex.printStackTrace();
                }
            }
          
            
            
        }
        
        public int countClientes(){
            int count = -1;
            
            Connection conn = ConBD.getConnection();
            PreparedStatement ps = null;
           
            
            try {
                ps = conn.prepareStatement("select count(*) qtd from clientes");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    count = rs.getInt("qtd");
                }
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Erro CountCliente. BD: SQLException");
                JOptionPane.showMessageDialog(null, "Erro CountCliente. BD: SQLException");
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (countClientes)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (countClientes)");
                    ex.printStackTrace();
                }
            }
          
            
            return count;
        }
        
        public int countPesqRSocial(String exp){
        Connection con = ConBD.getConnection();
        int count = 0;    
        Statement st = null;
            try {
                st = con.createStatement();
                       
                
                ResultSet rs = st.executeQuery("select count(*) qtd from clientes where upper(cli_rsocial) like upper('%"+exp+"%')");
                
                
                
              while(rs.next()){     
                    count = rs.getInt("qtd");
                }
                
                st.close();
                
                
            } catch (SQLException ex) {
                System.out.println("BD:SQLException (countPesqRSocial)");
                ex.printStackTrace();
            
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("BD:SQLException (countPesqRSocial)");
                    JOptionPane.showMessageDialog(null,"BD:SQLException (countPesqRSocial)");
                    ex.printStackTrace();
                }
            }
            
            return count;
        }
        
        public int countPesqNFant(String exp){
        Connection con = ConBD.getConnection();
        int count = 0;    
        Statement st= null;
            try {
                st = con.createStatement();
                ResultSet rs = st.executeQuery("select count(*) qtd from clientes where upper(cli_nfant) like upper('%"+exp+"%')");
                
                while(rs.next()){
                   
                    count = rs.getInt("qtd");
                }
                
                st.close();
                
                
            } catch (SQLException ex) {
                System.out.println("BD:SQLException (countPesqNFant)");
                ex.printStackTrace();
            
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("BD:SQLException (countPesqNFant)");
                    JOptionPane.showMessageDialog(null,"BD:SQLException (countPesqNFant)");
                    ex.printStackTrace();
                }
            }
            
            return count;
        }
        
        public int countPesqResp(String exp){
        Connection con = ConBD.getConnection();
        int count = 0;    
        Statement st = null;
            try {
                st = con.createStatement();
                
                ResultSet rs = st.executeQuery("select count(*) qtd from clientes where upper(cli_resp) like upper('%"+exp+"%')");
                
                while(rs.next()){
                   
                    count = rs.getInt("qtd");
                }
                
                st.close();
                
                
            } catch (SQLException ex) {
                System.out.println("BD:SQLException (countPesqNFant)");
                ex.printStackTrace();
            
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("BD:SQLException (countPesqNFant)");
                    JOptionPane.showMessageDialog(null,"BD:SQLException (countPesqNFant)");
                    ex.printStackTrace();
                }
            }
            
            return count;
        }
        
        public int countPesqTel(String exp){
        Connection con = ConBD.getConnection();
        int count = 0;    
        Statement st = null;
            try {
                st = con.createStatement();
                
                ResultSet rs = st.executeQuery("select count(*) qtd from clientes where upper(cli_tel) like upper('%"+exp+"%')");
                
                while(rs.next()){
                   
                    count = rs.getInt("qtd");
                }
                
                st.close();
                
                
            } catch (SQLException ex) {
                System.out.println("BD:SQLException (countPesqNFant)");
                ex.printStackTrace();
            
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("BD:SQLException (countPesqNFant)");
                    JOptionPane.showMessageDialog(null,"BD:SQLException (countPesqNFant)");
                    ex.printStackTrace();
                }
            }
            
            return count;
        }
        
        public int retornaID(String cnpj){
            int id  = 0;
            Statement st = null;
            Connection conn = ConBD.getConnection();
            try {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("select cli_id from clientes where cli_cnpj = '"+cnpj+"'");
                while(rs.next()){
                    id = rs.getInt("cli_id");
                            
                }
                st.close();
            } catch (SQLException ex) {
                 System.out.println("Falha ao selecionar id cliente. BD:SQLException");
                JOptionPane.showMessageDialog(null,"Falha ao selecionar id cliente. BD:SQLException");
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException ( retornaID)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException ( retornaID)");
                    ex.printStackTrace();
                }
            }
            
            
            
            
            
            return id;
            
            
        }
        
        public String retornaCNPJ(int id){
            String cnpj  = "";
            Statement st = null;
            Connection conn = ConBD.getConnection();
            try {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("select cli_cnpj from clientes where cli_id = '"+id+"'");
                while(rs.next()){
                    cnpj = rs.getString("cli_cnpj");
                            
                }
                st.close();
            } catch (SQLException ex) {
                 System.out.println("Falha ao selecionar CNPJ cliente. BD:SQLException");
                JOptionPane.showMessageDialog(null,"Falha ao selecionar CNPJ cliente. BD:SQLException");
            }finally{
                try {
                    st.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (retornaCNPJ)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (retornaCNPJ)");
                    ex.printStackTrace();
                }
            }
   
            return cnpj;
            
            
        }
        
        public boolean ExisteCNPJ(String cnpj){
            Connection con = ConBD.getConnection();
            PreparedStatement ps = null;
            int count = -1;
           
            try {
                ps = con.prepareStatement("select count(*) qtd from clientes where cli_cnpj = ?;");
                ps.setString(1, cnpj);
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    count = rs.getInt("qtd");
                    System.out.println("count ExisteCNPJ:" + count);
                }
                
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Não foi possível fechar a conexão. BD:SQLException (ExisteCNPJ)");
                    JOptionPane.showMessageDialog(null,"Não foi possível fechar a conexão. BD:SQLException (ExisteCNPJ)");
                    ex.printStackTrace();
                }
            }


            
           
            return count == 1;
        }
}
