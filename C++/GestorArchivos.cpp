#ifndef GESTORARCHIVOS_CPP
#define GESTORARCHIVOS_CPP


#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <sstream>
#include <stack>
#include "Alumno.cpp"
#include "Profesor.cpp"
using namespace std;


class GestorArchivos{
    private:
        
        vector<string> split(string frase){
            string palabra;
            vector<string> fraseSeparada;

            for(char word : frase){
                if(word == ','){
                    fraseSeparada.push_back(palabra);
                    palabra = "";
                }
                
                else if(word != '\n' && word != '\r' && (unsigned char)word >= 32){
                    palabra += word;
                }
            }

            if(!palabra.empty()){
                while(!palabra.empty() && ((unsigned char)palabra.back() < 32 || palabra.back() == '\r' || palabra.back() == '\n')) {
                    palabra.pop_back();
                }
                if(!palabra.empty()) {
                    fraseSeparada.push_back(palabra);
                }
            }

            return fraseSeparada;
        }

    public:
        string rutaAlumnos;
        string rutaProfesores;
        vector<string> Aprobados;
        vector<Alumno*> listaAlumnos;
        vector<Profesor> listaProfesores;

        GestorArchivos(string rAlumno, string rProfesor){
            this->rutaAlumnos = rAlumno;
            this->rutaProfesores = rProfesor;
        }

        void actualizar_Alumnostxt(Alumno* obj , string cedula){
            vector<string> Lineas;
            ifstream archivo(rutaAlumnos);
            Lineas.clear();

            string linea;
            while(getline(archivo,linea)){
                Lineas.push_back(linea);
            }

            for(int i = 0; i< Lineas.size(); i++){
                if(split(Lineas[i])[0] == cedula){
                    stringstream ss;
                    ss<<obj->getCedula() << "," << obj->getNombre() << "," << obj->getCorreo() << "," << obj->programa; 
                    
                    stack<double> Copia = obj->listaNotas;
                    while(!Copia.empty()){
                        ss<<","<<Copia.top();
                        Copia.pop();
                    }
                    Lineas[i] = ss.str();
                    break;
                }
            }

            ofstream escribir(rutaAlumnos , std::ios::trunc);
            for(string line : Lineas){
                escribir << line << "\n";
            }
            
            archivo.close();
            escribir.close();
            Lineas.clear();
            cout<<"Documento Actualizado con exito\n\n";
        }


        void recibir_Alumnostxt(){
            vector<string> Lineas;
            ifstream archivo(rutaAlumnos);
            Lineas.clear();

            string linea;
            while(getline(archivo,linea)){
                Lineas.push_back(linea);
            }

            for(string lineastxt : Lineas){
                vector<string> lineaObjetos = split(lineastxt);
                vector<double> notas;
                string cedula = lineaObjetos[0];
                string nombre = lineaObjetos[1];
                string correo = lineaObjetos[2];
                string programa = lineaObjetos[3];


                if(lineaObjetos.size() > 4){
                    for(int i = 4; i<lineaObjetos.size(); i++){
                        notas.push_back(stod(lineaObjetos[i]));
                    }   
                }

                Alumno* user = nullptr;

                if(programa == "CURSO"){
                    if(!notas.empty()){
                        user = new AlumnoCurso(cedula,nombre,correo,notas);
                        listaAlumnos.push_back(user);
                    }else{
                        user = new AlumnoCurso(cedula,nombre,correo);
                        listaAlumnos.push_back(user);
                    }
                }else if(programa == "DIPLOMADO"){
                    if(!notas.empty()){
                        user = new AlumnoDiplomado(cedula,nombre,correo,notas);
                        listaAlumnos.push_back(user);
                    }else{
                        user = new AlumnoDiplomado(cedula,nombre,correo);
                        listaAlumnos.push_back(user);
                    }
                }else{
                    if(!notas.empty()){
                        user = new AlumnoBootcamp(cedula,nombre,correo,notas);
                        listaAlumnos.push_back(user);
                    }else{
                        user = new AlumnoBootcamp(cedula,nombre,correo);
                        listaAlumnos.push_back(user);
                    }
                }
            }
            Lineas.clear();
            archivo.close();
        }

        void recibir_Profesorestxt(){
            vector<string> Lineas;
            ifstream archivo(rutaProfesores);
            string lineas;
            Lineas.clear();

            while(getline(archivo,lineas)){
                Lineas.push_back(lineas);
            }

            for(string lineastxt : Lineas){
                vector<string> lineaObjetos = split(lineastxt);
                string cedula = lineaObjetos[0];
                string nombre = lineaObjetos[1];
                string correo = lineaObjetos[2];
                string especialidad = lineaObjetos[3];
                string materia = lineaObjetos[4];

                Profesor user (cedula,nombre,correo,especialidad,materia);
                listaProfesores.push_back(user);
            }
            Lineas.clear();
            archivo.close();
        }

        void crearProfesorestxt(Profesor profeUsuario){
            ofstream escritor(rutaProfesores , ios::app);
            escritor<<profeUsuario.getCedula()<<","<<profeUsuario.getNombre()<<","<<profeUsuario.getCorreo()<<","<<profeUsuario.especialidadAcademica<<","<<profeUsuario.materiaAsignada<<"\n";
            escritor.close();
        }

        void crearAlumnostxt(Alumno* usuario){
            stack<double> Copia = usuario->listaNotas;
            ofstream escritor(rutaAlumnos , ios::app);
            escritor<<usuario->getCedula()<<","<<usuario->getNombre()<<","<<usuario->getCorreo()<<","<<usuario->programa<<"\n";
            
            while(!Copia.empty()){
                escritor<<","<<Copia.top();
                Copia.pop();
            }
            escritor.close();
        }

        void crearCertificadostxt(){
            ofstream escritor("C:\\Users\\Windows User\\Desktop\\Proyectos Diplomados Online\\C++\\Certificados.txt" , std::ios::trunc);
            for(string line : Aprobados){
                escritor<<line<<"\n\n";
            }
            escritor.close();
        }

        bool cedulaRepetida(string cedula , string ruta){
            ifstream archivo(ruta);
            vector<string> Lineas;
            string line;

            while(getline(archivo,line)){
                Lineas.push_back(line);
            }

            for(string line : Lineas){
                if(split(line)[0] == cedula){
                    archivo.close();
                    return true;
                }
            }
            archivo.close();
            return false;
        }
};  

#endif
