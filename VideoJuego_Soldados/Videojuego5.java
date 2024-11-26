import java.util.*;
// BY: SUBIA_EDSON_FP2

public class Videojuego5 {

    private static final int TAMAÑO_TABLERO = 10; // Tablero de 10x10
    private Soldado[][] tablero;
    private HashMap<String, Soldado> ejercito1; // NUEVO: HashMap para Ejército 1
    private HashMap<String, Soldado> ejercito2; // NUEVO: HashMap para Ejército 2

    // 1. Constructor para inicializar el juego con soldados en ambos ejércitos
    public Videojuego5(int cantidadSoldadosEjercito1, int cantidadSoldadosEjercito2) {
        tablero = new Soldado[TAMAÑO_TABLERO][TAMAÑO_TABLERO];
        ejercito1 = new HashMap<>();
        ejercito2 = new HashMap<>();

        inicializarEjercito(ejercito1, cantidadSoldadosEjercito1, "X1");
        inicializarEjercito(ejercito2, cantidadSoldadosEjercito2, "X2");
    }

    // 2. Método para inicializar un ejército con soldados aleatorios
    private void inicializarEjercito(HashMap<String, Soldado> ejercito, int cantidad, String etiquetaEjercito) {
        Random random = new Random();
        int soldadosCreados = 0;

        while (soldadosCreados < cantidad) {
            int fila = random.nextInt(TAMAÑO_TABLERO);
            int columna = random.nextInt(TAMAÑO_TABLERO);

            if (tablero[fila][columna] == null) { // Verifica que el espacio esté vacío
                String nombre = "Soldado" + soldadosCreados + etiquetaEjercito;
                int nivelVida = random.nextInt(5) + 1;
                Soldado soldado = new Soldado(nombre, nivelVida, fila, columna);

                tablero[fila][columna] = soldado; // Asignar el soldado al tablero
                ejercito.put(nombre, soldado); // Guardar el soldado en el HashMap
                soldadosCreados++;
            }
        }
    }


    // 3. Método para mostrar el tablero en la consola
    public void mostrarTablero() {
        System.out.println("Tablero de Soldados:");
        for (int fila = 0; fila < TAMAÑO_TABLERO; fila++) {
            for (int columna = 0; columna < TAMAÑO_TABLERO; columna++) {
                if (tablero[fila][columna] == null) {
                    System.out.print("[     ] ");
                } else {
                    String nombre = tablero[fila][columna].getNombre();
                    System.out.print("[" + nombre + "] ");
                }
            }
            System.out.println();
        }
    }

    // 4. Método para obtener el soldado con mayor nivel de vida en un ejército
    public Soldado soldadoConMayorVida(HashMap<String, Soldado> ejercito) {
        return Collections.max(ejercito.values(), Comparator.comparingInt(Soldado::getNivelVida));
    }

    // 5. Método para calcular el promedio de nivel de vida en un ejército
    public double promedioNivelVida(HashMap<String, Soldado> ejercito) {
        int suma = ejercito.values().stream().mapToInt(Soldado::getNivelVida).sum();
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

    // 8. Método para ordenar soldados de un ejército por poder (nivel de vida) - Algoritmo de selección (mejorado)
    public void rankingDePoder(ArrayList<Soldado> ejercito) {
        for (int i = 0; i < ejercito.size() - 1; i++) {
            int indexMax = i;
            Soldado maxSoldado = ejercito.get(i); // Guardar el soldado actual como máximo
    
            // Encontrar el soldado con el nivel de vida más alto en la sublista sin ordenar
            for (int j = i + 1; j < ejercito.size(); j++) {
                if (ejercito.get(j).getNivelVida() > maxSoldado.getNivelVida()) {
                    indexMax = j;
                    maxSoldado = ejercito.get(j);
                }
            }
    
            // Solo hacer el intercambio si el índice del máximo ha cambiado
            if (indexMax != i) {
                ejercito.set(indexMax, ejercito.get(i));
                ejercito.set(i, maxSoldado);
            }
        }
    
        System.out.println("Ranking de poder de soldados en " + (ejercito == ejercito1 ? "Ejército 1" : "Ejército 2") + " (ordenados por nivel de vida):");
        for (Soldado soldado : ejercito) {
            System.out.println(soldado);
        }
        System.out.println();
    }
    // 9. Nuevo método para ordenar soldados de un ejército por nombre (algoritmo de burbuja)
    public void ordenarPorNombre(ArrayList<Soldado> ejercito) {
        boolean intercambioRealizado;
        do {
            intercambioRealizado = false;
            for (int i = 0; i < ejercito.size() - 1; i++) {
                if (ejercito.get(i).getNombre().compareTo(ejercito.get(i + 1).getNombre()) > 0) {
                    Soldado temp = ejercito.get(i);
                    ejercito.set(i, ejercito.get(i + 1));
                    ejercito.set(i + 1, temp);
                    intercambioRealizado = true;
                }
            }
        } while (intercambioRealizado);

        System.out.println("Soldados en " + (ejercito == ejercito1 ? "Ejército 1" : "Ejército 2") + " ordenados por nombre:");
        for (Soldado soldado : ejercito) {
            System.out.println(soldado);
        }
        System.out.println();
    }

    // 10. Método para decidir cuál ejército gana la batalla en función del total de vida
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

    // 10. Método principal para ejecutar el juego
    public static void main(String[] args) {
        Scanner scanPro = new Scanner(System.in);
        boolean jugarOtraVez = true;
    
        while (jugarOtraVez) {
            Videojuego4 juego = new Videojuego4(10, 10); // Inicia el juego con 10 soldados en cada ejército
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
    
            System.out.print("¿Quieres jugar otra vez? (s/n): ");
            String respuesta = scanPro.nextLine().trim().toLowerCase();
            jugarOtraVez = respuesta.equals("s");
        }
        System.out.println("¡Gracias por jugar!");
    }
}