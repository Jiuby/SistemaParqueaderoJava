import modelo.*;
import servicio.*;
import utilidad.Validador;
import java.util.Scanner;

/**
 * Clase principal del Sistema de Parqueadero.
 * Contiene el menu interactivo para el usuario.
 *
 * @author Velez
 * @version 1.0
 */
public class Main {

    /**
     * Metodo principal que inicia el programa
     *
     * @param args Argumentos de linea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear el parqueadero
        System.out.println("========== SISTEMA DE PARQUEADERO ==========");
        System.out.print("Ingrese el nombre del parqueadero: ");
        String nombreParqueadero = scanner.nextLine();

        System.out.print("Ingrese la capacidad total de espacios: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine();

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

        System.out.println("Parqueadero creado exitosamente!");
        System.out.println("Espacios para AUTOS: " + espaciosAutos);
        System.out.println("Espacios para MOTOS: " + espaciosMotos);

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
        System.out.println("7. Salir");
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
}