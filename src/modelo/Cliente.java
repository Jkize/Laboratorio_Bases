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
public class Cliente  extends Persona{
    
    private String direccion;

    public Cliente() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = conver(direccion, 25);
    }
     
}
