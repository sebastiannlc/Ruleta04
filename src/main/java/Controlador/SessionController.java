package Controlador;

import Modelo.Usuario;
import Modelo.Resultado;
import java.util.List;

/**
 * Singleton: Gestiona la sesión del usuario activo (login, logout, datos personales).
 */
public class SessionController {

    private static SessionController instancia;
    private Usuario usuarioActual;

    private SessionController() {
    }

    public static SessionController getInstancia() {
        if (instancia == null) {
            instancia = new SessionController();
        }
        return instancia;
    }

    // Gestión de Sesión
    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public String getNombreUsuario() {
        return (usuarioActual != null) ? usuarioActual.getNombre() : "Invitado";
    }

    // Gestión de Saldo
    public boolean manejarCambioSaldo(double cambio) {
        if (usuarioActual == null) {
            System.err.println("Error: No hay sesión activa para gestionar el saldo.");
            return false;
        }

        // Si es una apuesta (cambio < 0), verifica saldo.
        if (cambio < 0 && usuarioActual.getSaldo() < Math.abs(cambio)) {
            return false; // Saldo insuficiente
        }

        usuarioActual.actualizarSaldo(cambio);
        return true;
    }

    // Gestión de Historial
    public List<Resultado> getHistorialPersonal() {
        if (usuarioActual != null) {
            return usuarioActual.getHistorialPersonal();
        }
        return List.of();
    }
}