from Primer_Entregable_Clase_SGA import Alumno, Profesor, Programa_Academico, AlumnoCurso, AlumnoBootcamp,AlumnoDiplomado , GestorArchivos
from collections import deque

import os
os.system('cls')




def revisar_cedula(cedula_solicitada : str, objetos : list[Alumno]) -> bool:
    cedulas = []
    for i in range(len(objetos)):
        cedulas.append(objetos[i].cedula)

    if cedula_solicitada  in   cedulas:
        return True
    else:
        return False        
        



opcion : str = '0'
historial_cedulas = deque()
archivos = GestorArchivos()

archivos.recibirAlumnostxt()
archivos.recibirProfesorestxt()

while opcion != '7':
    print("==================================================")
    print("SGA-DO: SISTEMA DIPLOMADOSONLINE")
    print("==================================================")
    print("1. Registrar Alumno")
    print("2. Registrar Profesor")
    print("3. Registrar Notas a un Alumno")
    print("4. Deshacer Último Registro de Nota")
    print("5. Generar Cola de Certificados")
    print("6. Mostrar Reporte General")
    print("7. Salir")
    print("==================================================")
    opcion = input("Seleccione una opción (1-7): ")


    match (opcion):
        case '1':
            os.system('cls')
            cedula = input('Ingrese la cedula del alumno: ')
            nombre = input('Ingrese el nombre del alumno: ')
            correo = input('Ingrese el correo del alumno: ')

            os.system('cls')
            print('->Curso -- 1\n->Diplomado -- 2\n->Bootcamp -- 3\n')
            programa = input('Ingrese la opcion: ')

            while programa != "1" and programa !="2" and programa !="3":
                os.system('cls')
                print("ERROR, el nuemro ingresado es distinto a 1 2 o 3\n\n")
                print('->Curso -- 1\n->Diplomado -- 2\n->Bootcamp -- 3\n')
                programa = input('Ingrese la opcion: ')

            usuario = None
            if int(programa) == 1:
                usuario = AlumnoCurso(cedula,nombre,correo,)
            elif int(programa) == 2:
                usuario = AlumnoDiplomado(cedula,nombre,correo)
            elif int(programa) == 3:
                usuario = AlumnoBootcamp(cedula,nombre,correo)

            archivos.listaAlumnos.append(usuario)
            archivos.aniadirAlumno(usuario)

            os.system('cls')
            
            print('Alumno Registrado con exito')
            print('\n')

        case '2':
            os.system('cls')
            cedula : str = input('Ingrese la cedula del profesor: ')
            nombre : str = input('Ingrese el nombre del profesor: ')
            correo : str = input('Ingrese el correo del profesor: ')
            especialidad : str = input('Ingrese la especialidad academica del profesor: ')
            materia : str = input('Ingrese la materia del profesor: ')
            
            usuario : Profesor = Profesor(cedula,nombre,correo,especialidad,materia)
            
            archivos.aniadirProfesorestxt(usuario)

            os.system('cls')
            archivos.listaProfesores.append(usuario)
            print('Profesor registrado con exito')
            print('\n')

        case '3':
            if len(archivos.listaAlumnos) == 0:

                os.system('cls')
                print('No hay alumnos registrados')
                print('\n')
                continue
            
            else:
                os.system('cls')
                print('Alumnos registrados por el momento')

                for i in range(len(archivos.listaAlumnos)):
                    print(f'{archivos.listaAlumnos[i].cedula} -- {archivos.listaAlumnos[i].nombre}')

                cedula_solicitada = input('Ingrese la cedula del alumno a el que va a registrar notas: ')

                if revisar_cedula(cedula_solicitada, archivos.listaAlumnos):
                    for i in range(len(archivos.listaAlumnos)):
                        if cedula_solicitada == archivos.listaAlumnos[i].cedula:
                            nota = float(input('Ingrese la nota del alumno: ')) 
                            if (not(len(archivos.listaAlumnos[i].lista_notas) == 3)) and nota > 0 and nota <= 20:
                                archivos.listaAlumnos[i].Registrar_nota(nota)

                                archivos.actualizarAlumnostxt(archivos.listaAlumnos[i] , i)
                                historial_cedulas.append(cedula_solicitada)
                                break
                            else:
                                os.system('cls')
                                print(f"ERROR nota invalida / alumno con mas de 3 notas")
                                break

                            

                else:
                    os.system('cls')
                    print('Cedula no encontrada')

            print('\n')

        case '4':
            os.system('cls')
            if len(historial_cedulas) == 0:
                os.system('cls')
                print('ERROR no hay mas alumnos con notas')

            else:
                ultima_cedula : str = historial_cedulas.pop()
                for i in range(len(archivos.listaAlumnos)):
                    if ultima_cedula == archivos.listaAlumnos[i].cedula:
                        archivos.listaAlumnos[i].Deshacer_nota()
                        archivos.actualizarAlumnostxt(archivos.listaAlumnos[i] , i)
                        break

            print('\n')
            
        case '5':
            os.system('cls')
            archivos.Aprobados.clear()
            archivos.crearCertificadostxt()
            print("Cola generada con exito")


        case '6': 
            os.system('cls')
            print('Alumnos registrados: \n')

            for i in range(len(archivos.listaAlumnos)):
                estado = ''
                archivos.listaAlumnos[i].Calculo_promedio()
                
                if archivos.listaAlumnos[i].Aprobacion():
                    estado = 'APROBADO'
                else:
                    estado = 'REPROBADO'

                print(f'---> {archivos.listaAlumnos[i].cedula} - {archivos.listaAlumnos[i].nombre} - {archivos.listaAlumnos[i].correo} - {archivos.listaAlumnos[i].programa} - Notas: ')

                for notas in range(len(archivos.listaAlumnos[i].lista_notas)):
                    print(f'{archivos.listaAlumnos[i].lista_notas[notas]}' ,end=' - ')      

                print(f'\n     PROMEDIO: {round(archivos.listaAlumnos[i].promedio,2)} - ESTADO: {estado}')
                print('\n'*2)
            
            print('\n')

            print('Profesores registrados: \n')
            if len(archivos.listaProfesores) > 0:
                with open('Profesores.txt','r') as archivo:
                    lineas : list[str] = archivo.readlines()
                    for i in range(len(lineas)):
                        print(f'--- {lineas[i]}\n')
            else:
                print("Sin profesores registrados")
            print('\n')

        case '7':
            print('ADIOS')
            break
            
        case _:
            os.system("cls")
            print(f'ERROR: opcion invalida {opcion}, numeros del 1 al 7 \n')
            

        