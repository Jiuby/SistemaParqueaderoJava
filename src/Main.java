import modelo.*;
import servicio.*;
import utilidad.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Clase principal del Sistema de Parqueadero.
 * Contiene el menu interactivo para el usuario.
 *
 * @author Velez
 * @version 1.0
 */
public class Main {


    private Main() {
    }

    /**
     * Metodo principal que inicia el programa
     *
     * @param args Argumentos de linea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parqueadero parqueadero = null;

        System.out.println("========== SISTEMA DE PARQUEADERO ==========");
        System.out.println();

        // Verificar si existen parqueaderos guardados
        if (GestorArchivos.existenParqueaderos()) {
            System.out.println("Se encontraron parqueaderos guardados.");
            System.out.println();
            System.out.println("1. Cargar un parqueadero existente");
            System.out.println("2. Crear un nuevo parqueadero");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            if (opcion == 1) {
                parqueadero = cargarParqueaderoExistente(scanner);
            } else {
                parqueadero = crearNuevoParqueadero(scanner);
            }
        } else {
            System.out.println("No hay parqueaderos guardados. Creando uno nuevo...");
            System.out.println();
            parqueadero = crearNuevoParqueadero(scanner);
        }

        if (parqueadero == null) {
            System.out.println("Error al inicializar el parqueadero. Cerrando programa...");
            scanner.close();
            return;
        }

        // Crear el gestor
        GestorParqueadero gestor = new GestorParqueadero(parqueadero);

        // Menu principal
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarEntrada(scanner, gestor);
                    break;

                case 2:
                    registrarSalida(scanner, gestor);
                    break;

                case 3:
                    consultarDisponibilidad(gestor);
                    break;

                case 4:
                    listarVehiculos(gestor);
                    break;

                case 5:
                    generarReporte(gestor);
                    break;

                case 6:
                    mostrarTarifas();
                    break;

                case 7:
                    generarReporteArchivo(gestor);
                    break;

                case 8:
                    continuar = false;
                    System.out.println("\nGracias por usar el Sistema de Parqueadero!");
                    System.out.println("Hasta pronto!\n");
                    break;

                default:
                    System.out.println("\nOpcion invalida. Intente nuevamente.\n");
            }

            if (continuar) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    /**
     * Carga un parqueadero existente desde el archivo
     *
     * @param scanner Scanner para leer entrada del usuario
     * @return El parqueadero cargado o null si no se pudo cargar
     */
    public static Parqueadero cargarParqueaderoExistente(Scanner scanner) {
        ArrayList<String> nombres = GestorArchivos.cargarNombresParqueaderos();

        if (nombres.isEmpty()) {
            System.out.println("No hay parqueaderos disponibles.");
            return null;
        }

        System.out.println("\n--- PARQUEADEROS DISPONIBLES ---");
        for (int i = 0; i < nombres.size(); i++) {
            System.out.println((i + 1) + ". " + nombres.get(i));
        }

        System.out.print("Seleccione un parqueadero (numero): ");
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (seleccion < 1 || seleccion > nombres.size()) {
            System.out.println("Seleccion invalida.");
            return null;
        }

        String nombreSeleccionado = nombres.get(seleccion - 1);
        Parqueadero parqueadero = GestorArchivos.cargarParqueadero(nombreSeleccionado);

        if (parqueadero != null) {
            System.out.println("\nParqueadero cargado exitosamente!");
            System.out.println("Nombre: " + parqueadero.getNombre());
            System.out.println("Capacidad: " + parqueadero.getCapacidadTotal() + " espacios");
        }

        return parqueadero;
    }

    /**
     * Crea un nuevo parqueadero y lo guarda en el archivo
     *
     * @param scanner Scanner para leer entrada del usuario
     * @return El parqueadero creado
     */
    public static Parqueadero crearNuevoParqueadero(Scanner scanner) {
        System.out.print("Ingrese el nombre del parqueadero: ");
        String nombreParqueadero = scanner.nextLine();

        System.out.print("Ingrese la capacidad total de espacios: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Parqueadero parqueadero = new Parqueadero(nombreParqueadero, capacidad);

        // Inicializar espacios (mitad para autos, mitad para motos)
        int espaciosAutos = capacidad / 2;
        int espaciosMotos = capacidad - espaciosAutos;

        System.out.println("\nCreando espacios...");
        for (int i = 1; i <= espaciosAutos; i++) {
            parqueadero.agregarEspacio(new Espacio(i, "AUTO"));
        }

        for (int i = espaciosAutos + 1; i <= capacidad; i++) {
            parqueadero.agregarEspacio(new Espacio(i, "MOTO"));
        }

        // Guardar el parqueadero en el archivo
        if (GestorArchivos.guardarParqueadero(parqueadero)) {
            System.out.println("Parqueadero guardado exitosamente!");
        } else {
            System.out.println("Advertencia: No se pudo guardar el parqueadero en el archivo.");
        }

        System.out.println("Parqueadero creado exitosamente!");
        System.out.println("Espacios para AUTOS: " + espaciosAutos);
        System.out.println("Espacios para MOTOS: " + espaciosMotos);

        return parqueadero;
    }

    /**
     * Muestra el menu principal del sistema
     */
    public static void mostrarMenu() {
        System.out.println("\n============================================");
        System.out.println("           MENU PRINCIPAL");
        System.out.println("============================================");
        System.out.println("1. Registrar Entrada de Vehiculo");
        System.out.println("2. Registrar Salida de Vehiculo");
        System.out.println("3. Consultar Disponibilidad");
        System.out.println("4. Listar Vehiculos Parqueados");
        System.out.println("5. Generar Reporte de Ocupacion");
        System.out.println("6. Ver Tarifas");
        System.out.println("7. Generar Reporte en Archivo TXT");
        System.out.println("8. Salir");
        System.out.println("============================================");
    }

    /**
     * Registra la entrada de un vehiculo
     *
     * @param scanner Scanner para leer entrada del usuario
     * @param gestor Gestor del parqueadero
     */
    public static void registrarEntrada(Scanner scanner, GestorParqueadero gestor) {
        System.out.println("\n--- REGISTRAR ENTRADA ---");
        System.out.print("Ingrese la placa del vehiculo: ");
        String placa = scanner.nextLine();

        Ticket ticket = gestor.registrarEntrada(placa);

        if (ticket != null) {
            System.out.println("\nIMPORTANTE: Guarde el numero de ticket para la salida.");
        }
    }

    /**
     * Registra la salida de un vehiculo
     *
     * @param scanner Scanner para leer entrada del usuario
     * @param gestor Gestor del parqueadero
     */
    public static void registrarSalida(Scanner scanner, GestorParqueadero gestor) {
        System.out.println("\n--- REGISTRAR SALIDA ---");
        System.out.print("Ingrese la placa del vehiculo: ");
        String placa = scanner.nextLine();

        Ticket ticket = gestor.registrarSalida(placa);

        if (ticket != null) {
            System.out.println("Salida registrada exitosamente.");
        }
    }

    /**
     * Consulta la disponibilidad de espacios
     *
     * @param gestor Gestor del parqueadero
     */
    public static void consultarDisponibilidad(GestorParqueadero gestor) {
        System.out.println(gestor.consultarDisponibilidad());
    }

    /**
     * Lista todos los vehiculos parqueados
     *
     * @param gestor Gestor del parqueadero
     */
    public static void listarVehiculos(GestorParqueadero gestor) {
        System.out.println(gestor.listarVehiculosParqueados());
    }

    /**
     * Genera el reporte de ocupacion
     *
     * @param gestor Gestor del parqueadero
     */
    public static void generarReporte(GestorParqueadero gestor) {
        System.out.println(gestor.generarReporteOcupacion());
    }

    /**
     * Muestra las tarifas del parqueadero
     */
    public static void mostrarTarifas() {
        System.out.println(CalculadorTarifa.mostrarTarifas());
    }

    /**
     * Genera un reporte de vehiculos en archivo TXT
     *
     * @param gestor Gestor del parqueadero
     */
    public static void generarReporteArchivo(GestorParqueadero gestor) {
        System.out.println("\n--- GENERAR REPORTE EN ARCHIVO ---");

        if (GestorArchivos.generarReporteVehiculos(gestor)) {
            System.out.println("El archivo se guardo en la carpeta 'reportes'");
        } else {
            System.out.println("Error al generar el reporte.");
        }
    }
}