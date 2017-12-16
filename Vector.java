package org.yourorghere;

public class Vector {

    float X, Y, Z;

    public static float dotProduct(Vector V1, Vector V2) {
        return V1.X * V2.X + V1.Y * V2.Y + V1.Z * V2.Z;
    }

    public float magnitude() {                                           // Calculate The Length Of The Vector ( NEW )
        return (float) Math.sqrt(X * X + Y * Y + Z * Z); // Return The Length Of The Vector ( NEW )
    }

    public void normalize() {                                     // Creates A Vector With A Unit Length Of 1 ( NEW )
        float M = magnitude();                                    // Calculate The Length Of The Vector  ( NEW )

        if (M != 0.0f) {                                              // Make Sure We Don't Divide By 0  ( NEW )
            X /= M;                                                 // Normalize The 3 Components  ( NEW )
            Y /= M;
            Z /= M;
        }
    }
}
