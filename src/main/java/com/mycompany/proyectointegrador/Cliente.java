/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ulise
 */
public class Cliente {

    int id;
    String nombre;
    String apellido;
    String correo;
    
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public void RegistrarCliente(JTextField parametronombre, JTextField parametroapellido,JTextField parametrocorreo) throws IOException, SQLException{
        // Supongamos que parametroid es un objeto de un componente gráfico como JTextField
      
        setNombre(parametronombre.getText());
        setApellido(parametroapellido.getText());
        setCorreo(parametrocorreo.getText());
        
        
        Cconexion objetoCconexion = new Cconexion();
        
        String consulta ="insert into Cliente(nombre,apellido,correoelectronico) values(?,?,?);";
        try {
            CallableStatement cs=objetoCconexion.establecerConexion().prepareCall(consulta);
            cs.setString(1,getNombre());
            cs.setString(2,getApellido());
            cs.setString(3, getCorreo());
            
           cs.execute();
            JOptionPane.showMessageDialog(null,"REGISTRO REALIZADO CORRECTAMENTE");
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR DE REGISTRO, Error:"+e.toString());

        }
        CrearXML();
        
    }
    
    public void MostrarClientes(JTable parametrotabala){
        
        Cconexion obejtoCconexion = new Cconexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        TableRowSorter<TableModel>OrdenarTabla=new TableRowSorter<TableModel>(modelo);
        parametrotabala.setRowSorter(OrdenarTabla);
        
        String sql ="";
        
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Correo");
        
        parametrotabala.setModel(modelo);
        
        sql="select * from Cliente";
        String [] datos = new String[4];
        Statement st;
        
        try {
            st =obejtoCconexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
     
                
                modelo.addRow(datos);
            }
            parametrotabala.setModel(modelo);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"no se pudo mostrar los registros, Error:"+e.toString());
        }
        
    }
 public void seleccionarCliente(JTable parametrotabala, JTextField parametroid, JTextField parametronombre, JTextField parametroapellido, JTextField parametrocorreo) {

    try {
        int fila = parametrotabala.getSelectedRow();

        if (fila >= 0) {
            parametroid.setText( parametrotabala.getValueAt(fila, 0).toString());
            parametronombre.setText( parametrotabala.getValueAt(fila, 1).toString());
            parametroapellido.setText( parametrotabala.getValueAt(fila, 2).toString());
            parametrocorreo.setText( parametrotabala.getValueAt(fila, 3).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Fila No seleccionada");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error de seleccion, Error;" + e.toString());
    }
}
 
public void ModificarCliente(JTextField parametroid, JTextField parametronombre, JTextField parametroapellido, JTextField parametrocorreo) throws IOException, SQLException {
    setId(Integer.parseInt(parametroid.getText()));
    setNombre(parametronombre.getText());
    setApellido(parametroapellido.getText());
    
    Cconexion objeCconexion = new Cconexion();
    
    String consulta = "UPDATE `proyectointegrador`.`cliente` SET `nombre` = ?, `apellido` = ?, `correoelectronico` = ? WHERE (`id` = ?);";
    
    try {
        CallableStatement cs=objeCconexion.establecerConexion().prepareCall(consulta);
        cs.setString(1,getNombre());
        cs.setString(2, getApellido());
        cs.setString(3, parametrocorreo.getText());
        cs.setInt(4, getId());
        
        cs.execute();
        JOptionPane.showMessageDialog(null,"MODIFICACIÓN EXISTOSA");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"ERROR EN LA MODIFICACIÓN"+e.toString());
        
    }
    CrearXML();
}

public void EliminarCliente (JTextField parametroid) throws IOException, SQLException{
    setId(Integer.parseInt(parametroid.getText()));
    
    Cconexion objeCconexion = new Cconexion();
    
    String consulta = "DELETE FROM `proyectointegrador`.`cliente` WHERE (`id` = ?);";
    
     try {
        CallableStatement cs=objeCconexion.establecerConexion().prepareCall(consulta);
        cs.setInt(1, getId());
        cs.execute();
   
        JOptionPane.showMessageDialog(null,"ELIMINACION EXITOSA");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"ERROR EN LA ELIMINACIÓN"+e.toString());
        
    }
     CrearXML();
}


public void CrearXML() throws IOException, SQLException {
        ResultSet rs = null;

        String filePath = "C:\\Users\\ulise\\Desktop\\clientes.xml";

        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);

        Cconexion obejtoCconexion = new Cconexion();
        String sql = "select * from Cliente";
        Statement st = null;

        try {
            st = obejtoCconexion.establecerConexion().createStatement();
            rs = st.executeQuery(sql);

            String line = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";  //Coloco la cabecera
            FileWriter cb = new FileWriter(filePath, true);
            cb.write(line);
            cb.close();

            line = "<Clientes>"; //abro el XML
            FileWriter ap = new FileWriter(filePath, true);
            ap.write(line);
            ap.close();

            while (rs.next()) {

                line = "<cliente><nombre>" + rs.getString("nombre") + "</nombre><apellido>"
        + rs.getString("apellido") + "</apellido><correo>"
        + rs.getString("correoelectronico") + "</correo></cliente>";


                FileWriter fw = new FileWriter(filePath, true);
                fw.write(line);
                fw.close();
            }

            line = "</Clientes>";
            FileWriter fo = new FileWriter(filePath, true);
            fo.write(line);
            fo.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            // Cerrar recursos en el bloque finally
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
        
    }


}
