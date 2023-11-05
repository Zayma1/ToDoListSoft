/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.io.Serializable;

/**
 *
 * @author Zaymã Kinsiona
 */
//classe para ser serializada
public class ClientesObject implements Serializable {
    public int id;
    public String nome;
    public String celular;
    public String email;
    
    ClientesObject(int id, String nome, String celular, String email){
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
    }
}
