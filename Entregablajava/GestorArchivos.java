package Entregablajava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {

    // Método para limpiar la consola (Equivalente a os.system('cls'))
    public static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }

    // 1. Crear_Alumnos_txt
    public static void crearAlumnosTxt(Alumno objeto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Alumnos.txt", true))) {
            String linea = objeto.getCedula() + " , " + 
                           objeto.getNombre() + " , " + 
                           objeto.getCorreo() + " , " + 
                           objeto.getProgramaAcademico().name() + "\n";
            writer.write(linea);
        } catch (IOException e) {
            System.out.println("Error al escribir en Alumnos.txt: " + e.getMessage());
        }
    }

    // 2. Actualizar_Alumnos_txt
    public static void actualizarAlumnosTxt(Alumno objeto, String cedula) {
        List<String> listaLinea = new ArrayList<>();

        // Leer el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader("Alumnos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                listaLinea.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer Alumnos.txt: " + e.getMessage());
            return;
        }

        // Modificar la línea correspondiente
        for (int i = 0; i < listaLinea.size(); i++) {
            if (listaLinea.get(i).contains(cedula)) {
                String lineaBase = objeto.getCedula() + " , " + 
                                   objeto.getNombre() + " , " + 
                                   objeto.getCorreo() + " , " + 
                                   objeto.getProgramaAcademico().name();

                if (objeto.getListaNotas() != null && !objeto.getListaNotas().isEmpty()) {
                    List<String> notasStr = new ArrayList<>();
                    for (Double nota : objeto.getListaNotas()) {
                        notasStr.add(String.valueOf(nota));
                    }
                    String notasUnidas = String.join(" , ", notasStr);
                    listaLinea.set(i, lineaBase + " , " + notasUnidas);
                } else {
                    listaLinea.set(i, lineaBase);
                }
            }
        }

        // Reescribir el archivo completo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Alumnos.txt", false))) {
            for (String linea : listaLinea) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar Alumnos.txt: " + e.getMessage());
        }
    }

    // 3. revisar_cedula
    public static boolean revisarCedula(String cedulaSolicitada, List<Alumno> objetos) {
        List<String> cedulas = new ArrayList<>();
        
        for (int i = 0; i < objetos.size(); i++) {
            cedulas.add(objetos.get(i).getCedula());
        }

        return cedulas.contains(cedulaSolicitada);
    }

    // 4. recibir_Alumnostxt
    public static List<Alumno> recibirAlumnostxt() {
        List<Alumno> listaObj = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Alumnos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

                String[] elementos = linea.split(",");
                List<String> fila = new ArrayList<>();
                for (String elemento : elementos) {
                    fila.add(elemento.trim());
                }

                String cedula = fila.get(0);
                String nombre = fila.get(1);
                String email = fila.get(2);
                String curso = fila.get(3);

                List<Double> notas = new ArrayList<>();
                for (int i = 4; i < fila.size(); i++) {
                    notas.add(Double.parseDouble(fila.get(i)));
                }

                Alumno usuario = new Alumno(cedula, nombre, email, curso, notas);
                listaObj.add(usuario);
            }
        } catch (IOException e) {
            System.out.println("Error al leer Alumnos.txt: " + e.getMessage());
        }

        return listaObj;
    }

    // 5. recibir_Profesorestxt
    public static List<Profesor> recibirProfesorestxt() {
        List<Profesor> listaObjProfesor = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Profesores.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

                String[] elementos = linea.split(",");
                List<String> fila = new ArrayList<>();
                for (String elemento : elementos) {
                    fila.add(elemento.trim());
                }

                String cedula = fila.get(0);
                String nombre = fila.get(1);
                String email = fila.get(2);
                String especialidad = fila.get(3);
                String materia = fila.get(4);

                Profesor usuario = new Profesor(cedula, nombre, email, especialidad, materia);
                listaObjProfesor.add(usuario);
            }
        } catch (IOException e) {
            System.out.println("Error al leer Profesores.txt: " + e.getMessage());
        }

        return listaObjProfesor;
    }
}
