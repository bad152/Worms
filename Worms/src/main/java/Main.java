import javax.swing.*;

public class Main extends JFrame{

    public Main(){
        setTitle("Worms");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320,345);
        setLocation(500,100);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args){
        Main mw = new Main();

    }
}