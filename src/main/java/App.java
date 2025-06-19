 

import game_parts.Game;

public class App {
    public String getGreeting() {
        return "Intanto parte ...!";
    }

   public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        Game game = new Game();
        game.getClass();
    }
}
