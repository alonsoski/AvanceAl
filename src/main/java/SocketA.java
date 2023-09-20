import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketA {
    String ip;
    int puierto;
    public SocketA(int puerto){
        this.puierto=puerto;
    }
    public SocketA(String id, int puerto){
        this.ip=id;
        this.puierto=puerto;
    }

    public String getId() {
        return ip;
    }public int getPuierto() {
        return puierto;
    }

    public void enviar(String mensaje){
        String servidorIP = this.ip; // Dirección IP o nombre de dominio del servidor
        int puerto = this.puierto; // Puerto en el que el servidor está escuchando

        try {
            // Crear un socket para conectarse al servidor
            Socket socket = new Socket(servidorIP, puerto);
            System.out.println("Conectado al servidor en " + servidorIP + ":" + puerto);

            // Aquí puedes establecer la lógica para comunicarte con el servidor a través del socket
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            // Enviar un mensaje al servidor
            out.println(mensaje);


            // Cerrar el socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String enviarYRecibir(String mensaje){
        String servidorIP = this.ip; // Dirección IP o nombre de dominio del servidor
        int puerto = this.puierto; // Puerto en el que el servidor está escuchando
        String retorno= "";
        try {
            // Crear un socket para conectarse al servidor
            Socket socket = new Socket(servidorIP, puerto);
            //System.out.println("Conectado al servidor en " + servidorIP + ":" + puerto);

            // Aquí puedes establecer la lógica para comunicarte con el servidor a través del socket
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            // Enviar un mensaje al servidor
            out.println(mensaje);


            retorno=""+in.readLine();
            // Cerrar el socket

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }
    public String[] escucha(){
        String[] retorno=new String[2];
        try {
            int puerto = this.puierto;
            // Crear un ServerSocket que escucha en el puerto especificado
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor esperando conexiones en el puerto " + puerto + "...");
            // Esperar a que un cliente se conecte
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

            // Aquí puedes establecer la lógica para comunicarte con el cliente a través del socket
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            // Leer el mensaje del cliente
            String mensajeCliente = in.readLine();
            retorno[1]=mensajeCliente;
            retorno[0]= (String.valueOf(clientSocket.getInetAddress()).substring(1));
            String[] peticion=mensajeCliente.split("/");
            System.out.println("Mensaje del cliente: " + mensajeCliente);

            tiposPeticiones(peticion,out);
            // Cerrar el socket del cliente
            clientSocket.close();
            out.close();
            in.close();
            // Cerrar el ServerSocket
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    private void tiposPeticiones(String[] peticion, PrintWriter out) {
        if (peticion[0].equals("IS")){
            System.out.println("fue una peticion IS");
            out.println(respuestaInicioSesion(peticion));
        }else if (peticion[0].equals("UE")){
            System.out.println("Fue una peticion UE");
            //System.out.println("la peticion en 1 es:"+peticion[1]);
            out.println(respuestUsuarioExiste(peticion[1]));

        }else if (peticion[0].equals("CU")){
            Servidor s = new Servidor();
            s.crearArchivoUsuarios(peticion[1],peticion[2]);
        }
    }

    private boolean respuestUsuarioExiste(String correo) {
        Servidor s = new Servidor();
        return s.usuarioExiste(correo);
    }

    private boolean respuestaInicioSesion(String[] peticion) {
        Servidor s = new Servidor();
        return s.inicioSesion(peticion[1],peticion[2]);
    }

}