/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassesPrincipais;

/**
 *
 * @author Admin
 */
public class Usuarios {
    
    private int id;
    private String nome;
    private String senha;
    private int acesso;
    
    public Usuarios(String nome, String senha, int acesso){
    
        /* 0 - adm(total) ; 1 - user(parcial) */
        
        this.nome = nome;
        this.senha = senha;
        this.acesso = acesso;
        
        
    
    }
    
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAcesso() {
        return acesso;
    }

    public void setAcesso(int acesso) {
        this.acesso = acesso;
    }
    
    
    
}
