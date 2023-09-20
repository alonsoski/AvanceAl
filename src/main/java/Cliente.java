import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        lobby();

    }

    private static void lobby() {

        System.out.println("Bienvenido a UfroEats");
        System.out.println("que quieres hacer");
        int eleccion = 0;
        while (eleccion<1 || eleccion>3){
            System.out.println("1.-Iniciar Sesion");
            System.out.println("2.-Crear Usuario");
            System.out.println("3.-Salir");
            eleccion = eleccion(3);
        }
        switcHLobby(eleccion);
    }

    private static void switcHLobby(int eleccion) {
        switch (eleccion){
            case 1:
                inicioSesion();
                break;
            case 2:
                crearUsuario();
                break;
            case 3:
                System.out.println("ok, adios");
        }
    }

    private static void crearUsuario() {
        SocketA s = new SocketA("192.168.1.8",2211);
        String correo=ingresoCorreo();
        String contra=ingresoContra();
        String peticion="UE/"+correo;
        boolean correoValid=correoValido(correo,peticion);
        boolean contraValid=contraValida(contra);
        if (correoValid && contraValid){
            peticion="CU/"+correo+"/"+contra;
            s.enviar(peticion);
            System.out.println("Usuario creado");
        }else {
            if (!correoValid){
                System.out.println("correo invalido");
            }
            if (!contraValid){
                System.out.println("la contraseña es invalida");
            }
        }
    }

    private static boolean correoValido(String correo, String peticion){
        if (correo.equals("")){
            return false;
        }else{
            SocketA s = new SocketA("192.168.1.8",2211);
            String respuesta = s.enviarYRecibir(peticion);
            return !respuesta.equals("true");
        }



    }
    private static boolean contraValida(String contra) {
        if (contra.equals("")){
            return false;
        }else {
            return !(contra.length()<8);
        }

    }

    private static String ingresoContra() {
        Scanner t = new Scanner(System.in);
        System.out.println("ingrese su contraseña");
        System.out.println("largo min 8 caracteres");
        return t.nextLine();
    }

    private static String ingresoCorreo() {
        Scanner t = new Scanner(System.in);
        System.out.println("ingrese su correo institucional");
        String correo=t.nextLine();
        return correo;
    }

    private static void inicioSesion() {
        Scanner t = new Scanner(System.in);
        SocketA s = new SocketA("192.168.1.8",2211);
        System.out.println("Escriba su email");
        String correo=t.nextLine();
        System.out.println("Escriba su contraseña");
        String contra=t.nextLine();
        String peticion="UE/"+correo+"/"+contra;

        String respueta = s.enviarYRecibir(peticion);
        if (respueta.equals("true")){
            System.out.println("Has iniciado sesion");
        }else {
            System.out.println("correo o contraseña incorrectos");
            lobby();
        }
    }

    private static int eleccion(int cantidadOpciones) {
        Scanner t = new Scanner(System.in);
        int eleccion=0;
        try {
            eleccion=t.nextInt();
        }catch (Exception e){
            t.nextLine();
        }
        if (eleccion<1 || eleccion>cantidadOpciones){
            System.out.println("eso no se puede");
        }
        return eleccion;
    }
}
