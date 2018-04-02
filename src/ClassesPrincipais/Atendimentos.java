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
public class Atendimentos {
    
    private int id;
    private int cli_id;
    private String descricao;
    private int status;
    private String data;
 
    public int getCli_id() {
        return cli_id;
    }

    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
 
}
