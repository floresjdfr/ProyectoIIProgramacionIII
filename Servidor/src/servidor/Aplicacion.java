/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;

/**
 *
 * @author Jose David
 */
public class Aplicacion {
    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        servidor.iniciarServidor();
    }
}
