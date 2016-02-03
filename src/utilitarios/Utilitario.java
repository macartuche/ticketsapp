/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author macbookpro
 */
public class Utilitario {

    /**
     * Validar el numero de cedula
     *
     * @param identification
     * @return
     */
    public static boolean validarCedula(String identification) {
        boolean cedulaCorrecta = false;
        try {

            if (identification.length() == 10) {
                int tercerDigito = Integer.parseInt(identification.substring(2, 3));
                if (tercerDigito < 6) {

                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(identification.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (identification.length() - 1); i++) {
                        digito = Integer.parseInt(identification.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
        }
        System.out.println("CEDULA" + cedulaCorrecta);
        return cedulaCorrecta;
    }

    public static boolean valida(String x) {
        int suma = 0;
        if (x.length() == 9) {
            System.out.println("Ingrese su cedula de 10 digitos");
            return false;
        } else {
            int a[] = new int[x.length() / 2];
            int b[] = new int[(x.length() / 2)];
            int c = 0;
            int d = 1;
            for (int i = 0; i < x.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
                c = c + 2;
                if (i < (x.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
                    d = d + 2;
                }
            }

            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] * 2;
                if (a[i] > 9) {
                    a[i] = a[i] - 9;
                }
                suma = suma + a[i] + b[i];
            }
            int aux = suma / 10;
            int dec = (aux + 1) * 10;
            if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1)))) {
                return true;
            } else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
                return true;
            } else {
                return false;
            }

        }
    }

    /**
     *
     * @param campo
     * @return
     */
    public static boolean campoVacio(String campo) {
//        return (campo.trim().isEmpty()) ? true : false;
        return (campo.trim().isEmpty());
    }

    /**
     * Encriptar la clave del usuario
     *
     * @param clave
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String encriptarClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(clave.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {  
               hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean numeroMayorCero(String text) {

        try {
            double value = Double.parseDouble(text);
            if (value <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getValue(String key) {
        Locale local = Locale.getDefault();
        ResourceBundle resource = ResourceBundle.getBundle("values", local);
        return resource.getString(key);
    }

}
