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
    
    /**
     * Busca una meta especifica.
     * 
     * @param codigoB int.
     * @return Un objeto tipo Meta.
     * @throws IOException 
     */
    public Meta buscarMeta(int codigoB) throws IOException{
        int pos = (int) arbol.getPosArchivo(codigoB);
        Meta meta = new Meta();
        archivo.seek(pos);
        meta.setCodigoBarras(archivo.readLong());
        meta.setFechaMeta(archivo.readUTF());
        meta.setCantMeta(archivo.readInt());
        return meta;
    }
    
    /**
     * Crea una meta.
     * 
     * @param meta Meta.
     * @return Boolean: True en caso de crear la meta exitosamente, en caso
     * contrarío retornará false.
     * @throws IOException 
     */
    public boolean crearMeta(Meta meta) throws IOException{
        archivo.seek(archivo.length());
        if(arbol.añadir(meta.getCodigoBarras(), (int)archivo.length())){
            archivo.writeLong(meta.getCodigoBarras());
            archivo.writeUTF(meta.getFechaMeta());
            archivo.writeInt(meta.getCantMeta());
            return true;
        }
        return false;
    }
    
    /**
     * Elimina una meta ya creada.
     * 
     * @param codigoB int.
     * @param fecha String.
     * @return boolean: True en caso de eliminarlo exitosamente, false en caso
     * contrarío.
     * @throws IOException .
     */
    public boolean eliminarMeta(int codigoB, String fecha) throws IOException{
        if (arbol.eliminar(codigoB) && archivo.length() != 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Actualizar una meta.
     * 
     * @param meta Meta
     * @return boolean: True en caso de actualizar correctamente, false en caso
     * contrarío.
     * @throws IOException 
     */
    public boolean actualizarMeta(Meta meta) throws IOException{
        int pos = (int) arbol.getPosArchivo(meta.getCodigoBarras());
        archivo.seek(pos);
        archivo.writeLong(meta.getCodigoBarras());
        archivo.writeUTF(meta.getFechaMeta());
        archivo.writeInt(meta.getCantMeta());
        return true;
    }
    
}
