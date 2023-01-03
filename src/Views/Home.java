package Views;

import javax.swing.*;
import java.awt.*;

public class Home {

  private JPanel homeMenu;
  private JButton embeddingButton;
  private JButton extractingButton;

  public Home() {
    homeMenu.setPreferredSize(new Dimension(1000, 500));
    homeMenu.setBackground(Color.decode("#0f172a"));

    embeddingButton.addActionListener(e -> {
      Embedding embedding = new Embedding();
      embedding.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(homeMenu);
      frame.dispose();
    });

    extractingButton.addActionListener(e -> {
      Extracting extracting = new Extracting();
      extracting.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(homeMenu);
      frame.dispose();
    });
  }

  public void show() {
    JFrame frame = new JFrame("Menu Utama");
    frame.setContentPane(homeMenu);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    embeddingButton = new JButton() {
      @Override
      public void paintBorder(Graphics g) {
        g.setColor(Color.decode("#0369a1"));
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
      }

      @Override
      public void paintComponent(Graphics g) {
        g.setColor(Color.decode("#4338ca"));
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
      }

      @Override
      public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.decode("#ffffff"));
        g.setFont(new Font("Inter", Font.BOLD, 16));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (getWidth() - metrics.stringWidth("Embedding")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Embedding", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(200, 30);
      }
    };

    extractingButton = new JButton() {
      @Override
      public void paintBorder(Graphics g) {
        g.setColor(Color.decode("#0369a1"));
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
      }

      @Override
      public void paintComponent(Graphics g) {
        g.setColor(Color.decode("#4338ca"));
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
      }

      @Override
      public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.decode("#ffffff"));
        g.setFont(new Font("Inter", Font.BOLD, 16));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (getWidth() - metrics.stringWidth("Extracting")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Extracting", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(200, 30);
      }
    };
  }
}
