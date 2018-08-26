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
public class Empleado extends Persona {

    private String contrasena;
    private String cargo;
    private String idcaja;
    private String idSupermercado;

    public Empleado() {
    }

    public Empleado(String contrasena, String cargo, String idcaja, String idSupermercado) {
        this.contrasena = contrasena;
        this.cargo = cargo;
        this.idcaja = idcaja;
        this.idSupermercado = idSupermercado;
    }

    /**
     * getContrasena()
     *
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
        this.contrasena = contrasena;

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
        this.cargo = cargo;
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
     * @param idcaja .
     */
    public void setIdcaja(String idcaja) {
        this.idcaja = idcaja;
    }

    public String getIdSupermercado() {
        return idSupermercado;
    }

    public void setIdSupermercado(String idSupermercado) {
        this.idSupermercado = idSupermercado;
    }

}
