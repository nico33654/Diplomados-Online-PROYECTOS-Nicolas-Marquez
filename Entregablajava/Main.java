package Entregablajava;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void limpiarConsolaANSI() {
        System.out.print("\033[H\033[2J");
            System.out.flush();
    }
    public static void main(String[] args) {
        Main.limpiarConsolaANSI();
        Scanner sc = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("SGA-DO: SISTEMA DIPLOMADOSONLINE");
        System.out.println("==================================================");
        System.out.println("1. Registrar Alumno");
        System.out.println("2. Registrar Profesor");
        System.out.println("3. Registrar Notas a un Alumno");
        System.out.println("4. Deshacer Último Registro de Nota");
        System.out.println("5. Generar Cola de Certificados");
        System.out.println("6. Mostrar Reporte General");
        System.out.println("7. Salir");
        System.out.println("==================================================");
        System.out.print("Seleccione una opción (1-7): ");

        System.out.print("Ingrese la opcion: ");
        String opcion = sc.nextLine();

        ArrayList<Object> Lista_Alumnos = new ArrayList<>();
        ArrayList<Object> Lista_Profesores = new ArrayList<>();
        Lista_Alumnos.add(GestorArchivos.recibirAlumnostxt());
        Lista_Profesores.add(GestorArchivos.recibirProfesorestxt());

        while(opcion.equals("7")){
            switch (opcion){
                case "1":
                    Main.limpiarConsolaANSI();
                    System.out.print("Ingrese la cedula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese el correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Ingrese el programa academico: ");
                    String program = sc.nextLine();


                    if(program.toLowerCase().equals("curso")){
                        AlumnoCurso usuario = new AlumnoCurso(nombre, cedula, correo);
                        Lista_Alumnos.add(usuario);
                        GestorArchivos.actualizarAlumnosTxt(usuario, cedula);
                    }else if(program.toLowerCase().equals("diplomado")){
                        AlumnoDiplomado usuario = new AlumnoDiplomado(nombre, cedula, correo);
                        Lista_Alumnos.add(usuario);
                        GestorArchivos.actualizarAlumnosTxt(usuario, cedula);
                    }else{
                        AlumnoBootcamp usuario = new AlumnoBootcamp(nombre, cedula, correo);
                        Lista_Alumnos.add(usuario);
                        GestorArchivos.actualizarAlumnosTxt(usuario, cedula);
                    }
                    break;
                
                case "2":
                    Main.limpiarConsolaANSI();
                    System.out.print("Ingrese la cedula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese el correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Ingrese el programa academico: ");
                    String program = sc.nextLine();
                
            }
        }

    }
}
