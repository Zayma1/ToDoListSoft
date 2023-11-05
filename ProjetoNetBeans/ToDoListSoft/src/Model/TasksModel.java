/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Control.TaskCategoriaDAO;
import Control.TasksModelDAO;
import View.UserTasks;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author Zaymã Kinsiona
 */
public class TasksModel {
    private int id_task;
    private String nome_task;
    private int id_categoria;
    private boolean isDone;
    private String validade;
    private String descricao;
    public TasksModel(int id_task,String nome_task,int id_categoria,boolean isDone,String validade,String descricao){
        this.id_task = id_task;
        this.nome_task = nome_task;
        this.id_categoria = id_categoria;
        this.isDone = isDone;
        this.validade = validade;
        this.descricao = descricao;
    }

    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public String getNome_task() {
        return nome_task;
    }

    public void setNome_task(String nome_task) {
        this.nome_task = nome_task;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public boolean isIsDone() {
        return isDone;
    }
    
    
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public static void addTaskRequest(FrameTask object){
        if(UserTasks.categoria_selecionada != UserTasks.select_types.getItemAt(0)){
            int categ_id = TaskCategoriaDAO.getCategoriaID(UserTasks.categoria_selecionada);
            
            object.information.setId_categoria(categ_id);
            
            TasksModelDAO.addTask(object);
            
            
        }
        
    }
   public static void loadTaskRequest(){
       TasksModelDAO.LoadContent();
   }
   
   public static void loadSelectRequest(String nome){
       TasksModelDAO.LoadSelect(nome);
   }
   
   public static void taskCompleteRequest(int id_task){
       TasksModelDAO.taskCompleted(id_task);
   }
   
   public static void verifyIsDoneRequest(int id_task){
       TasksModelDAO.verifyIsDone(id_task);
   }
   
   public static void updateTaskRequest(FrameTask task){
       TasksModelDAO.updateTask(task);
   }
   
   public static void removeTasksRequest(int id_task){
       TasksModelDAO.removeTask(id_task);
   }
}
