package Views;

import Controllers.ImageQualityController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class Comparison {
  private String pathCover;
  private String pathStego;
  private JPanel comparisonMenu;
  private JPanel panelCoverImage;
  private JPanel panelStegoImage;
  private JButton buttonProcess;
  private JButton buttonBackHome;
  private JButton buttonChooseCover;
  private JButton buttonChooseStego;
  private JTextField fieldMSE;
  private JTextField fieldPSNR;

  public Comparison() {
    comparisonMenu.setPreferredSize(new Dimension(1000, 500));
    comparisonMenu.setBackground(Color.decode("#0f172a"));

    buttonChooseCover.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("../../../"));
      fileChooser.setDialogTitle("Choose Cover Image");
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
        pathCover = fileChooser.getSelectedFile().getAbsolutePath();
        panelCoverImage.removeAll();
        panelCoverImage.add(new JLabel(new ImageIcon(new ImageIcon(pathCover).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
        panelCoverImage.revalidate();
        panelCoverImage.repaint();
      }
    });

    buttonChooseStego.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("../../../"));
      fileChooser.setDialogTitle("Choose Stego Image");
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
        pathStego = fileChooser.getSelectedFile().getAbsolutePath();
        panelStegoImage.removeAll();
        panelStegoImage.add(new JLabel(new ImageIcon(new ImageIcon(pathStego).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))));
        panelStegoImage.revalidate();
        panelStegoImage.repaint();
      }
    });

    buttonProcess.addActionListener(e -> {
      ImageQualityController imageQualityController = new ImageQualityController();
      try {
        HashMap<String, Double> result = imageQualityController.generate(pathCover, pathStego);
        fieldMSE.setText(String.valueOf(result.get("MSE")));
        fieldPSNR.setText(result.get("PSNR") + " dB");
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    });

    buttonBackHome.addActionListener(e -> {
      Base base = new Base();
      base.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(comparisonMenu);
      frame.dispose();
    });
  }

  public void show() {
    JFrame frame = new JFrame("Perbandingan Kualitas Citra");
    frame.setContentPane(comparisonMenu);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    panelCoverImage = new JPanel() {
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

    panelStegoImage = new JPanel() {
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
  }
}
