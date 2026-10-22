#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>
#include <stack>
#include <cstdlib>
#include "Alumno.cpp"
#include "Profesor.cpp"
#include "GestorArchivos.cpp"
using namespace std;



void borrarTerminal(){
    system("cls");
}

int main(){
    GestorArchivos archivo("Alumnos.txt", "Profesores.txt");
    archivo.recibir_Alumnostxt();
    archivo.recibir_Profesorestxt();

    stack<string> historialCedula;
    string opcion = "0";
    string cedula, nombre, correo, especialidad, materia;

    while (opcion != "7") {
        cout << "==================================================\r\n";
        cout << "SGA-DO: SISTEMA DIPLOMADOSONLINE\r\n";
        cout << "==================================================\r\n";
        cout << "1. Registrar Alumno\r\n";
        cout << "2. Registrar Profesor\r\n";
        cout << "3. Registrar Notas a un Alumno\r\n";
        cout << "4. Deshacer Ultimo Registro de Nota\r\n";
        cout << "5. Generar Cola de Certificados\r\n";
        cout << "6. Mostrar Reporte General\r\n";
        cout << "7. Salir\r\n";
        cout << "==================================================\r\n";

        cout << "Seleccione una opcion (1-7): ";
        cin >> opcion;

        if (opcion == "1") {
            Alumno* alumnoNuevo = nullptr;
            borrarTerminal();
            cout << "Ingrese la cedula: ";
            cin >> cedula;

            if(archivo.cedulaRepetida(cedula , archivo.rutaProfesores) || archivo.cedulaRepetida(cedula,archivo.rutaAlumnos)){
                borrarTerminal();
                cout<<"La cedula esta repetida, no se va a crear el Usuario\n\n";
                continue;
            }

            cout << "Ingrese el nombre: ";
            cin >> nombre;
            cout << "Ingrese el correo: ";
            cin >> correo;
            borrarTerminal();

            cout << "1 --> Curso\n2 --> Diplomado\n3 --> Bootcamp\n\n";
            cout << "\nIngrese la opcion al programa: ";
            string opcionEnum;
            cin >> opcionEnum;

            while (opcionEnum != "1" && opcionEnum != "2" && opcionEnum != "3") {
                borrarTerminal();
                cout << "ERROR: opcion invalida.\n";
                cout << "1 --> Curso\n2 --> Diplomado\n3 --> Bootcamp\n\n";
                cout << "\nIngrese la opcion al programa: ";
                cin >> opcionEnum;
            }

            borrarTerminal();
            switch (stoi(opcionEnum)) {
                case 1:
                    alumnoNuevo = new AlumnoCurso(cedula, nombre, correo);
                    break;
                case 2:
                    alumnoNuevo = new AlumnoDiplomado(cedula, nombre, correo);
                    break;
                case 3:
                    alumnoNuevo = new AlumnoBootcamp(cedula, nombre, correo);
                    break;
            }

            borrarTerminal();
            cout << "Alumno registrado con exito \n\n\n";
            archivo.listaAlumnos.push_back(alumnoNuevo);
            archivo.crearAlumnostxt(alumnoNuevo);
        }
        else if (opcion == "2") {
            borrarTerminal();
            cout << "Ingrese la cedula: ";
            cin >> cedula;

            if(archivo.cedulaRepetida(cedula , archivo.rutaProfesores) || archivo.cedulaRepetida(cedula,archivo.rutaAlumnos)){
                borrarTerminal();
                cout<<"La cedula esta repetida, no se va a crear el Usuario\n\n";
                continue;
            }

            cout << "Ingrese el nombre: ";
            cin >> nombre;
            cout << "Ingrese el correo: ";
            cin >> correo;
            cout << "Ingrese la Especialidad Academica: ";
            cin >> especialidad;
            cout << "Ingrese la materia asignada: ";
            cin >> materia;

            Profesor user(cedula, nombre, correo, especialidad, materia);
            archivo.crearProfesorestxt(user);
            borrarTerminal();

            cout << "Profesor registrado con exito\n\n";
            archivo.listaProfesores.push_back(user);
        }
        else if (opcion == "3") {
            if (!archivo.listaAlumnos.empty()) {
                double nota;
                borrarTerminal();
                cout << "Alumnos registrados por el momento\n";

                for (Alumno* user : archivo.listaAlumnos) {
                    cout << *user;
                }
                cout << "Ingrese la cedula: ";
                cin >> cedula;
                
                for (Alumno* user : archivo.listaAlumnos) {
                    if (cedula == user->getCedula()) {
                        borrarTerminal();
                        cout << "Ingrese la nota: ";
                        cin >> nota;
                        borrarTerminal();
                        user->registrarNota(nota);
                        archivo.actualizar_Alumnostxt(user, cedula);
                        break;
                    }else{
                        borrarTerminal();
                        cout<<"Cedula no encontrada\n\n";
                    }
                }
                historialCedula.push(cedula);
            }
            else {
                borrarTerminal();
                cout << "Ningun alumno registrado\n\n";
            }
        }
        else if (opcion == "4") {
            borrarTerminal();
            if (!(historialCedula.empty())) {
                for (Alumno* user : archivo.listaAlumnos) {
                    if (user->getCedula() == historialCedula.top()) {
                        user->deshacerNota();
                        archivo.actualizar_Alumnostxt(user, user->getCedula());
                        historialCedula.pop();
                        break;
                    }
                }
            }
            else {
                cout << "No hay mas acciones o notas registradas en esta sesion para deshacer\n\n";
            }
        }
        else if (opcion == "5") {
            archivo.Aprobados.clear();
            for (Alumno* user : archivo.listaAlumnos) {
                if (user->Aprobacion()) {
                    stack<double> Copia = user->listaNotas;
                    stringstream ss;

                    ss << "["<<user->getCedula() << "]" << user->getNombre() << "\n" <<"    -Programa:"<<user->programa <<"\n"<<"    -Promedio Final: "<<user->calculoPromedio()<< "\n"<<"    -Estatus: APROBADO";

                    archivo.Aprobados.push_back(ss.str());
                    ss.clear();
                }
            }

            if (!archivo.Aprobados.empty()) {
                archivo.crearCertificadostxt();
                borrarTerminal();
                cout << "Cola generada con exito\n\n";
            }else{
                borrarTerminal();
                cout<<"ERROR, ningun alumno ha aprobado\n\n";
            }
        }
        else if (opcion == "6") {
            borrarTerminal();
            cout << "Alumnos Registrados: \n\n\n";
            for (int i = 0; i < archivo.listaAlumnos.size(); i++) {
                stack<double> Copia = archivo.listaAlumnos[i]->listaNotas;
                string estado;

                if(archivo.listaAlumnos[i]->Aprobacion()){
                    estado = "APROBADO";
                }else{
                    estado = "REPROBADO";
                }

                cout << "[" << i + 1 << "]" << archivo.listaAlumnos[i]->getCedula() << "-" << archivo.listaAlumnos[i]->getNombre() << "-" << archivo.listaAlumnos[i]->programa << "--> "<< estado<< "\n";
                cout << "    Notas: ";

                if(!Copia.empty()){
                    while (!Copia.empty()) {
                        cout << Copia.top() << "-";
                        Copia.pop();
                    }
                }else{
                    cout<<" Sin notas registradas";
                }

                cout << "\n\n\n";
            }
            cout << "Profesores Registrados: \n\n\n";
            for (Profesor user : archivo.listaProfesores) {
                cout << user << "\n";
            }

            cout << "\n\n\n";
        }
        else if(opcion == "7"){
            cout << "\n\nCerrando programa . . . ";
        }
        else {
            borrarTerminal();
            cout << "Opcion no valida\n\n";
        }
    }
    cout<<"\nLiberando memoria";
    for(Alumno* user : archivo.listaAlumnos){
        delete user;
    }
    archivo.listaAlumnos.clear();
    cout<<"\nAdios\n";
    return 0;
}