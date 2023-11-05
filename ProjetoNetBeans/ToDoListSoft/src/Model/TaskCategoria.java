/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Control.TaskCategoriaDAO;
import View.UserTasks;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Zaymã Kinsiona
 */
public class TaskCategoria {
    
    public JLabel nomeCategoria;
    public Color corCategoria;
    
    
    public TaskCategoria(JLabel nome, Color corCategoria){
        this.nomeCategoria = nome;
        this.corCategoria = corCategoria;
    }
    
    
    public void DAOcreate_request(JLabel nome, Color corCateg){
        TaskCategoria categoria = new TaskCategoria(nome,corCateg);
        TaskCategoriaDAO classTask = new TaskCategoriaDAO();
        
        classTask.addCategoriaToDataBase(categoria);
        classTask.adicionarCategoria(categoria);
        
    }
    
    public static void loadRequest(){
       TaskCategoriaDAO classTask = new TaskCategoriaDAO();
       classTask.loadContent();
    }
    
    public static void removeCategoriaRequest(String nome){
        TaskCategoriaDAO.removeCategoria(nome);
    }
    
   public static boolean hasCategoria(String nome){
       for(int c = 0 ; c <= TaskCategoriaDAO.getCategorias().length - 1;c++){
           if(TaskCategoriaDAO.getCategorias()[c].nomeCategoria.getText() == nome){
               return true;
           }
       }
       return false;
   }
   
}