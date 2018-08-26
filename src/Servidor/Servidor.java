package Servidor;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket; 

public class Servidor {

    private ServerSocket server;
    private static final int PORT = 8000;

    private Servidor() {
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor example = new Servidor();
        example.servidorConexion();
    }

    private void servidorConexion() {
        System.out.println("Esperando Mensaje...");
        while (true) {
            try {
                Socket socket = server.accept();
                new MultiHilos(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
