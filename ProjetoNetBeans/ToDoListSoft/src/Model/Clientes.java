/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Control.ClienteDAO;
import Control.ClientesObject;

/**
 *
 * @author Aluno
 */
public class Clientes {
    private int id;
    private String nome;
    private String cpf;
    private String celular;
    private String email;
    private String senha;
    private String data;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public static void iniciarCadastro(Clientes dados){
        ClienteDAO enviar = new ClienteDAO();
        
        enviar.CadastrarCliente(dados);
    }
    
    public boolean verificarLogin(String email, String senha){
        ClienteDAO verificar = new ClienteDAO();
        return verificar.LoginVerify(email, senha);
        
    }
    
    public static void logout(){
        ClienteDAO.doLogout();
    }
    
    public static ClientesObject initilizateData() throws ClassNotFoundException{
        return ClienteDAO.getLoginInfo();
    }
    
    public static boolean alreadyLoggedRequest(){
        return ClienteDAO.alreadyLogged();
    }
            
}
