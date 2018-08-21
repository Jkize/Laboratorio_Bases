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
public class Empleado extends Persona{
    private String contrasena;
    private String cargo;
    private String idcaja;

    public Empleado() {
    }

    /**
     * getContrasena()
     * @return contaseña.
     */
    public String getContrasena() {
        return contrasena;
    }
    
    /**
     * 
     * @param contrasena .
     */

    public void setContrasena(String contrasena) {
         this.contrasena=conver(contrasena, 7);
     
    }

    /**
     * 
     * @return .
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * 
     * @param cargo .
     */
    public void setCargo(String cargo) {
        this.cargo = conver(cargo, 2);
    }

    /**
     * 
     * @return .
     */
    public String getIdcaja() {
        return idcaja;
    }

    /**
     * 
     * @param idcaja  .
     */
    public void setIdcaja(String idcaja) {
        this.idcaja =conver(idcaja,5);
    }
    
    
}
