/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
     * Añadir, el archivo del cliente debe comenzar siempre en la última
     * posición dentro del archivo en bytes.
     *
     * @param cliente.
     * @return true si se añadio al archivo, de lo contrario false.
     * @throws IOException
     */
    public boolean añadir(Cliente cliente) throws IOException {
        //PRIMORDIAL LA LINEA DE ABAJO, INDICA QUE DEBE REGISTRAR AL FINAL DEL ARCHIVO CLIENTE.
        archivo.seek(archivo.length());
        if (arbol.añadir(cliente.getIdPersona())) {
            archivo.writeLong(cliente.getIdPersona());
            archivo.writeUTF(cliente.getNombre());
            archivo.writeUTF(cliente.getDireccion());
            return true;
        }
        return false;
    }

    /**
     * Método para obtener cliente.
     *
     * @param id
     * @return Cliente si se encontro con el ID, de lo contrario retornará una
     * excepción ya que no se encontró.
     * @throws IOException lanzará un error si no esta dentro de la posición.
     */
    public Cliente buscarCliente(long id) throws IOException {
        int pos = (int) arbol.buscar(id);
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
     * @param cliente
     * @return true si se actualizaron correctamente, false si no ya que no
     * existe el ID.
     * @throws IOException
     */
    public boolean actualizarCliente(Cliente cliente) throws IOException {

        try {
            int pos = (int) arbol.buscar(cliente.getIdPersona());
            archivo.seek(pos);
            archivo.writeLong(cliente.getIdPersona());
            archivo.writeUTF(cliente.getNombre());
            archivo.writeUTF(cliente.getDireccion());

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
}
