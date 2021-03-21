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
    private String nombre;
    private double precio;
    private int numMesa;

    public Platillo(String nombre, double precio, int numMesa) {
        this.nombre = nombre;
        this.precio = precio;
        this.numMesa = numMesa;
    }
    
    
    public int getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
