/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import Estructura.Arbol_Archivo_IdLong;
import Estructura.Arbol_Archivo_IdString;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import modelo.Caja;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Caja {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdString arbol;

    public DAO_Caja() throws FileNotFoundException {
        archivo = new RandomAccessFile("caja", "rw");
        arbol = new Arbol_Archivo_IdString("caja");
    }

    /**
     * crea una nueva caja.
     *
     *
     * @param caja.
     * @return True: añadido correctamente, False: empleado ya estaba
     * registrado.
     * @throws IOException .
     */
    public boolean crearCaja(Caja caja) throws IOException {

        //PRIMORDIAL LA LINEA DE ABAJO, INDICA QUE DEBE REGISTRAR AL FINAL DEL ARCHIVO CAJA.
        archivo.seek(archivo.length());
        if (arbol.añadir(caja.getIdCaja(), (int) archivo.length())) {
            archivo.writeUTF(caja.getIdCaja());
            archivo.writeDouble(caja.getMontoActual());
            archivo.writeUTF(caja.getIdSuperMercado());
            return true;
        }
        return false;

    }

    /**
     * Obtener caja.
     *
     * @param id caja.
     * @return caja: si se encontro con el ID, de lo contrario retornará una
     * excepción.
     * @throws IOException e.
     */
    public Caja buscarCaja(String id) throws IOException {
        int pos = (int) arbol.getPosArchivo(id);
        Caja caja = new Caja();
        archivo.seek(pos);
        caja.setIdCaja(archivo.readUTF());
        caja.setMontoActual(archivo.readDouble());
        caja.setIdSuperMercado(archivo.readUTF());
        return caja;

    }

    /**
     * Actualizar datos de la caja.
     *
     * @param caja.
     * @return True si se actualizaron correctamente, False: no existe el ID.
     * @throws IOException .
     */
    public boolean actualizarCaja(Caja caja) {
        try {
            int pos = (int) arbol.getPosArchivo(caja.getIdCaja());
            archivo.seek(pos);
            archivo.writeUTF(caja.getIdCaja());
            archivo.writeDouble(caja.getMontoActual());
            archivo.writeUTF(caja.getIdSuperMercado());

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Elimina la caja .
     *
     * @param id venta.
     * @return true: elimanado correctamente, false: no se encontró.
     * @throws IOException .
     */
    public boolean eliminarCaja(String id) throws IOException {
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
    public boolean isCaja(String id) throws IOException {
        int n = (int) arbol.getPosArchivo(id);
        if (n != -1) {
            return true;
        }
        return false;
    }

    public ArrayList<Caja> getCajas() throws FileNotFoundException, IOException {
        ArrayList<Caja> caja = new ArrayList<>();
        RandomAccessFile archivoarbol = new RandomAccessFile("arbolcaja", "rw");
        long n = archivoarbol.length() / (7 + 8 + 5);
        archivoarbol.seek(0);
        for (int i = 0; i < n; i++) {
            archivoarbol.skipBytes(7 + 8);
            int pos = archivoarbol.readInt();

            if (pos != -1) {
                archivo.seek(pos);
                Caja cajita = new Caja();
                cajita.setIdCaja(archivo.readUTF());
                cajita.setMontoActual(archivo.readDouble());
                cajita.setIdSuperMercado(archivo.readUTF());
                caja.add(cajita);
            }

        }
        return caja;
    }

}
