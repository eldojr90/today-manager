/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Recursos;


import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public class GerenciaDeImagens {
    
    public static void iconePrincipal(JFrame tela){
        
        URL url = tela.getClass().getResource("/Imagens/IconeManager.png");  
        Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);
        tela.setIconImage(imagemTitulo);  
    
    }
    
    
}
