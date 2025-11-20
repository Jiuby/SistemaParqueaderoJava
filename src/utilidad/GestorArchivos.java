package utilidad;

import modelo.*;
import servicio.GestorParqueadero;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase encargada de manejar la lectura y escritura de archivos.
 * Permite guardar y cargar la configuracion del parqueadero y generar reportes.
 *
 * @author Velez
 * @version 1.0
 */
public class GestorArchivos {

    /**
     * Nombre del archivo donde se guardan los parqueaderos
     */
    private static final String ARCHIVO_PARQUEADEROS = "parqueaderos.txt";

    /**
     * Nombre de la carpeta donde se guardan los reportes
     */
    private static final String CARPETA_REPORTES = "reportes";

    /**
     * Constructor privado para evitar instanciacion.
     * Esta clase solo contiene metodos estaticos.
     */
    private GestorArchivos() {
        // Constructor privado
    }

    /**
     * Guarda la configuracion de un parqueadero en el archivo
     *
     * @param parqueadero El parqueadero a guardar
     * @return true si se guardo correctamente, false si hubo error
     */
    public static boolean guardarParqueadero(Parqueadero parqueadero) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_PARQUEADEROS, true));

            // Contar espacios por tipo
            int espaciosAuto = 0;
            int espaciosMoto = 0;

            ArrayList<Espacio> espacios = parqueadero.getEspacios();
            for (int i = 0; i < espacios.size(); i++) {
                if (espacios.get(i).getTipoVehiculo().equals("AUTO")) {
                    espaciosAuto++;
                } else {
                    espaciosMoto++;
                }
            }

            // Formato: NOMBRE|CAPACIDAD|ESPACIOS_AUTO|ESPACIOS_MOTO
            escritor.println(parqueadero.getNombre() + "|" +
                    parqueadero.getCapacidadTotal() + "|" +
                    espaciosAuto + "|" +
                    espaciosMoto);

            escritor.close();
            return true;

        } catch (IOException e) {
            System.out.println("Error al guardar el parqueadero: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga todos los parqueaderos guardados en el archivo
     *
     * @return Lista de nombres de parqueaderos disponibles
     */
    public static ArrayList<String> cargarNombresParqueaderos() {
        ArrayList<String> nombres = new ArrayList<String>();

        try {
            File archivo = new File(ARCHIVO_PARQUEADEROS);

            if (!archivo.exists()) {
                return nombres;
            }

            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()) {
                String linea = lector.nextLine();

                // Buscar la primera posicion del separador |
                int posicion = linea.indexOf('|');

                if (posicion > 0) {
                    String nombre = linea.substring(0, posicion);
                    nombres.add(nombre);
                }
            }

            lector.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontro el archivo de parqueaderos.");
        }

        return nombres;
    }

    /**
     * Carga un parqueadero especifico por su nombre
     *
     * @param nombre El nombre del parqueadero a cargar
     * @return El parqueadero cargado o null si no se encuentra
     */
    public static Parqueadero cargarParqueadero(String nombre) {
        try {
            File archivo = new File(ARCHIVO_PARQUEADEROS);

            if (!archivo.exists()) {
                return null;
            }

            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()) {
                String linea = lector.nextLine();

                // Separar manualmente por el caracter |
                int pos1 = linea.indexOf('|');
                if (pos1 == -1) continue;

                String nombreParq = linea.substring(0, pos1);

                if (nombreParq.equals(nombre)) {
                    // Buscar segunda posicion
                    int pos2 = linea.indexOf('|', pos1 + 1);
                    if (pos2 == -1) continue;

                    // Buscar tercera posicion
                    int pos3 = linea.indexOf('|', pos2 + 1);
                    if (pos3 == -1) continue;

                    // Extraer los datos
                    String capStr = linea.substring(pos1 + 1, pos2);
                    String autoStr = linea.substring(pos2 + 1, pos3);
                    String motoStr = linea.substring(pos3 + 1);

                    int capacidad = Integer.parseInt(capStr);
                    int espaciosAuto = Integer.parseInt(autoStr);
                    int espaciosMoto = Integer.parseInt(motoStr);

                    // Crear el parqueadero
                    Parqueadero parqueadero = new Parqueadero(nombreParq, capacidad);

                    // Agregar espacios para autos
                    for (int i = 1; i <= espaciosAuto; i++) {
                        parqueadero.agregarEspacio(new Espacio(i, "AUTO"));
                    }

                    // Agregar espacios para motos
                    for (int i = espaciosAuto + 1; i <= capacidad; i++) {
                        parqueadero.agregarEspacio(new Espacio(i, "MOTO"));
                    }

                    lector.close();
                    return parqueadero;
                }
            }

            lector.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontro el archivo.");
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato del archivo.");
        }

        return null;
    }

    /**
     * Genera un reporte de vehiculos en un archivo de texto
     *
     * @param gestor El gestor del parqueadero
     * @return true si se genero correctamente, false si hubo error
     */
    public static boolean generarReporteVehiculos(GestorParqueadero gestor) {
        try {
            // Crear carpeta de reportes si no existe
            File carpeta = new File(CARPETA_REPORTES);
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            // Crear nombre de archivo con fecha y hora
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String nombreArchivo = CARPETA_REPORTES + "/reporte_" + ahora.format(formato) + ".txt";

            PrintWriter escritor = new PrintWriter(nombreArchivo);

            // Escribir encabezado
            escritor.println("========================================");
            escritor.println("    REPORTE DE VEHICULOS PARQUEADOS");
            escritor.println("========================================");
            escritor.println("Parqueadero: " + gestor.getParqueadero().getNombre());
            escritor.println("Fecha y Hora: " + ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            escritor.println();

            // Escribir informacion del parqueadero
            escritor.println("ESTADO DEL PARQUEADERO:");
            escritor.println("Capacidad Total: " + gestor.getParqueadero().getCapacidadTotal());
            escritor.println("Espacios Ocupados: " + gestor.getParqueadero().contarEspaciosOcupados());
            escritor.println("Espacios Disponibles: " + gestor.getParqueadero().contarEspaciosDisponibles());
            escritor.println();

            // Escribir lista de vehiculos
            ArrayList<Ticket> ticketsActivos = gestor.getTicketsActivos();

            if (ticketsActivos.isEmpty()) {
                escritor.println("No hay vehiculos parqueados actualmente.");
            } else {
                escritor.println("VEHICULOS PARQUEADOS (" + ticketsActivos.size() + "):");
                escritor.println("----------------------------------------");

                for (int i = 0; i < ticketsActivos.size(); i++) {
                    Ticket ticket = ticketsActivos.get(i);
                    Vehiculo vehiculo = ticket.getVehiculo();

                    escritor.println("Ticket #" + ticket.getId());
                    escritor.println("  Placa: " + vehiculo.getPlaca());
                    escritor.println("  Tipo: " + vehiculo.getTipo());
                    escritor.println("  Espacio: " + ticket.getEspacio().getNumero());
                    escritor.println("  Hora Entrada: " + vehiculo.getHoraEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    escritor.println("----------------------------------------");
                }
            }

            escritor.println();
            escritor.println("========================================");
            escritor.println("Fin del reporte");

            escritor.close();

            System.out.println("\nReporte generado exitosamente en: " + nombreArchivo);
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si existe el archivo de parqueaderos
     *
     * @return true si existe, false si no
     */
    public static boolean existenParqueaderos() {
        File archivo = new File(ARCHIVO_PARQUEADEROS);
        return archivo.exists() && archivo.length() > 0;
    }
}