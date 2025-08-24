package org.example.model;

public class Parallelepiped {
    private Point3D center;

    private double length, width, height; 

    private Point3D A;
    private Point3D B;
    private Point3D C;
    private Point3D D;
    private Point3D E;
    private Point3D F;
    private Point3D G;
    private Point3D H;

    public Parallelepiped(Point3D center, double length, double width, double height) {
        this.center = center;
        this.length = length;
        this.width = width;
        this.height = height;

        double x = center.getX();
        double y = center.getY();
        double z = center.getZ();

        double dx = length / 2.0;
        double dy = height / 2.0;
        double dz = width / 2.0;

        // Нижняя грань (z - dz)
        A = new Point3D(x - dx, y - dy, z - dz); // нижняя задняя левая
        B = new Point3D(x + dx, y - dy, z - dz); // нижняя задняя правая
        C = new Point3D(x + dx, y + dy, z - dz); // верхняя задняя правая
        D = new Point3D(x - dx, y + dy, z - dz); // верхняя задняя левая

        // Верхняя грань (z + dz)
        E = new Point3D(x - dx, y - dy, z + dz); // нижняя передняя левая
        F = new Point3D(x + dx, y - dy, z + dz); // нижняя передняя правая
        G = new Point3D(x + dx, y + dy, z + dz); // верхняя передняя правая
        H = new Point3D(x - dx, y + dy, z + dz); // верхняя передняя левая
    }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public Point3D getA() { return A; }
    public void setA(Point3D a) { this.A = a; }

    public Point3D getB() { return B; }
    public void setB(Point3D b) { this.B = b; }

    public Point3D getC() { return C; }
    public void setC(Point3D c) { this.C = c; }

    public Point3D getD() { return D; }
    public void setD(Point3D d) { this.D = d; }

    public Point3D getE() { return E; }
    public void setE(Point3D e) { this.E = e; }

    public Point3D getF() { return F; }
    public void setF(Point3D f) { this.F = f; }

    public Point3D getG() { return G; }
    public void setG(Point3D g) { this.G = g; }

    public Point3D getH() { return H; }
    public void setH(Point3D h) { this.H = h; }

    @Override
    public String toString() {
        return "Parallelepiped {\n" +
                "A=" + A + ",\n" +
                "B=" + B + ",\n" +
                "C=" + C + ",\n" +
                "D=" + D + ",\n" +
                "E=" + E + ",\n" +
                "F=" + F + ",\n" +
                "G=" + G + ",\n" +
                "H=" + H + "\n}";
    }
}
