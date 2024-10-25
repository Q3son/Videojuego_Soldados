import java.util.*;
// BY: SUBIA_EDSON_FP2

public class Videojuego3 {

    private static final int TAMAÑO_TABLERO = 10; // Tablero de 10x10
    private ArrayList<ArrayList<Soldado>> tablero;
    private ArrayList<Soldado> soldados;
    private int cantidadSoldados = 0;

    // 1. Constructor para inicializar el juego con un número determinado de soldados
    public Videojuego3(int cantidad) {
        tablero = new ArrayList<>(TAMAÑO_TABLERO);
        for (int i = 0; i < TAMAÑO_TABLERO; i++){
            ArrayList<Soldado> fila = new ArrayList<>();
            for (int j = 0; j < TAMAÑO_TABLERO; j++){
                fila.add(null);
            }
            tablero.add(fila);
        }
        soldados = new ArrayList<>(cantidad);
        this.cantidadSoldados = cantidad;
        inicializarSoldados(); // Inicializa los soldados en el tablero
    }
    // 2. Método para crear soldados de manera aleatoria en el tablero
    private void inicializarSoldados() {
        Random random = new Random();
        int soldadosCreados = 0;

        while (soldadosCreados < cantidadSoldados) {
            int fila = random.nextInt(TAMAÑO_TABLERO);
            int columna = random.nextInt(TAMAÑO_TABLERO);

            if (tablero.get(fila).get(columna) == null) { // Verifica que el espacio esté vacío
                String nombre = "Soldado" + soldadosCreados; // Nombre del soldado
                int nivelVida = random.nextInt(5) + 1; // Nivel de vida entre 1 y 5
                Soldado soldado = new Soldado(nombre, nivelVida, fila, columna);

                tablero.get(fila).set(columna, soldado); // Asignar el soldado al tablero
                soldados.add(soldado); // Guardar el soldado en la lista
                soldadosCreados++; // Incrementar el contador de soldados creados
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
                for (Soldado soldado : soldados) {
                    if (soldado != null && soldado.getFila() == fila && soldado.getColumna() == columna) {
                        celda = String.format("%-9s", soldado.getNombre()); // Asignar el nombre del soldado
                        break;
                    }
                }
                System.out.print(celda + "|"); // Imprimir la celda
            }
            System.out.println(); // Nueva línea para la siguiente fila
        }
        
        System.out.println(lineaDivisoria); // Línea divisoria final
    }

    // 4. Método para encontrar el soldado con mayor nivel de vida
    public Soldado soldadoConMayorVida() {
        Soldado maxSoldado = soldados.get(0); // Asume que hay al menos un soldado
        for (int i = 1; i < cantidadSoldados; i++) {
            if (soldados.get(i).getNivelVida() > maxSoldado.getNivelVida()) {
                maxSoldado = soldados.get(i);
            }
        }
        return maxSoldado;
    }

    // 5. Método para calcular el promedio de nivel de vida de los soldados
    public double promedioNivelVida() {
        int suma = 0;
        for (int i = 0; i < cantidadSoldados; i++) {
            suma += soldados.get(i).getNivelVida();
        }
        return (double) suma / cantidadSoldados;
    }

    // 6. Método para calcular el nivel de vida total del ejército
    public int nivelVidaTotal() {
        int total = 0;
        for (int i = 0; i < cantidadSoldados; i++) {
            total += soldados.get(i).getNivelVida();
        }
        return total;
    }

    // 7. Método para mostrar los datos de todos los soldados
    public void mostrarDatosSoldados() {
        System.out.println("Datos de todos los soldados en orden de creación:");
        for (int i = 0; i < cantidadSoldados; i++) {
            System.out.println(soldados[i]);
        }
        System.out.println();
    }

    // 8. Método para clasificar soldados por poder (nivel de vida)
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

    // 9. Método para clasificar soldados por nombre
    public void rankingPorNombre() {
        // Ordenamiento burbuja (mejorado)
        boolean Cambiazo;
        for (int i = 0; i < cantidadSoldados - 1; i++) {
            Cambiazo = false;
            for (int j = 0; j < cantidadSoldados - 1 - i; j++) {
                if (soldados[j].getNombre().compareTo(soldados[j + 1].getNombre()) > 0) {
                    // Intercambio
                    Soldado temporal = soldados[j];
                    soldados[j] = soldados[j + 1];
                    soldados[j + 1] = temporal;
                    Cambiazo = true;
                }
            }
            if (!Cambiazo) break; // Si no hubo intercambios, el array ya está ordenado
        }

        System.out.println("Ranking de soldados por nombre:");
        for (int i = 0; i < cantidadSoldados; i++) {
            System.out.println(soldados[i]);
        }
        System.out.println();
    }

    // 10. Método principal para ejecutar el juego
    public static void main(String[] args) {
        Videojuego2 juego = new Videojuego2(10); // Cambia la cantidad de soldados si lo necesitas
        juego.mostrarTablero();
        System.out.println("Soldado con mayor nivel de vida: " + juego.soldadoConMayorVida());
        System.out.println("Promedio de nivel de vida: " + juego.promedioNivelVida());
        System.out.println("Nivel de vida total del ejército: " + juego.nivelVidaTotal());
        juego.mostrarDatosSoldados();
        juego.rankingDePoder(); // Método para ordenar por poder (nivel de vida)
        juego.rankingPorNombre(); // Método para ordenar por nombre
    }
}
