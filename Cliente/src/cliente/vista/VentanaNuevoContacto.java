/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.vista;

import cliente.Controlador;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Jose David
 */
public class VentanaNuevoContacto extends JFrame{

    public VentanaNuevoContacto(String titulo, Controlador gestorPrincipal){
        super(titulo);
        this.gestorPrincipal = gestorPrincipal;
        configurar();
    }
    
    
    private JTextField campoTextoUsuario;
    private JButton btnAgregar;
    
    private final Controlador gestorPrincipal;
}
