/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author giuse
 */
public class venta {
    private int idventa;
    private String numventa;
    private String date;
    private int idclientev;
    private double total;
    private int idusuariov;
    private String ruc;

    public venta() {
    }

    public venta(int idventa, String numventa, String date, int idclientev, double total, int idusuariov, String ruc) {
        this.idventa = idventa;
        this.numventa = numventa;
        this.date = date;
        this.idclientev = idclientev;
        this.total = total;
        this.idusuariov = idusuariov;
        this.ruc = ruc;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public String getNumventa() {
        return numventa;
    }

    public void setNumventa(String numventa) {
        this.numventa = numventa;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdclientev() {
        return idclientev;
    }

    public void setIdclientev(int idclientev) {
        this.idclientev = idclientev;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdusuariov() {
        return idusuariov;
    }

    public void setIdusuariov(int idusuariov) {
        this.idusuariov = idusuariov;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    
    
}
