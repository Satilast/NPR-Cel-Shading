/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.awt.Color;
import javax.media.opengl.GL;

/**
 *
 * @author Satilast
 */
public class Builder {

    static public void build(GL gl, Figure f, Color color) {
        Matrix TmpMatrix = new Matrix();                            // Временная матричная структура
        Vector TmpVector = new Vector(), TmpNormal = new Vector();
        gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, TmpMatrix.Data, 0);  //Копирование матрицы
        gl.glEnable(GL.GL_TEXTURE_1D);                              // Включение 1D текстурирования
        gl.glBindTexture(GL.GL_TEXTURE_1D, Data.shaderTexture[0]);  // Привязывание текстуры
        for (Figure.Plane plane : f.planes) {
            gl.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
            switch (plane.verts.length) {
                case 1:
                    gl.glBegin(GL.GL_POINTS);
                    break;
                case 2:
                    gl.glBegin(GL.GL_LINES);
                    break;
                case 3:
                    gl.glBegin(GL.GL_TRIANGLES);
                    break;
                case 4:
                    gl.glBegin(GL.GL_QUADS);
                    break;
                default:
                    gl.glBegin(GL.GL_POLYGON);
                    break;
            }
            for (Figure.Plane.Vert vert : plane.verts) {
                TmpNormal.X = plane.X; // Определение нормалей
                TmpNormal.Y = plane.Y; // 
                TmpNormal.Z = plane.Z;
                TmpMatrix.rotateVector(TmpNormal, TmpVector);         // Вращение вектора по матрице
                TmpVector.normalize();
                float TmpShade = Vector.dotProduct(TmpVector, Data.lightAngle); // Вычисление затенения
                if (TmpShade == 1) {
                    TmpShade -= 0.0000001;
                }
                if (TmpShade < 0.0f) {
                    TmpShade = 0.0f;                                      // Нулевое затенение в случае отриц. значения
                }
                gl.glTexCoord1f(TmpShade);
                gl.glNormal3f(plane.X, plane.Y, plane.Z);
                gl.glVertex3f(vert.X, vert.Y, vert.Z);
            }
            gl.glEnd();
        }
        gl.glDisable(GL.GL_TEXTURE_1D);

        // Рисуем черный контур
        gl.glEnable(GL.GL_BLEND);                                 // Включаем режим наложения цветов
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);// Смешивание цветов с эффектом прозрачности

        gl.glPolygonMode(GL.GL_BACK, GL.GL_LINE);                  // Рисование полигонов как каркас
        gl.glLineWidth(0.5f);
        gl.glCullFace(GL.GL_FRONT);                               // Скрытие передних поверхностей

        gl.glDepthFunc(GL.GL_LEQUAL);                             // Проверка глубины
        gl.glColor3fv(new float[]{0, 0, 0}, 0);
        for (Figure.Plane plane : f.planes) {
            switch (plane.verts.length) {
                case 1:
                    gl.glBegin(GL.GL_POINTS);
                    break;
                case 2:
                    gl.glBegin(GL.GL_LINES);
                    break;
                case 3:
                    gl.glBegin(GL.GL_TRIANGLES);
                    break;
                case 4:
                    gl.glBegin(GL.GL_QUADS);
                    break;
                default:
                    gl.glBegin(GL.GL_POLYGON);
                    break;
            }
            for (Figure.Plane.Vert vert : plane.verts) {
                gl.glVertex3f(vert.X, vert.Y, vert.Z);
            }
        }
        gl.glEnd();
        gl.glDepthFunc(GL.GL_LESS);                               // Сброс теста глубины
        if (!Data.wireframe) {
            gl.glCullFace(GL.GL_BACK);                            // Сброс режима каркаса
            gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL);             // Сброс скрывания задних поверностей
        } else {
            gl.glCullFace(GL.GL_FRONT);
        }
        gl.glDisable(GL.GL_BLEND);                                // Отмена смешивания цветов
    }

    static public void build(GL gl, float[][] matr, Color color) {
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        for (float[] matr1 : matr) {
            gl.glVertex2f(matr1[0], matr1[1]);
        }
        gl.glEnd();
    }

    static public void buildModel(GL gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, 0f);
        if (!(Data.xrot == 0 && Data.yrot == 0 && Data.zrot == 0)) {
            gl.glRotatef(Data.figure.angle, Data.xrot, Data.yrot, Data.zrot);
        }
        Builder.build(gl, Data.figure, Data.lightColor);
        Builder.buildNormal(gl, Data.figure, Data.detail);
        gl.glPopMatrix();
    }

    static public void buildLight(GL gl, boolean active) {
        if (active) {
            gl.glPushMatrix();
            gl.glTranslatef(Data.lightAngle.X, Data.lightAngle.Y, Data.lightAngle.Z);
            Builder.build(gl, new float[][]{
                new float[]{0, 0, 0},
                new float[]{Data.lightAngle.X, Data.lightAngle.Y, Data.lightAngle.Z}
            }, new Color(255, 255, 0));
            gl.glPopMatrix();
        }
    }

    static public void buildNormal(GL gl, Figure f, boolean active) {
        if (active) {
            gl.glLineWidth(0.1f);
            for (Figure.Plane plane : f.planes) {
                gl.glBegin(GL.GL_LINES);
                gl.glColor3f(0.3f, 0.4f, 0.9f);
                gl.glVertex3f(plane.X, plane.Y, plane.Z);
                gl.glVertex3f(plane.X * 2, plane.Y * 2, plane.Z * 2);
                gl.glEnd();
            }
        }
    }
}
