import java.util.*;
//BY: SUBIA_EDSON_FP2
public class Videojuego2 {
    
    private static final int TAMAÑO_TABLERO = 10; // Tablero de 10x10
    private Soldado[][] tablero;
    private Soldado[] soldados;
    private int cantidadSoldados;

    public Videojuego2(int cantidad) {
        tablero = new Soldado[TAMAÑO_TABLERO][TAMAÑO_TABLERO];
        soldados = new Soldado[cantidad];
        this.cantidadSoldados = cantidad;
        inicializarSoldados();
    }

    private void inicializarSoldados() {
        Random random = new Random();
        int soldadosCreados = 0;

        while (soldadosCreados < cantidadSoldados) {
            int fila = random.nextInt(TAMAÑO_TABLERO);
            int columna = random.nextInt(TAMAÑO_TABLERO);
            if (tablero[fila][columna] == null) { // Verifica que el espacio esté vacío
                String nombre = "Soldado" + soldadosCreados;
                int nivelVida = random.nextInt(5) + 1; // Nivel de vida entre 1 y 5
                Soldado soldado = new Soldado(nombre, nivelVida, fila, columna);
                tablero[fila][columna] = soldado;
                soldados[soldadosCreados] = soldado;
                soldadosCreados++;
            }
        }
    }

    public void mostrarTablero() {
        System.out.println("Tablero de soldados:");
        for (int i = 0; i < TAMAÑO_TABLERO; i++) {
            for (int j = 0; j < TAMAÑO_TABLERO; j++) {
                if (tablero[i][j] != null) {
                    System.out.print("| S" + tablero[i][j].getNombre().substring(7) + " "); // "S" y el número de soldado
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public Soldado soldadoConMayorVida() {
        Soldado maxSoldado = soldados[0]; // Asume que hay al menos un soldado
        for (int i = 1; i < cantidadSoldados; i++) {
            if (soldados[i].getNivelVida() > maxSoldado.getNivelVida()) {
                maxSoldado = soldados[i];
            }
        }
        return maxSoldado;
    }

    public double promedioNivelVida() {
        int suma = 0;
        for (int i = 0; i < cantidadSoldados; i++) {
            suma += soldados[i].getNivelVida();
        }
        return (double) suma / cantidadSoldados;
    }

    public int nivelVidaTotal() {
        int total = 0;
        for (int i = 0; i < cantidadSoldados; i++) {
            total += soldados[i].getNivelVida();
        }
        return total;
    }

    public void mostrarDatosSoldados() {
        System.out.println("Datos de todos los soldados en orden de creación:");
        for (int i = 0; i < cantidadSoldados; i++) {
            System.out.println(soldados[i]);
        }
        System.out.println();
    }

    public void rankingDePoder() {
        // Ordenamiento por selección (mejorado)
        for (int i = 0; i < cantidadSoldados - 1; i++) {
            int indexMax = i;
            for (int j = i + 1; j < cantidadSoldados; j++) {
                if (soldados[j].getNivelVida() > soldados[indexMax].getNivelVida()) {
                    indexMax = j;
                }
            }
            // Solo intercambiar si es necesario
            if (indexMax != i) {
                Soldado temp = soldados[indexMax];
                soldados[indexMax] = soldados[i];
                soldados[i] = temp;
            }
        }

        System.out.println("Ranking de poder de los soldados (ordenados por nivel de vida):");
        for (int i = 0; i < cantidadSoldados; i++) {
            System.out.println(soldados[i]);
        }
        System.out.println();
    }

    public void rankingPorNombre() {
        // Ordenamiento burbuja (mejorado para reducir el número de comparaciones)
        boolean swapped;
        for (int i = 0; i < cantidadSoldados - 1; i++) {
            swapped = false;
            for (int j = 0; j < cantidadSoldados - 1 - i; j++) {
                if (soldados[j].getNombre().compareTo(soldados[j + 1].getNombre()) > 0) {
                    // Intercambio
                    Soldado temp = soldados[j];
                    soldados[j] = soldados[j + 1];
                    soldados[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // Si no hubo intercambios, el array ya está ordenado
        }

        System.out.println("Ranking de soldados por nombre:");
        for (int i = 0; i < cantidadSoldados; i++) {
            System.out.println(soldados[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Videojuego2 juego = new Videojuego2(10); // Cambia la cantidad de soldados si lo necesitas
        juego.mostrarTablero();
        System.out.println("Soldado con mayor nivel de vida: " + juego.soldadoConMayorVida());
        System.out.println("Promedio de nivel de vida: " + juego.promedioNivelVida());
        System.out.println("Nivel de vida total del ejército: " + juego.nivelVidaTotal());
        juego.mostrarDatosSoldados();
        juego.rankingDePoder();
        juego.rankingPorNombre(); // Muestra ranking por nombre
    }
}