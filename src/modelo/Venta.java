/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Jhoan Saavedra
 */
public class Venta {

    private String idVenta;
    private long idvendedor;
    private long idcliente;
    private Date date;
    private int monto;

    public Venta() {
    }

    /**
     *
     * @return .
     */
    public String getIdVenta() {
        return idVenta;
    }

    /**
     *
     * @param idVenta .
     */
    public void setIdVenta(String idVenta) {
        this.idVenta = conver(idVenta, 5);
    }

    /**
     *
     * @return .
     */
    public long getIdvendedor() {
        return idvendedor;
    }

    /**
     *
     * @param idvendedor .
     */
    public void setIdvendedor(long idvendedor) {
        this.idvendedor = idvendedor;
    }

    /**
     *
     * @return .
     */
    public long getIdcliente() {
        return idcliente;
    }

    /**
     *
     * @param idcliente .
     */
    public void setIdcliente(long idcliente) {
        this.idcliente = idcliente;
    }

    /**
     *
     * @return .
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date .
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return .
     */
    public int getMonto() {
        return monto;
    }

    /**
     *
     * @param mondo_venta .
     */
    public void setMonto(int mondo_venta) {
        this.monto = mondo_venta;
    }

    /**
     * Devuelve un tamaño predeterminado.
     *
     * @param string.
     * @param n tamño.
     * @return El string con el tamaño específico.
     */
    private String conver(String string, int n) {
        if (string.length() >= n) {
            return (String) string.subSequence(0, n);
        } else {
            String vas = "";
            for (int i = 0; i < n - string.length(); i++) {
                vas += " ";
            }
            return string + vas;
        }
    }

}
