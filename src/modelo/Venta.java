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

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public long getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(long idvendedor) {
        this.idvendedor = idvendedor;
    }

    public long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(long idcliente) {
        this.idcliente = idcliente;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int mondo_venta) {
        this.monto = mondo_venta;
    }
    
}
