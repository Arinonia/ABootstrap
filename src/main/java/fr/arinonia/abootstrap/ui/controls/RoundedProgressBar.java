package fr.arinonia.abootstrap.ui.controls;

import fr.arinonia.abootstrap.utils.Util;

import javax.swing.*;
import java.awt.*;

/**
 * @author Arinonia
 * Created at 18/01/2022 - 01:23
 **/
public class RoundedProgressBar extends JComponent {
    private int maximum;
    private int minimum;
    private int value;

    public RoundedProgressBar() {
        this.setBackground(new Color(0, 0, 0, 0));
    }

    /**
     *
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(15, 15, 15));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);

        g.setColor(new Color(65, 65, 65));

        final int progress = Util.crossMult(this.value, this.maximum, this.getWidth());

        if (progress > 0) {
            g.fillRoundRect(0, 0, progress, this.getHeight(), 20, 20);
        }
    }

    /**
     * set the maximum value
     * @param maximum int
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * set the minimum value
     * @param minimum int
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * update the value for the progress bar and repaint it
     * @param value int
     */
    public void setValue(int value) {
        this.value = value;
        this.repaint();
    }
}
