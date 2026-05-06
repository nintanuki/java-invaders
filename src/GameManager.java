public class GameManager {
    private boolean isRunning;

    public GameManager() {
        // Constructor: Initialize your "pygame" equivalent here later
        System.out.println("Initializing " + Settings.GAME_TITLE);
        this.isRunning = true;
    }

    private void update() {
        // Physics, collision, input
    }

    private void render() {
        System.out.println("Rendering frame at " + Settings.SCREEN_WIDTH + "x" + Settings.SCREEN_HEIGHT);
    }

    public void run() {
        // Main Loop
        while (isRunning) {
            update();
            render();
            
            // Temporary "circuit breaker" so we don't loop forever in the console!
            isRunning = false; 
        }
    }
}