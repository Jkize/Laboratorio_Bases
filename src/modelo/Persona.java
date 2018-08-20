/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Jhoan Saavedra
 */
public class Persona {

    protected long idPersona;
    protected String nombre;

    public Persona() {
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre=conver(nombre, 40);
    }
    /**
     * Devuelve un tamaño predeterminado.
     * @param string.
     * @param n tamño.
     * @return El string con el tamaño específico.
     */
    protected String conver(String string, int n){
          if (string.length() >= n) {
            return (String) string.subSequence(0, 40);
        } else {
            String vas="";
            for(int i=0; i<40-string.length();i++){
                vas+=" ";
            }
           return string+vas;
        }
    }
     
}
