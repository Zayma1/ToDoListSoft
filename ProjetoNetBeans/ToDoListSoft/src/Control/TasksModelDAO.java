package Control;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import Model.FrameTask;
import Model.TaskCategoria;
import Model.TasksModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import BancoDados.ConexaoBanco;
import static Control.TaskCategoriaDAO.getCategorias;
import static Control.TaskCategoriaDAO.setCategorias;
import static Control.TasksModelDAO.getTasks_list;
import View.UserTasks;
import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author Zaymã Kinsiona
 */
public class TasksModelDAO {
    
    private static Connection conectar;
    
    private static FrameTask tasks_list[] = new FrameTask[0];


    public static FrameTask[] getTasks_list() {
        return tasks_list;
    }

    public static void setTasks_list(FrameTask[] tasks_list) {
        TasksModelDAO.tasks_list = tasks_list;
    }
    
    
    
    public static void addTask(FrameTask object){
        object.taskname.setText(object.getInfo().getNome_task());
        
        String sql = "INSERT INTO tasks (nome,id_categoria,isDone,validade,descricao) VALUES "+
                "(?,?,?,?,?)";
        conectar = new ConexaoBanco().getConnection();
        try{
            PreparedStatement request = conectar.prepareStatement(sql);
            
            request.setString(1, object.getInfo().getNome_task());
            request.setInt(2, object.getInfo().getId_categoria());
            request.setBoolean(3, object.getInfo().isIsDone());
            
            String dia = object.getInfo().getValidade().substring(0,2);
            String mes = object.getInfo().getValidade().substring(3,5);
            String ano = object.getInfo().getValidade().substring(6);
            
            String dataParaSQL = ano+'-'+mes+'-'+dia;
            
            request.setString(4, dataParaSQL);
            request.setString(5, object.getInfo().getDescricao());
            
            request.execute();
            request.close();   
            
            
            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "Erro ao adicionar task(addTask()) " + erro);
        }
        
        LoadContent();
        LoadSelect(UserTasks.TaskTitle.getText());
        
    }
    
    
    public static void LoadContent(){
        String sql = "SELECT * from tasks JOIN categorias " +
        "ON tasks.id_categoria = categorias.id_categoria " +
        "WHERE id_cliente = ?";
        
        FrameTask temp[] = new FrameTask[0];
        setTasks_list(temp);
      
        
        conectar = new ConexaoBanco().getConnection();
        try{
            PreparedStatement request = conectar.prepareStatement(sql);
            
            request.setInt(1, ClienteDAO.getLoginInfo().id);
            
            ResultSet response = request.executeQuery();
            
            while(response.next()){
                FrameTask object = new FrameTask();
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataFormatada = dateFormat.format(response.getDate("validade"));
                
                object.getInfo().setValidade(dataFormatada);
                
                
                
                object.getInfo().setId_task(response.getInt("id_task"));
                object.getInfo().setNome_task(response.getString("nome"));
                object.getInfo().setId_categoria(response.getInt("id_categoria"));
                object.getInfo().setIsDone(response.getBoolean("isDone"));
                object.getInfo().setDescricao(response.getString("descricao"));
                
                object.taskname.setText(object.getInfo().getNome_task());
                
                temp = getTasks_list();
                int newArrayLenght = temp.length + 1;
                FrameTask newArray[] = new FrameTask[newArrayLenght]; 

                for(int c = 0; c <= temp.length-1;c++){
                     newArray[c] = temp[c];
                }


                newArray[newArrayLenght -1] = object;
                setTasks_list(newArray);
                
            }
        }catch(Exception erro){
            JOptionPane.showConfirmDialog(null, "Erro ao carregar Tasks do usuario LoadContent() " + erro);
        }
}  
   
    public static void LoadSelect(String nome){
        UserTasks.ScrollTasks.removeAll();
        
        String sql = "SELECT * from tasks JOIN categorias " +
        "ON tasks.id_categoria = categorias.id_categoria " +
        "WHERE id_cliente = ? AND nome_categoria = ?";
        
        conectar = new ConexaoBanco().getConnection();
        try{
            
            PreparedStatement request = conectar.prepareStatement(sql);
            
            request.setInt(1, ClienteDAO.getLoginInfo().id);
            request.setString(2, nome);
            
            ResultSet response = request.executeQuery();
            
            while(response.next()){
  
              int categoria = response.getInt("id_categoria");
              
              for(int c=0;c <= getTasks_list().length-1;c++){
                if(getTasks_list()[c].getInfo().getId_categoria() == categoria){
                  getTasks_list()[c].setBackground(new Color(response.getInt("cor_categoria")));
                  UserTasks.ScrollTasks.add(getTasks_list()[c]);
                  UserTasks.ScrollTasks.updateUI();
               }
              }
              
              
            }
        }catch(Exception erro){
            JOptionPane.showConfirmDialog(null, "Erro ao carregar Tasks do usuario LoadContent() " + erro);
        }
        UserTasks.ScrollTasks.repaint();
    }
    
    
    public static void taskCompleted(int id_task){
        String sql2 = "SELECT * FROM tasks WHERE id_task = ?";
        String sql = "UPDATE tasks set isDone = ? WHERE id_task = ?";
        try{
            PreparedStatement request2 = conectar.prepareStatement(sql2);
            request2.setInt(1, id_task);
            
            ResultSet response = request2.executeQuery();
            if(response.next()){
                if(response.getBoolean("isDone") == false){
                    PreparedStatement request = conectar.prepareStatement(sql);
                    request.setBoolean(1, true);
                    request.setInt(2, id_task);
                    
                    request.execute();
                    request.close(); 
                    
                    UserTasks.isDonePainel.setBackground(Color.green);
                }else{
                    PreparedStatement request = conectar.prepareStatement(sql);
                    request.setBoolean(1, false);
                    request.setInt(2, id_task);

                    request.execute();
                    request.close(); 
                    
                    UserTasks.isDonePainel.setBackground(Color.gray);
                }
                
            }
            
            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "erro ao atulizar isDone " + erro);
        }
    }
    
    
    public static void verifyIsDone(int id_task){
        String sql = "SELECT isDone from tasks WHERE id_task = ?";
        try{
            PreparedStatement request = conectar.prepareStatement(sql);
            request.setInt(1, id_task);
            
            ResultSet response = request.executeQuery();
            if(response.next()){
                if(response.getBoolean("isDone") == false){
                    UserTasks.isDonePainel.setBackground(Color.gray);
                }else{
                    UserTasks.isDonePainel.setBackground(Color.green);
                }
            }
            
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "erro ao verificar isDone " + erro);
        }
    }
    
    
    public static void updateTask(FrameTask task){
      String sql = "UPDATE tasks SET nome = ?,descricao = ?,validade = ? WHERE id_task = ?";
      try{
          PreparedStatement request = conectar.prepareCall(sql);
          request.setString(1, task.getInfo().getNome_task());
          request.setString(2, task.getInfo().getDescricao());
          
          
          String data = (task.getInfo().getValidade().replace('/', '-'));
          
          String dia = data.substring(0,2);
          String mes = data.substring(3,5);
          String ano = data.substring(6);
            
          String dataParaSQL = ano+'-'+mes+'-'+dia;
          
          request.setString(3, dataParaSQL);
          
          request.setInt(4, task.getInfo().getId_task());
          
          request.execute();
          request.close();
      }catch(Exception erro){
          JOptionPane.showMessageDialog(null, "Não foi possivel editar a task updateTask() " + erro);
      }
  }
    
    public static void removeTask(int id_task){
        String sql = "DELETE FROM tasks WHERE id_task = ?";
        try{
           PreparedStatement request = conectar.prepareStatement(sql);
           request.setInt(1, id_task);
           
           request.execute();
           request.close();
           
           
           
           Component temp[] = UserTasks.ScrollTasks.getComponents();
           for(Component componente:temp){
               if (componente instanceof FrameTask){
                   if(((FrameTask) componente).getInfo().getId_task() == id_task){
                       UserTasks.ScrollTasks.remove(componente);
                   }
               }
           }
        }catch(Exception erro){
            JOptionPane.showMessageDialog(null, "Não foi possivel deletar a task " + erro);
        }
    }
}   
    


