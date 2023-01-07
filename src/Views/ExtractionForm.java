package Views;

import Controllers.SteganographyController;
import Helpers.GenerateId;
import Helpers.Path;
import Utils.FileManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;

public class ExtractionForm {
  private JPanel panelExtraction;
  private JPanel panelStegoImage;
  private JButton buttonProcess;
  private JButton buttonChooseImage;
  private JButton buttonBackHome;
  private JButton buttonSaveMessage;
  private JTextField fieldKey;
  private JTextArea areaResultMessage;
  private String stegoImagePath;
  private String tempMessagePath;

  public ExtractionForm() {
    buttonBackHome.addActionListener(e -> {
      Base base = new Base();
      base.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelExtraction);
      frame.dispose();
    });

    buttonChooseImage.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("../../../"));
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
      GenerateId generateId = new GenerateId();
      String key = fieldKey.getText();
      String messagePath = Path.TEMP_MESSAGE.getPath() + "message-in-" + stegoImagePath.substring(stegoImagePath.lastIndexOf("\\") + 1, stegoImagePath.lastIndexOf(".")) + "-" + generateId.generateId() + ".txt";

      SteganographyController steganographyController = new SteganographyController();
      FileManagement fileManagement = new FileManagement();

      try {
        steganographyController.extracting(stegoImagePath, messagePath, key);
        tempMessagePath = messagePath;
        String message = fileManagement.getFileMessage(messagePath);
        areaResultMessage.setText(message);
        JOptionPane.showMessageDialog(null, "Pesan berhasil diekstrak");
      } catch (Exception exception) {
        JOptionPane.showMessageDialog(null, exception.getMessage());
      }
    });

    buttonSaveMessage.addActionListener(e -> {
      if (tempMessagePath != null) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("../../../"));
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
            JOptionPane.showMessageDialog(null, "Pesan berhasil disimpan");
          } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
          }
        }
      } else {
        JOptionPane.showMessageDialog(null, "Pesan belum diekstrak");
      }
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
  }
}
