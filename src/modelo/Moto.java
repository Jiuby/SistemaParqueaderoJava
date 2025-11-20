package modelo;

/**
 * Clase que representa una motocicleta en el parqueadero.
 * Hereda de la clase Vehiculo.
 * Las motos se identifican porque su placa termina en letra.
 *
 * @author Velez
 * @version 1.0
 */
public class Moto extends Vehiculo {

    /**
     * Constructor de la clase Moto
     *
     * @param placa La placa de la moto
     */
    public Moto(String placa) {
        super(placa, "MOTO");
    }

    /**
     * Muestra la informacion de la moto
     *
     * @return String con la informacion de la moto
     */
    public String mostrarInformacion() {
        return "MOTO - Placa: " + getPlaca() + " - Entrada: " + getHoraEntrada();
    }
}