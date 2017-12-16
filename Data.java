/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.awt.Color;
import java.io.File;
import java.nio.FloatBuffer;

/**
 *
 * @author Satilast
 */
public class Data {

    static FloatBuffer shaderDataFile = Reader.getShaderData("./data/Shader.txt");
    static int countTokensFile = Reader.getShaderData("./data/Shader.txt").capacity() / 3;
    static long time;
    static File model;
    static Figure figure;                                                     
    static Vector lightAngle;
    static boolean detail = false, wireframe = false;
    static Color lightColor = Color.GRAY;

    static float xrot = 0;
    static float yrot = 0;
    static float zrot = 0;
    static float speedOfRot = 0;
    
    static int countOfBall = 3;
    static int frames;
    static int fps;
    static int countHue = 50;
    static int angleH = 60;
    static int angleV = 90;
    static int toCenter = 10;
    static int shaderTexture[] = new int[1];

    public static double xView, zView, yView;

}
