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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import modelo.Venta;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Venta {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdString arbol;
    private SimpleDateFormat sdf;

    public DAO_Venta() throws FileNotFoundException {
        archivo = new RandomAccessFile("venta", "rw");
        arbol = new Arbol_Archivo_IdString("venta");
        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }

    /**
     * crea un nueva venta.
     *
     *
     * @param venta.
     * @return True: añadido correctamente, False: empleado ya estaba
     * registrado.
     * @throws IOException .
     */
    public boolean crearVenta(Venta venta) throws IOException {
        archivo.seek(archivo.length());
        if (arbol.añadir(venta.getIdVenta(), (int) archivo.length())) {
            archivo.writeUTF(venta.getIdVenta());
            archivo.writeLong(venta.getIdvendedor());
            archivo.writeLong(venta.getIdcliente());
            archivo.writeUTF(sdf.format(venta.getDate()));
            archivo.writeInt(venta.getMonto());
            return true;
        }
        return false;
    }

    /**
     * Obtener venta.
     *
     * @param id venta.
     * @return venta: si se encontro con el ID, de lo contrario retornará una
     * excepción.
     * @throws IOException  e.
     */
    public Venta buscarVenta(String id) throws IOException, ParseException {
        int pos = (int) arbol.getPosArchivo(id);
        Venta venta = new Venta();
        archivo.seek(pos);
        venta.setIdVenta(archivo.readUTF());
        venta.setIdvendedor(archivo.readLong());
        venta.setIdcliente(archivo.readLong());
        venta.setDate(sdf.parse(archivo.readUTF()));
        venta.setMonto(archivo.readInt());
        return venta;
    }

    /**
     * Actualizar datos de la venta.
     *
     * @param venta.
     * @return True si se actualizaron correctamente, False: no existe el ID.
     * @throws IOException .
     */
    public boolean actualizarVenta(Venta venta) throws IOException {

        try {
            int pos = (int) arbol.getPosArchivo(venta.getIdVenta());
            archivo.seek(pos);
            archivo.writeUTF(venta.getIdVenta());
            archivo.writeLong(venta.getIdvendedor());
            archivo.writeLong(venta.getIdcliente());
            archivo.writeUTF(sdf.format(venta.getDate()));
            archivo.writeInt(venta.getMonto());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina la Venta .
     *
     * @param id venta.
     * @return true: elimanado correctamente, false: no se encontró.
     * @throws IOException .
     */
    public boolean eliminarVenta(String id) throws IOException {

        if (arbol.eliminar(id) && archivo.length() != 0) {
            return true;
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
    public boolean isVenta(String id) throws IOException {
        int n = (int) arbol.getPosArchivo(id);
        if (n != -1) {
            return true;
        }
        return false;
    }

    //algunas pruebas con el atributo Date.
    /*public static void main(String[] args) throws ParseException {
        Date d;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        d = sdf.parse("1-2-1998");
        String h = sdf.format(d);
        System.out.println(h);
    }*/

}
