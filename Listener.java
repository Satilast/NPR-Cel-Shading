/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Satilast
 */
public class Listener implements KeyListener{

    public void keyReleased(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            if (!Cel_Shading.setting.isVisible()) {
                Setting.readData();
                Cel_Shading.setting.setVisible(true);
            } else {
                Cel_Shading.setting.dispose();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_PAGE_UP
                || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    Data.lightAngle.Y += 0.01;
                    break;
                case KeyEvent.VK_DOWN:
                    Data.lightAngle.Y -= 0.01;
                    break;
                case KeyEvent.VK_LEFT:
                    Data.lightAngle.X -= 0.01;
                    break;
                case KeyEvent.VK_RIGHT:
                    Data.lightAngle.X += 0.01;
                    break;
                case KeyEvent.VK_PAGE_UP:
                    Data.lightAngle.Z += 0.01;
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    Data.lightAngle.Z -= 0.01;
                    break;
            }
            Data.lightAngle.normalize();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (Data.detail) {
                Data.detail = false;
            } else {
                Data.detail = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (Data.wireframe) {
                Data.wireframe = false;
            } else {
                Data.wireframe = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
