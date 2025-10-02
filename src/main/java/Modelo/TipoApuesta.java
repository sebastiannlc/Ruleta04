package Modelo;

public enum TipoApuesta {
    ROJO('R'),
    NEGRO('N'),
    PAR('P'),
    IMPAR('I');

    private final char valor;

    TipoApuesta(char valor) {
        this.valor = valor;
    }

    public char getValor() {
        return valor;
    }
}
