/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

public class productos {
    private int idproducto;
    private String nombre;
    private String descripcion;
    private double preciocom;
    private double preciovent;
    private int stock;
    private int idcategoria;

    public productos() {
    }

    public productos(int idproducto, String nombre, String descripcion, double preciocom, double preciovent, int stock, int idcategoria) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preciocom = preciocom;
        this.preciovent = preciovent;
        this.stock = stock;
        this.idcategoria = idcategoria;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPreciocom() {
        return preciocom;
    }

    public void setPreciocom(double preciocom) {
        this.preciocom = preciocom;
    }

    public double getPreciovent() {
        return preciovent;
    }

    public void setPreciovent(double preciovent) {
        this.preciovent = preciovent;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }
    
    
}
