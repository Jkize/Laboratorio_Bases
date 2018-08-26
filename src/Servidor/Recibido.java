package Servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author Jhoan Saavedra
 */
public class Recibido implements Serializable{
    private String id;
    private String id2;
    private Object ob;

    public Recibido() {
    }

    public Recibido(String id, String id2, Object ob) {
        this.id = id;
        this.id2 = id2;
        this.ob = ob;
    }
     
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public Object getOb() {
        return ob;
    }

    public void setOb(Object ob) {
        this.ob = ob;
    }

    @Override
    public String toString() {
        return "Recibido{" + "id=" + id + ", id2=" + id2 + ", ob=" + ob + '}';
    }
    
    
}
