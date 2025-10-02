package Modelo;

public class Usuario {
    private final String username;
    private final String password;
    private final String nombre;
    private double saldo;

    public Usuario(String username, String password, String nombre, double saldoInicial) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.saldo = saldoInicial;
    }

    public Usuario(String username, String password, String nombre) {
        this(username, password, nombre, 1000.0);
    }

    public boolean validarCredenciales(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public double getSaldo() {
        return saldo;
    }

    public void actualizarSaldo(double cambio) {
        this.saldo += cambio;
    }
}