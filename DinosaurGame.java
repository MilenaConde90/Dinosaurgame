import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class DinosaurGame extends JFrame implements KeyListener {
    private int maxX = 40;
    private int maxY = 10;
    private int playerX = 10;

    private int piso = 20;
    private int playerY = maxY - 3;
    private int jump = 0;
    private int obstacleX = maxX;
    private int score = 0;
    private Timer timer;

    private JLabel scoreLabel;

    public DinosaurGame() {
        setTitle("Dinosaur Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        addKeyListener(this);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arya", Font.PLAIN, 20));
        add(scoreLabel, BorderLayout.NORTH);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    private void update() {
        obstacleX--;

        if (playerX == obstacleX && playerY == maxY - 3) {
            JOptionPane.showMessageDialog(this, "¡Has perdido!");
            timer.stop();
            int choice = JOptionPane.showConfirmDialog(this, "¿Quieres jugar de nuevo?", "Replay", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                restart();
            } else {
                System.exit(0);
            }
        }

        if (obstacleX == 0) {
            score++;
            obstacleX = maxX;
            updateScoreLabel();
        }

        if (jump != 0) {
            playerY -= 2;
            if (playerY <= 0) {
                jump = 0;
                playerY = maxY - 3;
            }
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void restart() {
        playerX = 10;
        playerY = maxY - 3;
        jump = 0;
        obstacleX = maxX;
        score = 0;
        updateScoreLabel();
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Lógica para cambiar el color cada 5 puntos
        Color obstacleColor = (score / 5) % 2 == 0 ? Color.RED : Color.BLUE;
        g.setColor(obstacleColor);

        for (int i = 0; i < maxX; i++) {
            g.drawString("_", i * 9, (maxY - 3) * 20);
        }

        // Restaura el color por defecto
        g.setColor(Color.BLACK);

        // Ubica el puntaje en el centro de la pantalla
        String scoreText = "Score: " + score;
        int scoreX = (getWidth() - g.getFontMetrics().stringWidth(scoreText)) / 2;
        g.drawString(scoreText, scoreX, 20);

        g.drawString("w", playerX * 10, playerY * 20);

        // Establece el color del obstáculo
        g.setColor(obstacleColor);
        g.drawString("l", obstacleX * 10, (maxY - 3) * 20);

        // Restaura el color por defecto
        g.setColor(Color.BLACK);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && jump == 0) {
            jump = 2;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DinosaurGame().setVisible(true);
            }
        });
    }
}
