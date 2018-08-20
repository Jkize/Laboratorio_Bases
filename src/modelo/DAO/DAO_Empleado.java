/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import Estructura.Arbol_Archivo_IdLong;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import modelo.Empleado;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Empleado {
    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Empleado() throws FileNotFoundException {
        archivo = new RandomAccessFile("empleado", "rw");
        arbol = new Arbol_Archivo_IdLong("empleado");
    }
    
    public boolean añadir(Empleado empleado) throws IOException{
         archivo.seek(archivo.length());
        if (arbol.añadir(empleado.getIdPersona(),(int)archivo.length())) {
            archivo.writeLong(empleado.getIdPersona());
            archivo.writeUTF(empleado.getNombre());
            archivo.writeUTF(empleado.getContrasena());
            archivo.writeUTF(empleado.getCargo());
            archivo.writeUTF(empleado.getIdcaja());
            return true;
        }
        return false;
    }
    
    
    public Empleado buscarEmpleado(long id) throws IOException {
        int pos = (int) arbol.getPosArchivo(id);
        Empleado empleado = new Empleado();
        archivo.seek(pos);
        empleado.setIdPersona(archivo.readLong());
        empleado.setNombre(archivo.readUTF());        
        empleado.setContrasena(archivo.readUTF());           
        empleado.setCargo(archivo.readUTF());           
        empleado.setIdcaja(archivo.readUTF());
        return empleado;
    }
    
     public boolean actualizarEmpleado(Empleado empleado) throws IOException {

        try {
            int pos = (int) arbol.getPosArchivo(empleado.getIdPersona());
            archivo.seek(pos);
            archivo.writeLong(empleado.getIdPersona());
            archivo.writeUTF(empleado.getNombre());
            archivo.writeUTF(empleado.getContrasena());
            archivo.writeUTF(empleado.getCargo());
            archivo.writeUTF(empleado.getIdcaja());

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
