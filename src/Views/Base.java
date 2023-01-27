package Views;

import Enums.EnumPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;

public class Base {
  private JPanel basePanel;
  private JButton extractingButton;
  private JButton buttonEmbedding;
  private JButton buttonComparison;

  public Base() {
    basePanel.setPreferredSize(new Dimension(1000, 500));
    basePanel.setBackground(Color.decode("#0f172a"));

    buttonEmbedding.addActionListener(e -> {
      EmbeddingForm embeddingForm = new EmbeddingForm();
      embeddingForm.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(basePanel);
      frame.dispose();
    });

    extractingButton.addActionListener(e -> {
      ExtractionForm extractionForm = new ExtractionForm();
      extractionForm.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(basePanel);
      frame.dispose();
    });

    buttonComparison.addActionListener(e -> {
      Comparison comparison = new Comparison();
      comparison.show();
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(basePanel);
      frame.dispose();
    });
  }

  public void show() {
    JFrame frame = new JFrame("Beranda");
    frame.setContentPane(basePanel);
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
}
