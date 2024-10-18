//BY: SUBIA_EDSON_FP2
public class Soldado {
    private String nombre;
    private int nivelVida;
    private int fila;
    private int columna;

    public Soldado(String nombre, int nivelVida, int fila, int columna) {
        this.nombre = nombre;
        this.nivelVida = nivelVida;
        this.fila = fila;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivelVida() {
        return nivelVida;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Nivel de vida: " + nivelVida + ", Posición: (" + fila + ", " + columna + ")";
    }
}
