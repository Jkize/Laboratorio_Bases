/*
 *Estructura de arbol enlazada con archivo de acceso aleatorio para PK de tipo Long.
 */
package Estructura;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author LabingXEON
 */
public class Arbol_Archivo_IdLong {

    private RandomAccessFile arbol;
    /**
     * atributo auxiliar para el la exepcion añadir.
     */
    private boolean validar;

    public Arbol_Archivo_IdLong(String archivo_dato) throws FileNotFoundException {
        arbol = new RandomAccessFile("arbol" + archivo_dato, "rw");
        this.validar = false;

    }

    /**
     * Devuelve si es o no es.
     *
     * @return true o false.
     */
    private boolean isValidar() {
        return validar;
    }

    /**
     * Remplaza el valor boolean.
     *
     * @param validar boolean.
     */
    private void setValidar(boolean validar) {
        this.validar = validar;
    }

    /**
     * Método añadir en el arbol, 4 campos -Id Tipo Long -Hijo izquierdo tipo
     * int -Hijo derecho tipo int -Posición del archivo (osea el otro mai).
     *
     * @param id identifiación.
     * @param length el tamaño del archivo tipo clase.
     * @return True si se añadio correctamento, de lo contrario false.
     * @throws IOException .
     */
    public boolean añadir(long id, int length) throws IOException {
        arbol.seek(0);
        if (arbol.length() == 0) {
            arbol.writeLong(id);
            arbol.writeInt(-1);
            arbol.writeInt(-1);
            arbol.writeInt(0);
        } else {
            long pos_arbol = busqueda(id);
            if (pos_arbol == 0) {

                if (isValidar()) {
                    añadirExcepcion(length);
                    setValidar(false);
                    return true;
                }

                return false;
            }
            arbol.seek(pos_arbol);
            arbol.writeInt((int) arbol.length());
            arbol.seek(arbol.length());
            arbol.writeLong(id);
            arbol.writeInt(-1);
            arbol.writeInt(-1);
            arbol.writeInt(length);
        }
        return true;
    }

    /**
     * Obteniene la posición del archivo de tipo clase.
     *
     * @param id identificacion.
     * @return Si lo encuentra el id: Posición en bytes sino -1 (ya que no se
     * encontro).
     *
     * @throws IOException .
     */
    public long getPosArchivo(long id) throws IOException {
        arbol.seek(0);
        return search(id);
    }

    /**
     * Elimina el campo "Posición del archivo (osea el otro mai)".
     *
     * @param id identifiacion.
     * @return true si se elimino de lo contrario false.
     * @throws IOException .
     */
    public boolean eliminar(long id) throws IOException {
        arbol.seek(0);
        if (remove(id)) {
            return true;
        }
        return false;
    }

    /**
     * Mètodo para buscar el "id" en el arbol binario
     *
     * @param id identifiación.
     * @return Si se encontrò el id dentro del arbol retorna 0, Si no retorna la
     * posición del izquierdo o derecho.
     * @throws IOException .
     */
    private long busqueda(long id) throws IOException {
        long t = arbol.readLong();
        if (id == t) {

            arbol.skipBytes(8);
            if (arbol.readInt() == -1) {
                arbol.seek(arbol.getFilePointer() - 4);
                setValidar(true);
            }

            return 0;
        }

        if (id < t) {
            long pos = arbol.getFilePointer();
            int dat = arbol.readInt();
            if (dat == -1) {
                return pos;
            } else {
                arbol.seek(dat);
            }
            return busqueda(id);
        } else {
            arbol.skipBytes(4);
            long pos = arbol.getFilePointer();
            int dat = arbol.readInt();

            if (dat == -1) {
                return pos;
            } else {
                arbol.seek(dat);
            }
            return busqueda(id);

        }

    }

    /**
     * Método de busqueda dentro del arbol.
     *
     * @par am id Identifiación
     * @return Una posición en bytes si se encuentra, de lo contrario -1.
     * @throws IOException .
     */
    private long search(long id) throws IOException {
        long t = arbol.readLong();
        if (id == t) {
            arbol.skipBytes(8);
            return arbol.readInt();
        }
        if (id < t) {
            long pos = arbol.getFilePointer();
            int dat = arbol.readInt();
            if (dat == -1) {
                return -1;
            } else {
                arbol.seek(dat);
            }
            return search(id);
        } else {
            arbol.skipBytes(4);
            long pos = arbol.getFilePointer();
            int dat = arbol.readInt();

            if (dat == -1) {
                return -1;
            } else {
                arbol.seek(dat);
            }
            return search(id);

        }
    }

    /**
     * Imprimir el arbol.
     *
     * @throws IOException .
     */
    public void imprimir() throws IOException {
        arbol.seek(0);
        while (true) {
            System.out.println(arbol.readLong() + " " + arbol.readInt() + " " + arbol.readInt() + " " + arbol.readInt());
            if (arbol.getFilePointer() == arbol.length()) {
                break;
            }
        }

    }

    /**
     * Eliminar
     * @param id .
     * @return Eliminacion correcta :true .
     * @throws IOException  .
     */
    private boolean remove(long id) throws IOException {
        long t = arbol.readLong();
        if (t == id) {
            arbol.skipBytes(8);
            arbol.writeInt(-1);
            return true;
        }
        if (id < t) {
            long pos = arbol.getFilePointer();
            int dat = arbol.readInt();
            if (dat == -1) {
                return false;
            } else {
                arbol.seek(dat);
            }
            return remove(id);
        } else {
            arbol.skipBytes(4);
            long pos = arbol.getFilePointer();
            int dat = arbol.readInt();

            if (dat == -1) {
                return false;
            } else {
                arbol.seek(dat);
            }
            return remove(id);
        }
    }

    /**
     * Caso especial: Cuando se elimina y se vuelve a registrar con la misma id.
     *
     * @param length tamaño del archivo tipo clase.
     * @throws IOException .
     */
    private void añadirExcepcion(int length) throws IOException {
        arbol.writeInt(length);
    }

}
