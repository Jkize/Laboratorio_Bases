/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Jkize .
 */
public class Cliente extends Persona {

    private String direccion;

    public Cliente() {
    }

    /**
     * getDireccion.
     *
     * @return direccion.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * setDireccion.
     *
     * @param direccion .
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
