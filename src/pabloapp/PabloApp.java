/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pabloapp;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitarios.Utilitario;

/**
 *
 * @author macbookpro
 */
public class PabloApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            String clave = Utilitario.encriptarClave("admin");
            System.out.println("clave >>> " + clave);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PabloApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PabloApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
