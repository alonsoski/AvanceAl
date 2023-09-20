import java.io.File;
import java.util.Scanner;

public class Servidor extends Thread{
    public static void main(String[] args) {
        Servidor a =new Servidor();
        a.start();


    }
    public static boolean inicioSesion(String correo, String contrasena){
        boolean retorno1=false;
        boolean retorno2=false;
        File carpeta = new File("./Usuarios");
        String[] lista = carpeta.list();
        for (int i = 0; i < lista.length ; i++) {
            if (lista[i].equals(correo)){
                retorno1=true;
                retorno2=contrasnaCorrecta(correo,contrasena);
            }
        }
        return retorno1 && retorno2;
    }

    private static boolean contrasnaCorrecta(String correo, String contrasena) {
        File archivo = new File("./Usuarios/"+correo+"/"+contrasena+".txt");
        //System.out.println("./Usuarios/"+correo+"/"+contrasena+".txt");
        return archivo.exists();
    }
    public boolean usuarioExiste(String correo) {
        if (correo.equals(null)){
            return false;
        }else {
            File archivo = new File("./Usuarios/"+correo);
            return archivo.exists();
        }
    }
    public void crearArchivoUsuarios(String correo, String contra){

        crearCarpeta(correo);
        crearArchivoContrasena(correo,contra);
    }

    private void crearArchivoContrasena(String correo, String contra) {
        File carpeta = new File("./Usuarios/"+correo+"/"+contra+".txt");
        try {
            carpeta.createNewFile();
        }catch (Exception e){
            System.out.println("no se pudo crear");
        }
    }

    private void crearCarpeta(String correo) {
        File carpeta= new File("./Usuarios/"+correo);
        carpeta.mkdir();
    }

    @Override
    public void run() {
        while (true){
            SocketA r =new SocketA(2211);
            String[] a = r.escucha();
            System.out.println("ip del cliente:"+a[1]);
            System.out.println("mensaje del cliente:"+a[0]);
        }
    }


}
