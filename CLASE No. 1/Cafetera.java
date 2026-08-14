public final class Cafetera {

    private static Cafetera instance;
    private String estado;

    private Cafetera() {
        this.estado = "Prendida";
    }

    public static Cafetera getInstance() {
        if (instance == null) {
            instance = new Cafetera();
        }
        return instance;
    }

    public void prepararCafe(String persona) {
        // Lógica de la cafetera
    }

    public static void main(String[] args) {
        Cafetera maria = Cafetera.getInstance();
        Cafetera juan = Cafetera.getInstance();
    }
}