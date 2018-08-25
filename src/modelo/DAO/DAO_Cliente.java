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
import modelo.Cliente;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Cliente implements DAO<Cliente> {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Cliente() throws FileNotFoundException {
        archivo = new RandomAccessFile("cliente", "rw");
        arbol = new Arbol_Archivo_IdLong("cliente");
    }

    @Override
    public boolean crear(Cliente cliente) throws FileNotFoundException, IOException {
        //PRIMORDIAL LA LINEA DE ABAJO, INDICA QUE DEBE REGISTRAR AL FINAL DEL ARCHIVO CLIENTE.
        archivo.seek(archivo.length());
        if (arbol.añadir(cliente.getIdPersona(), (int) archivo.length())) {
            archivo.writeLong(cliente.getIdPersona());
            archivo.writeUTF(cliente.getDireccion());
            return true;
        }
        return false;
    }

    @Override
    public Cliente buscar(Object id) throws FileNotFoundException, IOException {
        int pos = (int) arbol.getPosArchivo((long) id);
        if (pos != -1) {
            Cliente cliente = new Cliente();
            archivo.seek(pos);
            cliente.setIdPersona(archivo.readLong());
            cliente.setNombre(archivo.readUTF());
            cliente.setDireccion(archivo.readUTF());
            return cliente;
        }

        return null;
    }

    @Override
    public boolean actualizar(Cliente cliente) throws FileNotFoundException, IOException {

        int pos = (int) arbol.getPosArchivo(cliente.getIdPersona());
        if (pos != -1) {
            archivo.seek(pos);
            archivo.writeLong(cliente.getIdPersona());
            archivo.writeUTF(cliente.getNombre());
            archivo.writeUTF(cliente.getDireccion());

            return true;
        }

        return false;

    }

    @Override
    public boolean eliminar(Object id) throws FileNotFoundException, IOException {
        if (archivo.length() != 0 && arbol.eliminar((long) id)) {
            return true;
        }
        return false;
    }

}
