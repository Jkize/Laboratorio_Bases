/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import Estructura.Arbol_Archivo_IdString;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import modelo.Supermercado;

/**
 *
 * @author PC02
 */
public class DAO_Supermercado {
    
    private RandomAccessFile archivo;
    private Arbol_Archivo_IdString arbol;
    
    public DAO_Supermercado() throws FileNotFoundException {
        archivo = new RandomAccessFile("supermercado", "rw");
        arbol = new Arbol_Archivo_IdString("supermercado");
    }
    
    public Supermercado buscarSM(String id) throws IOException{
        int pos = (int) arbol.getPosArchivo(id);
        Supermercado sm = new Supermercado();
        archivo.seek(pos);
        sm.setIdSM(archivo.readUTF());
        sm.setNombreSM(archivo.readUTF());
        sm.setDireccionSM(archivo.readUTF());
        return sm;
    }
    
    public boolean crearSM(Supermercado sup) throws IOException{
        archivo.seek(archivo.length());
        if(arbol.añadir(sup.getIdSM(), (int)archivo.length())){
            archivo.writeUTF(sup.getIdSM());
            archivo.writeUTF(sup.getNombreSM());
            archivo.writeUTF(sup.getDireccionSM());
            return true;
        }
        return false;
    }
    
    public boolean eliminarSM(String id){
        return false;
    }
    
    public boolean actualizarSM(Supermercado sup) throws IOException{
        int pos = (int) arbol.getPosArchivo(sup.getIdSM());
        archivo.writeUTF(sup.getIdSM());
        archivo.writeUTF(sup.getNombreSM());
        archivo.writeUTF(sup.getDireccionSM());
        return true;
    }
    
}
