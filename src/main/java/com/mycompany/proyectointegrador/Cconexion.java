/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author ulise
 */
public class Cconexion {
    Connection conectar = null;
    
    String usuario ="root";
    String contraseña ="ulises";
    String basededatos ="proyectointegrador";
    String ip ="localhost";
    String puerto ="3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+basededatos;
    
    
    public Connection establecerConexion (){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contraseña);
           // JOptionPane.showMessageDialog(null,"LA CONEXION HA SIDO EXITOSA");
        
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectar la base de datos, Error:"+e.toString());
        }
        return conectar;
        
    }
     
    
}
