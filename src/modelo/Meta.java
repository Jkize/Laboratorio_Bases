/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author PC02
 */
public class Meta {
    
    private long codigoBarras;
    private Date fechaMeta;
    private int cantMeta;

    public Meta() {
    }

    public long getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(long codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Date getFechaMeta() {
        return fechaMeta;
    }

    public void setFechaMeta(Date fechaMeta) {
        this.fechaMeta = fechaMeta;
    }

    public int getCantMeta() {
        return cantMeta;
    }

    public void setCantMeta(int cantMeta) {
        this.cantMeta = cantMeta;
    }
        
}
