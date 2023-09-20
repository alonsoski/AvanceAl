public class Main {

    public static void main(String[] args) {

        SocketA envio=new SocketA("192.168.1.7",2211);
        SocketA recibo=new SocketA(2211);

        envio.enviar("Hola como estas");
        //System.out.println(recibo.recibir());
    }
}
