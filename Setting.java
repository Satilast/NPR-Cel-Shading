/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Satilast
 */
public class Setting extends JFrame {

    private static JSlider xrotSlider;
    private static JSlider yrotSlider;
    private static JSlider zrotSlider;
    private static JSlider speedRotSlider;

    private static JLabel xrotLabel;
    private static JLabel yrotLabel;
    private static JLabel zrotLabel;
    private static JLabel speedRotLabel;

    private final static Dimension SLIDER_SIZE = new Dimension(300, 20);
    private final static Dimension LABEL_SIZE = new Dimension(200, 20);

    public Setting() {
        initComponent();
    }

    public static void readData() {
        xrotSlider.setValue((int) Data.xrot);
        yrotSlider.setValue((int) Data.yrot);
        zrotSlider.setValue((int) Data.zrot);
        speedRotSlider.setValue((int) Data.speedOfRot);
        setText();
    }

    private static void setText() {
        xrotLabel.setText("Поворот по оси x : " + Data.xrot + "%");
        yrotLabel.setText("Поворот по оси y : " + Data.yrot + "%");
        zrotLabel.setText("Поворот по оси z : " + Data.zrot + "%");
        speedRotLabel.setText("Скорость вращения : " + Data.speedOfRot / 100 + "° в мс");
    }

    private void initComponent() {
        setType(Type.UTILITY);
        setTitle("Окно настроек");
        setLayout(null);
        setSize(500, 300);
        setVisible(false);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        xrotSlider = new JSlider();
        xrotSlider.setSize(SLIDER_SIZE);
        xrotSlider.setLocation((getWidth() - xrotSlider.getWidth()) / 2, xrotSlider.getHeight() + 20);
        xrotSlider.setMinimum(0);
        xrotSlider.setMaximum(100);
        xrotSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Data.xrot = xrotSlider.getValue();
                xrotLabel.setText("Поворот по оси x : " + Data.xrot + "%");
            }
        });
        xrotLabel = new JLabel();
        xrotLabel.setSize(LABEL_SIZE);
        xrotLabel.setLocation((getWidth() - xrotLabel.getWidth()) / 2, xrotSlider.getY() - 25);

        yrotSlider = new JSlider();
        yrotSlider.setSize(SLIDER_SIZE);
        yrotSlider.setLocation((getWidth() - yrotSlider.getWidth()) / 2, xrotSlider.getY() + xrotSlider.getHeight() + yrotSlider.getHeight() + 20);
        yrotSlider.setMinimum(0);
        yrotSlider.setMaximum(100);
        yrotSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Data.yrot = yrotSlider.getValue();
                yrotLabel.setText("Поворот по оси y : " + Data.yrot + "%");
            }
        });
        yrotLabel = new JLabel();
        yrotLabel.setSize(LABEL_SIZE);
        yrotLabel.setLocation((getWidth() - yrotLabel.getWidth()) / 2, yrotSlider.getY() - 25);

        zrotSlider = new JSlider();
        zrotSlider.setSize(SLIDER_SIZE);
        zrotSlider.setLocation((getWidth() - zrotSlider.getWidth()) / 2, yrotSlider.getY() + yrotSlider.getHeight() + zrotSlider.getHeight() + 20);
        zrotSlider.setMinimum(0);
        zrotSlider.setMaximum(100);
        zrotSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Data.zrot = zrotSlider.getValue();
                zrotLabel.setText("Поворот по оси z : " + Data.zrot + "%");
            }
        });
        zrotLabel = new JLabel();
        zrotLabel.setSize(LABEL_SIZE);
        zrotLabel.setLocation((getWidth() - zrotLabel.getWidth()) / 2, zrotSlider.getY() - 25);

        speedRotSlider = new JSlider();
        speedRotSlider.setSize(SLIDER_SIZE);
        speedRotSlider.setLocation((getWidth() - speedRotSlider.getWidth()) / 2, zrotSlider.getY() + zrotSlider.getHeight() + speedRotSlider.getHeight() + 20);
        speedRotSlider.setMinimum(0);
        speedRotSlider.setMaximum(500);
        speedRotSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Data.speedOfRot = speedRotSlider.getValue();
                speedRotLabel.setText("Скорость вращения : " + Data.speedOfRot / 100 + "° в мс");
            }
        });
        speedRotLabel = new JLabel();
        speedRotLabel.setSize(LABEL_SIZE);
        speedRotLabel.setLocation((getWidth() - speedRotLabel.getWidth()) / 2, speedRotSlider.getY() - 25);

        add(xrotSlider);
        add(yrotSlider);
        add(zrotSlider);
        add(speedRotSlider);

        add(xrotLabel);
        add(yrotLabel);
        add(zrotLabel);
        add(speedRotLabel);

        //repaint();
    }
}
