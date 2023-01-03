package Views;

import Controllers.SteganographyController;
import Helpers.Path;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Extracting {
  private JPanel panelExtractingMenu;
  private JButton buttonChooseImage;
  private JTextField fieldKey;
  private JButton buttonSaveMessage;
  private JButton buttonBackHome;
  private JPanel panelShowStegoImage;
  private String stegoImagePath;

  public Extracting() {
    panelExtractingMenu.setPreferredSize(new Dimension(1000, 500));
    panelExtractingMenu.setBackground(Color.decode("#0f172a"));
    panelShowStegoImage.setBackground(Color.decode("#cbd5e1"));

    buttonBackHome.addActionListener(e -> {
      Home home = new Home();
      home.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelExtractingMenu);
      frame.dispose();
    });

    buttonChooseImage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("."));
      fileChooser.setDialogTitle("Choose Image");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        @Override
        public boolean accept(File f) {
          return f.getName().toLowerCase().endsWith(".png") || f.isDirectory();
        }

        @Override
        public String getDescription() {
          return "PNG Images (*.png)";
        }
      });
      fileChooser.showOpenDialog(null);
      if (fileChooser.getSelectedFile() != null) {
        stegoImagePath = fileChooser.getSelectedFile().getAbsolutePath();
        panelShowStegoImage.removeAll();
        panelShowStegoImage.add(new JLabel(new ImageIcon(new ImageIcon(stegoImagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
        panelShowStegoImage.revalidate();
        panelShowStegoImage.repaint();
      }
    });

    buttonSaveMessage.addActionListener(e -> {
      String key = fieldKey.getText();
      String messagePath = Path.RESULT_MESSAGE_PATH.getPath() + "message-in-" + stegoImagePath.substring(stegoImagePath.lastIndexOf("\\") + 1, stegoImagePath.lastIndexOf(".")) + ".txt";

      SteganographyController steganographyController = new SteganographyController();

      try {
        steganographyController.extracting(stegoImagePath, messagePath, key);
        JOptionPane.showMessageDialog(null, "Pesan berhasil diekstrak");
      } catch (Exception exception) {
        JOptionPane.showMessageDialog(null, exception.getMessage());
      }
    });
  }

  public void show() {
    JFrame frame = new JFrame("Extracting");
    frame.setContentPane(panelExtractingMenu);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    buttonChooseImage = new JButton() {
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
        int x = (getWidth() - metrics.stringWidth("Pilih Gambar")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Pilih Gambar", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(130, 30);
      }
    };

    panelShowStegoImage = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - 400) / 2;
        int y = (getHeight() - 300) / 2;
        g.drawImage(new ImageIcon("").getImage(), x, y, 400, 300, null);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(300, 300);
      }
    };

    buttonSaveMessage = new JButton() {
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
        int x = (getWidth() - metrics.stringWidth("Proses & Simpan Pesan")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Proses & Simpan Pesan", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(190, 30);
      }
    };

    buttonBackHome = new JButton() {
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
        int x = (getWidth() - metrics.stringWidth("Kembali")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Kembali", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(130, 30);
      }
    };

    fieldKey = new JTextField() {
      @Override
      public void paintBorder(Graphics g) {
        g.setColor(Color.decode("#0369a1"));
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
      }
    };
  }
}
