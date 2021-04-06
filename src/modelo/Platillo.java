/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author caste
 */
public class Platillo {
    private String platillo;
    private String complementos;
    private String extras;
    private double precio;
    private int numMesa;
    private int cantidad;

    public Platillo(String platillo, int cantidad, double precio, int numMesa) {
        this.platillo = platillo;
        this.precio = precio;
        this.numMesa = numMesa;
        this.cantidad = cantidad;
    }
    
    public Platillo(String platillo, int cantidad, String complementos, double precio, int numMesa) {
        this.platillo = platillo;
        this.complementos = complementos;
        this.precio = precio;
        this.numMesa = numMesa;
        this.cantidad = cantidad;
    }

    public Platillo(String platillo, int cantidad, String complementos, String extras, double precio, int numMesa) {
        this.platillo = platillo;
        this.complementos = complementos;
        this.extras = extras;
        this.precio = precio;
        this.numMesa = numMesa;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPlatillo() {
        return platillo;
    }

    public void setPlatillo(String platillo) {
        this.platillo = platillo;
    }

    public String getComplementos() {
        return complementos;
    }

    public void setComplementos(String complementos) {
        this.complementos = complementos;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }
    
    
}