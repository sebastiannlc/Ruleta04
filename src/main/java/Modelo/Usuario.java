package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String password;
    private final String nombre;
    private double saldo;

    private final List<Resultado> historialPersonal = new ArrayList<>();

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

    public double getSaldo() {
        return saldo;
    }

    public void actualizarSaldo(double cambio) {
        this.saldo += cambio;
    }

    public void agregarResultado(Resultado resultado) {
        this.historialPersonal.add(resultado);
    }

    public List<Resultado> getHistorialPersonal() {
        return historialPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }
}