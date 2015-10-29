/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.ventas;

/**
 *
 * @author josimar
 */
public class Impuesto {

    private final String value;
    private final String label;

    public Impuesto(String id, String label) {
        this.value = id;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }

}
