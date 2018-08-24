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
public class DAO_Persona implements DAO<Persona>{

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Persona() throws FileNotFoundException {        
        archivo = new RandomAccessFile("persona", "rw");
        arbol = new Arbol_Archivo_IdLong("persona");
    }
    
    
    
    @Override
    public boolean crear(Persona ob) throws FileNotFoundException, IOException { .
    }

    @Override
    public Persona buscar(Object id) throws FileNotFoundException, IOException { 
    }

    @Override
    public boolean actualizar(Persona ob) throws FileNotFoundException, IOException { 
    }

    @Override
    public boolean eliminar(Object id) throws FileNotFoundException, IOException { 
    }
    
}
