/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Telas;

import ClassesDAO.AtendimentosDAO;
import ClassesDAO.ClienteDAO;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import ClassesPrincipais.Atendimentos;
import ClassesPrincipais.Cliente;


/**
 *
 * @author Admin
 */
public class TelaToday extends javax.swing.JFrame {
       
    static TelaToday instance;
            
    static public TelaToday getTela(){
        if (instance != null){
            return instance;
        } else {
            instance = new TelaToday();
            return instance;
        }
    }
    
    boolean modoAlteracao = false;
    int importacao = 1930;
    int countclickCli = 0;
    int ultimaLinhaCli = -1;
    
    
    int countclickHist = 0;
    int ultimaLinhaHist = -1;
    
    int countclickPend = 0;
    int ultimaLinhaPend = -1;
    

    public TelaToday() {
        initComponents();
       
        inicializacao(1);
        Recursos.GerenciaDeImagens.iconePrincipal(this);
        
    }
   
    public void inicializacao(int aba){
                            
                            exibePendendtes();
                            limpaTabela(tbClientes);
                            LimpaTodosOsCampos();
                            DesativaCamposCliente();
                            desabilitaCamposID();
                            ocultaBotoesDeSegundoPlanoAtd();
                            desabilitaModoAlteracaoAtd();
                            
                            CadCliente.setVisible(false);
                            txtDescricao.setEnabled(true);
                            
                            jtabPrinc.setSelectedIndex(aba);
                           
    
    }
    //armazena os botoes
    public JButton[] botoesDeSegPlanoAtd(){
        JButton[] botSegPlano = new JButton[]{btnAtdCancelar, btnAtdModoCOnsulta, btnAtdCancelar, btnAtdFinaliza};
        
        return botSegPlano;
        
    }
    
    public void desabilitaCamposID(){
      txtCliCod.setEnabled(false);
      txtCodAtd.setEnabled(false);
    }
    
    //armazena todos os JTextField
    public JTextField[] camposCliente(){
       JTextField campos [] = new JTextField[]{txtCliCNPJ, txtCliDDD, 
       txtCliDDDTel2, txtCliNFant, txtCliResp,
       txtCliRz, txtCliTel, txtCliTel2};
       
       return campos;
   }
    public JTextField[] todosCampos(){
       JTextField campos [] = new JTextField[]{txtCnpj, txtCliCNPJ, txtCliCod, txtCliDDD, 
       txtCliDDDTel2, txtCliNFant, txtCliResp, txtCliRz, txtCliTel, txtCliTel2, txtCodAtd, txtPesquisa};
       
       return campos;
   }
    
    //armazena todas as labels
    public JLabel[] todasLabels(){
        JLabel[] todasLabels = new JLabel[]{lbData, lbNomeCliente, lbStatus};
        
        return todasLabels;
    }
    
    //preenche uma tabela por um parametro vector[] e um objeto JTable
    public void populaTabela(Vector[] linhas, JTable tabela){
        DefaultTableModel tb = (DefaultTableModel)tabela.getModel();
        int count = (tb).getRowCount();
        
        if(count>0){
            limpaTabela(tabela);
        }


        for(int i = 0; i<linhas.length; i++){
                 tb.addRow(linhas[i]);
         }
         
    }    
    
    //limpa uma tabela basta informá-la no parametro
    public void limpaTabela(JTable tabela){
        DefaultTableModel tb = (DefaultTableModel) tabela.getModel();
        int count = tb.getRowCount();
            
            if(count>0){
                for(int i = 0; i<count; i++){
                    tb.removeRow(0);
                
                }
            
            }
        
    }
    
    //ativar ou desativar campos de texto (JTextField)
    public void DesativaCamposCliente(){
       JTextField[] campos = camposCliente();
       
       for(int i = 0; i<campos.length; i++){
           campos[i].setEnabled(false);
       }
       
       
   }
    public void AtivarCamposCliente(){
       JTextField[] campos = camposCliente();
       
       for(int i = 0; i<campos.length; i++){
           campos[i].setEnabled(true);
       }
       
       
   }
    public void DesativaCampInalteraveis(){
        txtCliCNPJ.setEnabled(false);
       
    }
    
    //exibe ou oculta grupo de botoens
    public void ocultaBotoesDeSegundoPlanoAtd(){
        JButton[] botSegPlan = botoesDeSegPlanoAtd();
        
        for(int i = 0; i<botSegPlan.length; i++){
            botSegPlan[i].setVisible(false);
        }
    }
    public void exibeBotoesDeSegundoPlanoAtd(){
        JButton[] botSegPlan = botoesDeSegPlanoAtd();
        
        for(int i = 0; i<botSegPlan.length; i++){
            botSegPlan[i].setVisible(true);
        }
    }
    
    //metodo para busca personalizada
    public void buscaPersonalizada(){
        ClienteDAO cd = new ClienteDAO();
       String pesquisa = txtPesquisa.getText();
       String jcbx = jcbxTipoPesq.getSelectedItem().toString();
       

            if(pesquisa.equals("%") || pesquisa.equals(" %") || pesquisa.equals("% ") || pesquisa.equals("") && jcbx.equals(null)){
                exibeClientes();
            }
            else{       
       
                     if(jcbx.equalsIgnoreCase("CNPJ")){
                                        Vector row = new Vector();
                                        Cliente c = new Cliente();   
     
                                        c = cd.PesquisaCNPJ(pesquisa);

                                             if(!c.equals(null)){
                                                     preecheTabCliente(c);
                                             }       
                                    }else
                                    if(jcbx.equalsIgnoreCase("Razão Social")){
                                        
                                        populaTabela(cd.PesquisaRSocial(pesquisa), tbClientes);
                                        
                                    }else
                                    if(jcbx.equalsIgnoreCase("Nome Fantasia")){
                                        
                                        populaTabela(cd.PesquisaNFantasia(pesquisa), tbClientes);

                                    }else
                                    if(jcbx.equalsIgnoreCase("Responsável")){
                                       
                                        
                                        populaTabela(cd.PesquisaResponsavel(pesquisa), tbClientes);
                                        
                                    }else
                                    if(jcbx.equalsIgnoreCase("Telefone")){
                                        populaTabela(cd.PesquisaTelefone(pesquisa), tbClientes);
                                        
                                    }else
                                    if(jcbx.equalsIgnoreCase(null) ||jcbx.equalsIgnoreCase("") ||jcbx.equalsIgnoreCase(" ")){
                                        
                                        exibeClientes();
                                    } 
                                             
       
             
            }
    }
    
    
    public void LimpaTudo(){
        LimpaTodosOsCampos();
        CadCliente.setVisible(false);
        limpaTabela(tbHist);
        limpaTabela(tbClientes);
    }
    
    //Limpar campos e labels
    public void LimpaTodosOsCampos(){
        JTextField todosOsCampos[] = todosCampos();
        txtDescricao.setText(null);
        for(int i = 0; i<todosOsCampos.length; i++){
            todosOsCampos[i].setText(null);
        }
        JLabel labels[] = todasLabels();
        for(int i = 0; i<labels.length; i++){
            labels[i].setText(null);
        }
    }
    
    public void preencheCamposCli(Cliente c){
                    txtCliCod.setText(c.getId()+"");
                    txtCliCNPJ.setText(c.getCNPJ());
                    txtCliDDD.setText(c.getDDDTel());
                    txtCliTel.setText(c.getTel());
                    txtCliRz.setText(c.getRSocial());
                    txtCliNFant.setText(c.getNFantasia());
                    txtCliResp.setText(c.getRsp());
                    txtCliDDDTel2.setText(c.getDDDCel());
                    txtCliTel2.setText(c.getTel2());
                    
                if(c.getTel2().equals(null) || c.getTel2().equals(" ") || c.getTel2().equals(" ")){    
                    txtCliDDDTel2.setText(null);
                    txtCliTel2.setText(null);
                }
     }
    
    
     public void selecionaCliente(String cnpj, String nome){
                    LimpaTudo();
                    desabilitaModoAlteracaoAtd();
                    if(nome.equals(null)){ JOptionPane.showMessageDialog(null, "Inclua um Nome Fantasia.");}
                    lbNomeCliente.setText(nome);
                    txtCnpj.setText(cnpj);
                }
    
    
     
    //modos
    public void modoAlteracaoAtd(){
                btnAtdCancelar.setVisible(true);
                btnAtdSalvar.setVisible(false);
                btnAtdSelectCliente.setVisible(false);
                btnAtdSalvaAlteracoes.setVisible(true); 
                btnAtdFinaliza.setVisible(true);
                jtabPrinc.setSelectedIndex(0);
                txtDescricao.setEnabled(true);
                modoAlteracao = true;
               
    }  
    public void desabilitaModoAlteracaoAtd(){
                btnAtdCancelar.setVisible(false);
                btnAtdSalvar.setVisible(true);
                btnAtdSelectCliente.setVisible(true);
                btnAtdSalvaAlteracoes.setVisible(false);
                btnAtdFinaliza.setVisible(false);
                modoAlteracao = false;
                
    } 
   
    public void modoConsulta(){
                
                btnAtdModoCOnsulta.setVisible(true);
                btnAtdSalvaAlteracoes.setVisible(false);
                btnAtdSalvar.setVisible(false);
                btnAtdCancelar.setVisible(false);
                txtDescricao.setEnabled(false);
                btnAtdSelectCliente.setVisible(false);
    
    }
    
    public void desativaModoConsulta(){
                
                btnAtdModoCOnsulta.setVisible(false);
                btnAtdSalvaAlteracoes.setVisible(false);
                btnAtdSalvar.setVisible(true);
                btnAtdCancelar.setVisible(false);
                txtDescricao.setEnabled(true);
                btnAtdSelectCliente.setVisible(true);
    
    }
    
    public void exibePendendtes(){ 
                            AtendimentosDAO ad = new AtendimentosDAO();
                            populaTabela(ad.exibePendentes(), tbPends);
                                                         
                           
                         }   
    public void exibeHistorico(){
         
        AtendimentosDAO ad= new AtendimentosDAO();
         populaTabela(ad.exibeHistórico(), tbHist);
    }
    public void exibeClientes(){
        ClienteDAO cd = new ClienteDAO();
        populaTabela(cd.ExibirTodosClientes(), tbClientes);
     
     
    }
    
    //captura da entrada cadastro clientes
    public Cliente ddCliente(String modo){
        ClienteDAO cd = new ClienteDAO();
        Cliente c = null;
        String rz = txtCliRz.getText()+"";
        String nFantasia = txtCliNFant.getText()+"";
        String DDDTel = txtCliDDD.getText()+"";
        String tel = txtCliTel.getText()+"";
        String DDDCel = txtCliDDDTel2.getText()+"";
        String cel = txtCliTel2.getText()+"";
        String rsp = txtCliResp.getText()+"";  
        String cnpj = txtCliCNPJ.getText()+"";
        c = new Cliente();
       
        if(modo.equalsIgnoreCase("a")){  
            int cod = Integer.parseInt(txtCliCod.getText());
             c.setId(cod);
        } 
                    
                    c.setCNPJ(cnpj);
                    c.setRSocial(rz);
                    c.setNFantasia(nFantasia);
                    c.setDDDCel(DDDCel);
                    c.setTel2(cel);
                    c.setRsp(rsp);
                    c.setDDDTel(DDDTel);
                    c.setTel(tel);
                    
                    return c;
    }
    
    //preencher tabela com os dados de um cliente
    public void preecheTabCliente(Cliente c){
           
                        limpaTabela(tbClientes);
                        DefaultTableModel model = (DefaultTableModel) tbClientes.getModel();
                        Vector row = new Vector();
                        
                        row.add(c.getCNPJ());
                        row.add(c.getRSocial());
                        row.add(c.getNFantasia());
                        row.add(c.getRsp());
                        row.add("("+c.getDDDTel()+")"+c.getTel());
                        
                        model.addRow(row);
        
    }
    
    
    public void funcNovoCli(){
        ocultaBotoesDeSegundoPlanoAtd();
        LimpaTodosOsCampos();
        CadCliente.setVisible(true);
        btnCliSalNovo.setVisible(true);
        btnCliAlteracoes.setVisible(false);
        AtivarCamposCliente();
        
    }
    public void funcAlterarCli(){
        AtivarCamposCliente();
        DesativaCampInalteraveis();
        
        int selecionado = tbClientes.getSelectedRow();
                if(selecionado != -1){
        LimpaTodosOsCampos();
        CadCliente.setVisible(true);
        btnCliSalNovo.setVisible(false);
        btnCliAlteracoes.setVisible(true);
        
       
        
        
        Cliente c = new Cliente();
         ClienteDAO cd = new ClienteDAO();
       
                CadCliente.setVisible(true);
                String cnpj = tbClientes.getValueAt(selecionado, 0).toString();
                c = cd.PesquisaCNPJ(cnpj);
                
                if(c != null){
                    preencheCamposCli(c);
                    c = null;
                }else{
                    JOptionPane.showMessageDialog(null, "Registro de cliente deletado.");
        
                }
            }else{
                JOptionPane.showMessageDialog(null, "Selecione um registro da tabela de clientes para alteração do cliente.");
            }  


        
    }
    public void funcDeletar(){
                     int selecionado = tbClientes.getSelectedRow();
                    ClienteDAO cd = new ClienteDAO();

                            if(selecionado != -1){
                                int resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cliente?");


                                System.out.println(resp+"");

                                     switch(resp){
                                         case 0:
                                            String cnpj = tbClientes.getValueAt(selecionado, 0).toString();
                                            cd.DeletarCliente(cnpj);
                                            
                                            funcFinalizacao2Cli();
                                         break;

                                     }

                            }else{
                                JOptionPane.showMessageDialog(null, "Selecione o cliente a ser excluído!");


                            }

    }
    public void funcSalvarNvCli(){
        Cliente c =  ddCliente("n");
       
                //validação dos campos obrigatórios
                if(c.getCNPJ().equals("") || c.getRSocial().equals("") || c.getNFantasia().equals("")){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
                }else{
                    
                        ClienteDAO cd = new ClienteDAO();
                     if(!cd.ExisteCNPJ(c.getCNPJ())){   
                        int la = cd.NovoCliente(c);
                        preecheTabCliente(c);
                        
                        if(la == 1){
                            funcFinalizacao1Cli("cadastrado");
                        }
                     }else{
                     int r =JOptionPane.showConfirmDialog(null, "CNPJ já cadastrado, deseja salvar mesmo assim?");
                                
                                if(r == 0){
                                        int la = cd.NovoCliente(c);
                                        preecheTabCliente(c);
                                         if(la == 1){
                                            funcFinalizacao1Cli("cadastrado");
                                        }
                                }
                     
                     }
    }
    
    }
    public void funcSalvarAltCli(){
        Cliente c =  ddCliente("a");
       
                //validação dos campos obrigatórios
                if(c.getCNPJ().equals("") || c.getRSocial().equals("") || c.getNFantasia().equals("")){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
                }else{
                    
                        ClienteDAO td = new ClienteDAO();
                        int la = td.AlterarCliente(c);
                        preecheTabCliente(c);
                        
                        if(la == 1){
                            funcFinalizacao1Cli("alterado");
                        }
                        
    }
    
    }
    public void funcCancelarCli(){
        int count = ((DefaultTableModel) tbClientes.getModel()).getRowCount();
        
        if(count>0){
            limpaTabela(tbClientes);
            funcFinalizacao3Cli();
            
        }else{
            funcFinalizacao3Cli();
        }
        
    }    
    public void funcFinalizaAtd(){
        /*int idAtd = Integer.parseInt(txtCodAtd.getText());
      AtendimentosDAO td = new AtendimentosDAO();
      Atendimentos at = td.PesquisaAtd(idAtd);
      at.setDescricao(txtDescricao.getText());
      
                    td.AlterarAtendimentoMtData(at);*/   
        
        
        AtendimentosDAO atd = new AtendimentosDAO();
           int codAtd = Integer.parseInt(txtCodAtd.getText());
           Atendimentos at = atd.PesquisaAtd(codAtd);
           at.setDescricao(txtDescricao.getText());
           atd.AlterarAtendimentoMtData(at);
           atd.AlterarStatus(codAtd, atd.retornaStatusID("Finalizado"));
           inicializacao(1);
    }
    

    public void funcFinalizacao1Cli(String status){
        JOptionPane.showMessageDialog(null,"Cliente "+status+" com sucesso");
                            exibeClientes();
                            LimpaTodosOsCampos();
                            CadCliente.setVisible(false);
    }
    public void funcFinalizacao2Cli(){
       
                            exibeClientes();
                            LimpaTodosOsCampos();
                            CadCliente.setVisible(false);
    }
     public void funcFinalizacao3Cli(){
       
                            LimpaTodosOsCampos();
                            CadCliente.setVisible(false);
    }
    
    public void funcSairModoConsulta(){
       int retorno = JOptionPane.showConfirmDialog(null, "Deseja utilizar os dados desse cliente para criar outro atendimento?");
              
            if(retorno == 0){
                
                desativaModoConsulta();
                lbStatus.setText(null);
                lbData.setText(null);
                
            }else if(retorno == 1){
                
                inicializacao(1);
                
            } 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbtgPesquisas = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jtabPrinc = new javax.swing.JTabbedPane();
        jpToday = new javax.swing.JPanel();
        btnAtdSelectCliente = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        lbNomeCliente = new javax.swing.JLabel();
        btnAtdSalvar = new javax.swing.JButton();
        btnAtdSalvaAlteracoes = new javax.swing.JButton();
        lbStatus = new javax.swing.JLabel();
        lbData = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodAtd = new javax.swing.JTextField();
        btnAtdCancelar = new javax.swing.JButton();
        btnAtdModoCOnsulta = new javax.swing.JButton();
        txtCnpj = new javax.swing.JTextField();
        btnAtdFinaliza = new javax.swing.JButton();
        jpPend = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPends = new javax.swing.JTable();
        jcbxStatPend = new javax.swing.JComboBox();
        btnPendModSta = new javax.swing.JButton();
        jpHist = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbHist = new javax.swing.JTable();
        btnHistExibirHistoricoCompleto = new javax.swing.JButton();
        btnExAtd = new javax.swing.JButton();
        jpCliente = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbClientes = new javax.swing.JTable();
        btnCliAlterar = new javax.swing.JButton();
        btnCliExibirTodos = new javax.swing.JButton();
        CadCliente = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnCliSalNovo = new javax.swing.JButton();
        txtCliTel2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCliDDDTel2 = new javax.swing.JTextField();
        txtCliCNPJ = new javax.swing.JTextField();
        txtCliDDD = new javax.swing.JTextField();
        txtCliNFant = new javax.swing.JTextField();
        txtCliTel = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCliRz = new javax.swing.JTextField();
        txtCliResp = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnCliLimparCampos = new javax.swing.JButton();
        btnCliCancelar = new javax.swing.JButton();
        txtCliCod = new javax.swing.JTextField();
        btnCliAlteracoes = new javax.swing.JButton();
        btnCliNovo = new javax.swing.JButton();
        txtPesquisa = new javax.swing.JTextField();
        jcbxTipoPesq = new javax.swing.JComboBox();
        btnCliBuscar = new javax.swing.JButton();
        btnCliDeletar = new javax.swing.JButton();
        btnCliLimpaTabela = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Today Manager");
        setResizable(false);

        btnAtdSelectCliente.setText("Selecionar Cliente");
        btnAtdSelectCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtdSelectClienteActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição"));

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane1.setViewportView(txtDescricao);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lbNomeCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbNomeCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        btnAtdSalvar.setText("Salvar Novo");
        btnAtdSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtdSalvarActionPerformed(evt);
            }
        });

        btnAtdSalvaAlteracoes.setText("Salvar Alterações");
        btnAtdSalvaAlteracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtdSalvaAlteracoesActionPerformed(evt);
            }
        });

        lbStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lbData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel6.setText("Código do Atendimento:");

        btnAtdCancelar.setText("Cancelar");
        btnAtdCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtdCancelarActionPerformed(evt);
            }
        });

        btnAtdModoCOnsulta.setText("Sair Modo Consulta");
        btnAtdModoCOnsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtdModoCOnsultaActionPerformed(evt);
            }
        });

        txtCnpj.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCnpj.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCnpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCnpjActionPerformed(evt);
            }
        });

        btnAtdFinaliza.setText("Finalizar Atendimento");
        btnAtdFinaliza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtdFinalizaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTodayLayout = new javax.swing.GroupLayout(jpToday);
        jpToday.setLayout(jpTodayLayout);
        jpTodayLayout.setHorizontalGroup(
            jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpTodayLayout.createSequentialGroup()
                .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTodayLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpTodayLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodAtd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTodayLayout.createSequentialGroup()
                                .addGap(0, 448, Short.MAX_VALUE)
                                .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTodayLayout.createSequentialGroup()
                        .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpTodayLayout.createSequentialGroup()
                                .addComponent(btnAtdSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAtdModoCOnsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71))
                            .addGroup(jpTodayLayout.createSequentialGroup()
                                .addComponent(btnAtdSalvaAlteracoes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAtdCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtdFinaliza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jpTodayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAtdSelectCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpTodayLayout.setVerticalGroup(
            jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTodayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAtdSelectCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbNomeCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbStatus)
                    .addComponent(jLabel6)
                    .addComponent(txtCodAtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbData)
                .addGap(36, 36, 36)
                .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtdModoCOnsulta)
                    .addComponent(btnAtdFinaliza)
                    .addComponent(btnAtdSalvar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtdCancelar)
                    .addComponent(btnAtdSalvaAlteracoes))
                .addGap(18, 18, 18))
        );

        jtabPrinc.addTab("Novo Atendimento", jpToday);

        tbPends.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data", "Cliente", "Descrição", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPends.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbPends.setFocusable(false);
        tbPends.setOpaque(false);
        tbPends.setRequestFocusEnabled(false);
        tbPends.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbPends.getTableHeader().setReorderingAllowed(false);
        tbPends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPendsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbPends);
        if (tbPends.getColumnModel().getColumnCount() > 0) {
            tbPends.getColumnModel().getColumn(0).setResizable(false);
            tbPends.getColumnModel().getColumn(0).setPreferredWidth(5);
            tbPends.getColumnModel().getColumn(1).setResizable(false);
            tbPends.getColumnModel().getColumn(1).setPreferredWidth(30);
            tbPends.getColumnModel().getColumn(2).setPreferredWidth(120);
            tbPends.getColumnModel().getColumn(3).setPreferredWidth(120);
            tbPends.getColumnModel().getColumn(4).setResizable(false);
            tbPends.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        jcbxStatPend.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Finalizado", "Aguardando Cliente", "Aguardando Solução", "Em Execução" }));

        btnPendModSta.setText("Modificar Status");
        btnPendModSta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPendModStaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPendLayout = new javax.swing.GroupLayout(jpPend);
        jpPend.setLayout(jpPendLayout);
        jpPendLayout.setHorizontalGroup(
            jpPendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPendLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addGroup(jpPendLayout.createSequentialGroup()
                        .addComponent(jcbxStatPend, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPendModSta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpPendLayout.setVerticalGroup(
            jpPendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPendLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpPendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbxStatPend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPendModSta))
                .addContainerGap())
        );

        jtabPrinc.addTab("Pendências", jpPend);

        tbHist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data", "Cliente", "Descrição", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHist.setFocusable(false);
        tbHist.setOpaque(false);
        tbHist.setRequestFocusEnabled(false);
        tbHist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbHist.getTableHeader().setReorderingAllowed(false);
        tbHist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHistMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbHist);

        btnHistExibirHistoricoCompleto.setText("Exibir Histórico Completo");
        btnHistExibirHistoricoCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistExibirHistoricoCompletoActionPerformed(evt);
            }
        });

        btnExAtd.setText("Excluir Atendimento");
        btnExAtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExAtdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpHistLayout = new javax.swing.GroupLayout(jpHist);
        jpHist.setLayout(jpHistLayout);
        jpHistLayout.setHorizontalGroup(
            jpHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addGroup(jpHistLayout.createSequentialGroup()
                        .addComponent(btnHistExibirHistoricoCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExAtd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpHistLayout.setVerticalGroup(
            jpHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHistExibirHistoricoCompleto)
                    .addComponent(btnExAtd))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jtabPrinc.addTab("Histórico", jpHist);

        tbClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CNPJ", "Razão Social", "Nome Fantasia", "Responsável", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbClientes.setOpaque(false);
        tbClientes.setRequestFocusEnabled(false);
        tbClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbClientes.getTableHeader().setReorderingAllowed(false);
        tbClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbClientesMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbClientesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbClientes);
        if (tbClientes.getColumnModel().getColumnCount() > 0) {
            tbClientes.getColumnModel().getColumn(4).setResizable(false);
        }

        btnCliAlterar.setText("Alterar");
        btnCliAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAlterarActionPerformed(evt);
            }
        });

        btnCliExibirTodos.setText("Exibir Todos");
        btnCliExibirTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliExibirTodosActionPerformed(evt);
            }
        });

        CadCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastro"));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Resp.:");

        btnCliSalNovo.setText("Salvar Novo");
        btnCliSalNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliSalNovoActionPerformed(evt);
            }
        });

        jLabel12.setText("Tel2:");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Telefone:");

        jLabel14.setText("Razão Social:");

        jLabel15.setText("N. Fantasia:");

        jLabel16.setText("CNPJ:");

        btnCliLimparCampos.setText("Limpar Campos");
        btnCliLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliLimparCamposActionPerformed(evt);
            }
        });

        btnCliCancelar.setText("Cancelar");
        btnCliCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliCancelarActionPerformed(evt);
            }
        });

        btnCliAlteracoes.setText("Salvar Alterações");
        btnCliAlteracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAlteracoesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadClienteLayout = new javax.swing.GroupLayout(CadCliente);
        CadCliente.setLayout(CadClienteLayout);
        CadClienteLayout.setHorizontalGroup(
            CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadClienteLayout.createSequentialGroup()
                .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadClienteLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(CadClienteLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliNFant, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CadClienteLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliRz, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CadClienteLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliCod, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCliResp)
                            .addGroup(CadClienteLayout.createSequentialGroup()
                                .addComponent(txtCliDDDTel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliTel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CadClienteLayout.createSequentialGroup()
                                .addComponent(txtCliDDD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliTel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(CadClienteLayout.createSequentialGroup()
                        .addComponent(btnCliSalNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCliAlteracoes, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCliCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCliLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        CadClienteLayout.setVerticalGroup(
            CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCliRz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliResp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliNFant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtCliDDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txtCliCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliDDDTel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliTel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CadClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCliSalNovo)
                    .addComponent(btnCliLimparCampos)
                    .addComponent(btnCliCancelar)
                    .addComponent(btnCliAlteracoes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCliNovo.setText("Novo");
        btnCliNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliNovoActionPerformed(evt);
            }
        });

        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });

        jcbxTipoPesq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "CNPJ", "Razão Social", "Nome Fantasia", "Responsável", "Telefone" }));

        btnCliBuscar.setText("Buscar");
        btnCliBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliBuscarActionPerformed(evt);
            }
        });

        btnCliDeletar.setText("Deletar");
        btnCliDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliDeletarActionPerformed(evt);
            }
        });

        btnCliLimpaTabela.setText("Limpar Tabela");
        btnCliLimpaTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliLimpaTabelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpClienteLayout = new javax.swing.GroupLayout(jpCliente);
        jpCliente.setLayout(jpClienteLayout);
        jpClienteLayout.setHorizontalGroup(
            jpClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpClienteLayout.createSequentialGroup()
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbxTipoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCliBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCliLimpaTabela)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpClienteLayout.createSequentialGroup()
                        .addComponent(btnCliNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCliAlterar)
                        .addGap(96, 96, 96)
                        .addComponent(btnCliExibirTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCliDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addComponent(CadCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpClienteLayout.setVerticalGroup(
            jpClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpClienteLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jpClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbxTipoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCliBuscar)
                    .addComponent(btnCliLimpaTabela))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCliNovo)
                    .addComponent(btnCliAlterar)
                    .addComponent(btnCliExibirTodos)
                    .addComponent(btnCliDeletar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CadCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtabPrinc.addTab("Cliente", jpCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtabPrinc)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtabPrinc, javax.swing.GroupLayout.PREFERRED_SIZE, 586, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtdSelectClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtdSelectClienteActionPerformed
        jtabPrinc.setSelectedIndex(3);
    }//GEN-LAST:event_btnAtdSelectClienteActionPerformed

    private void btnCliNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliNovoActionPerformed
      
        funcNovoCli();
              
    }//GEN-LAST:event_btnCliNovoActionPerformed

    private void btnCliSalNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliSalNovoActionPerformed
     funcSalvarNvCli();

    }//GEN-LAST:event_btnCliSalNovoActionPerformed

    private void btnCliLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliLimparCamposActionPerformed
   LimpaTodosOsCampos();
   
    }//GEN-LAST:event_btnCliLimparCamposActionPerformed

    private void btnCliAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAlterarActionPerformed
       
       funcAlterarCli();
       
    }//GEN-LAST:event_btnCliAlterarActionPerformed

    private void btnCliCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliCancelarActionPerformed
     funcCancelarCli();
       
    }//GEN-LAST:event_btnCliCancelarActionPerformed

    private void btnCliLimpaTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliLimpaTabelaActionPerformed
        limpaTabela(tbClientes);
    
    }//GEN-LAST:event_btnCliLimpaTabelaActionPerformed

    private void btnCliExibirTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliExibirTodosActionPerformed
        exibeClientes();
    }//GEN-LAST:event_btnCliExibirTodosActionPerformed

    private void btnCliBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliBuscarActionPerformed
        buscaPersonalizada();
        
    }//GEN-LAST:event_btnCliBuscarActionPerformed

    private void tbClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClientesMouseClicked
            
            if(ultimaLinhaCli == tbClientes.getSelectedRow()){
                countclickCli++;
            }
        
            
            
            if(countclickCli > 0){
                String nomeCli = tbClientes.getValueAt(tbClientes.getSelectedRow(), 2).toString();
                String cnpj = tbClientes.getValueAt(tbClientes.getSelectedRow(), 0).toString();
                
                selecionaCliente(cnpj, nomeCli);
              
                jtabPrinc.setSelectedIndex(0);
                countclickCli = 0;
                    
            }
            
           
            ultimaLinhaCli = tbClientes.getSelectedRow();
        
    
    }//GEN-LAST:event_tbClientesMouseClicked

    private void btnCliAlteracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAlteracoesActionPerformed
        funcSalvarAltCli();

    }//GEN-LAST:event_btnCliAlteracoesActionPerformed

    private void tbClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClientesMousePressed
      
          // TODO add your handling code here:
    }//GEN-LAST:event_tbClientesMousePressed

    private void btnCliDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliDeletarActionPerformed
        funcDeletar();

    }//GEN-LAST:event_btnCliDeletarActionPerformed

    private void btnAtdSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtdSalvarActionPerformed
       if(!txtCnpj.equals(null)){ 
                ClienteDAO cd = new  ClienteDAO();
                int id = cd.retornaID(txtCnpj.getText());
                Cliente c = cd.PesquisaCNPJ(txtCnpj.getText());
                String desc = txtDescricao.getText();
                int status = 3;

                        if( desc.equals(null) || desc.equals("") || c.equals(null)){

                            if( desc.equals(null) || desc.equals("")){
                                JOptionPane.showMessageDialog(null, "Campo descrição vazio! ");
                            }

                            if (c.equals(null)){
                                JOptionPane.showMessageDialog(null, "Selecione um cliente!");
                            }


                        }else{
                                    Atendimentos at = new Atendimentos();
                                    AtendimentosDAO ad = new AtendimentosDAO();
                                    at.setCli_id(id);
                                    at.setStatus(status);
                                    at.setDescricao(desc);

                                    int retorno = ad.NovoAtendimento(at);

                                    if(retorno == 1 ){
                                        JOptionPane.showMessageDialog(null, "Atendimento salvo com sucesso!");
                                        inicializacao(1);
                                        //   btnCancelarAtdActionPerformed(evt);

                                    }
                        }


       }else{
           JOptionPane.showMessageDialog(null, "Selecione um cliente clicando no botão 'Selecionar Cliente'!");
       }
        
    }//GEN-LAST:event_btnAtdSalvarActionPerformed

    private void tbHistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHistMouseClicked
        if(ultimaLinhaHist == tbHist.getSelectedRow()){
                countclickHist++;
            }
        
            
            
            if(countclickHist > 0){
                //se clicar duas vezes faz alguma coisa
                
                String id = tbHist.getValueAt(tbHist.getSelectedRow(), 0).toString();
                
                AtendimentosDAO td = new AtendimentosDAO();
                Atendimentos at = td.PesquisaAtd(Integer.parseInt(id));
                Cliente c = new ClienteDAO().PesquisaID(at.getCli_id());
                
                LimpaTudo();
                
                jtabPrinc.setSelectedIndex(0);
                countclickHist = 0;
                
                modoConsulta();
                 
                txtCnpj.setText(c.getCNPJ());
                lbNomeCliente.setText(c.getNFantasia());
                lbStatus.setText(td.retornaStatus(at.getStatus()));
                lbData.setText(at.getData());
                txtDescricao.setText(at.getDescricao());
                txtCodAtd.setText(at.getId()+"");
               
                
                
                
                
                
                
            }
            
           
            ultimaLinhaHist = tbHist.getSelectedRow();
    }//GEN-LAST:event_tbHistMouseClicked

    private void tbPendsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPendsMouseClicked
          
        if(ultimaLinhaPend == tbPends.getSelectedRow()){
                countclickPend++;
            }
        
            
            
            if(countclickPend > 0){
                LimpaTodosOsCampos();

                //se clicar duas vezes faz alguma coisa
                String id = tbPends.getValueAt(tbPends.getSelectedRow(), 0).toString();
                
                AtendimentosDAO td = new AtendimentosDAO();
                Atendimentos at = td.PesquisaAtd(Integer.parseInt(id));
                Cliente c = new ClienteDAO().PesquisaID(at.getCli_id());
                
                LimpaTudo();
                
                txtCnpj.setText(c.getCNPJ());
                lbNomeCliente.setText(c.getNFantasia());
                lbStatus.setText(td.retornaStatus(at.getStatus()));
                lbData.setText(at.getData());
                txtDescricao.setText(at.getDescricao());
                txtCodAtd.setText(at.getId()+"");
               
              
                countclickPend = 0;
                
                modoAlteracaoAtd();
                
                     
            }
            
            ultimaLinhaPend = tbPends.getSelectedRow();
           
    }//GEN-LAST:event_tbPendsMouseClicked

    private void btnAtdSalvaAlteracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtdSalvaAlteracoesActionPerformed

       
      int idAtd = Integer.parseInt(txtCodAtd.getText());
      AtendimentosDAO td = new AtendimentosDAO();
      Atendimentos at = td.PesquisaAtd(idAtd);
      at.setDescricao(txtDescricao.getText());
      
                    td.AlterarAtendimentoMtData(at);
                    JOptionPane.showMessageDialog(null, "Atendimento Alterado com sucesso!");
                    inicializacao(1);
                   // btnCancelarAtdActionPerformed(evt);

             
       
      
    }//GEN-LAST:event_btnAtdSalvaAlteracoesActionPerformed

    
    
    
    private void btnPendModStaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPendModStaActionPerformed
        AtendimentosDAO ad = new AtendimentosDAO();
        int retorno = tbPends.getSelectedRow();
         if(retorno != -1){
             int status = ad.retornaStatusID(jcbxStatPend.getSelectedItem().toString());
             
            if(status != -1){
                    int idAtd = Integer.parseInt(tbPends.getValueAt(retorno, 0).toString());
                    System.out.println("idAtd = "+idAtd);

                        int la = ad.AlterarStatus(idAtd, status);

                    if(la == 1){
                     JOptionPane.showMessageDialog(null, "Status alterado com sucesso!");
                     inicializacao(1);
                    // btnCancelarAtdActionPerformed(evt);
                    }
            }else{
                JOptionPane.showMessageDialog(null, "Selecione o Status que deseja para que seja modificado.");
            }    
           
            
        }else{
        
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
            
        }
    }//GEN-LAST:event_btnPendModStaActionPerformed

    private void btnHistExibirHistoricoCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistExibirHistoricoCompletoActionPerformed
        exibeHistorico();
    }//GEN-LAST:event_btnHistExibirHistoricoCompletoActionPerformed

    private void btnAtdCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtdCancelarActionPerformed
        inicializacao(1);
    }//GEN-LAST:event_btnAtdCancelarActionPerformed

    private void btnAtdModoCOnsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtdModoCOnsultaActionPerformed
       funcSairModoConsulta();
    }//GEN-LAST:event_btnAtdModoCOnsultaActionPerformed

    private void txtCnpjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCnpjActionPerformed
      if(!txtCnpj.equals(null) && modoAlteracao == false){
          ClienteDAO cd = new ClienteDAO();
          Cliente c = cd.PesquisaCNPJ(txtCnpj.getText());
          
          selecionaCliente(c.getCNPJ(), c.getNFantasia());
      }
    }//GEN-LAST:event_txtCnpjActionPerformed

    private void btnExAtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExAtdActionPerformed
        int linhaSelecionada = tbHist.getSelectedRow();
        
        if(linhaSelecionada != -1){
            
            AtendimentosDAO atd = new AtendimentosDAO();
            
            int codigo = Integer.parseInt(tbHist.getValueAt(linhaSelecionada, 0).toString());
            
            int retorno = atd.ExcluiAtendimento(codigo);
            
            if(retorno == 1){ JOptionPane.showMessageDialog(null, "Atendimento excluído com sucesso");}
            
            exibeHistorico();
            
        }
    }//GEN-LAST:event_btnExAtdActionPerformed

    private void btnAtdFinalizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtdFinalizaActionPerformed
           funcFinalizaAtd();
    }//GEN-LAST:event_btnAtdFinalizaActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
         buscaPersonalizada();
    }//GEN-LAST:event_txtPesquisaActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CadCliente;
    private javax.swing.JButton btnAtdCancelar;
    private javax.swing.JButton btnAtdFinaliza;
    private javax.swing.JButton btnAtdModoCOnsulta;
    private javax.swing.JButton btnAtdSalvaAlteracoes;
    private javax.swing.JButton btnAtdSalvar;
    private javax.swing.JButton btnAtdSelectCliente;
    private javax.swing.JButton btnCliAlteracoes;
    private javax.swing.JButton btnCliAlterar;
    private javax.swing.JButton btnCliBuscar;
    private javax.swing.JButton btnCliCancelar;
    private javax.swing.JButton btnCliDeletar;
    private javax.swing.JButton btnCliExibirTodos;
    private javax.swing.JButton btnCliLimpaTabela;
    private javax.swing.JButton btnCliLimparCampos;
    private javax.swing.JButton btnCliNovo;
    private javax.swing.JButton btnCliSalNovo;
    private javax.swing.JButton btnExAtd;
    private javax.swing.JButton btnHistExibirHistoricoCompleto;
    private javax.swing.JButton btnPendModSta;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox jcbxStatPend;
    private javax.swing.JComboBox jcbxTipoPesq;
    private javax.swing.JPanel jpCliente;
    private javax.swing.JPanel jpHist;
    private javax.swing.JPanel jpPend;
    private javax.swing.JPanel jpToday;
    private javax.swing.JTabbedPane jtabPrinc;
    private javax.swing.JLabel lbData;
    private javax.swing.JLabel lbNomeCliente;
    private javax.swing.JLabel lbStatus;
    private javax.swing.ButtonGroup rbtgPesquisas;
    private javax.swing.JTable tbClientes;
    private javax.swing.JTable tbHist;
    private javax.swing.JTable tbPends;
    private javax.swing.JTextField txtCliCNPJ;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliDDD;
    private javax.swing.JTextField txtCliDDDTel2;
    private javax.swing.JTextField txtCliNFant;
    private javax.swing.JTextField txtCliResp;
    private javax.swing.JTextField txtCliRz;
    private javax.swing.JTextField txtCliTel;
    private javax.swing.JTextField txtCliTel2;
    private javax.swing.JTextField txtCnpj;
    private javax.swing.JTextField txtCodAtd;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
