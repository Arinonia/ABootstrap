package fr.arinonia.abootstrap;

import fr.arinonia.abootstrap.ui.BootstrapPanel;
import fr.arinonia.abootstrap.ui.controls.RoundedProgressBar;
import fr.arinonia.abootstrap.utils.UiUtil;
import fr.arinonia.abootstrap.utils.WindowMover;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arinonia
 * Created at 18/01/2022 - 01:16
 **/
public class Bootstrap extends JFrame {

    private String title;
    private Color titleColor;
    private Color backgroundColor;
    private ImageIcon icon;
    private JLabel downloadLabel;
    private Color downloadLabelColor;
    private RoundedProgressBar progressBar;
    private String poweredLabel;
    private Color poweredLabelColor;
    private BootstrapPanel panel;

    private final List<String> downloadLinks = new ArrayList<>();

    public String getTitle() {
        return this.title;
    }

    public Color getTitleColor() {
        return this.titleColor;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public JLabel getDownloadLabel() {
        return this.downloadLabel;
    }

    public Color getDownloadLabelColor() {
        return this.downloadLabelColor;
    }

    public RoundedProgressBar getProgressBar() {
        return this.progressBar;
    }

    public String getPoweredLabel() {
        return this.poweredLabel;
    }

    public Color getPoweredLabelColor() {
        return this.poweredLabelColor;
    }

    public BootstrapPanel getPanel() {
        return this.panel;
    }

    public List<String> getDownloadLinks() {
        return this.downloadLinks;
    }

    public static class BootstrapBuilder {
        private String title = "Bootstrap";
        private Color titleColor = Color.WHITE;
        private Color backgroundColor = new Color(33, 33, 33);
        private ImageIcon icon = UiUtil.getIconImage("/images/bootstrap_icon.png");
        private JLabel downloadLabel = new JLabel("Welcome");
        private Color downloadLabelColor = Color.WHITE;
        private RoundedProgressBar roundedProgressBar = new RoundedProgressBar();
        private String poweredLabel = "Powered by Arinonia";
        private Color poweredLabelColor = new Color(109, 109, 109);
        private BootstrapPanel panel;

        public BootstrapBuilder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public BootstrapBuilder setBackgroundColor(final Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public BootstrapBuilder setIcon(final ImageIcon icon) {
            this.icon = icon;
            return this;
        }

        public BootstrapBuilder setDownloadLabelText(final String downloadLabel) {
            this.downloadLabel.setText(downloadLabel);
            return this;
        }

        public BootstrapBuilder setDownloadLabelColor(final Color downloadLabelColor) {
            this.downloadLabelColor = downloadLabelColor;
            return this;
        }

        public BootstrapBuilder setTitleColor(final Color titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public BootstrapBuilder setPoweredLabel(final String poweredLabel) {
            this.poweredLabel = poweredLabel;
            return this;
        }

        public BootstrapBuilder setPoweredLabelColor(final Color poweredLabelColor) {
            this.poweredLabelColor = poweredLabelColor;
            return this;
        }

        public Bootstrap build() {
            this.panel = new BootstrapPanel(this);
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.title = this.title;
            bootstrap.titleColor = this.titleColor;
            bootstrap.backgroundColor = this.backgroundColor;
            bootstrap.icon = this.icon;
            bootstrap.downloadLabel = this.downloadLabel;
            bootstrap.downloadLabelColor = this.downloadLabelColor;
            bootstrap.progressBar = this.roundedProgressBar;
            bootstrap.poweredLabel = this.poweredLabel;
            bootstrap.poweredLabelColor = this.poweredLabelColor;
            bootstrap.panel = this.panel;

            bootstrap.setTitle(this.title);
            bootstrap.setSize(375, 500);
            bootstrap.setUndecorated(true);
            bootstrap.setBackground(this.backgroundColor);
            bootstrap.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            bootstrap.setLocationRelativeTo(null);
            bootstrap.setContentPane(this.panel);
            bootstrap.setAlwaysOnTop(true);
            bootstrap.setIconImage(this.icon.getImage());

            final WindowMover mover = new WindowMover(bootstrap);
            bootstrap.addMouseListener(mover);
            bootstrap.addMouseMotionListener(mover);

            return bootstrap;
        }

        public String getTitle() {
            return this.title;
        }

        public Color getTitleColor() {
            return this.titleColor;
        }

        public Color getBackgroundColor() {
            return this.backgroundColor;
        }

        public ImageIcon getIcon() {
            return this.icon;
        }

        public JLabel getDownloadLabel() {
            return this.downloadLabel;
        }

        public Color getDownloadLabelColor() {
            return this.downloadLabelColor;
        }

        public RoundedProgressBar getRoundedProgressBar() {
            return this.roundedProgressBar;
        }

        public String getPoweredLabel() {
            return this.poweredLabel;
        }

        public Color getPoweredLabelColor() {
            return this.poweredLabelColor;
        }

        public BootstrapPanel getPanel() {
            return this.panel;
        }
    }
}
