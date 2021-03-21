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
public class Chilaquiles extends Platillo{
    private String proteina;
    private String salsa;

    public Chilaquiles(String nombre, double precio, int numMesa) {
        super(nombre, precio, numMesa);
    }


    public Chilaquiles(String proteina, String salsa, String nombre, double precio, int numMesa) {
        super(nombre, precio, numMesa);
        this.proteina = proteina;
        this.salsa = salsa;
    }

    public String getProteina() {
        return proteina;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }

    public String getSalsa() {
        return salsa;
    }

    public void setSalsa(String Salsa) {
        this.salsa = Salsa;
    }
    
}
