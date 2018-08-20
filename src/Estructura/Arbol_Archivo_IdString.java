/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructura;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.xml.transform.Source;

/**
 *
 * @author LabingXEON
 */
public class Arbol_Archivo_IdString  {

    private RandomAccessFile arbol; 
 
    public Arbol_Archivo_IdString(String archivo_dato) throws FileNotFoundException {
        arbol = new RandomAccessFile("arbol" + archivo_dato, "rw"); 

    } 

    /**
     *  Método añadir en el arbol.
     * @param id identifiación.
     * @return True si se añadio correctamento, de lo contrario false.
     * @throws IOException 
     */
    public boolean añadir (String id, int length) throws IOException {
        arbol.seek(0); 
        if (arbol.length() == 0) {
            arbol.writeUTF(id);            
            arbol.writeInt(-1);
            arbol.writeInt(-1);
            arbol.writeInt(0);
        } else {
            long pos_arbol = busqueda(id);
            if (pos_arbol == 0) {
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
     * Mètodo para buscar el "id" en el arbol binario
     * @param id identifiación.
     * @return Si se encontrò el id dentro del arbol retorna 0, Si no retorna la posición del izquierdo
     * o derecho.
     * @throws IOException 
     */
    private long busqueda(String id) throws IOException {
        String t = arbol.readUTF();
        int compare=id.compareTo(t);
        if (compare==0) {
            return 0;
        }
 
        if (compare<0) {
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
     * Método de busqueda que invoca el método privado serach(long id).
     * @param id identificacion.
     * @return Si se encuentra dentro del arbol retorna la posición en bytes del archivo "x"
     * donde se encuentra la información, Ejemplo: Persona tiene como atributos ID y Nombre,
     * entonces hay un archivo "arbolpersona y persona", al utilizar este metodo retornará la posición
     * en bytes donde se encuenta la ID en el archivo persona,
     * Al no encontrase el ID retorna-1.
     * 
     * @throws IOException 
     */
    public long getPosArchivo(String id) throws IOException{
        arbol.seek(0);
        return search(id);
    }
    
    /**
     *  Método de busqueda dentro del arbol.
     * @par am id Identifiación
     * @return Una posición en bytes si se encuentra, de lo contrario -1.
     * @throws IOException 
     */
    private long search(String id) throws IOException {
        String t = arbol.readUTF();
        int compare=id.compareTo(t); 
        if (compare==0) {
            arbol.skipBytes(8);
            return arbol.readInt();
        }
        if (compare<0) {
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
     *  Imprimir el arbol.
     * @throws IOException 
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
