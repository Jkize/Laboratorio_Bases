/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import Estructura.Arbol_Archivo_IdString;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import modelo.Detallado;

/**
 *
 * @author PC02
 */
public class DAO_Detallado {
    
    private RandomAccessFile archivo;
    private Arbol_Archivo_IdString arbol;
    
    public DAO_Detallado() throws FileNotFoundException {
        archivo = new RandomAccessFile("detallado", "rw");
        arbol = new Arbol_Archivo_IdString("detallado");
    }
    
    public Detallado buscarDetallado(int idVenta){
        return null;
    }
    
    public boolean crearDetallado(Detallado detallado){
        return false;
    }
    
}
