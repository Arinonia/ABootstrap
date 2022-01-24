package fr.arinonia.abootstrap.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * @author Arinonia
 * Created at 18/01/2022 - 01:34
 **/
public class WindowMover extends MouseAdapter {


    private final JFrame frame;
    private Point click;

    /**
     *
     * @param frame window need to be move
     */
    public WindowMover(JFrame frame) {
        this.frame = frame;
    }

    /**
     *
     * @param mouseEvent detect when the mouse is dragged and if click is not null, move the frame
     */
    @Override
    public void mouseDragged(final MouseEvent mouseEvent) {
        if (this.click != null) {
            final Point draggedPoint = MouseInfo.getPointerInfo().getLocation();
            this.frame.setLocation(new Point((int)draggedPoint.getX() - (int)this.click.getX(), (int)draggedPoint.getY() - (int)this.click.getY()));
        }
    }


    /**
     *
     * @param mouseEvent detect when the mouse is clicked and set the point.
     */
    @Override
    public void mousePressed(final MouseEvent mouseEvent) {
        this.click = mouseEvent.getPoint();
    }}
