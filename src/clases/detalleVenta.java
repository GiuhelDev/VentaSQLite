/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

public class detalleVenta {
    private int iddetalle;
    private int idventad;
    private int idproductod;

    public detalleVenta() {
    }

    public detalleVenta(int iddetalle, int idventad, int idproductod) {
        this.iddetalle = iddetalle;
        this.idventad = idventad;
        this.idproductod = idproductod;
    }

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getIdventad() {
        return idventad;
    }

    public void setIdventad(int idventad) {
        this.idventad = idventad;
    }

    public int getIdproductod() {
        return idproductod;
    }

    public void setIdproductod(int idproductod) {
        this.idproductod = idproductod;
    }
    
    
}
