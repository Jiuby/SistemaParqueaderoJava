package servicio;

import modelo.*;
import utilidad.Validador;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar todas las operaciones del parqueadero.
 * Maneja el registro de entradas, salidas y consultas.
 *
 * @author Velez
 * @version 1.0
 */
public class GestorParqueadero {

    /**
     * El parqueadero que se esta gestionando
     */
    private Parqueadero parqueadero;

    /**
     * Lista de tickets activos (vehiculos actualmente parqueados)
     */
    private ArrayList<Ticket> ticketsActivos;

    /**
     * Lista de tickets finalizados (historico)
     */
    private ArrayList<Ticket> ticketsFinalizados;

    /**
     * Constructor de la clase GestorParqueadero
     *
     * @param parqueadero El parqueadero a gestionar
     */
    public GestorParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
        this.ticketsActivos = new ArrayList<Ticket>();
        this.ticketsFinalizados = new ArrayList<Ticket>();
    }

    /**
     * Obtiene el parqueadero
     *
     * @return El parqueadero
     */
    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    /**
     * Obtiene la lista de tickets activos
     *
     * @return La lista de tickets activos
     */
    public ArrayList<Ticket> getTicketsActivos() {
        return ticketsActivos;
    }

    /**
     * Obtiene la lista de tickets finalizados
     *
     * @return La lista de tickets finalizados
     */
    public ArrayList<Ticket> getTicketsFinalizados() {
        return ticketsFinalizados;
    }

    /**
     * Registra la entrada de un vehiculo al parqueadero
     *
     * @param placa La placa del vehiculo
     * @return El ticket generado o null si no se pudo registrar
     */
    public Ticket registrarEntrada(String placa) {
        // Validar la placa
        if (!Validador.validarPlaca(placa)) {
            System.out.println("Error: Placa invalida");
            return null;
        }

        // Convertir placa a mayusculas
        placa = placa.trim().toUpperCase();

        // Verificar si el vehiculo ya esta en el parqueadero
        if (buscarTicketPorPlaca(placa) != null) {
            System.out.println("Error: El vehiculo con placa " + placa + " ya esta en el parqueadero");
            return null;
        }

        // Identificar tipo de vehiculo
        String tipoVehiculo = Validador.identificarTipoVehiculo(placa);
        if (tipoVehiculo == null) {
            System.out.println("Error: No se pudo identificar el tipo de vehiculo");
            return null;
        }

        // Buscar espacio disponible
        Espacio espacioDisponible = parqueadero.buscarEspacioDisponible(tipoVehiculo);
        if (espacioDisponible == null) {
            System.out.println("Error: No hay espacios disponibles para " + tipoVehiculo);
            return null;
        }

        // Crear el vehiculo
        Vehiculo vehiculo;
        if (tipoVehiculo.equals("AUTO")) {
            vehiculo = new Auto(placa);
        } else {
            vehiculo = new Moto(placa);
        }

        // Ocupar el espacio
        espacioDisponible.ocupar(vehiculo);

        // Crear el ticket
        Ticket ticket = new Ticket(vehiculo, espacioDisponible);
        ticketsActivos.add(ticket);

        System.out.println("\n=== ENTRADA REGISTRADA ===");
        System.out.println("Placa: " + placa);
        System.out.println("Tipo: " + tipoVehiculo);
        System.out.println("Espacio asignado: " + espacioDisponible.getNumero());
        System.out.println("Ticket #: " + ticket.getId());
        System.out.println("Hora entrada: " + ticket.getHoraEntrada());
        System.out.println("=========================\n");

        return ticket;
    }

    /**
     * Registra la salida de un vehiculo del parqueadero
     *
     * @param placa La placa del vehiculo
     * @return El ticket finalizado o null si no se encontro
     */
    public Ticket registrarSalida(String placa) {
        // Convertir placa a mayusculas
        placa = placa.trim().toUpperCase();

        // Buscar el ticket activo
        Ticket ticket = buscarTicketPorPlaca(placa);
        if (ticket == null) {
            System.out.println("Error: No se encontro el vehiculo con placa " + placa);
            return null;
        }

        // Establecer hora de salida
        ticket.setHoraSalida(LocalDateTime.now());

        // Calcular tiempo y tarifa
        long horas = ticket.calcularTiempo();
        double tarifa = CalculadorTarifa.calcularTarifa(ticket.getVehiculo().getTipo(), horas);
        ticket.setTarifa(tarifa);

        // Liberar el espacio
        ticket.getEspacio().liberar();

        // Mover el ticket de activos a finalizados
        ticketsActivos.remove(ticket);
        ticketsFinalizados.add(ticket);

        // Mostrar recibo
        System.out.println(ticket.generarRecibo());

        return ticket;
    }

    /**
     * Busca un ticket activo por placa de vehiculo
     *
     * @param placa La placa a buscar
     * @return El ticket encontrado o null si no existe
     */
    public Ticket buscarTicketPorPlaca(String placa) {
        placa = placa.trim().toUpperCase();
        for (int i = 0; i < ticketsActivos.size(); i++) {
            Ticket ticket = ticketsActivos.get(i);
            if (ticket.getVehiculo().getPlaca().equals(placa)) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Consulta la disponibilidad de espacios en el parqueadero
     *
     * @return String con el reporte de disponibilidad
     */
    public String consultarDisponibilidad() {
        StringBuilder reporte = new StringBuilder();
        reporte.append(parqueadero.mostrarInformacion());

        // Contar espacios por tipo
        int espaciosAutoDisponibles = 0;
        int espaciosMotoDisponibles = 0;
        int espaciosAutoOcupados = 0;
        int espaciosMotoOcupados = 0;

        ArrayList<Espacio> espacios = parqueadero.getEspacios();
        for (int i = 0; i < espacios.size(); i++) {
            Espacio espacio = espacios.get(i);
            if (espacio.getTipoVehiculo().equals("AUTO")) {
                if (espacio.estaDisponible()) {
                    espaciosAutoDisponibles++;
                } else {
                    espaciosAutoOcupados++;
                }
            } else if (espacio.getTipoVehiculo().equals("MOTO")) {
                if (espacio.estaDisponible()) {
                    espaciosMotoDisponibles++;
                } else {
                    espaciosMotoOcupados++;
                }
            }
        }

        reporte.append("\n--- Detalle por Tipo ---\n");
        reporte.append("AUTOS - Disponibles: ").append(espaciosAutoDisponibles);
        reporte.append(" | Ocupados: ").append(espaciosAutoOcupados).append("\n");
        reporte.append("MOTOS - Disponibles: ").append(espaciosMotoDisponibles);
        reporte.append(" | Ocupados: ").append(espaciosMotoOcupados).append("\n");

        return reporte.toString();
    }

    /**
     * Lista todos los vehiculos actualmente parqueados
     *
     * @return String con la lista de vehiculos
     */
    public String listarVehiculosParqueados() {
        if (ticketsActivos.isEmpty()) {
            return "\nNo hay vehiculos parqueados actualmente.\n";
        }

        StringBuilder lista = new StringBuilder();
        lista.append("\n========== VEHICULOS PARQUEADOS ==========\n");

        for (int i = 0; i < ticketsActivos.size(); i++) {
            Ticket ticket = ticketsActivos.get(i);
            lista.append("Ticket #").append(ticket.getId()).append(" | ");
            lista.append(ticket.getVehiculo().mostrarInformacion());
            lista.append(" | Espacio: ").append(ticket.getEspacio().getNumero()).append("\n");
        }

        lista.append("==========================================\n");

        return lista.toString();
    }

    /**
     * Genera un reporte de ocupacion del parqueadero
     *
     * @return String con el reporte completo
     */
    public String generarReporteOcupacion() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n========== REPORTE DE OCUPACION ==========\n");
        reporte.append("Parqueadero: ").append(parqueadero.getNombre()).append("\n");
        reporte.append("Capacidad Total: ").append(parqueadero.getCapacidadTotal()).append("\n");
        reporte.append("Espacios Ocupados: ").append(parqueadero.contarEspaciosOcupados()).append("\n");
        reporte.append("Espacios Disponibles: ").append(parqueadero.contarEspaciosDisponibles()).append("\n");

        // Calcular porcentaje de ocupacion
        double porcentaje = (parqueadero.contarEspaciosOcupados() * 100.0) / parqueadero.getCapacidadTotal();
        reporte.append("Porcentaje de Ocupacion: ").append(String.format("%.2f", porcentaje)).append("%\n");

        reporte.append("\nVehiculos Actualmente Parqueados: ").append(ticketsActivos.size()).append("\n");
        reporte.append("Total Vehiculos Atendidos Hoy: ").append(ticketsFinalizados.size()).append("\n");
        reporte.append("==========================================\n");

        return reporte.toString();
    }
}