/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ConexaoBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Admin
 */
public class ReadConfig {
    static String caminho = "";
    
    
    public static void inserirDados(String caminho, String[] dados){
        
        
        try {
            FileWriter fl = new FileWriter(caminho);
            PrintWriter pw = new PrintWriter(fl);
            
            pw.write(dados[0]+":"+ dados[1]);
            System.out.println(dados[0]+":"+ dados[1]);
            fl.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ReadConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static String[] leituraDados(String caminho){
        String user = "";
        String password = "";
        try {
            BufferedReader bf = new BufferedReader(new FileReader(caminho));
            
            String linha = bf.readLine();
            
            while(linha != null){
             int n = linha.indexOf(":");
             
              user = linha.substring(0, n);
              password = linha.substring(n+1, linha.length());
             
                System.out.println("user: " + user);
                System.out.println("password: " + password);
            
            
            }
            
            bf.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    
             return new String[]{user, password};
    
    }
    
    
}
