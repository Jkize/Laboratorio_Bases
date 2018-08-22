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
public class Caja {

    private String idCaja;
    private double montoActual;
    private String idSuperMercado;

    public Caja() {
    }

    public String getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(String idCaja) {
        this.idCaja = conver(idCaja, 5);
    }

    public double getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(double montoActual) {
        this.montoActual = montoActual;
    }

    public String getIdSuperMercado() {
        return idSuperMercado;
    }

    public void setIdSuperMercado(String idSuperMercado) {
        this.idSuperMercado = conver(idCaja, 3);
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
