import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;

    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;



    public GameField(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }


    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++){                 //Построение начального червя из 3 единиц
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }
    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE; //Рандомное создание яблока на поле
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }



    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }



        public void TextFieldExample() {
            JTextField tf1;
            tf1 = new JTextField("Твой счет: " + dots);
            tf1.setBounds(120, 200, 100, 20);
            tf1.setEditable(false);
            add(tf1);
            setVisible(true);

        }



        Image fon = new ImageIcon("fon.jpg").getImage();


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            Graphics2D gr = (Graphics2D)g;
            gr.drawImage(fon,0,0,null);
            g.drawImage(apple, appleX, appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);

            }


        }else {
            TextFieldExample();
            String str = "Ты проиграл ха-ха";
            Font f = new Font("Arial",Font.BOLD, 10);
            g.setFont(f);
            g.setColor(Color.white);
            g.drawString(str, 120, SIZE / 2);


            JButton restart = new JButton();
            restart.setText("Restart");
            restart.setSize(100,20);
            restart.setLocation(120,170);

            ActionListener AListener1 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    add(new Main());

                }
            };
            restart.addActionListener(AListener1);
            add(restart);



        }
    }

    public void move(){
        for(int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){                   //Проверка на сьедания червем яблока, рост червяка и генерация новго яблока
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){              //Проверка на столкновения с рамками игрового окна и столкновения с самим собой
        for (int i = dots; i > 0; i--) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if (x[0] > SIZE){
            inGame = false;
        }
        if (x[0] < 0){
            inGame = false;
        }
        if (y[0] > SIZE){
            inGame = false;
        }
        if (y[0] < 0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {           //Метод обновления таймера(В игре ли находится игрок)
        if(inGame){
            checkCollisions();
            checkApple();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up){
                down = true;
                right = false;
                left = false;
            }
        }
    }
}