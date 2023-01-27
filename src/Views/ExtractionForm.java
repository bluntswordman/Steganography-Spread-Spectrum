package Views;

import Controllers.ExtractingController;
import Enums.EnumPath;
import lib.FileHandler;
import lib.PrivateId;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;

public class ExtractionForm {
  private String stegoImagePath;
  private String tempMessagePath;
  private JLabel labelTime;
  private JPanel panelExtraction;
  private JPanel panelStegoImage;
  private JButton buttonProcess;
  private JButton buttonChooseImage;
  private JButton buttonBackHome;
  private JButton buttonSaveMessage;
  private JButton buttonReset;
  private JTextArea areaResultMessage;
  private JTextField fieldKey;

  public ExtractionForm() {
    buttonBackHome.addActionListener(e -> {
      Base base = new Base();
      base.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelExtraction);
      frame.dispose();
    });

    buttonChooseImage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("../../"));
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
        panelStegoImage.removeAll();
        panelStegoImage.add(new JLabel(new ImageIcon(new ImageIcon(stegoImagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
        panelStegoImage.revalidate();
        panelStegoImage.repaint();
      }
    });

    buttonProcess.addActionListener(e -> {
      String key = fieldKey.getText();
      long startTime = System.currentTimeMillis();
      try {
        String messagePath = EnumPath.TEMP_MESSAGE.getPath() + "message-in-" + stegoImagePath.substring(stegoImagePath.lastIndexOf("\\") + 1, stegoImagePath.lastIndexOf(".")) + new PrivateId().generate() + ".txt";
        boolean isExtracted = new ExtractingController(stegoImagePath, messagePath, key).extractMessage();
        if (isExtracted) {
          long endTime = System.currentTimeMillis();
          double duration = (endTime - startTime) / 1000.0;
          tempMessagePath = messagePath;
          String message = new FileHandler().getMessage(messagePath);
          areaResultMessage.setText(message);
          labelTime.setText("Waktu Embedding: " + duration + " detik");
          JOptionPane.showMessageDialog(null, "Pesan berhasil diekstrak", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(null, "Pesan gagal diekstrak", "Error", JOptionPane.ERROR_MESSAGE);
        }
      } catch (Exception exception) {
        if (stegoImagePath == null) {
          JOptionPane.showMessageDialog(null, "Pilih gambar stego terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (key.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Masukkan kunci terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(null, "Extracting gagal dilakukan", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    buttonSaveMessage.addActionListener(e -> {
      if (tempMessagePath != null) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("../../"));
        fileChooser.setDialogTitle("Save Message");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
          @Override
          public boolean accept(File f) {
            return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
          }

          @Override
          public String getDescription() {
            return "Text Files (*.txt)";
          }
        });
        fileChooser.showSaveDialog(null);
        if (fileChooser.getSelectedFile() != null) {
          String messagePath = fileChooser.getSelectedFile().getAbsolutePath();
          if (!messagePath.endsWith(".txt")) {
            messagePath += ".txt";
          }
          try {
            Files.copy(new File(tempMessagePath).toPath(), new File(messagePath).toPath());
            JOptionPane.showMessageDialog(null, "Pesan berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
          } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
          }
        }
      } else {
        JOptionPane.showMessageDialog(null, "Pesan belum diekstrak", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    buttonReset.addActionListener(e -> {
      stegoImagePath = null;
      tempMessagePath = null;
      fieldKey.setText("");
      areaResultMessage.setText("");
      panelStegoImage.removeAll();
      panelStegoImage.revalidate();
      panelStegoImage.repaint();
      labelTime.setText("Waktu Embedding: -");
    });
  }

  public void show() {
    JFrame frame = new JFrame("Extraction Form");
    frame.setContentPane(panelExtraction);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        File folderImage = new File(EnumPath.TEMP_STEGO_IMAGE.getPath());
        File folderMessage = new File(EnumPath.TEMP_MESSAGE.getPath());
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
  }
}
