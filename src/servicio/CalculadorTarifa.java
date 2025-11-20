package servicio;

/**
 * Clase encargada de calcular las tarifas de parqueo.
 * Las tarifas son diferentes para autos y motos.
 *
 * @author Velez
 * @version 1.0
 */
public class CalculadorTarifa {

    /**
     * Tarifa por hora para autos
     */
    private static final double TARIFA_AUTO = 3000.0;

    /**
     * Tarifa por hora para motos
     */
    private static final double TARIFA_MOTO = 2000.0;

    /**
     * Calcula la tarifa total segun el tipo de vehiculo y las horas parqueadas
     *
     * @param tipoVehiculo El tipo de vehiculo (AUTO o MOTO)
     * @param horas El numero de horas parqueadas
     * @return La tarifa total a pagar
     */
    public static double calcularTarifa(String tipoVehiculo, long horas) {
        double tarifaPorHora = 0.0;

        if (tipoVehiculo.equals("AUTO")) {
            tarifaPorHora = TARIFA_AUTO;
        } else if (tipoVehiculo.equals("MOTO")) {
            tarifaPorHora = TARIFA_MOTO;
        }

        return tarifaPorHora * horas;
    }

    /**
     * Obtiene la tarifa por hora para un tipo de vehiculo
     *
     * @param tipoVehiculo El tipo de vehiculo (AUTO o MOTO)
     * @return La tarifa por hora
     */
    public static double obtenerTarifaPorHora(String tipoVehiculo) {
        if (tipoVehiculo.equals("AUTO")) {
            return TARIFA_AUTO;
        } else if (tipoVehiculo.equals("MOTO")) {
            return TARIFA_MOTO;
        }
        return 0.0;
    }

    /**
     * Muestra las tarifas actuales del parqueadero
     *
     * @return String con las tarifas
     */
    public static String mostrarTarifas() {
        StringBuilder tarifas = new StringBuilder();
        tarifas.append("\n========== TARIFAS ==========\n");
        tarifas.append("AUTO: $").append(String.format("%.2f", TARIFA_AUTO)).append(" por hora\n");
        tarifas.append("MOTO: $").append(String.format("%.2f", TARIFA_MOTO)).append(" por hora\n");
        tarifas.append("=============================\n");

        return tarifas.toString();
    }
}