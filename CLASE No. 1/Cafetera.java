/*
 *  CONTEXTO:
 *  En una oficina hay UNA sola cafetera. Por la manana llegan Maria,
 *  Juan y Pedro, y cada uno prepara su cafe. Como es la misma cafetera,
 *  el contador de "cafes hechos en el dia" se suma entre todos.
 *  Si cada persona tuviera su propia cafetera, el contador no tendria
 *  sentido. Por eso usamos Singleton: UNA unica instancia compartida.
 *
 *  COMO SE VIVE EN POO (las 3 partes del Singleton):
 *  1. Atributo privado estatico  -> guarda la unica cafetera.
 *  2. Constructor privado        -> nadie puede crear otra con "new".
 *  3. Metodo estatico publico    -> devuelve siempre esa cafetera.
 *
 *  =======================================================
 */

public class Cafetera {

    // 1. La unica cafetera que existira en el programa.
    private static Cafetera laCafetera;

    // 2. Constructor privado: impide crear otras cafeteras.
    private Cafetera() {
        System.out.println(">>> Cafetera prendida (una sola vez).");
    }

    // 3. Metodo para obtenerla: si no existe, la crea.
    public static Cafetera obtenerCafetera() {
        if (laCafetera == null) {
            laCafetera = new Cafetera();
        }
        return laCafetera;
    }

    // Atributo compartido: total de cafes del dia.
    private int cafesDelDia = 0;

    // Metodo del comportamiento: prepara un cafe y lo cuenta.
    public void prepararCafe(String persona) {
        cafesDelDia++;
        System.out.println(persona + " se hizo un cafe. Total del dia: " + cafesDelDia);
    }

    public static void main(String[] args) {
        // Todos "piden" la cafetera por su cuenta...
        Cafetera maria  = Cafetera.obtenerCafetera();
        Cafetera juan   = Cafetera.obtenerCafetera();
        Cafetera pedro  = Cafetera.obtenerCafetera();

        // ...pero es LA MISMA (prendida sale UNA sola vez).
        maria.prepararCafe("Maria");    // Total: 1
        juan.prepararCafe("Juan");      // Total: 2
        pedro.prepararCafe("Pedro");    // Total: 3

        System.out.println();
        System.out.println("Maria y Pedro usan la misma cafetera? " + (maria == pedro));
    }
}