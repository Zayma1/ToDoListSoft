/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import BancoDados.ConexaoBanco;
import Model.Clientes;
import View.LoginCliente;
import View.UserTasks;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import Control.ClientesObject;

/**
 *
 * @author Zaymã Kinsiona
 */
public class ClienteDAO {
    private Connection conectar;
    
    
    public ClienteDAO(){
        conectar = new ConexaoBanco().getConnection();
    };
    
    //Função para serializar os dados assim que o usuario fizer Login, podendo consulta-los posteriomente
    public static void isLogged(Clientes dados){
        ClientesObject serializedObject = new ClientesObject(dados.getId(),dados.getNome(),
                dados.getCelular(),dados.getEmail());
      
        try(FileOutputStream arquivoSaida = new FileOutputStream("lib/Cliente_logado_info.ser");
                ObjectOutputStream streamSaida = new ObjectOutputStream(arquivoSaida)){
           streamSaida.writeObject(serializedObject);
        }catch(IOException erro){
            JOptionPane.showMessageDialog(null, "Erro ao serializar Clientes " + erro);
        }
    }   
    
    public void CadastrarCliente(Clientes dados){
        try{
            
            String dia = dados.getData().substring(0,2);
            String mes = dados.getData().substring(3,5);
            String ano = dados.getData().substring(7);
            
            String dataParaSQL = ano+'-'+mes+'-'+dia;
            
            
            String sql = "INSERT INTO tb_clientes (nome,data,cpf,celular,email,senha) VALUES" + "(?,?,?,?,?,?)";
            PreparedStatement inserir = conectar.prepareStatement(sql);
            
            inserir.setString(1, dados.getNome());
            inserir.setString(2, dataParaSQL);
            inserir.setString(3, dados.getCpf());
            inserir.setString(4, dados.getCelular());
            inserir.setString(5, dados.getEmail());
            inserir.setString(6, dados.getSenha());
            
            inserir.execute();
            inserir.close();
            JOptionPane.showMessageDialog(null, "Cadastro feito com sucesso.");
            
            LoginCliente loginFrame = new LoginCliente();
            loginFrame.setVisible(true);
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null,"deu erro" + erro);
        }
    }
    
      
    public boolean LoginVerify(String email,String senha){
        Clientes dados = new Clientes();
        LoginCliente telaClientes = new LoginCliente();
        try{
            
            String sql = "SELECT id_cliente,nome, data,cpf,celular,"
                    + "email,senha FROM tb_clientes WHERE email = ? AND senha = ?";
            
            PreparedStatement hasLogin = conectar.prepareStatement(sql);
            hasLogin.setString(1, email);
            hasLogin.setString(2, senha);
            
            ResultSet request = hasLogin.executeQuery();
            boolean verify = request.next();
            
     
            try{
                if(verify == true){
                    dados.setId(request.getInt("id_cliente"));
                    dados.setNome(request.getString("nome"));
                    dados.setCelular(request.getString("celular"));
                    dados.setCpf(request.getString("cpf"));
                    dados.setEmail(request.getString("email"));
                    dados.setSenha(request.getString("senha"));
                    
                    isLogged(dados);
                    
                    UserTasks UserFrame = new UserTasks();
                    UserFrame.setVisible(true);
                    return true;
                   
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Login não encontrado, verifique se as informações solicitadas estão corretas.");
                }
                  
            }catch (Exception erro){
                JOptionPane.showMessageDialog(null, "erro " + erro);
            }
            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null,"deu erro " + erro);
        }
      return false;
    }
    public static void doLogout(){
        Clientes dados = new Clientes();
        
        File deletar = new File("lib/Cliente_logado_info.ser");
        if(deletar.exists()){
            deletar.delete();
        }
        
        LoginCliente backLoginScreen = new LoginCliente();
        backLoginScreen.setVisible(true);
        
        
    }
    
    public static ClientesObject getLoginInfo() throws ClassNotFoundException{
        try(FileInputStream arquivoSaida = new FileInputStream("lib/Cliente_logado_info.ser");
                ObjectInputStream streamEntrada = new ObjectInputStream(arquivoSaida)){
           ClientesObject dados = (ClientesObject) streamEntrada.readObject();
           return dados;
        }catch(IOException erro){
            JOptionPane.showMessageDialog(null, "Erro ao desserializar Clientes " + erro);
        }
     return null;
    }
    
    public static boolean alreadyLogged(){
        File deletar = new File("lib/Cliente_logado_info.ser");
        
        if(deletar.exists()){
            return true;
        }else{
            return false;
        }
    }
}
