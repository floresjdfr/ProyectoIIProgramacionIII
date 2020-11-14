/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatservidor;

import java.util.Scanner;
import chatprotocolo.Servidor;

public class ChatServidor {

    public ChatServidor() {
        servidorBase = new Servidor();
    }

    public void iniciar() {
        servidorBase.iniciar();
        System.out.println("Servidor iniciado..");

        try (Scanner entrada = new Scanner(System.in)) {
            boolean continuar = true;
            while (continuar) {
                System.out.println("Comando: ? ");
                String comando = entrada.nextLine();
                continuar = ejecutar(comando);
            }
        } catch (Exception exc) {
            System.err.printf("Error de consola: '%s'%n", exc.getMessage());
        }

        servidorBase.detener();
        System.out.println("Aplicación del servidor cerrada..");
    }

    public boolean ejecutar(String comando) {
        boolean resultado = true;
        String cmd = comando.toUpperCase();
        switch (cmd) {
            case "SALIR":
                resultado = false;
                break;
            case "INICIAR":
                servidorBase.iniciar();
                break;
            case "DETENER":
                servidorBase.detener();
                break;
            default:
                System.err.printf("Comando no reconocido: '%s'%n", cmd);
                break;
        }
        return resultado;
    }

    public static void main(String[] args) {
        try (Scanner archivoAyuda = new Scanner(ChatServidor.class.getResourceAsStream("help.txt"))) {
            String ayuda = archivoAyuda.useDelimiter("\\A").next();
            System.out.println(ayuda);
            System.out.println();
        }

        new ChatServidor().iniciar();
    }

    private final Servidor servidorBase;
}
