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
public class DAO_Empleado {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Empleado() throws FileNotFoundException {
        archivo = new RandomAccessFile("empleado", "rw");
        arbol = new Arbol_Archivo_IdLong("empleado");
    }

    /**
     * crea un nuevo empleado.
     *
     *
     * @param empleado.
     * @return True: añadido correctamente, False: empleado ya estaba
     * registrado.
     * @throws IOException .
     */
    public boolean crearEmpleado(Empleado empleado) throws IOException {
        archivo.seek(archivo.length());
        if (arbol.añadir(empleado.getIdPersona(), (int) archivo.length())) {
            archivo.writeLong(empleado.getIdPersona());
            archivo.writeUTF(empleado.getNombre());
            archivo.writeUTF(empleado.getContrasena());
            archivo.writeUTF(empleado.getCargo());
            archivo.writeUTF(empleado.getIdcaja());
            return true;
        }
        return false;
    }

    /**
     * Obtener empleado.
     *
     * @param id empleado.
     * @return empleado: si se encontro con el ID, de lo contrario retornará una
     * excepción.
     * @throws IOException .
     */
    public Empleado buscarEmpleado(long id) throws IOException {
        int pos = (int) arbol.getPosArchivo(id);
        Empleado empleado = new Empleado();
        archivo.seek(pos);
        empleado.setIdPersona(archivo.readLong());
        empleado.setNombre(archivo.readUTF());
        empleado.setContrasena(archivo.readUTF());
        empleado.setCargo(archivo.readUTF());
        empleado.setIdcaja(archivo.readUTF());
        return empleado;
    }

    /**
     * Actualizar datos del empleado.
     *
     * @param empleado.
     * @return True si se actualizaron correctamente, False: no existe el ID.
     * @throws IOException .
     */
    public boolean actualizarEmpleado(Empleado empleado) throws IOException {

        try {
            int pos = (int) arbol.getPosArchivo(empleado.getIdPersona());
            archivo.seek(pos);
            archivo.writeLong(empleado.getIdPersona());
            archivo.writeUTF(empleado.getNombre());
            archivo.writeUTF(empleado.getContrasena());
            archivo.writeUTF(empleado.getCargo());
            archivo.writeUTF(empleado.getIdcaja());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina el empleado .
     *
     * @param id empleado.
     * @return true: elimanado correctamente, false: no se encontró.
     * @throws IOException .
     */
    public boolean eliminarEmpleado(long id) throws IOException {

        if (arbol.eliminar(id) && archivo.length() != 0) {
            return true;
        }
        return false;
    }

    public boolean usuarioValido(long id, String contraseña) throws IOException {
        int n = (int) arbol.getPosArchivo(id);
        if (n != -1) {
            archivo.seek(n + 50);
            if (contraseña.equals(archivo.readUTF())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Esta o no esta registrado.
     *
     * @param id identificacion.
     * @return true, false.
     * @throws IOException .
     */
    public boolean isEmpleado(long id) throws IOException {
        int n = (int) arbol.getPosArchivo(id);
        if (n != -1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        DAO_Empleado daoem = new DAO_Empleado();
        Empleado em = new Empleado();
        em.setIdPersona(1234);
        em.setNombre("jorge");
        em.setContrasena("123a");
        em.setCargo("evs");
        em.setIdcaja("5fs");
        Empleado sd = new Empleado();

        sd.setContrasena("123a");
        sd.setIdPersona(1234);
        if (daoem.usuarioValido(sd.getIdPersona(), sd.getContrasena())) {
            System.out.println("Exlent si esta");
        }

    }

}
