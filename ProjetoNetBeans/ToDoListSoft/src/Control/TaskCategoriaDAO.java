/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import BancoDados.ConexaoBanco;
import Model.Clientes;
import Model.TaskCategoria;
import View.UserTasks;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 *
 * @author Zaymã Kinsiona
 */
public class TaskCategoriaDAO {
    private static Connection conectar;
    
    public TaskCategoriaDAO(){
        conectar = new ConexaoBanco().getConnection();
    }
      
    private static TaskCategoria categorias[] = new TaskCategoria[0];
   
   

    public static TaskCategoria[] getCategorias() {
        return categorias;
    }

    public static void setCategorias(TaskCategoria[] categorias) {
        TaskCategoriaDAO.categorias = categorias;
    }
    
    
    public TaskCategoria getCateg(String nome){
        for(int c =0; c <= getCategorias().length-1;c++){
            if(getCategorias()[c].nomeCategoria.getText() == nome){
                return getCategorias()[c];
            }
        }
        return getCategorias()[0];
    }       
   
    
    public void loadContent(){
        String sql = "SELECT * FROM categorias WHERE id_cliente = ?";
        try{
            PreparedStatement request = conectar.prepareStatement(sql);
            request.setInt(1, ClienteDAO.getLoginInfo().id);
            
            ResultSet response = request.executeQuery();
            
            while(response.next()){
                JLabel LabelCategoria = new JLabel();
                
                Color corClass = new Color(response.getInt("cor_categoria"));
                LabelCategoria.setText(response.getString("nome_categoria"));
                
                TaskCategoria categoria = new TaskCategoria(LabelCategoria,corClass);
                adicionarCategoria(categoria);
            }
            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "Erro ao carregar o conteúdo em loadContent() " + erro);
        }
    }
    
   
    
    public void addCategoriaToDataBase(TaskCategoria object){
         String sql = "INSERT INTO categorias (nome_categoria,cor_categoria, id_cliente) VALUES " +
                "(?,?,?)";
        try{
            PreparedStatement request = conectar.prepareStatement(sql);
            request.setString(1, object.nomeCategoria.getText());
            request.setInt(2, object.corCategoria.getRGB());
            request.setInt(3, ClienteDAO.getLoginInfo().id);
            
            request.execute();
            
        }catch(Exception erro){
            JOptionPane.showConfirmDialog(null, "Erro ao adicionar categoria em adicionarCategoria() " + erro);
        }
    }
    
    public void adicionarCategoria(TaskCategoria object){
        TaskCategoria temp[] = getCategorias();//Pegando o Array de categorias atual e colocando em uma variabel temporaria, temp
        int newArrayLenght = temp.length + 1; //criando uma variavel que recebe o tamanho do array + 1
        TaskCategoria newArray[] = new TaskCategoria[newArrayLenght]; //criando um novo array com o tamanho aumentado
        
        for(int c = 0;c <= getCategorias().length-1;c++){
            newArray[c] = temp[c];
        }
        
        newArray[newArray.length-1] = object;
        setCategorias(newArray);
        
        UserTasks.updateList(object.nomeCategoria,object.corCategoria);
    }
    
    public static int getCategoriaID(String categ){
        String sql = "SELECT id_categoria,nome_categoria, id_cliente from categorias WHERE " +
                "id_cliente = ? AND nome_categoria = ?";
        try{
            PreparedStatement request = conectar.prepareStatement(sql);
            request.setInt(1, ClienteDAO.getLoginInfo().id);
            request.setString(2, categ);
            
            ResultSet response = request.executeQuery();
            
            if(response.next()){
                return response.getInt("id_categoria");
            }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "Erro ao buscar ID da categoria: " + erro);
        }
        return 0;
    }
   
   public static void removeCategoria(String nome){
       int idCateg = TaskCategoriaDAO.getCategoriaID(nome);
       
       String removeTasks = "DELETE FROM tasks WHERE id_categoria = ? ";
       String sql = "DELETE FROM categorias WHERE nome_categoria = ? AND id_cliente = ?";
 
       try{
           PreparedStatement remove = conectar.prepareStatement(removeTasks);
           remove.setInt(1, TaskCategoriaDAO.getCategoriaID(nome));
           remove.execute();
           remove.close();
           
           PreparedStatement request = conectar.prepareStatement(sql);
           request.setString(1, nome);
           request.setInt(2,ClienteDAO.getLoginInfo().id );
           
           request.execute();
           request.close();
           
    
           UserTasks.select_types.removeItem(nome);
       }catch(Exception erro){
           JOptionPane.showConfirmDialog(null, "Não foi possivel remover a categoria " + erro);
       }
  }
    
      
       
  
      
    
  

}
    