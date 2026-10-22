package Entregablejava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {

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

    public static void Crear_Alumnos_txt(Alumno objeto) {
        try (BufferedWriter archivo = new BufferedWriter(new FileWriter("Entregablejava/Alumnos.txt", true))) {
            String tipoPrograma = "";
            if (objeto instanceof AlumnoCurso) tipoPrograma = "CURSO";
            else if (objeto instanceof AlumnoDiplomado) tipoPrograma = "DIPLOMADO";
            else if (objeto instanceof AlumnoBootcamp) tipoPrograma = "BOOTCAMP";

            archivo.write(objeto.cedula + " , " + objeto.nombre + " , " + objeto.correo + " , " + tipoPrograma + "\n");
        } catch (IOException e) {
            System.out.println("Error al escribir en Alumnos.txt: " + e.getMessage());
        }
    }

    public static void Actualizar_Alumnos_txt(Alumno objeto, String cedula) {
        List<String> lista_linea = new ArrayList<>();

        try (BufferedReader archivo = new BufferedReader(new FileReader("Entregablejava/Alumnos.txt"))) {
            String linea;
            while ((linea = archivo.readLine()) != null) {
                lista_linea.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer Alumnos.txt: " + e.getMessage());
            return;
        }

        for (int i = 0; i < lista_linea.size(); i++) {
            if (lista_linea.get(i).contains(cedula)) {
                String tipoPrograma = "";
                if (objeto instanceof AlumnoCurso) tipoPrograma = "CURSO";
                else if (objeto instanceof AlumnoDiplomado) tipoPrograma = "DIPLOMADO";
                else if (objeto instanceof AlumnoBootcamp) tipoPrograma = "BOOTCAMP";

                String linea_base = objeto.cedula + " , " + objeto.nombre + " , " + objeto.correo + " , " + tipoPrograma;

                if (objeto.Lista_notas.size() > 0) {
                    List<String> notasStr = new ArrayList<>();
                    for (Double nota : objeto.Lista_notas) {
                        notasStr.add(String.valueOf(nota));
                    }
                    String notas_str = String.join(" , ", notasStr);
                    // Guardamos solo el texto sin \n dentro de la lista
                    lista_linea.set(i, linea_base + " , " + notas_str);
                } else {
                    lista_linea.set(i, linea_base);
                }
            }
        }

        try (BufferedWriter archivo = new BufferedWriter(new FileWriter("Entregablejava/Alumnos.txt", false))) {
            for (String linea : lista_linea) {
                archivo.write(linea.trim() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar Alumnos.txt: " + e.getMessage());
        }
    }

    public static boolean revisar_cedula(String cedula_solicitada, List<Alumno> objetos) {
        List<String> cedulas = new ArrayList<>();
        for (int i = 0; i < objetos.size(); i++) {
            cedulas.add(objetos.get(i).cedula);
        }
        return cedulas.contains(cedula_solicitada);
    }

    public static List<Alumno> recibir_Alumnostxt() {
        List<Alumno> lista_obj = new ArrayList<>();

        try (BufferedReader archivo = new BufferedReader(new FileReader("Entregablejava/Alumnos.txt"))) {
            String linea;
            while ((linea = archivo.readLine()) != null) {
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

                Alumno usuario = null;
                if (curso.equalsIgnoreCase("curso")) {
                    usuario = new AlumnoCurso(nombre, cedula, email);
                } else if (curso.equalsIgnoreCase("diplomado")) {
                    usuario = new AlumnoDiplomado(nombre, cedula, email);
                } else if (curso.equalsIgnoreCase("bootcamp")) {
                    usuario = new AlumnoBootcamp(nombre, cedula, email);
                }

                if (usuario != null) {
                    usuario.Lista_notas.addAll(notas);
                    lista_obj.add(usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer Alumnos.txt: " + e.getMessage());
        }

        return lista_obj;
    }

    public static List<Profesor> recibir_Profesorestxt() {
        List<Profesor> lista_obj_profesor = new ArrayList<>();

        try (BufferedReader archivo = new BufferedReader(new FileReader("Entregablejava/Profesores.txt"))) {
            String linea;
            while ((linea = archivo.readLine()) != null) {
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
                lista_obj_profesor.add(usuario);
            }
        } catch (IOException e) {
            System.out.println("Error al leer Profesores.txt: " + e.getMessage());
        }

        return lista_obj_profesor;
    }

    public static void Crear_Profesores_txt(Profesor objeto) {
        try (BufferedWriter archivo = new BufferedWriter(new FileWriter("Entregablejava/Profesores.txt", true))) {
            archivo.write(objeto.cedula + " , " + objeto.nombre + " , " + objeto.correo + " , " + objeto.Especialidad_academica + " , " + objeto.Materia + "\n");
        } catch (IOException e) {
            System.out.println("Error al escribir en Profesores.txt: " + e.getMessage());
    }
}
}
