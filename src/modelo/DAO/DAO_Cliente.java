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
public class DAO_Cliente {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Cliente() throws FileNotFoundException {
        archivo = new RandomAccessFile("cliente", "rw");
        arbol = new Arbol_Archivo_IdLong("cliente");
    }

    /**
     * crea un nuevo cliente.     *
     * @param cliente.
     * @return True: añadido correctamente, False: cliente ya estaba registrado.
     * @throws IOException .
     */
    public boolean crearCliente(Cliente cliente) throws IOException {
        //PRIMORDIAL LA LINEA DE ABAJO, INDICA QUE DEBE REGISTRAR AL FINAL DEL ARCHIVO CLIENTE.
        archivo.seek(archivo.length());
        if (arbol.añadir(cliente.getIdPersona(), (int) archivo.length())) {
            archivo.writeLong(cliente.getIdPersona());
            archivo.writeUTF(cliente.getNombre());
            archivo.writeUTF(cliente.getDireccion());
            return true;
        }
        return false;
    }

    /**
     * Obtener cliente.
     *
     * @param id idetificacion.
     * @return Cliente: si se encontro con el ID, de lo contrario retornará una
     * excepción.
     * @throws IOException  .
     */
    public Cliente buscarCliente(long id) throws IOException {
        int pos = (int) arbol.getPosArchivo(id);
        Cliente cliente = new Cliente();
        archivo.seek(pos);
        cliente.setIdPersona(archivo.readLong());
        cliente.setNombre(archivo.readUTF());
        cliente.setDireccion(archivo.readUTF());
        return cliente;

    }

    /**
     * Actualizar datos del cliente.
     *
     * @param cliente.
     * @return True si se actualizaron correctamente, False: no existe el ID.
     * @throws IOException .
     */
    public boolean actualizarCliente(Cliente cliente) throws IOException {

        try {
            int pos = (int) arbol.getPosArchivo(cliente.getIdPersona());
            archivo.seek(pos);
            archivo.writeLong(cliente.getIdPersona());
            archivo.writeUTF(cliente.getNombre());
            archivo.writeUTF(cliente.getDireccion());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina el cliente .
     * @param id identificacion.
     * @return true: elimanado correctamente, false: no se encontró.
     * @throws IOException .
     */
    public boolean eliminarCliente(long id) throws IOException {

        if (arbol.eliminar(id) && archivo.length() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Esta o no esta registrado.
     * @param id identificacion.
     * @return true, false. 
     * @throws IOException  .
     */
    public boolean isCliente(long id) throws IOException {
        int n = (int) arbol.getPosArchivo(id);
        if (n != -1) {
            return true;
        }
        return false;
    }

}
