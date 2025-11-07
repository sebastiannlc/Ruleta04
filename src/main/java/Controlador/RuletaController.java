package Controlador;

import Modelo.*;

/**
 * Coordina el Modelo (Ruleta) y la lógica de negocio (saldo, registro).
 */
public class RuletaController {

    public static final double PAGO_MULTIPLICADOR = 2.0;

    public Resultado jugarRonda(ApuestaBase apuesta) {
        Usuario usuarioActual = SessionController.getInstancia().getUsuarioActual();

        if (usuarioActual == null) {
            throw new IllegalStateException("No hay un usuario activo para jugar.");
        }

        // 1. Descontar el monto de la apuesta. El saldo debe ser suficiente.
        if (!SessionController.getInstancia().manejarCambioSaldo(-apuesta.getMonto())) {
            throw new IllegalStateException("Saldo insuficiente para realizar la apuesta.");
        }

        int numeroGanador = Ruleta.girar();
        String colorGanador = Ruleta.getColor(numeroGanador);

        boolean acierto = apuesta.acierta(numeroGanador, colorGanador);

        double gananciaBruta = acierto ? apuesta.getMonto() * PAGO_MULTIPLICADOR : 0;
        double gananciaNeta = gananciaBruta - apuesta.getMonto(); // Ganancia/Pérdida neta

        // Crear el Objeto Resultado
        Resultado resultado = new Resultado(
                numeroGanador,
                apuesta.getMonto(),
                acierto,
                apuesta.getEtiqueta(),
                gananciaNeta
        );

        // 2. Registro y Actualización de Saldo Final
        ResultadoController.registrarResultado(resultado);
        usuarioActual.agregarResultado(resultado);

        // Aplicar la ganancia (solo la ganancia bruta, la pérdida ya se descontó)
        if (gananciaBruta > 0) {
            SessionController.getInstancia().manejarCambioSaldo(gananciaBruta);
        }

        return resultado;
    }
}