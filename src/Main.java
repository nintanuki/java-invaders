/** Main class: Entry point of the application */
public class Main {
    
    /**
     * Main method: Entry point of the application
     * @param args Command-line arguments
     * @return void
     */
    public static void main(String[] args) {
        // This is the Java equivalent of Python's "if __name__ == '__main__':"
        GameManager game = new GameManager(); // Create an instance of the GameManager class
        game.run(); // Call the run method to start the game
    }
}