/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import chat.entidades.Modelo;
import chat.entidades.Usuario;
import chat.entidades.dao.UsuarioDAO;
import java.time.LocalDate;

/**
 *
 * @author Jose David
 */
public class Chat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Usuario marvin = new Usuario("marv02", "Marvin Aguilar", "1234", LocalDate.now());
        Modelo modelo = new Modelo();
        //modelo.agregar(marvin);
        Usuario usuario2 = modelo.recuperar("Jose");
        System.out.println(usuario2);
    }
    
}
