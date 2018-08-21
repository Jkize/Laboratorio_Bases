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
    
    /**
     * Busca la información de un detallado.
     *
     *
     * @param idVenta String.
     * @return Detallado: Retorna un objeto tipo Detallado si lo encuenta con el
     * idVenta, en caso contrarío retorna una excepción.
     * @throws IOException .
    */
    public Detallado buscarDetallado(String idVenta) throws IOException{
        int pos = (int) arbol.getPosArchivo(idVenta);
        Detallado detallado = new Detallado();
        archivo.seek(pos);
        detallado.setIdVenta(archivo.readUTF());
        detallado.setCodigoBarras(archivo.readLong());
        detallado.setCantidadProd(archivo.readInt());
        return detallado;
    }
    
    /**
     * Obtener empleado.
     *
     * @param detallado Detallado.
     * @return bollean: Retornará un true si se encontró con el objeto tipo 
     * detallado, de lo contrario retornará un false.
     * @throws IOException .
     */
    public boolean crearDetallado(Detallado detallado) throws IOException{
        archivo.seek(archivo.length());
        if(arbol.añadir(detallado.getIdVenta(), (int)archivo.length())){
            archivo.writeUTF(detallado.getIdVenta());
            archivo.writeLong(detallado.getCodigoBarras());
            archivo.writeInt(detallado.getCantidadProd());
            return true;
        }
        return false;
    }
    
}
