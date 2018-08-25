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
import modelo.Empleado;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Empleado implements DAO<Empleado> {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Empleado() throws FileNotFoundException {
        archivo = new RandomAccessFile("empleado", "rw");
        arbol = new Arbol_Archivo_IdLong("empleado");
    }

    @Override
    public boolean crear(Empleado empleado) throws FileNotFoundException, IOException {
        archivo.seek(archivo.length());
        if (arbol.añadir(empleado.getIdPersona(), (int) archivo.length())) {
            archivo.writeLong(empleado.getIdPersona());
            archivo.writeUTF(empleado.getContrasena());
            archivo.writeUTF(empleado.getCargo());
            archivo.writeUTF(empleado.getIdcaja());
            return true;
        }
        return false;
    }

    @Override
    public Empleado buscar(Object id) throws FileNotFoundException, IOException {
        int pos = (int) arbol.getPosArchivo((long) id);
        if (pos != -1) {
            Empleado empleado = new Empleado();
            archivo.seek(pos);
            empleado.setIdPersona(archivo.readLong());
            empleado.setContrasena(archivo.readUTF());
            empleado.setCargo(archivo.readUTF());
            empleado.setIdcaja(archivo.readUTF());
            return empleado;
        }
        return null;
    }

    @Override
    public boolean actualizar(Empleado empleado) throws FileNotFoundException, IOException {

        int pos = (int) arbol.getPosArchivo(empleado.getIdPersona());

        if (pos != -1) {
            archivo.seek(pos);
            archivo.writeLong(empleado.getIdPersona());
            archivo.writeUTF(empleado.getContrasena());
            archivo.writeUTF(empleado.getCargo());
            archivo.writeUTF(empleado.getIdcaja());

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

    public boolean usuarioValido(long id, String contraseña) throws IOException {
        int n = (int) arbol.getPosArchivo(id);
        if (n != -1) {
            archivo.seek(n + 8);
            if (contraseña.equals(archivo.readUTF())) {
                return true;
            }
        }
        return false;
    }

    public boolean existe(long id) throws IOException {
        if (arbol.getPosArchivo(id) != -1) {
            return true;
        }
        return false;
    }
}
