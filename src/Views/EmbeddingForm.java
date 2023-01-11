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

public class EmbeddingForm {
  private JPanel panelEmbedding;
  private JPanel panelCoverImage;
  private JPanel panelStegoImage;
  private JButton buttonProcess;
  private JButton buttonBackHome;
  private JButton buttonSaveStegoImage;
  private JButton buttonChooseCoverImage;
  private JButton buttonChooseFileMessage;
  private JTextField fieldKey;
  private JTextField fieldMessagePath;
  private JLabel labelTime;
  private JButton buttonReset;
  private String messagePath;
  private String coverImagePath;
  private String tempStegoImagePath;

  public EmbeddingForm() {
    buttonBackHome.addActionListener(e -> {
      Base base = new Base();
      base.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelEmbedding);
      frame.dispose();
    });

    buttonChooseCoverImage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("../../"));
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
        panelCoverImage.removeAll();
        panelCoverImage.add(new JLabel(new ImageIcon(new ImageIcon(coverImagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
        panelCoverImage.revalidate();
        panelCoverImage.repaint();
      }
    });

    buttonChooseFileMessage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("../../"));
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
        messagePath = fileChooser.getSelectedFile().getAbsolutePath();
        fieldMessagePath.setText(fileChooser.getSelectedFile().getName());
      }
    });

    buttonProcess.addActionListener(e -> {
      SteganographyController steganographyController = new SteganographyController();
      GenerateId generateId = new GenerateId();
      String key = fieldKey.getText();
      long startTime = System.currentTimeMillis();
      try {
        String stego = Path.TEMP_STEGO_IMAGE.getPath() + "stego-" + coverImagePath.substring(coverImagePath.lastIndexOf("\\") + 1, coverImagePath.lastIndexOf(".")) + "-" + generateId.generateId() + ".png";
        if (Files.size(new File(messagePath).toPath()) > 0) {
          boolean isEmbedding = steganographyController.embedding(messagePath, coverImagePath, stego, key);
          if (isEmbedding) {
            long endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / 1000.0;
            tempStegoImagePath = stego;
            panelStegoImage.removeAll();
            panelStegoImage.add(new JLabel(new ImageIcon(new ImageIcon(stego).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
            panelStegoImage.revalidate();
            panelStegoImage.repaint();
            labelTime.setText("Waktu Embedding: " + duration + " detik");
            JOptionPane.showMessageDialog(null, "Embedding berhasil");
          } else {
            JOptionPane.showMessageDialog(null, "Embedding gagal dilakukan", "Gagal", JOptionPane.ERROR_MESSAGE);
          }
        }
      } catch (Exception exception) {
        if (coverImagePath == null) {
          JOptionPane.showMessageDialog(null, "Gambar cover belum dipilih", "Gagal", JOptionPane.ERROR_MESSAGE);
        } else if (messagePath == null) {
          JOptionPane.showMessageDialog(null, "Pesan belum dipilih", "Gagal", JOptionPane.ERROR_MESSAGE);
        } else if (key.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Kunci belum diisi", "Gagal", JOptionPane.ERROR_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(null, "Embedding gagal dilakukan", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    buttonSaveStegoImage.addActionListener(e -> {
      if (tempStegoImagePath != null) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("../../"));
        fileChooser.setDialogTitle("Save Message");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
          @Override
          public boolean accept(File f) {
            return f.getName().toLowerCase().endsWith(".png") || f.isDirectory();
          }

          @Override
          public String getDescription() {
            return "PNG Images";
          }
        });
        fileChooser.showSaveDialog(null);
        if (fileChooser.getSelectedFile() != null) {
          String path = fileChooser.getSelectedFile().getAbsolutePath();
          if (!path.endsWith(".png")) {
            path += ".png";
          }
          try {
            Files.copy(new File(tempStegoImagePath).toPath(), new File(path).toPath());
            JOptionPane.showMessageDialog(null, "Gambar berhasil disimpan");
          } catch (IOException ioException) {
            ioException.printStackTrace();
          }
        }
      } else {
        JOptionPane.showMessageDialog(null, "Gambar Stego belum di proses");
      }
    });

    buttonReset.addActionListener(e -> {
      coverImagePath = null;
      messagePath = null;
      tempStegoImagePath = null;
      panelCoverImage.removeAll();
      panelCoverImage.revalidate();
      panelCoverImage.repaint();
      panelStegoImage.removeAll();
      panelStegoImage.revalidate();
      panelStegoImage.repaint();
      fieldMessagePath.setText("");
      fieldKey.setText("");
      labelTime.setText("Waktu Embedding: -");
    });
  }

  public void show() {
    JFrame frame = new JFrame("Embedding Form");
    frame.setContentPane(panelEmbedding);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        File folderImage = new File(Path.TEMP_STEGO_IMAGE.getPath());
        File folderMessage = new File(Path.TEMP_MESSAGE.getPath());
        File[] filesImage = folderImage.listFiles();
        File[] filesMessage = folderMessage.listFiles();
        assert filesImage != null;
        assert filesMessage != null;
        try {
          for (File file : filesImage) {
            Files.delete(file.toPath());
          }
          for (File file : filesMessage) {
            Files.delete(file.toPath());
          }
        } catch (Exception ioException) {
          ioException.printStackTrace();
        }
      }
    });
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    panelStegoImage = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - 300) / 2;
        int y = (getHeight() - 300) / 2;
        g.drawImage(new ImageIcon("").getImage(), x, y, 300, 300, null);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(300, 300);
      }
    };

    panelCoverImage = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - 300) / 2;
        int y = (getHeight() - 300) / 2;
        g.drawImage(new ImageIcon("").getImage(), x, y, 300, 300, null);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(300, 300);
      }
    };
  }
}
