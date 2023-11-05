/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoDados;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class ConexaoBanco {
    
    public Connection getConnection(){
        try{
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1/todolist","root","");
        }catch(Exception erro){
            throw new RuntimeException(erro);
        }
    }
    public static void main(String[] args){
        try{
            new ConexaoBanco().getConnection();
            JOptionPane.showMessageDialog(null, "CONEXÃO COM EXITO");
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null,"ERRO NA CONEXÃO");
        }
    }
}
