/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCanvas;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

/**
 *
 * @author Satilast
 */
public class Interface extends JFrame {

    static Container common, center, south, left;
    static Listener myListener;
    static GLCanvas canvas;
    static Animator animator;
    private static JButton choice, color;
    public static JButton add, rem;

    public Interface() {
        init();
    }

    private void init() {
        setTitle("NPR: Cel-Shading");
        setType(Type.UTILITY);
        setSize(800, 600);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        myListener = new Listener();

        common = getContentPane();
        south = new Container();
        center = new Container();

        common.setLayout(new BorderLayout());
        south.setLayout(new FlowLayout());
        common.add(center, BorderLayout.CENTER);
        common.add(south, BorderLayout.SOUTH);

        canvas = new GLCanvas();
        canvas.addKeyListener(myListener);
        canvas.setBounds(0, 0, center.getWidth(), center.getHeight());
        choice = new JButton("Âûáðàòü ôàéë ìîäåëè");
        choice.setSize(130, 25);
        choice.setLocation(0, getHeight());
        choice.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                Reader.loadModel();
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        color = new JButton("Âûáðàòü îòòåíîê îñâåùåíèÿ");
        color.setSize(130, 25);
        color.setLocation(0, getHeight());
        color.setVisible(true);
        color.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                Color c = JColorChooser.showDialog(null, "", getBackground());
                if (c != null) {
                    Data.lightColor = c;
                }
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        center.add(canvas);
        south.add(choice);
        south.add(color);
        animator = new Animator(canvas);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        animator.start();
        setVisible(true);
    }

}
