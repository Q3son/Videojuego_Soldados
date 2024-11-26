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

    // 6. Método para ordenar soldados usando MergeSort (NUEVO)
    public List<Soldado> rankingMergeSort(HashMap<String, Soldado> ejercito) {
        List<Soldado> lista = new ArrayList<>(ejercito.values());
        mergeSort(lista, 0, lista.size() - 1);
        return lista;
    }

    private void mergeSort(List<Soldado> lista, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(lista, left, mid);
            mergeSort(lista, mid + 1, right);

            merge(lista, left, mid, right);
        }
    }

    private void merge(List<Soldado> lista, int left, int mid, int right) {
        List<Soldado> temp = new ArrayList<>(lista);
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (temp.get(i).getNivelVida() >= temp.get(j).getNivelVida()) {
                lista.set(k++, temp.get(i++));
            } else {
                lista.set(k++, temp.get(j++));
            }
        }

        while (i <= mid) {
            lista.set(k++, temp.get(i++));
        }
    }

    // 7. Método para determinar el ejército ganador
    public void determinarGanador() {
        int vidaTotalEjercito1 = ejercito1.values().stream().mapToInt(Soldado::getNivelVida).sum();
        int vidaTotalEjercito2 = ejercito2.values().stream().mapToInt(Soldado::getNivelVida).sum();

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

    // 8. Método principal para ejecutar el juego
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

            List<Soldado> ranking1 = juego.rankingMergeSort(juego.ejercito1);
            List<Soldado> ranking2 = juego.rankingMergeSort(juego.ejercito2);

            System.out.println("Ranking de poder del Ejército 1:");
            ranking1.forEach(System.out::println);

            System.out.println("Ranking de poder del Ejército 2:");
            ranking2.forEach(System.out::println);

            juego.determinarGanador();

            System.out.print("¿Quieres jugar otra vez? (s/n): ");
            String respuesta = scanPro.nextLine().trim().toLowerCase();
            jugarOtraVez = respuesta.equals("s");
        }
        System.out.println("¡Gracias por jugar!");
    }
}