package org.yourorghere;

import com.sun.opengl.util.BufferUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Reader {

    private ArrayList<String> vertexOld;
    private ArrayList<String> vertexNormalOld;
    private ArrayList<String> functionsOld;
    private ArrayList<float[]> vertex;
    private ArrayList<float[]> vertexNormal;
    private ArrayList<ArrayList<int[]>> functions;

    private Reader() {

    }

    static Reader create() {
        return new Reader();
    }

    void readFigure(String filename) {
        try {
            Scanner scan = new Scanner(new FileReader(filename));
            vertexOld = new ArrayList();
            vertexNormalOld = new ArrayList();
            functionsOld = new ArrayList();
            while (scan.hasNext()) {
                String str = scan.nextLine();
                if (!"".equals(str)) {
                    if (str.charAt(0) == 'v') {
                        if (str.charAt(1) == ' ') {
                            vertexOld.add(str);
                        } else if (str.charAt(1) == 'n') {
                            vertexNormalOld.add(str);
                        }
                    }
                    if (str.charAt(0) == 'f') {
                        functionsOld.add(str);
                    }
                }

            }
            clear();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл с именем " + filename + " не найден ");
        }
    }

    void clear() {
        vertex = new ArrayList();
        vertexNormal = new ArrayList();
        functions = new ArrayList();
        for (int i = 0; i < vertexOld.size(); i++) {
            vertex.add(new float[]{
                Float.valueOf(vertexOld.get(i).substring(2).split(" ")[0]),
                Float.valueOf(vertexOld.get(i).substring(2).split(" ")[1]),
                Float.valueOf(vertexOld.get(i).substring(2).split(" ")[2])
            });
        }
        for (int i = 0; i < vertexNormalOld.size(); i++) {
            vertexNormal.add(new float[]{
                Float.valueOf(vertexNormalOld.get(i).substring(3).split(" ")[0]),
                Float.valueOf(vertexNormalOld.get(i).substring(3).split(" ")[1]),
                Float.valueOf(vertexNormalOld.get(i).substring(3).split(" ")[2])
            });
        }
        int[] pairs = null;
        for (int i = 0; i < functionsOld.size(); i++) {
            ArrayList<int[]> tempArr = new ArrayList<int[]>();
            for (int j = 0; j < functionsOld.get(i).substring(2).split(" |//").length; j++) {
                if (j % 2 == 0) {
                    pairs = new int[2];
                    tempArr.add(pairs);
                }
                pairs[j % 2] = Integer.valueOf(functionsOld.get(i).substring(2).split(" |//|/")[j]);
            }
            functions.add(tempArr);
        }
    }

    float[][][] readVertex() {
        float[][][] matrix = null;
        int max = 0;
        for (ArrayList f : functions) {
            if (max < f.size()) {
                max = f.size();
            }
        }
        for (int i = 0; i < functions.size(); i++) {
            if (i == 0) {
                matrix = new float[functions.size()][max][3];
            }
            for (int j = 0; j < functions.get(i).size(); j++) {
                matrix[i][j] = vertex.get(functions.get(i).get(j)[0] - 1);
            }
        }
        return matrix;
    }

    float[][] readNormal() {
        float[][] matrix = null;
        int max = 0;
        for (ArrayList f : functions) {
            if (max < f.size()) {
                max = f.size();
            }
        }
        for (int i = 0; i < functions.size(); i++) {
            if (i == 0) {
                matrix = new float[functions.size()][3];
            }
            for (int j = 0; j < functions.get(i).size(); j++) {
                matrix[i] = vertexNormal.get(functions.get(i).get(j)[1] - 1);
            }
        }
        return matrix;
    }

    public static InputStream readShader(String filename) throws FileNotFoundException {
        InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
        if (stream == null) {
            return new FileInputStream(filename);
        } else {
            return stream;
        }
    }

    public static FloatBuffer getShaderData(String filename) {
        StringBuilder readShaderData = new StringBuilder();
        StringTokenizer tokenizer;
        try {
            InputStream inputStream = Reader.readShader(filename);
            int info;
            while ((info = inputStream.read()) != -1) {
                readShaderData.append((char) info);
            }
            inputStream.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        tokenizer = new StringTokenizer(readShaderData.toString());
        int countTokens = tokenizer.countTokens();
        FloatBuffer shaderData = BufferUtil.newFloatBuffer(countTokens * 3);
        while (tokenizer.hasMoreTokens()) {
            float value = Float.parseFloat(tokenizer.nextToken());
            shaderData.put(value);
            shaderData.put(value);
            shaderData.put(value);
        }
        shaderData.flip();
        return shaderData;
    }

    public static void loadModel() {
        JFileChooser fileopen = new JFileChooser("data/");
        fileopen.setFileFilter(new FileNameExtensionFilter("Трёхмерные объекты", "obj"));
        int ret = fileopen.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            Data.model = fileopen.getSelectedFile();
            Data.figure = new Figure(Data.model.getAbsolutePath());
        }
    }
}
