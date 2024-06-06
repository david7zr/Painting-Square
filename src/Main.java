import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Create a JFrame to hold our game panel
        JFrame frame = new JFrame("Square Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create an instance of the GamePanel
        GamePanel gamePanel = new GamePanel();

        //Add the game panel to the frame
        frame.add(gamePanel);

        //Set the size of the frame and make it visible
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
