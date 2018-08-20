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
    
    public boolean añadir(Venta venta) throws IOException {
        archivo.seek(archivo.length());
        if (arbol.añadir(venta.getIdVenta(),(int) archivo.length())) {
            archivo.writeUTF(venta.getIdVenta());
            archivo.writeLong(venta.getIdvendedor());
            archivo.writeLong(venta.getIdcliente());            
            archivo.writeUTF(sdf.format(venta.getDate()));
            archivo.writeInt(venta.getMonto());
            return true;
        }
        return false;
    }
    
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
    public static void main(String[] args) throws ParseException {
        Date d;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        d = sdf.parse("1-2-1998");
        String h = sdf.format(d);
        System.out.println(h);
    }
    
}
