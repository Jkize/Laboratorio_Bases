/*
 *Estructura de arbol enlazada con archivo de acceso aleatorio para PK de tipo String.
 */
package Estructura;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.xml.transform.Source;

/**
 *
 * @author Jkize.
 */
public class Arbol_Archivo_IdString {

    private RandomAccessFile arbol;
    private boolean validar;

    public Arbol_Archivo_IdString(String archivo_dato) throws FileNotFoundException {
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
    public boolean añadir(String id, int length) throws IOException {
        arbol.seek(0);
        if (arbol.length() == 0) {
            arbol.writeUTF(id);
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
            arbol.writeUTF(id);
            arbol.writeInt(-1);
            arbol.writeInt(-1);
            arbol.writeInt((int) length);
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
    public long getPosArchivo(String id) throws IOException {
        arbol.seek(0);
        return search(id);
    }

    /**
     * Elimina el campo "Posición del archivo (osea el otro mai)".
     *
     * @param id identifiacion.
     * @return true sí se elimino de lo contrario false.
     * @throws IOException .
     */
    public boolean eliminar(String id) throws IOException {
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
    private long busqueda(String id) throws IOException {
        String t = arbol.readUTF();
        int compare = id.compareTo(t);
        if (compare == 0) {
            arbol.skipBytes(8);
            if (arbol.readInt() == -1) {
                arbol.seek(arbol.getFilePointer() - 4);
                setValidar(true);
            }
            return 0;
        }

        if (compare < 0) {
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
     * Caso especial: Cuando se elimina y se vuelve a registrar con la misma id.
     *
     * @param length tamaño del archivo tipo clase.
     * @throws IOException
     */
    private void añadirExcepcion(int length) throws IOException {
        arbol.writeInt(length);
    }

    /**
     * Método de busqueda dentro del arbol.
     *
     * @par am id Identifiación
     * @return Una posición en bytes si se encuentra, de lo contrario -1.
     * @throws IOException .
     */
    private long search(String id) throws IOException {
        String t = arbol.readUTF();
        int compare = id.compareTo(t);
        if (compare == 0) {
            arbol.skipBytes(8);
            return arbol.readInt();
        }
        if (compare < 0) {
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

    private boolean remove(String id) throws IOException {
        String t = arbol.readUTF();
        int compare = id.compareTo(t);
        if (compare == 0) {
            arbol.skipBytes(8);
            arbol.writeInt(-1);
            return true;
        }
        if (compare < 0) {
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
     * Imprimir el arbol.
     *
     * @throws IOException .
     */
    public void imprimir() throws IOException {
        arbol.seek(0);
        while (true) {
            System.out.println(arbol.readUTF() + " " + arbol.readInt() + " " + arbol.readInt() + " " + arbol.readInt());
            if (arbol.getFilePointer() == arbol.length()) {
                break;
            }
        }

    }

}
