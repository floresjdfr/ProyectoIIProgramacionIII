/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatusuario;

import chatprotocolo.Cliente;

/**
 *
 * @author Georges Alfaro S.
 * @version 1.2
 */
public class ChatUsuario {

    public static void main(String[] args) {
        ChatUsuario cliente = new ChatUsuario();
        cliente.iniciar();
    }

    public void iniciar() {
        clienteBase = new Cliente();
        clienteBase.iniciar();
    }

    private Cliente clienteBase;
}
