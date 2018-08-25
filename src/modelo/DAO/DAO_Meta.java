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
import java.util.Date;
import modelo.Meta;

/**
 *
 * @author PC02
 */
public class DAO_Meta implements DAO<Meta> {

    private RandomAccessFile archivo;
    private Arbol_Archivo_IdLong arbol;

    public DAO_Meta() throws FileNotFoundException {
        archivo = new RandomAccessFile("meta", "rw");
        arbol = new Arbol_Archivo_IdLong("meta");
    }

    @Override
    public boolean crear(Meta meta) throws FileNotFoundException, IOException {

        archivo.seek(archivo.length());
        if (arbol.añadir(meta.getCodigoBarras(), (int) archivo.length())) {
            archivo.writeLong(meta.getCodigoBarras());
            archivo.writeUTF(meta.getFechaMeta());
            archivo.writeInt(meta.getCantMeta());
            return true;
        }
        return false;

    }

    @Override
    public Meta buscar(Object codigoB) throws FileNotFoundException, IOException {
        int pos = (int) arbol.getPosArchivo((long) codigoB);

        if (pos != -1) {
            Meta meta = new Meta();
            archivo.seek(pos);
            meta.setCodigoBarras(archivo.readLong());
            meta.setFechaMeta(archivo.readUTF());
            meta.setCantMeta(archivo.readInt());
            return meta;
        }
        return null;

    }

    @Override
    public boolean actualizar(Meta meta) throws FileNotFoundException, IOException {
        int pos = (int) arbol.getPosArchivo(meta.getCodigoBarras());
        if (pos != -1) {
            archivo.seek(pos);
            archivo.writeLong(meta.getCodigoBarras());
            archivo.writeUTF(meta.getFechaMeta());
            archivo.writeInt(meta.getCantMeta());
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

    public ArrayList<Meta> getMetas(String mes_año) throws FileNotFoundException, IOException {
        ArrayList<Meta> metas = new ArrayList<>();
        RandomAccessFile archivoarbol = new RandomAccessFile("arbolmeta", "rw");
        int n = (int) (archivoarbol.length() / (8 + 4 + 4 + 4));
        archivoarbol.seek(0);
        for (int i = 0; i < n; i++) {
            archivoarbol.skipBytes(8 + 4 + 4);
            int pos = archivoarbol.readInt();

            if (pos != -1) {
                archivo.seek(pos);
                Meta meta = new Meta();
                meta.setCodigoBarras(archivo.readLong());
                meta.setCantMeta(archivo.readInt());
                meta.setFechaMeta(archivo.readUTF());
                if (mes_año.equals(meta.getFechaMeta())) {
                    metas.add(meta);
                }
            }
        }

        return metas;
    }

}
