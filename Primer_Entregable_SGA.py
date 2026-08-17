from Primer_Entregable_Clase_SGA import Programa_Academico, Alumno, Profesor
import os
os.system('cls')


def Crear_Alumnos_txt(objeto):
    with open('Alumnos.txt', 'a', encoding='utf-8') as archivo:
        archivo.write(f'{objeto.cedula} , {objeto.nombre} , {objeto.correo} , {objeto.programa_academico.name}\n')



def Actualizar_Alumnos_txt(objeto, cedula):
    lista_linea = []
    with open('Alumnos.txt', 'r', encoding='utf-8') as archivo:
        lista_linea = archivo.readlines()

    for i in range(len(lista_linea)):
        if cedula in lista_linea[i]:
            linea_base = f'{objeto.cedula} , {objeto.nombre} , {objeto.correo} , {objeto.programa_academico.name}'
            
            if len(objeto.lista_notas) > 0:
                notas_str = " , ".join(str(nota) for nota in objeto.lista_notas)
                lista_linea[i] = f'{linea_base} , {notas_str}\n'
            else:
                lista_linea[i] = f'{linea_base}\n'

    with open('Alumnos.txt', 'w', encoding='utf-8') as archivo:
        archivo.writelines(lista_linea)



def revisar_cedula(cedula_solicitada : str, objetos : list[object]) -> bool:
    cedulas = []
    for i in range(len(objetos)):
        cedulas.append(objetos[i].cedula)

    if cedula_solicitada  in   cedulas:
        return True
    else:
        return False        
        

def recibir_Alumnostxt():
    lista_obj = []
    with open('Alumnos.txt', 'r', encoding='utf-8') as archivo:
    
        for linea in archivo:
            linea = linea.strip()
                
            fila = [elemento.strip() for elemento in linea.split(',')]
            
                
            cedula = fila[0]
            nombre = fila[1]
            email = fila[2]
            curso = fila[3]

            notas = [float(nota) for nota in fila[4:]]
            usuario = Alumno(cedula, nombre, email, curso, notas) 
            lista_obj.append(usuario)

        
    return lista_obj


def recibir_Profesorestxt():
    lista_obj_profesor = []
    
    with open('Profesores.txt', 'r', encoding='utf-8') as archivo:
        for linea in archivo:
            linea = linea.strip()
            if not linea:  
                continue
                
            fila = [elemento.strip() for elemento in linea.split(',')]
                
            cedula = fila[0]
            nombre = fila[1]
            email = fila[2]
            especialidad = fila[3]
            materia = fila[4]

            usuario = Profesor(cedula, nombre, email, especialidad, materia) 
            lista_obj_profesor.append(usuario)

        
    return lista_obj_profesor

opcion : str = '0'
Alumnos_registrados = recibir_Alumnostxt()
Profesores_registrados =recibir_Profesorestxt()

while opcion !='7':
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
            programa = input('Ingrese el nombre del programa academico del alumno: ').lower()

            usuario = Alumno(cedula, nombre,correo,programa)

            Crear_Alumnos_txt(usuario)

            os.system('cls')
            Alumnos_registrados.append(usuario)
            print('Alumno Registrado con exito')
            print('\n')

        case '2':
            os.system('cls')
            cedula = input('Ingrese la cedula del profesor: ')
            nombre = input('Ingrese el nombre del profesor: ')
            correo = input('Ingrese el correo del profesor: ')
            especialidad = input('Ingrese la especialidad academica del profesor: ')
            materia = input('Ingrese la materia del profesor: ')
            
            usuario = Profesor(cedula,nombre,correo,especialidad,materia)
            
            with open ('Profesores.txt' , 'a') as archivo:    
                archivo.write(f'{usuario.cedula} , {usuario.nombre} , {usuario.correo} , {usuario.Especilidad_academica} , {usuario.Materia_asignada} \n')

            os.system('cls')
            Profesores_registrados.append(usuario)
            print('Profesor registrado con exito')
            print('\n')

        case '3':
            if len(Alumnos_registrados) == 0:

                os.system('cls')
                print('No hay alumnos registrados')
                print('\n')
                continue
            
            else:
                os.system('cls')
                print('Alumnos registrados por el momento')

                for i in range(len(Alumnos_registrados)):
                    print(f'{Alumnos_registrados[i].cedula} -- {Alumnos_registrados[i].nombre}')

                cedula_solicitada = input('Ingrese la cedula del alumno a el que va a registrar notas: ')

                if revisar_cedula(cedula_solicitada, Alumnos_registrados):
                    nota = float(input('Ingrese la nota del alumno: '))

                    for i in range(len(Alumnos_registrados)):
                        if cedula_solicitada == Alumnos_registrados[i].cedula:
                            Alumnos_registrados[i].Registrar_nota(nota)

                            Actualizar_Alumnos_txt(Alumnos_registrados[i] , cedula_solicitada)

                else:
                    os.system('cls')
                    print('Cedula no encontrada')
                    continue

            print('\n')

        case '4':
            os.system('cls')
            print('Alumnos registrados por el momento')
        
            for i in range(len(Alumnos_registrados)):
                print(f'{Alumnos_registrados[i].cedula} -- {Alumnos_registrados[i].nombre}')
        
            cedula_solicitada : str = input('Ingrese la cedula del alumno a el que va a deshacer la ultima nota registrada: ')

            if revisar_cedula(cedula_solicitada, Alumnos_registrados):
                for i in range(len(Alumnos_registrados)):
                    if cedula_solicitada == Alumnos_registrados[i].cedula:
                        Alumnos_registrados[i].Deshacer_nota()
                        Actualizar_Alumnos_txt(Alumnos_registrados[i], cedula_solicitada)

            else:
                os.system('cls')
                print('Cedula no encontrada')
                print('\n')
                continue

            print('\n')
            
        case '5':
            for i in range(len(Alumnos_registrados)):
                Alumnos_registrados[i].Aprobacion()

                if Alumnos_registrados[i].aprobado:
                    texto_certificado = f'{len(Alumno.Aprobados) + 1}. [{Alumnos_registrados[i].cedula}] {Alumnos_registrados[i].nombre} \n  -PROGRAMA : {Alumnos_registrados[i].programa_academico.name.lower()}\n  -PROMEDIO FINAL : {round(Alumnos_registrados[i].promedio, 2)} \n  -ESTATUS : APROBADO\n\n\n'

                    Alumno.Aprobados.append(texto_certificado)   

            if len(Alumno.Aprobados) > 0:      
                with open ('certificados_pendientes.txt' , 'a') as archivo:
                    for i in range(len(Alumno.Aprobados)):
                        archivo.write(f'{Alumno.Aprobados[i]} \n')
                        continue

                os.system('cls')
                print('\n')
                print('Cola generada con exito')

            else:
                os.system('cls')
                print('Ningun alumno ha aprobado aun')
                print('\n')

        case '6': 
            os.system('cls')
            print('Alumnos registrados: \n')

            for i in range(len(Alumnos_registrados)):
                estado = ''
                Alumnos_registrados[i].Calculo_promedio()
                
                if Alumnos_registrados[i].Aprobacion():
                    estado = 'APROBADO'
                else:
                    estado = 'REPROBADO'

                print(f'---> {Alumnos_registrados[i].cedula} - {Alumnos_registrados[i].nombre} - {Alumnos_registrados[i].correo} - {Alumnos_registrados[i].programa_academico} - Notas: ')

                for notas in range(len(Alumnos_registrados[i].lista_notas)):
                    print(f'{Alumnos_registrados[i].lista_notas[notas]}' ,end=' - ')      

                print(f'\n     PROMEDIO: {round(Alumnos_registrados[i].promedio,2)} - ESTADO: {estado}')
                print('\n'*2)
            
            print('\n')

            print('Profesores registrados: \n')
            
            with open('Profesores.txt','r') as archivo:
                lineas : list[str] = archivo.readlines()
                for i in range(len(lineas)):
                    print(f'--- {lineas[i]}\n')
            print('\n')

        case '7':
            print('ADIOS')
            break
            
        case _:
            print(f'ERROR: opcion invalida {opcion}, numeros del 1 al 7')

        