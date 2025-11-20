package modelo;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Clase que representa un ticket de parqueo.
 * El ticket se genera cuando un vehiculo ingresa al parqueadero.
 *
 * @author Velez
 * @version 1.0
 */
public class Ticket {

    /**
     * Identificador unico del ticket
     */
    private int id;

    /**
     * Vehiculo asociado al ticket
     */
    private Vehiculo vehiculo;

    /**
     * Espacio asignado al vehiculo
     */
    private Espacio espacio;

    /**
     * Fecha y hora de entrada del vehiculo
     */
    private LocalDateTime horaEntrada;

    /**
     * Fecha y hora de salida del vehiculo
     */
    private LocalDateTime horaSalida;

    /**
     * Tarifa total a pagar
     */
    private double tarifa;

    /**
     * Contador estatico para generar IDs unicos
     */
    private static int contadorId = 1;

    /**
     * Constructor de la clase Ticket
     *
     * @param vehiculo El vehiculo del ticket
     * @param espacio El espacio asignado
     */
    public Ticket(Vehiculo vehiculo, Espacio espacio) {
        this.id = contadorId++;
        this.vehiculo = vehiculo;
        this.espacio = espacio;
        this.horaEntrada = vehiculo.getHoraEntrada();
        this.horaSalida = null;
        this.tarifa = 0.0;
    }

    /**
     * Obtiene el ID del ticket
     *
     * @return El ID del ticket
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el vehiculo del ticket
     *
     * @return El vehiculo
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Establece el vehiculo del ticket
     *
     * @param vehiculo El nuevo vehiculo
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * Obtiene el espacio del ticket
     *
     * @return El espacio
     */
    public Espacio getEspacio() {
        return espacio;
    }

    /**
     * Establece el espacio del ticket
     *
     * @param espacio El nuevo espacio
     */
    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    /**
     * Obtiene la hora de entrada
     *
     * @return La hora de entrada
     */
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    /**
     * Establece la hora de entrada
     *
     * @param horaEntrada La nueva hora de entrada
     */
    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    /**
     * Obtiene la hora de salida
     *
     * @return La hora de salida
     */
    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    /**
     * Establece la hora de salida
     *
     * @param horaSalida La nueva hora de salida
     */
    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    /**
     * Obtiene la tarifa del ticket
     *
     * @return La tarifa
     */
    public double getTarifa() {
        return tarifa;
    }

    /**
     * Establece la tarifa del ticket
     *
     * @param tarifa La nueva tarifa
     */
    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * Calcula el tiempo en horas que el vehiculo estuvo parqueado
     *
     * @return El numero de horas (redondeado hacia arriba)
     */
    public long calcularTiempo() {
        if (horaSalida == null) {
            horaSalida = LocalDateTime.now();
        }

        Duration duracion = Duration.between(horaEntrada, horaSalida);
        long minutos = duracion.toMinutes();

        // Redondear hacia arriba: cada hora iniciada se cobra completa
        long horas = (minutos + 59) / 60;

        // Minimo 1 hora
        if (horas < 1) {
            horas = 1;
        }

        return horas;
    }

    /**
     * Genera el recibo del ticket con toda la informacion
     *
     * @return String con el recibo formateado
     */
    public String generarRecibo() {
        StringBuilder recibo = new StringBuilder();
        recibo.append("\n========== RECIBO DE PARQUEO ==========\n");
        recibo.append("Ticket #: ").append(id).append("\n");
        recibo.append("Placa: ").append(vehiculo.getPlaca()).append("\n");
        recibo.append("Tipo: ").append(vehiculo.getTipo()).append("\n");
        recibo.append("Espacio: ").append(espacio.getNumero()).append("\n");
        recibo.append("Hora Entrada: ").append(horaEntrada).append("\n");
        recibo.append("Hora Salida: ").append(horaSalida).append("\n");
        recibo.append("Tiempo: ").append(calcularTiempo()).append(" hora(s)\n");
        recibo.append("Tarifa: $").append(String.format("%.2f", tarifa)).append("\n");
        recibo.append("=======================================\n");

        return recibo.toString();
    }
}