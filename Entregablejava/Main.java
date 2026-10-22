package Entregablejava;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        GestorArchivos.limpiarConsola();
        Scanner sc = new Scanner(System.in);

        String opcion = "0";

        Stack <String> Historial_cedulas = new Stack<>();
        ArrayList<Alumno> Lista_Alumnos = new ArrayList<>();
        ArrayList<Profesor> Lista_Profesores = new ArrayList<>();
        Lista_Alumnos.addAll(GestorArchivos.recibir_Alumnostxt());
        Lista_Profesores.addAll(GestorArchivos.recibir_Profesorestxt());

        while(!opcion.equals("7")){

            System.out.println("==================================================");
            System.out.println("SGA-DO: SISTEMA DIPLOMADOSONLINE");
            System.out.println("==================================================");
            System.out.println("1. Registrar Alumno");
            System.out.println("2. Registrar Profesor");
            System.out.println("3. Registrar Notas a un Alumno");
            System.out.println("4. Deshacer ultimo Registro de Nota");
            System.out.println("5. Generar Cola de Certificados");
            System.out.println("6. Mostrar Reporte General");
            System.out.println("7. Salir");
            System.out.println("==================================================");
            System.out.print("Seleccione una opción (1-7): ");

            System.out.print("Ingrese la opcion: ");
            opcion = sc.nextLine();


            switch (opcion){
                case "1":
                    GestorArchivos.limpiarConsola();
                    System.out.print("Ingrese la cedula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese el correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Ingrese el programa academico: ");
                    String programa = sc.nextLine().toLowerCase();

                    Alumno Alumno_nuevo;

                    while(!programa.equals("curso") && !programa.equals("diplomado") && !programa.equals("bootcamp")){
                        GestorArchivos.limpiarConsola();
                        System.out.println("ERROR -- Opcion no valida \n\ncurso \ndiplomado\nbootcamp");
                        System.out.print("Ingrese el programa academico: ");
                        programa = sc.nextLine();
                    }
                        if (programa.equals("curso")) {
                            Alumno_nuevo = new AlumnoCurso(nombre, cedula, correo);
                        } else if (programa.equals("diplomado")) {
                            Alumno_nuevo = new AlumnoDiplomado(nombre, cedula, correo);
                        } else {
                            Alumno_nuevo = new AlumnoBootcamp(nombre, cedula, correo);
                        }

                    Lista_Alumnos.add(Alumno_nuevo);
                    GestorArchivos.Crear_Alumnos_txt(Alumno_nuevo);

                    GestorArchivos.limpiarConsola();
                    System.out.println("Alumno registrado con exito\n");
                    break;
                
                case "2":
                   GestorArchivos.limpiarConsola();
                    System.out.print("Ingrese la cedula: ");
                    cedula = sc.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    nombre = sc.nextLine();
                    System.out.print("Ingrese el correo: ");
                    correo = sc.nextLine();
                    System.out.print("Ingrese la especialidad academica: ");
                    String especialidad_academica = sc.nextLine();
                    System.out.print("Ingrese la Materia asignada: ");
                    String Materia_asignada = sc.nextLine();

                    Profesor usuario = new Profesor(nombre,cedula,correo,especialidad_academica,Materia_asignada);
                    Lista_Profesores.add(usuario);
                    GestorArchivos.Crear_Profesores_txt(usuario);
                    GestorArchivos.limpiarConsola();
                    System.out.print("Profesor registrado con exito\n\n");
                    break;
                
                case "3":
                    if(Lista_Alumnos.size() == 0){
                        GestorArchivos.limpiarConsola();
                        System.out.print("No hay alumnos registrados");
                    }
                    else{
                        GestorArchivos.limpiarConsola();
                        System.out.print("Alumnos registrados: ");
                        for(Alumno user : Lista_Alumnos){
                            System.out.print(user);
                        }
                        System.out.print("\nIngrese la cedula: ");
                        cedula = sc.nextLine();

                        if(GestorArchivos.revisar_cedula(cedula, Lista_Alumnos)){
                            for(Alumno user : Lista_Alumnos){
                                if(cedula.equals(user.cedula)){
                                    if(!(user.Lista_notas.size() >= 3)){
                                        System.out.print("Ingrese la nota: ");
                                        Double nota = sc.nextDouble();
                                        sc.nextLine();
                                        user.Registrar_nota(nota);
                                        GestorArchivos.Actualizar_Alumnos_txt(user, cedula);
                                        Historial_cedulas.add(cedula);
                                        GestorArchivos.limpiarConsola();
                                        System.out.print("Nota registrada con exito\n\n");
                                        break;
                                    }else{
                                        GestorArchivos.limpiarConsola();
                                        System.out.println("ERROR el alumno con cedula " + user.cedula + " tiene mas de 3 notas");
                                    }
                                    
                                }
                            }
                        }else{
                            GestorArchivos.limpiarConsola();
                            System.out.println("Cedula no encontrada\n");
                        }
                    }
                    break;

                case "4":
                    GestorArchivos.limpiarConsola();
                    if(Lista_Alumnos.size() == 0 || Historial_cedulas.size() == 0){
                        System.out.print("No hay notas/alumnos registrados\n\n");
                    }else{
                        GestorArchivos.limpiarConsola();
                        String ultima_cedula = Historial_cedulas.pop();
                        if(GestorArchivos.revisar_cedula(ultima_cedula, Lista_Alumnos)){

                            for(Alumno alumno : Lista_Alumnos){
                                if(ultima_cedula.equals(alumno.cedula)){
                                    alumno.Deshacer_nota();
                                    GestorArchivos.Actualizar_Alumnos_txt(alumno, ultima_cedula);
                                    break;
                                }
                            }
                            GestorArchivos.limpiarConsola();
                            System.out.print("Nota eliminada con exito\n\n");
                        }else{
                            GestorArchivos.limpiarConsola();
                            System.out.println("Cedula no encontrada \n");
                        }
                    }
                    break;

                case "5":
                    String texto_certificado;
                    Alumno.Aprobados.clear();
                    GestorArchivos.limpiarConsola();

                    for (Alumno alumno : Lista_Alumnos) {
                        alumno.Calculo_promedio();
                        if (alumno.Aprobacion()) {

                            texto_certificado = (Alumno.Aprobados.size() + 1) + ". [" + alumno.cedula + "] " + alumno.nombre + "\n" +
                            "-PROMEDIO FINAL : " + Math.round(alumno.promedio) + " ESTATUS: APROBADO \n\n\n";
                            Alumno.Aprobados.add(texto_certificado);
                        }
                    }

                    if (Alumno.Aprobados.size() > 0) {
                        GestorArchivos.limpiarConsola();
                        FileWriter archivo_certificado = new FileWriter("Entregablejava/certificado_pendientes.txt");
                        for (String alumnos_aprobado : Alumno.Aprobados) {
                            archivo_certificado.write(alumnos_aprobado);
                        }
                        archivo_certificado.close();
                        GestorArchivos.limpiarConsola();
                        System.out.println("Cola de certificados generada con éxito.\n");
                    } else {
                        System.out.println("Ningún alumno ha aprobado aún.\n");
                    }
                    break;
                    
                case "6":
                    GestorArchivos.limpiarConsola();
                    System.out.println("Alumnos registrados: ");
                    

                    for(Alumno alumno_registrado : Lista_Alumnos){
                        String estado;
                        alumno_registrado.Calculo_promedio();
                        if (alumno_registrado.Aprobacion()){
                            estado = "APROBADO";
                        }else{
                            estado = "REPROBADO";
                        }

                        System.out.print("-->"+ alumno_registrado.cedula + " - " + alumno_registrado.nombre + " - " + alumno_registrado.correo + " - " + alumno_registrado.programa + " - Notas: ");

                        for(Double nota : alumno_registrado.Lista_notas){
                            System.out.print(nota + " - ");
                        }
                        System.out.print("\n");

                        if(alumno_registrado.Lista_notas.size() > 0){
                            long texto_promedio = Math.round(alumno_registrado.promedio);
                            System.out.print("PROMEDIO:" + texto_promedio + " - ESTADO: " + estado +"\n\n");
                        }else{
                            System.out.println("PROMEDIO: Sin notas registradas - ESTADO: REPROBADO\n\n" );
                        }
                        
                    }
                    System.out.print("\n");
                    System.out.println("Profesores Registrados: ");

                    Scanner scaneo_profesores = new Scanner(new File("Entregablejava/Profesores.txt"));
                    while(scaneo_profesores.hasNextLine()){
                        String contenido = scaneo_profesores.nextLine();
                        System.out.println("--> " + contenido + "\n\n");
                    }
                    System.out.print("\n\n\n");
                    break;
            
                case "7":
                    System.out.println("\n\nSALIENDO SGA \n\n");
                    break;

                default:
                    GestorArchivos.limpiarConsola();
                    System.out.println("Opcion no valida. Intente nuevamente \n\n");
                    break;
            
            }
        }
        sc.close();
    }
}
