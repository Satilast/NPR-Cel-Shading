package org.yourorghere;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author Satilast
 */
public class Cel_Shading implements GLEventListener {

    static GL gl;
    static GLU glu;
    static GLUT glut;
    static Interface Interface;
    static Setting setting;

    public static void main(String[] args) {
        Interface = new Interface();
        setting = new Setting();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL();

        if (!Data.wireframe) {
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glDepthFunc(GL.GL_LESS);
        }
        gl.glEnable(GL.GL_CULL_FACE);

        gl.glGenTextures(1, Data.shaderTexture, 0);                                     // Получаем номер текстуры
        gl.glBindTexture(GL.GL_TEXTURE_1D, Data.shaderTexture[0]);                      // Привязываем эту текстуру
        gl.glTexParameteri(GL.GL_TEXTURE_1D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);  // Устанавливаем режим фильтрации
        gl.glTexParameteri(GL.GL_TEXTURE_1D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexImage1D(GL.GL_TEXTURE_1D, 0, GL.GL_RGB, Data.countTokensFile, 0, GL.GL_RGB, GL.GL_FLOAT, Data.shaderDataFile); // Подгрузка текстуры

        gl.glClearColor(0.7f, 0.7f, 0.7f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.setSwapInterval(1);

        Data.lightAngle = new Vector();
        Data.lightAngle.X = 0.0f;
        Data.lightAngle.Y = 0.0f;
        Data.lightAngle.Z = 1.0f;
        Data.lightAngle.normalize();
        Data.figure = new Figure("data/spheremin.obj");
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl = drawable.getGL();
        glu = new GLU();

        height = (height == 0) ? 1 : height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, (float) width / height, 1, 1000);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL();
        glut = new GLUT();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);

        Data.frames++;
        long newTime = System.currentTimeMillis();
        if (newTime - Data.time >= 1000) {
            Data.fps = Data.frames;
            Data.time = newTime;
            Data.frames = 0;
        }

        gl.glColor3f(255f, 255f, 0f);
        gl.glWindowPos2i(drawable.getWidth() - 80, 40);
        glut.glutBitmapString(GLUT.BITMAP_8_BY_13, "FPS:  " + Data.fps);
        gl.glWindowPos2i(10, drawable.getHeight() - 25);
        glut.glutBitmapString(GLUT.BITMAP_9_BY_15, "Press 'Shift' to open the settings window.");
        gl.glWindowPos2i(10, drawable.getHeight() - 50);
        glut.glutBitmapString(GLUT.BITMAP_9_BY_15, "Press 'Spacebar' to show/hide the normals.");
        gl.glWindowPos2i(10, drawable.getHeight() - 75);
        glut.glutBitmapString(GLUT.BITMAP_9_BY_15, "Press 'W' to show/hide the wireframe mode.");
        gl.glWindowPos2i(10, drawable.getHeight() - 100);
        glut.glutBitmapString(GLUT.BITMAP_9_BY_15, "Use the arrows to change the position of the light.");

        Data.figure.angle += Data.speedOfRot / 100;
        Data.figure.angle += Data.speedOfRot / 100;
        if (Data.figure.angle >= 360) {
            Data.figure.angle = 0;
        }

        gl.glLineWidth(0.2f);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
        gl.glEnable(GL.GL_LINE_SMOOTH);

        Builder.buildModel(gl);
        Builder.buildLight(gl, Data.detail);

        gl.glFlush();
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
