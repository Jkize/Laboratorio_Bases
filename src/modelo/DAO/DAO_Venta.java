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
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Venta;

/**
 *
 * @author Jhoan Saavedra
 */
public class DAO_Venta implements DAO<Venta> {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdString arbol;
    private SimpleDateFormat sdf;

    public DAO_Venta() throws FileNotFoundException {
        archivo = new RandomAccessFile("venta", "rw");
        arbol = new Arbol_Archivo_IdString("venta");
        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Override
    public boolean crear(Venta venta) throws FileNotFoundException, IOException {

        archivo.seek(archivo.length());
        if (arbol.añadir(venta.getIdVenta(), (int) archivo.length())) {
            archivo.writeUTF(venta.getIdVenta());
            archivo.writeLong(venta.getIdvendedor());
            archivo.writeLong(venta.getIdcliente());
            archivo.writeUTF(sdf.format(venta.getDate()));
            archivo.writeDouble(venta.getMonto());
            return true;
        }
        return false;

    }

    @Override
    public Venta buscar(Object id) throws FileNotFoundException, IOException {

        int pos = (int) arbol.getPosArchivo((String) id);
        if (pos != -1) {
            Venta venta = new Venta();
            archivo.seek(pos);
            venta.setIdVenta(archivo.readUTF());
            venta.setIdvendedor(archivo.readLong());
            venta.setIdcliente(archivo.readLong());
            try {
                venta.setDate(sdf.parse(archivo.readUTF()));
            } catch (ParseException ex) {
                Logger.getLogger(DAO_Venta.class.getName()).log(Level.SEVERE, null, ex);
            }
            venta.setMonto(archivo.readDouble());
            return venta;
        }
        return null;
    }

    @Override
    public boolean actualizar(Venta venta) throws FileNotFoundException, IOException {

        int pos = (int) arbol.getPosArchivo(venta.getIdVenta());
        if (pos != -1) {
            archivo.seek(pos);
            archivo.writeUTF(venta.getIdVenta());
            archivo.writeLong(venta.getIdvendedor());
            archivo.writeLong(venta.getIdcliente());
            archivo.writeUTF(sdf.format(venta.getDate()));
            archivo.writeDouble(venta.getMonto());
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

    //fecha MM/AA
    public ArrayList<Venta> getVentasVend(String fecha, long id_empleado) throws FileNotFoundException, IOException, ParseException {

        ArrayList<Venta> ventas = new ArrayList<>();

        RandomAccessFile archivoarbol = new RandomAccessFile("arbolventa", "rw");
        int n = (int) (archivoarbol.length() / (7 + 4 + 4 + 4));
        archivoarbol.seek(0);

        for (int i = 0; i < n; i++) {
            archivoarbol.skipBytes(7 + 4 + 4);
            int pos = archivoarbol.readInt();
            archivo.seek(pos);
            Venta venta = new Venta();
            venta.setIdVenta(archivo.readUTF());
            venta.setIdvendedor(archivo.readLong());
            venta.setIdcliente(archivo.readLong());
            String fecha2 = archivo.readUTF();
            venta.setDate(sdf.parse(fecha2));
            venta.setMonto(archivo.readDouble());

            if (venta.getIdvendedor() == id_empleado && fecha.equals(fecha2.substring(3))) {
                ventas.add(venta);

            }

        }
        return ventas;
    }

}
