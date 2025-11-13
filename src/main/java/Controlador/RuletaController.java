package Controlador;

import Modelo.ApuestaBase;
import Modelo.Resultado;
import Modelo.Ruleta;
import Modelo.Usuario;

public class RuletaController {

    private final Ruleta ruleta = new Ruleta();

    public Resultado jugarRonda(ApuestaBase apuesta) {

        // 1. Obtener usuario de la sesión
        Usuario usuario = SessionController.getInstancia().getUsuarioActual();
        if (usuario == null) {
            throw new IllegalStateException("No hay sesión de usuario activa.");
        }

        // 2. Verificar saldo
        if (usuario.getSaldo() < apuesta.getMonto()) {
            throw new IllegalStateException("Saldo insuficiente para realizar la apuesta.");
        }

        // 3. Girar Ruleta
        int numeroGanador = ruleta.girar();
        String colorGanador = Ruleta.getColor(numeroGanador);

        // 4. Evaluar Apuesta (Polimorfismo)
        boolean acierto = apuesta.acierta(numeroGanador, colorGanador);

        double gananciaNeta = 0;
        if (acierto) {
            // Ganancia = Apuesta * 2 - Apuesta inicial (es decir, Apuesta * 1)
            gananciaNeta = apuesta.getMonto();
        } else {
            // Pérdida = - Apuesta inicial
            gananciaNeta = -apuesta.getMonto();
        }

        // 5. Actualizar Saldo del Usuario
        usuario.actualizarSaldo(gananciaNeta);

        // 6. Crear Objeto Resultado
        Resultado resultadoRonda = new Resultado(
                numeroGanador,
                apuesta.getMonto(),
                acierto,
                apuesta.getEtiqueta(),
                gananciaNeta
        );

        // 7. REGISTRAR RESULTADO (Delegación)
        // Historial Personal (Asociación directa, se persiste con el usuario)
        usuario.agregarResultado(resultadoRonda);

        // Historial Global (Acceso al repositorio inyectado en ResultadoController)
        ResultadoController.registrarResultado(resultadoRonda);

        return resultadoRonda;
    }
}