package org.yourorghere;

public class Matrix {

    float Data[] = new float[16];

    public void rotateVector(Vector V, Vector D) {              // Rotate A Vector Using The Supplied Matrix ( NEW )
        D.X = (Data[0] * V.X) + (Data[4] * V.Y) + (Data[8] * V.Z);  // Rotate Around The X Axis ( NEW )
        D.Y = (Data[1] * V.X) + (Data[5] * V.Y) + (Data[9] * V.Z);  // Rotate Around The Y Axis ( NEW )
        D.Z = (Data[2] * V.X) + (Data[6] * V.Y) + (Data[10] * V.Z);  // Rotate Around The Z Axis ( NEW )
    }
}
