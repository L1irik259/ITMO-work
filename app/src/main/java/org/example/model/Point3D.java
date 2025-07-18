package org.example.model;

public class Point3D {
    private double x, y, z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() { return x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return y; }

    public void setY(double y) { this.y = y; }

    public double getZ() { return z; }

    public void setZ(double z) { this.z = z; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    // метод сложения точек в 3da
    public void sum(Point3D secondElement) {
        this.x += secondElement.x;
        this.y += secondElement.y;
        this.z += secondElement.z;
    }

    // Функция для вычисления векторного произведения
    public static Point3D crossProduct(Point3D v1, Point3D v2) {
        return new Point3D(
            v1.y * v2.z - v1.z * v2.y,
            v1.z * v2.x - v1.x * v2.z,
            v1.x * v2.y - v1.y * v2.x
        );
    }

    // Функция для нормализации вектора
    public static Point3D normalize(Point3D v) {
        double length = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
        if (length == 0) throw new IllegalArgumentException("Zero vector cannot be normalized");
        return new Point3D(v.x / length, v.y / length, v.z / length);
    }
}
