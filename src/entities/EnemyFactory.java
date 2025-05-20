package entities;

import java.util.Random;

/**
 * genera nemici in posizioni casuali.
 */
public class EnemyFactory {
    private static final int GAME_WIDTH = 2000;
    private static final int GAME_HEIGHT = 1000;
    private static final Random rand = new Random();

    public static Entity createEnemy(String type) {
        int x = rand.nextInt(GAME_WIDTH - 100) + 50;
        int y = rand.nextInt(GAME_HEIGHT - 100) + 50;

        return switch (type) {
            case "STATIC" -> new StaticEnemy(x, y, 16, 16);
            case "CHASE" -> new ChasingEnemy(x, y, 16, 16, 120);
            default -> throw new IllegalArgumentException("tipo di nemico sconosciuto: " + type);
        };
    }
}
