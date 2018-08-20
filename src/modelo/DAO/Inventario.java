/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import Estructura.Arbol_Archivo_IdLong;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import modelo.Producto;

/**
 *
 * @author PC02
 */
public class Inventario {
    
    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;
    ArrayList<Producto> inventario;
    
    public Inventario() throws FileNotFoundException {
        archivo = new RandomAccessFile("inventario", "rw");
        arbol = new Arbol_Archivo_IdLong("inventario");
        inventario = new ArrayList();
    }
    
    public Producto buscarProd(int codigoB){
        return null;
    }
    
    public boolean crearProd(Producto prod){
        return false;
    }
    
    public boolean eliminarProd(int codigoB){
        return false;
    }
    
    public boolean actualizarProd(Producto prod){
        return false;
    }

    public ArrayList<Producto> getInventario() {
        return inventario;
    }
    
}
