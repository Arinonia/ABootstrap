package fr.arinonia.abootstrap.ui;

import fr.arinonia.abootstrap.Bootstrap;

import javax.swing.*;
import java.awt.*;

/**
 * @author Arinonia
 * Created at 18/01/2022 - 01:22
 **/
public class BootstrapPanel extends JPanel {

    private final JLabel title = new JLabel();
    private final JLabel poweredLabel = new JLabel();

    public BootstrapPanel(final Bootstrap.BootstrapBuilder builder) {
        super(null);
        this.setBackground(builder.getBackgroundColor());

        this.title.setText(builder.getTitle());
        this.title.setForeground(builder.getTitleColor());
        this.setCustomFont(this.title, null, 28.0F);
        this.title.setBounds(375 / 2 - this.getLabelWidth(title) / 2, 10 ,375, 40);
        this.add(this.title);

        final ImageIcon imgIcon = builder.getIcon();
        final Image image = imgIcon.getImage();
        final Image imageScale = image.getScaledInstance(240, 240, Image.SCALE_SMOOTH);
        final JLabel gif = new JLabel();
        gif.setBounds(375 / 2 - 240 / 2, 50, 240, 240);
        gif.setIcon(new ImageIcon(imageScale));
        this.add(gif);

        builder.getDownloadLabel().setForeground(builder.getDownloadLabelColor());
        this.setCustomFont(builder.getDownloadLabel(), null, 18f);
        builder.getDownloadLabel().setBounds((int)187.5 - this.getLabelWidth(builder.getDownloadLabel()) / 2, 300 ,300, 40);
        this.add(builder.getDownloadLabel());

        builder.getRoundedProgressBar().setBounds(20, 350, 320, 20);
        builder.getRoundedProgressBar().setMinimum(0);
        this.add(builder.getRoundedProgressBar());

        this.poweredLabel.setText(builder.getPoweredLabel());
        this.poweredLabel.setForeground(builder.getPoweredLabelColor());
        this.setCustomFont(this.poweredLabel, null, 13.0f);
        poweredLabel.setBounds(375 / 2 - this.getLabelWidth(this.poweredLabel) / 2, 470 ,190, 30);
        this.add(this.poweredLabel);
    }

    /**
     *
     * @param label the label whose width you want
     * @return width of JLabel
     */
    public int getLabelWidth(final JLabel label) {
        return this.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }

    /**
     *
     * @param label who needs to be modified
     * @param font of the label (null for change only the size)
     * @param size of the font
     */
    public void setCustomFont(final JLabel label, Font font, float size) {
        label.setFont(font != null ? font.deriveFont(size) : label.getFont().deriveFont(size));
    }
}
