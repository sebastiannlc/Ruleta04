package Controlador;

import Modelo.Ruleta;
import Modelo.Resultado;
import Modelo.TipoApuesta; // Importación del Enum

public class RuletaController {

    public Resultado jugarRonda(double monto, TipoApuesta tipoApuesta) {
        char tipoApuestaChar = tipoApuesta.getValor();

        int numeroGanador = Ruleta.girar();

        boolean acierto = Ruleta.evaluarApuesta(numeroGanador, tipoApuestaChar);

        double gananciaNeta = acierto
                ? monto * ResultadoController.PAGO_MULTIPLICADOR
                : -monto;

        Resultado resultado = new Resultado(
                numeroGanador,
                monto,
                acierto,
                tipoApuestaChar,
                gananciaNeta
        );

        ResultadoController.registrarResultado(resultado);

        return resultado;
    }
}