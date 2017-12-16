package org.yourorghere;

public class Figure {

    float angle = 0;
    Plane[] planes;

    Figure(float[][][] vertexs, float[][] normals) {
        setVertex(vertexs, normals);
    }

    Figure(String filename) {
        Reader r = Reader.create();
        r.readFigure(filename);
        setVertex(r.readVertex(), r.readNormal());
    }

    private void setVertex(float[][][] vertexs, float[][] normals) {
        planes = new Plane[vertexs.length];
        for (int i = 0; i < planes.length; i++) {
            planes[i] = new Plane(vertexs[i].length);
        }
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs[i].length; j++) {
                this.planes[i].verts[j].X = vertexs[i][j][0];
                this.planes[i].verts[j].Y = vertexs[i][j][1];
                this.planes[i].verts[j].Z = vertexs[i][j][2];
            }
        }
        for (int i = 0; i < normals.length; i++) {
            this.planes[i].X = normals[i][0];
            this.planes[i].Y = normals[i][1];
            this.planes[i].Z = normals[i][2];
        }
    }

    public class Plane {

        Vert[] verts;
        float X, Y, Z;

        Plane(int numV) {
            verts = new Vert[numV];
            for (int i = 0; i < verts.length; i++) {
                verts[i] = new Vert();
            }
        }

        public class Vert {
            float X, Y, Z;
        }
    }

}
