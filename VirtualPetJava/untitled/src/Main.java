/*
 Primera fase del proyecto 1 Del VirtualPet
 El juego se basa en realizar distintas acciones para que la mascota no muera y consiga llegar al 100 en todo para ganar
 Recuerda que si llega a 100 de peso tu mascota morira de sobrepeso
*/
import java.util.Random;
import java.util.Scanner;

abstract class Mascota {
    protected String nombre;
    protected int energia;
    protected int higiene;
    protected int peso;
    protected int animo;

    public Mascota(String nombre) {
        Random rand = new Random();
        this.nombre = nombre;
        this.energia = rand.nextInt(21) + 40;
        this.higiene = rand.nextInt(21) + 40;
        this.peso = rand.nextInt(21) + 40;
        this.animo = rand.nextInt(21) + 40;
    }

    public abstract void dormir();
    public abstract void jugar();
    public void comer() {
        energia += 10;
        higiene -= 15;
        peso += 10;
        verificarRangos();
    }
    public abstract void limpiar();

    protected void verificarRangos() {
        energia = Math.max(0, Math.min(100, energia));
        higiene = Math.max(0, Math.min(100, higiene));
        peso = Math.max(0, Math.min(100, peso));
        animo = Math.max(0, Math.min(100, animo));
    }

    public boolean estaViva() {
        return energia > 0 && higiene > 0 && peso > 0 && animo > 0;
    }

    public boolean sobrepeso() {
        return peso >= 100;
    }

    public boolean gano() {
        return energia == 100 && higiene == 100 && animo == 100;
    }



    public void mostrarEstado() {
        System.out.println("\n--- Estado de " + nombre + " ---");
        mostrarBarra("Energía", energia);
        mostrarBarra("Higiene", higiene);
        mostrarBarra("Peso", peso);
        mostrarBarra("Ánimo", animo);
        System.out.println("---------------------------");
    }

    private void mostrarBarra(String atributo, int valor) {
        int totalBarras = 20;
        int barrasLlenas = (valor * totalBarras) / 100;
        int barrasVacias = totalBarras - barrasLlenas;

        StringBuilder barra = new StringBuilder();
        for (int i = 0; i < barrasLlenas; i++) {
            barra.append("█");
        }
        for (int i = 0; i < barrasVacias; i++) {
            barra.append("-");
        }

        System.out.printf("%-8s [%s] %d%%\n", atributo, barra.toString(), valor);
    }
}

class Perro extends Mascota {
    public Perro(String nombre) {
        super(nombre);
    }

    public void dormir() {
        energia += 25;
        animo += 10;
        verificarRangos();
    }

    public void jugar() {
        energia -= 20;
        animo += 20;
        peso -= 8;
        verificarRangos();
    }

    public void limpiar() {
        energia -= 10;
        higiene += 25;
        animo -= 15;
        verificarRangos();
    }
}

class Gato extends Mascota {
    public Gato(String nombre) {
        super(nombre);
    }

    public void dormir() {
        energia += 20;
        animo += 10;
        verificarRangos();
    }

    public void jugar() {
        energia -= 18;
        animo += 20;
        peso -= 8;
        verificarRangos();
    }

    public void limpiar() {
        energia -= 5;
        higiene += 25;
        animo += 5;
        verificarRangos();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenido a la Mascota Virtual");
        System.out.print("¿Quieres un perro o un gato? (perro/gato): ");
        String tipo = sc.nextLine().toLowerCase();

        System.out.print("Elige un nombre para tu mascota: ");
        String nombre = sc.nextLine();

        Mascota mascota;
        if (tipo.equals("perro")) {
            mascota = new Perro(nombre);
        } else {
            mascota = new Gato(nombre);
        }

        int opcion;
        do {
            if (mascota instanceof Perro) {
                mascota.mostrarEstado();
                System.out.println("\nMenú:");
                System.out.println("1. Dormir(+25 Energía, +10 Ánimo)");
                System.out.println("2. Jugar(-20 Energía, +20 Ánimo, -8 Peso)");
                System.out.println("3. Comer (+10 Energía, -15 Higiene, +10 Peso)");
                System.out.println("4. Ducha(-10 Energía, +25 Higiene -15 Ánimo)");
                System.out.println("0. Salir");
            }else {
                mascota.mostrarEstado();
                System.out.println("\nMenú:");
                System.out.println("1. Dormir(+20 Energía, +10 Ánimo)");
                System.out.println("2. Jugar(-18 Energía, +20 Ánimo, -8 Peso)");
                System.out.println("3. Comer(+10 Energía, -15 Higiene, +10 Peso)");
                System.out.println("4. Autolimpieza(-5 Energía, +25 Higiene +5 Animo)");
                System.out.println("0. Salir");
            }
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1: mascota.dormir(); break;
                case 2: mascota.jugar(); break;
                case 3: mascota.comer(); break;
                case 4: mascota.limpiar(); break;
                case 0: System.out.println("¡Hasta pronto!"); break;
                default: System.out.println("Opción inválida");
            }

            if (!mascota.estaViva()) {
                System.out.println("\nTu mascota ha muerto... Fin del juego.");
                break;
            }
            if (mascota.sobrepeso()) {
                System.out.println("\nTu mascota ha muerto de sobrepeso... Fin del juego.");
                break;
            }
            if (mascota.gano()) {
                System.out.println("\n¡Felicidades! Tu mascota ha ganado... Fin del juego.");
                break;
            }


        } while (opcion != 0);

        sc.close();
    }
}