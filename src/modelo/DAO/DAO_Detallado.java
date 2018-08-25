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
import modelo.Detallado;
import modelo.Venta;

/**
 *
 * @author PC02
 */
public class DAO_Detallado {

    private RandomAccessFile archivo;
    private SimpleDateFormat sdf;

    public DAO_Detallado() throws FileNotFoundException {
        archivo = new RandomAccessFile("detallado", "rw");
        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }

    /**
     *
     * @param idVenta
     * @return
     * @throws IOException
     */
    public ArrayList<Detallado> buscarDetallado(String idVenta) throws IOException {
        archivo.seek(0);

        ArrayList<Detallado> listdetallado = new ArrayList<>();
        int n = (int) (archivo.length() / (7 + 8 + 4));
        for (int i = 0; i < n; i++) {
            String id = archivo.readUTF();
            if (id.equals(idVenta)) {
                long codb = archivo.readLong();
                int cantidad = archivo.readInt();
                Detallado detallado = new Detallado();
                detallado.setIdVenta(id);
                detallado.setCodigoBarras(codb);
                detallado.setCantidadProd(cantidad);
                listdetallado.add(detallado);
            } else {
                archivo.skipBytes(8 + 4);
            }
        }
        return listdetallado;
    }

    /**
     *
     * @param detallado
     * @throws IOException
     */
    public void crearDetallado(Detallado detallado) throws IOException {
        archivo.seek(archivo.length());
        archivo.writeUTF(detallado.getIdVenta());
        archivo.writeLong(detallado.getCodigoBarras());
        archivo.writeInt(detallado.getCantidadProd());

    }

    /**
     * .
     * @param codB .
     * @param fecha .
     * @return .
     */
    public ArrayList<Detallado> getDetallado(long codB, Date fecha) throws FileNotFoundException, IOException, ParseException {
        DAO_Venta daoventa = new DAO_Venta();
        ArrayList<Detallado> listdetallado = new ArrayList<>();
        int n = (int) (archivo.length() / (7 + 8 + 4));
        archivo.seek(0);
        for (int i = 0; i < n; i++) {
            String id = archivo.readUTF();
            long codb = archivo.readLong();
            int cantidad = archivo.readInt();
            if (codb == codB) {
                Venta venta = new Venta();
                venta = daoventa.buscar(id);
                if (venta.getDate().equals(fecha)) {
                    Detallado detallado = new Detallado();
                    detallado.setIdVenta(id);
                    detallado.setCodigoBarras(codb);
                    detallado.setCantidadProd(cantidad);
                    listdetallado.add(detallado);
                }

            }

        }
        return listdetallado;
    }
}
