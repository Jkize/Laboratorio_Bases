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
import java.util.ArrayList;
import modelo.Producto;

/**
 *
 * @author PC02
 */
public class DAO_Inventario implements DAO<Producto> {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Inventario() throws FileNotFoundException {
        archivo = new RandomAccessFile("inventario", "rw");
        arbol = new Arbol_Archivo_IdLong("inventario");
    }

    @Override
    public boolean crear(Producto prod) throws FileNotFoundException, IOException {
        archivo.seek(archivo.length());
        if (arbol.añadir(prod.getCodigoBarras(), (int) archivo.length())) {
            archivo.writeLong(prod.getCodigoBarras());
            archivo.writeUTF(prod.getNombreProducto());
            archivo.writeDouble(prod.getPrecio());
            archivo.writeInt(prod.getCantidad());
            return true;
        }
        return false;
    }

    @Override
    public Producto buscar(Object codigoB) throws FileNotFoundException, IOException {
        int pos = (int) arbol.getPosArchivo((long) codigoB);
        if (pos != -1) {
            Producto prod = new Producto();
            archivo.seek(pos);
            prod.setCodigoBarras(archivo.readLong());
            prod.setNombreProducto(archivo.readUTF());
            prod.setPrecio(archivo.readDouble());
            prod.setCantidad(archivo.readInt());
            return prod;
        }
        return null;

    }

    @Override
    public boolean actualizar(Producto prod) throws FileNotFoundException, IOException {
        int pos = (int) arbol.getPosArchivo(prod.getCodigoBarras());
        if (pos != -1) {
            archivo.seek(pos);
            archivo.writeLong(prod.getCodigoBarras());
            archivo.writeUTF(prod.getNombreProducto());
            archivo.writeDouble(prod.getPrecio());
            archivo.writeInt(prod.getCantidad());
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminar(Object codigoB) throws FileNotFoundException, IOException {
        if (archivo.length() != 0 && arbol.eliminar((long) codigoB)) {
            return true;
        }
        return false;
    }

    /**
     * Obtiene todo el inventario.
     *
     * @return ArrayList<Producto>: La lista de todos los productos que se
     * tienen registardos.
     * @throws IOException
     */
    public ArrayList<Producto> getCajas() throws FileNotFoundException, IOException {
        ArrayList<Producto> inventario = new ArrayList<>();
        RandomAccessFile archivoarbol = new RandomAccessFile("arbol" + "inventario", "rw");
        int n = (int) (archivoarbol.length() / (8 + 4 + 4 + 4));
        archivoarbol.seek(0);
        for (int i = 0; i < n; i++) {
            archivoarbol.skipBytes(8 + 8);
            int pos = archivoarbol.readInt();

            if (pos != -1) {
                archivo.seek(pos);
                Producto producto = new Producto();
                producto.setCodigoBarras(archivo.readLong());
                producto.setNombreProducto(archivo.readUTF());
                producto.setPrecio(archivo.readDouble());
                producto.setCantidad(archivo.readInt());
                inventario.add(producto);

            }

        }
        return inventario;
    }

}
