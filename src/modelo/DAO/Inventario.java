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
import java.util.ArrayList;
import modelo.Producto;

/**
 *
 * @author PC02
 */
public class Inventario {
    
    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;
    
    public Inventario() throws FileNotFoundException {
        archivo = new RandomAccessFile("inventario", "rw");
        arbol = new Arbol_Archivo_IdLong("inventario");
    }
    
    /**
     * Buscar un producto.
     * 
     * @param codigoB int.
     * @return Producto: Retorna un objeto tipo Producto en caso de encontrarlo,
     * en caso contrario retorna un exception.
     * @throws IOException 
     */
    public Producto buscarProd(int codigoB) throws IOException{
        int pos = (int) arbol.getPosArchivo(codigoB);
        Producto prod = new Producto();
        archivo.seek(pos);
        prod.setCodigoBarras(archivo.readLong());
        prod.setNombreProducto(archivo.readUTF());
        prod.setPrecio(archivo.readDouble());
        prod.setCantidad(archivo.readInt());
        return prod;
    }
    
    /**
     * Crea un producto nuevo.
     * 
     * @param prod Producto.
     * @return boolean: True en caso de crearlo correctamente, false en caso
     * contrarío.
     * @throws IOException 
     */
    public boolean crearProd(Producto prod) throws IOException{
        archivo.seek(archivo.length());
        if(arbol.añadir(prod.getCodigoBarras(), (int)archivo.length())){
            archivo.writeLong(prod.getCodigoBarras());
            archivo.writeUTF(prod.getNombreProducto());
            archivo.writeDouble(prod.getPrecio());
            archivo.writeInt(prod.getCantidad());
            return true;
        }
        return false;
    }
    
    /**
     * Elimina un producto determinado.
     * 
     * @param codigoB int.
     * @return boolean: True en caso de eliminarlo exitosamente, false en caso
     * contrarío.
     * @throws IOException 
     */
    public boolean eliminarProd(int codigoB)throws IOException{
        if (arbol.eliminar(codigoB) && archivo.length() != 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Actualiza un producto deseado.
     * 
     * @param prod Producto.
     * @return boolean: True en caso de actualizarlo correctamente, false en
     * caso contrarío.
     * @throws IOException 
     */
    public boolean actualizarProd(Producto prod) throws IOException{
        int pos = (int) arbol.getPosArchivo(prod.getCodigoBarras());
        archivo.seek(pos);
        archivo.writeLong(prod.getCodigoBarras());
        archivo.writeUTF(prod.getNombreProducto());
        archivo.writeDouble(prod.getPrecio());
        archivo.writeInt(prod.getCantidad());
        return true;
    }
    
    /**
     * Obtiene todo el inventario.
     * 
     * @return ArrayList<Producto>: La lista de todos los productos que se
     * tienen registardos.
     * @throws IOException 
     */
    public ArrayList<Producto> getInventario() throws IOException {
        ArrayList<Producto> inventario = new ArrayList();
        while(true){
            Producto prod = new Producto();
            prod.setCodigoBarras(archivo.readLong());
            prod.setNombreProducto(archivo.readUTF());
            prod.setPrecio(archivo.readDouble());
            prod.setCantidad(archivo.readInt());
            inventario.add(prod);
            if(archivo.getFilePointer() == archivo.length())
                break;
        }
        return inventario;
    }
    
}
