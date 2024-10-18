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
                String nombre = "Soldado" + soldadosCreados; // Nombre del soldado
                int nivelVida = random.nextInt(5) + 1; // Nivel de vida entre 1 y 5
                Soldado soldado = new Soldado(nombre, nivelVida, fila, columna);
                tablero[fila][columna] = soldado; // Asignar el soldado al tablero
                soldados[soldadosCreados] = soldado; // Guardar el soldado en el array
                soldadosCreados++; // Incrementar el contador de soldados creados
            }
        }
    }
    

    public void mostrarTablero() {
        // Imprimir la cabecera del tablero
        System.out.println("Tablero de Soldados:");
    
        // Crear una línea divisoria que ocupe todo el ancho del tablero
        String lineaDivisoria = new String(new char[100]).replace("\0", "-"); // Línea divisoria de 110 caracteres
    
        // Mostrar el tablero en formato de celdas
        for (int fila = 0; fila < 10; fila++) {
            // Imprimir línea divisoria para la fila
            System.out.println(lineaDivisoria);
            System.out.print("|"); // Iniciar la fila con una barra vertical
    
            for (int columna = 0; columna < 10; columna++) {
                // Variable para almacenar el contenido de la celda
                String celda = "         "; // Espacio vacío para 9 caracteres
    
                for (int i = 0; i < soldados.length; i++) {
                    if (soldados[i] != null && soldados[i].getFila() == fila && soldados[i].getColumna() == columna) {
                        // Si hay un soldado en esta posición, actualizamos la celda
                        celda = String.format("%-9s", soldados[i].getNombre()); // Asignar el nombre del soldado con formato
                        break;
                    }
                }
    
                // Imprimir la celda con el contenido
                System.out.print(celda + "|"); // Concatenar con el símbolo de celda
            }
            // Finalizar la fila
            System.out.println(); // Nueva línea para la siguiente fila
        }
    
        // Imprimir la línea divisoria al final
        System.out.println(lineaDivisoria); // Imprimir línea divisoria final
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
        boolean Cambiazo;
        for (int i = 0; i < cantidadSoldados - 1; i++) {
            Cambiazo = false;
            for (int j = 0; j < cantidadSoldados - 1 - i; j++) {
                if (soldados[j].getNombre().compareTo(soldados[j + 1].getNombre()) > 0) {
                    // Intercambio
                    Soldado temp = soldados[j];
                    soldados[j] = soldados[j + 1];
                    soldados[j + 1] = temp;
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