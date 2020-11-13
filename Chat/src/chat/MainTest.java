/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import chat.entidades.Mensaje;
import chat.entidades.dao.MensajeDAO;
import java.time.LocalDate;

/**
 *
 * @author Jose David
 */
public class MainTest {
    public static void main(String[] args) {
        Mensaje m = new Mensaje(0, "joseflores", "marv02", "Hola Marvin", LocalDate.now(), true);
        MensajeDAO mDao = MensajeDAO.obtenerInstancia();
        mDao.agregar(m);
    }
}
