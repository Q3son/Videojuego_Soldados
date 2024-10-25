import java.util.*;
// BY: SUBIA_EDSON_FP2

public class Videojuego3 {

    private static final int TAMAÑO_TABLERO = 10; // Tablero de 10x10
    private ArrayList<ArrayList<Soldado>> tablero;
    private ArrayList<Soldado> ejercito1;
    private ArrayList<Soldado> ejercito2;

    // 1. Constructor para inicializar el juego con soldados en ambos ejércitos
    public Videojuego3(int cantidadSoldadosEjercito1, int cantidadSoldadosEjercito2) {
        tablero = new ArrayList<>(TAMAÑO_TABLERO);
        for (int i = 0; i < TAMAÑO_TABLERO; i++) {
            tablero.add(new ArrayList<>(TAMAÑO_TABLERO));
            for (int j = 0; j < TAMAÑO_TABLERO; j++) {
                tablero.get(i).add(null);
            }
        }
        
        ejercito1 = new ArrayList<>(cantidadSoldadosEjercito1);
        ejercito2 = new ArrayList<>(cantidadSoldadosEjercito2);

        inicializarEjercito(ejercito1, cantidadSoldadosEjercito1, "X1");
        inicializarEjercito(ejercito2, cantidadSoldadosEjercito2, "X2");
    }

    // 2. Método para inicializar un ejército con soldados aleatorios
    private void inicializarEjercito(ArrayList<Soldado> ejercito, int cantidad, String etiquetaEjercito) {
        Random random = new Random();
        int soldadosCreados = 0;

        while (soldadosCreados < cantidad) {
            int fila = random.nextInt(TAMAÑO_TABLERO);
            int columna = random.nextInt(TAMAÑO_TABLERO);
            if (tablero.get(fila).get(columna) == null) { // Verifica que el espacio esté vacío
                String nombre = "Soldado" + soldadosCreados + etiquetaEjercito;
                int nivelVida = random.nextInt(5) + 1;
                Soldado soldado = new Soldado(nombre, nivelVida, fila, columna);
                
                tablero.get(fila).set(columna, soldado); // Asignar el soldado al tablero
                ejercito.add(soldado); // Guardar el soldado en el ejército
                soldadosCreados++;
            }
        }
    }

    // 3. Método para mostrar el tablero en la consola
    public void mostrarTablero() {
        System.out.println("Tablero de Soldados:");
        String lineaDivisoria = new String(new char[100]).replace("\0", "-");

        for (int fila = 0; fila < TAMAÑO_TABLERO; fila++) {
            System.out.println(lineaDivisoria);
            System.out.print("|");

            for (int columna = 0; columna < TAMAÑO_TABLERO; columna++) {
                String celda = "         "; // Espacio vacío
                Soldado soldado = tablero.get(fila).get(columna);
                
                if (soldado != null) {
                    celda = String.format("%-9s", soldado.getNombre()); // Nombre del soldado
                }
                
                System.out.print(celda + "|");
            }
            System.out.println();
        }
        System.out.println(lineaDivisoria); // Línea divisoria final
    }

    // 4. Método para mostrar datos del soldado con mayor nivel de vida de un ejército
    public Soldado soldadoConMayorVida(ArrayList<Soldado> ejercito) {
        Soldado maxSoldado = ejercito.get(0);
        for (Soldado soldado : ejercito) {
            if (soldado.getNivelVida() > maxSoldado.getNivelVida()) {
                maxSoldado = soldado;
            }
        }
        return maxSoldado;
    }

    // 5. Método para calcular el promedio de nivel de vida de un ejército
    public double promedioNivelVida(ArrayList<Soldado> ejercito) {
        int suma = 0;
        for (Soldado soldado : ejercito) {
            suma += soldado.getNivelVida();
        }
        return (double) suma / ejercito.size();
    }

    // 6. Método para calcular el nivel de vida total de un ejército
    public int nivelVidaTotal(ArrayList<Soldado> ejercito) {
        int total = 0;
        for (Soldado soldado : ejercito) {
            total += soldado.getNivelVida();
        }
        return total;
    }

    // 7. Método para mostrar datos de los soldados de un ejército
    public void mostrarDatosEjercito(ArrayList<Soldado> ejercito, String nombreEjercito) {
        System.out.println("Datos de los soldados en " + nombreEjercito + " en orden de creación:");
        for (Soldado soldado : ejercito) {
            System.out.println(soldado);
        }
        System.out.println();
    }

    // Método para ordenar soldados de un ejército por poder (nivel de vida) - Algoritmo de selección
    public void rankingDePoder(ArrayList<Soldado> ejercito) {
        for (int i = 0; i < ejercito.size() - 1; i++) {
            int indexMax = i;
            for (int j = i + 1; j < ejercito.size(); j++) {
                if (ejercito.get(j).getNivelVida() > ejercito.get(indexMax).getNivelVida()) {
                    indexMax = j;
                }
            }
            if (indexMax != i) {
                Soldado temp = ejercito.get(indexMax);
                ejercito.set(indexMax, ejercito.get(i));
                ejercito.set(i, temp);
            }
        }
        
        System.out.println("Ranking de poder de soldados en " + (ejercito == ejercito1 ? "Ejército 1" : "Ejército 2") + " (ordenados por nivel de vida):");
        for (Soldado soldado : ejercito) {
            System.out.println(soldado);
        }
        System.out.println();
    }

    // 8. Método para decidir cuál ejército gana la batalla en función del total de vida
    public void determinarGanador() {
        int vidaTotalEjercito1 = nivelVidaTotal(ejercito1);
        int vidaTotalEjercito2 = nivelVidaTotal(ejercito2);

        System.out.println("Vida total del Ejército 1: " + vidaTotalEjercito1);
        System.out.println("Vida total del Ejército 2: " + vidaTotalEjercito2);
        
        if (vidaTotalEjercito1 > vidaTotalEjercito2) {
            System.out.println("¡Ejército 1 gana la batalla!");
        } else if (vidaTotalEjercito2 > vidaTotalEjercito1) {
            System.out.println("¡Ejército 2 gana la batalla!");
        } else {
            System.out.println("La batalla termina en empate.");
        }
    }

    // 9. Método principal para ejecutar el juego
    public static void main(String[] args) {
        Videojuego3 juego = new Videojuego3(10, 10); // Inicia el juego con 10 soldados en cada ejército
        juego.mostrarTablero();
        
        System.out.println("Soldado con mayor nivel de vida en Ejército 1: " + juego.soldadoConMayorVida(juego.ejercito1));
        System.out.println("Soldado con mayor nivel de vida en Ejército 2: " + juego.soldadoConMayorVida(juego.ejercito2));

        System.out.println("Promedio de nivel de vida en Ejército 1: " + juego.promedioNivelVida(juego.ejercito1));
        System.out.println("Promedio de nivel de vida en Ejército 2: " + juego.promedioNivelVida(juego.ejercito2));

        juego.mostrarDatosEjercito(juego.ejercito1, "Ejército 1");
        juego.mostrarDatosEjercito(juego.ejercito2, "Ejército 2");

        juego.rankingDePoder(juego.ejercito1);
        juego.rankingDePoder(juego.ejercito2);

        juego.determinarGanador();
    }
}