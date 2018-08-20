/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import Estructura.Arbol_Archivo_IdLong;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import modelo.Meta;

/**
 *
 * @author PC02
 */
public class DAO_Meta {
    
    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;
    
    public DAO_Meta() throws FileNotFoundException {
        archivo = new RandomAccessFile("meta", "rw");
        arbol = new Arbol_Archivo_IdLong("meta");
    }
    
    public Meta buscarMeta(int codigoB){
        return null;
    }
    
    public boolean crearMeta(Meta meta){
        return false;
    }
    
    public boolean eliminarMeta(int codigoB, String fecha){
        return false;
    }
    
    public boolean actualizarMeta(Meta meta){
        return false;
    }
}
