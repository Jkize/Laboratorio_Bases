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
public class DAO_Supermercado implements DAO<Supermercado> {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdString arbol;

    public DAO_Supermercado() throws FileNotFoundException {
        archivo = new RandomAccessFile("supermercado", "rw");
        arbol = new Arbol_Archivo_IdString("supermercado");
    }
 
    @Override
    public boolean crear(Supermercado sup) throws FileNotFoundException, IOException {
        archivo.seek(archivo.length());
        if (arbol.añadir(sup.getIdSM(), (int) archivo.length())) {
            archivo.writeUTF(sup.getIdSM());
            archivo.writeUTF(sup.getNombreSM());
            archivo.writeUTF(sup.getDireccionSM());
            return true;
        }
        return false;
    }

    @Override
    public Supermercado buscar(Object id) throws FileNotFoundException, IOException {
        int pos = (int) arbol.getPosArchivo((String) id);
        if (pos != -1) {
            Supermercado sm = new Supermercado();
            archivo.seek(pos);
            sm.setIdSM(archivo.readUTF());
            sm.setNombreSM(archivo.readUTF());
            sm.setDireccionSM(archivo.readUTF());
            return sm;
        }
        return null;

    }

    @Override
    public boolean actualizar(Supermercado sup) throws FileNotFoundException, IOException {

        int pos = (int) arbol.getPosArchivo(sup.getIdSM());
        if (pos != -1) {
            archivo.writeUTF(sup.getIdSM());
            archivo.writeUTF(sup.getNombreSM());
            archivo.writeUTF(sup.getDireccionSM());
            return true;
        }
        return false;

    }

    @Override
    public boolean eliminar(Object id) throws FileNotFoundException, IOException {
        if (archivo.length() != 0 && arbol.eliminar((String) id)) {
            return true;
        }
        return false;
    }

}
