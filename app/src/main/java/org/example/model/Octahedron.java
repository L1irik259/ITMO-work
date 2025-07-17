package org.example.model;

import org.checkerframework.checker.units.qual.t;

public class Octahedron {
    private double x;
    private double y;
    private double z;

    private double radius;
    
    private double height;
    private double length;
    private double width;
    
    // Два угла (в радианах)
    private double angleXY;
    private double angleXZ;
    private double angleYZ;

    private Point3D pointE;
    private Point3D pointA;
    private Point3D pointB;
    private Point3D pointC;
    private Point3D pointD;
    private Point3D pointF;

    public Octahedron(double x, double y, double z,
                      double height, double length, double width,
                      double angleXY, double angleXZ, double angleYZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = height;
        this.length = length;
        this.width = width;
        this.angleXY = normalizeAngle(angleXY);
        this.angleXZ = normalizeAngle(angleXZ);
        this.angleYZ = normalizeAngle(angleYZ);
        this.radius = (double) (((length * length + width * width) > 2 * height) ? 
                    Math.sqrt((length * length + width * width) / 4) :
                    Math.sqrt(height / 2));
    }

    // Вспомогательный метод для нормализации угла (приведение к диапазону [0, 2π))
    private double normalizeAngle(double angle) {
        double normalized = angle % (2 * Math.PI);
        if (normalized < 0) {
            normalized += 2 * Math.PI;
        }
        return normalized;
    }

    // проверка на пересечение
    // false -- если не пересекается 
    // true -- если есть пересечение, по окружностям 
    public boolean checkingIntersection(Octahedron octahedron) {
        // Квадрат расстояния между центрами
        double dx = this.x - octahedron.x;
        double dy = this.y - octahedron.y;
        double dz = this.z - octahedron.z;
        double distanceSquared = dx*dx + dy*dy + dz*dz;

        if ((this.radius + octahedron.radius) * (this.radius + octahedron.radius) >= distanceSquared) {
            return false;
        } else {
            return true;
        }
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getZ() { return z; }
    public void setZ(double z) { this.z = z; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getAngleXY() { return angleXY; }
    public void setAngleXY(double angleXY) { this.angleXY = normalizeAngle(angleXY); }

    public double getAngleXZ() { return angleXZ; }
    public void setAngleXZ(double angleXZ) { this.angleXZ = normalizeAngle(angleXZ); }

    public double getAngleYZ() { return angleYZ; }
    public void setAngleYZ(double angleYZ) { this.angleYZ = normalizeAngle(angleYZ); }

    public Point3D getPointE() { return pointE; }
    public void setPointE(Point3D pointE) { this.pointE = pointE; }

    public Point3D getPointA() { return pointA; }
    public void setPointA(Point3D pointA) { this.pointA = pointA; }

    public Point3D getPointB() { return pointB; }
    public void setPointB(Point3D pointB) { this.pointB = pointB; }

    public Point3D getPointC() { return pointC; }
    public void setPointC(Point3D pointC) { this.pointC = pointC; }

    public Point3D getPointD() { return pointD; }
    public void setPointD(Point3D pointD) { this.pointD = pointD; }

    public Point3D getPointF() { return pointF; }
    public void setPointF(Point3D pointF) { this.pointF = pointF; }

    @Override
    public String toString() {
        return String.format(
            "SpatialData[Coord: (%.2f, %.2f, %.2f), Values: (%.2f, %.2f), Angles: (%.2fπ, %.2fπ)]",
            x, y, z, height, length, angleXY / Math.PI, angleXZ / Math.PI
        );
    }

    public void FindingCoordinates(double angleXY, double angleXZ, double angleZY, double length, double width, double height, double alpha) {
        this.pointE = PlaneIntersection.getPointCoordinates(angleXY, angleXZ, angleZY, height / 2);
        this.pointF = new Point3D(-1 * pointE.getX(), -1 * pointE.getY(), -1 * pointE.getZ());
        
        Point3D[] arrH = PlaneIntersection.findHeightdPoints(-1 * pointE.getX() / pointE.getY());
        Point3D H = arrH[0];

        Point3D P = PlaneIntersection.findIntersectionPoint(pointE.getX(), pointE.getY(), pointE.getZ(), 
                                                            -1 * pointE.getY(), pointE.getX(), 0);
                                                        
        Point3D T = PlaneIntersection.findPointWithAngle(H, P, alpha, width / 2);

        Point3D plane = new Point3D(pointE.getX(), pointE.getY(), pointE.getZ());
        Point3D[] arrAB = PlaneIntersection.findPointsABCD(plane, T, length / 2);
        this.pointA = arrAB[0];
        this.pointB = arrAB[1];

        Point3D T1 = new Point3D(-1 * T.getX(), -1 * T.getY(), -1 * T.getZ());
        Point3D[] arrCD = PlaneIntersection.findPointsABCD(plane, T1, length / 2);
        this.pointC = arrCD[0];
        this.pointD = arrCD[1];
    }
}
