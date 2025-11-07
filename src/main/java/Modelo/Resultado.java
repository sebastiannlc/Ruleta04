package Modelo;

import java.io.Serializable;

public class Resultado implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int numero;
    private final double monto;
    private final boolean acierto;
    private final char tipoApuesta;
    private final double ganancia;

    public Resultado(int numero, double monto, boolean acierto, char tipoApuesta, double ganancia) {
        this.numero = numero;
        this.monto = monto;
        this.acierto = acierto;
        this.tipoApuesta = tipoApuesta;
        this.ganancia = ganancia;
    }

    public int getNumero() {
        return numero;
    }

    public double getMonto() {
        return monto;
    }

    public boolean isAcierto() {
        return acierto;
    }

    public char getTipoApuesta() {
        return tipoApuesta;
    }

    public double getGanancia() {
        return ganancia;
    }
}