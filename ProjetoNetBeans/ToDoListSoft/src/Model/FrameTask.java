/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Model;

import View.UserTasks;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author Zaymã Kinsiona
 */
public class FrameTask extends javax.swing.JPanel {
    
    TasksModel information = new TasksModel(0,"name",0,false,"data","desc");
    
    public FrameTask() {
        initComponents();
    }
    
    public TasksModel getInfo(){
        return information;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskname = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 0, 0));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        taskname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        taskname.setForeground(new java.awt.Color(210, 210, 210));
        taskname.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskname, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskname, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        UserTasks.TaskNameInfo.setText(information.getNome_task());
        UserTasks.validadeInfo.setText(information.getValidade());
        UserTasks.textInfo.setText(information.getDescricao());
        UserTasks.id_task_selecionada = information.getId_task();
        
        TasksModel.verifyIsDoneRequest(information.getId_task());
    }//GEN-LAST:event_formMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        setBorder(javax.swing.BorderFactory.createLineBorder(Color.black, 2));
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        setBorder(null);
    }//GEN-LAST:event_formMouseExited
    
    public void setName(String nome){
        taskname.setText(nome);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel taskname;
    // End of variables declaration//GEN-END:variables
}
