/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import protocolo.Peticiones;

/**
 *
 * @author Jose David
 */
public class Servidor{

    public Servidor(){
        try{
            serverSocket = new ServerSocket(Peticiones.PUERTO_POR_DEFECTO);
            usuarios = new ArrayList<>();
        }
        catch(IOException ex){
            
        }    
    }
    
    public void iniciarServidor(){
        
    }
    
    
    
    ServerSocket serverSocket;
    ArrayList<UsuarioServidor> usuarios;
    
    
}
