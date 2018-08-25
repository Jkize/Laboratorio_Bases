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
import modelo.Persona;

/**
 *
 * @author LabingXEON
 */
public class DAO_Persona implements DAO<Persona> {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Persona() throws FileNotFoundException {
        archivo = new RandomAccessFile("persona", "rw");
        arbol = new Arbol_Archivo_IdLong("persona");
    }

    @Override
    public boolean crear(Persona persona) throws FileNotFoundException, IOException {
        archivo.seek(archivo.length());
        if (arbol.añadir(persona.getIdPersona(), (int) archivo.length())) {
            archivo.writeLong(persona.getIdPersona());
            archivo.writeUTF(persona.getNombre());
            return true;
        }
        return false;
    }

    @Override
    public Persona buscar(Object id) throws FileNotFoundException, IOException {

        int pos = (int) arbol.getPosArchivo((long) id);
        if (pos != -1) {
            archivo.seek(pos);
            Persona persona = new Persona();
            persona.setIdPersona(archivo.readLong());
            persona.setNombre(archivo.readUTF());
            return persona;
        }
        return null;

    }

    @Override
    public boolean actualizar(Persona persona) throws FileNotFoundException, IOException {

        int pos = (int) arbol.getPosArchivo(persona.getIdPersona());
        if (pos != -1) {
            archivo.seek(pos);
            archivo.writeLong(persona.getIdPersona());
            archivo.writeUTF(persona.getNombre());
            return true;
        }

        return false;

    }

    @Override
    public boolean eliminar(Object id) throws FileNotFoundException, IOException {
        if (archivo.length() != 0 && arbol.eliminar((long)id)) {
            return true;
        }
        return false;
        
    }

}
