package Controlador;

import Modelo.Usuario;

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

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean manejarCambioSaldo(double cambio) {
        if (usuarioActual == null) {
            System.err.println("Error: No hay sesión activa para gestionar el saldo.");
            return false;
        }

        if (cambio < 0 && usuarioActual.getSaldo() < Math.abs(cambio)) {
            return false;
        }

        usuarioActual.actualizarSaldo(cambio);
        return true;
    }

    public String getNombreUsuario() {
        return (usuarioActual != null) ? usuarioActual.getNombre() : "Invitado";
    }
}