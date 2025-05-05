package entities;

import javax.swing.text.html.parser.Entity;

public class EnemyFactory {
    public static Entity createEnemy(String type, int x, int y) {
        switch (type) {
            case "STATIC" -> {
                return new StaticEnemy(x, y, 16, 16);
            }
            case "CHASE" -> {
                return new ChasingEnemy(x, y, 16, 16, 120);
            }
           
            default -> throw new IllegalArgumentException("Tipo di nemico sconosciuto: " + type);
        }
    }
}
