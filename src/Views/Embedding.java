package Views;

import Controllers.SteganographyController;
import Helpers.GenerateId;
import Helpers.Path;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Embedding {
  private JPanel EmbeddingMenu;
  private JButton buttonChooseImage;
  private JButton buttonSaveStegoImage;
  private JPanel showCoverImage;
  private JPanel showStegoImage;
  private JTextField fieldPathFileMessage;
  private JTextField fieldKey;
  private JButton buttonChooseFileMessage;
  private JButton buttonProcess;
  private JButton buttonBackHome;
  private String coverImagePath;
  private String tempStegoImagePath;

  public Embedding() {
    EmbeddingMenu.setPreferredSize(new Dimension(1000, 500));
    EmbeddingMenu.setBackground(Color.decode("#0f172a"));
    showCoverImage.setBackground(Color.decode("#cbd5e1"));
    showStegoImage.setBackground(Color.decode("#cbd5e1"));

    buttonBackHome.addActionListener(e -> {
      Home home = new Home();
      home.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(EmbeddingMenu);
      frame.dispose();
    });

    buttonChooseImage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("."));
      fileChooser.setDialogTitle("Pilih Gambar");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        @Override
        public boolean accept(File f) {
          return f.getName().toLowerCase().endsWith(".png") || f.isDirectory();
        }

        @Override
        public String getDescription() {
          return "JPG Images";
        }
      });
      fileChooser.showOpenDialog(null);
      if (fileChooser.getSelectedFile() != null) {
        coverImagePath = fileChooser.getSelectedFile().getAbsolutePath();
        showCoverImage.removeAll();
        showCoverImage.add(new JLabel(new ImageIcon(new ImageIcon(coverImagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
        showCoverImage.revalidate();
        showCoverImage.repaint();
      }
    });

    buttonChooseFileMessage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Pilih Pesan");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        @Override
        public boolean accept(File f) {
          return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
        }

        @Override
        public String getDescription() {
          return "Text Files";
        }
      });
      fileChooser.showOpenDialog(null);
      if (fileChooser.getSelectedFile() != null) {
        fieldPathFileMessage.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
    });

    buttonProcess.addActionListener(e -> {
      SteganographyController steganographyController = new SteganographyController();
      GenerateId generateId = new GenerateId();
      String message = fieldPathFileMessage.getText();
      String stego = Path.TEMP_STEGO_IMAGE.getPath() + "stego-" + coverImagePath.substring(coverImagePath.lastIndexOf("\\") + 1, coverImagePath.lastIndexOf(".")) + "-" + generateId.generateId() + ".png";
      String key = fieldKey.getText();

      try {
        if (Files.size(new File(message).toPath()) > 0) {
          steganographyController.embedding(message, coverImagePath, stego, key);
          tempStegoImagePath = stego;
          showStegoImage.removeAll();
          showStegoImage.add(new JLabel(new ImageIcon(new ImageIcon(stego).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
          showStegoImage.revalidate();
          showStegoImage.repaint();
          JOptionPane.showMessageDialog(null, "Pesan berhasil disisipkan");
        } else {
          JOptionPane.showMessageDialog(null, "Pesan tidak boleh kosong");
        }
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    });

    buttonSaveStegoImage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Simpan Gambar");
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.showOpenDialog(null);
      if (fileChooser.getSelectedFile() != null) {
        String path = fileChooser.getSelectedFile().getAbsolutePath();
        File source = new File(tempStegoImagePath);
        File dest = new File(path + "\\" + source.getName());
        try {
          Files.copy(source.toPath(), dest.toPath());
          JOptionPane.showMessageDialog(null, "Gambar berhasil disimpan");
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });

  }

  public void show() {
    JFrame frame = new JFrame("Embedding Menu");
    frame.setContentPane(EmbeddingMenu);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        File folder = new File(Path.TEMP_STEGO_IMAGE.getPath());
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
          file.delete();
        }
      }
    });
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    showCoverImage = new JPanel() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(300, 300);
      }
    };
    showStegoImage = new JPanel() {
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
        return new Dimension(195, 30);
      }
    };
    buttonSaveStegoImage = new JButton() {
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
        int x = (getWidth() - metrics.stringWidth("Simpan Gambar")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Simpan Gambar", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(160, 30);
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
    };
    buttonChooseFileMessage = new JButton() {
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
        int x = (getWidth() - metrics.stringWidth("Pilih Pesan")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Pilih Pesan", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(130, 30);
      }
    };
    buttonProcess = new JButton() {
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
        int x = (getWidth() - metrics.stringWidth("Proses")) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString("Proses", x, y);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(130, 30);
      }
    };

    fieldPathFileMessage = new JTextField() {
      @Override
      public void paintBorder(Graphics g) {
        g.setColor(Color.decode("#0369FF"));
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
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
