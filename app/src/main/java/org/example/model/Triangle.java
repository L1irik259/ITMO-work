package org.example.model;

public class Triangle {
    private Point3D a, b, c;

    public Triangle(Point3D a, Point3D b, Point3D c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Point3D getA() { return a; }
    public void setA(Point3D a) { this.a = a; }

    public Point3D getB() { return b; }
    public void setB(Point3D b) { this.b = b; }

    public Point3D getC() { return c; }
    public void setC(Point3D c) { this.c = c; }

    // Проверяет, пересекаются ли два треугольника
    public static boolean doTrianglesIntersect(Triangle t1, Triangle t2) {
        // Проверяем, лежат ли треугольники в одной плоскости
        if (areTrianglesCoplanar(t1, t2)) {
            return checkCoplanarIntersection(t1, t2);
        }

        // Проверяем пересечение каждого треугольника с плоскостью другого
        return checkTrianglePlaneIntersection(t1, t2) &&
               checkTrianglePlaneIntersection(t2, t1);
    }

    // Проверяет, лежат ли треугольники в одной плоскости
    private static boolean areTrianglesCoplanar(Triangle t1, Triangle t2) {
        // Нормаль к первому треугольнику
        Point3D v1 = new Point3D(t1.b.getX() - t1.a.getX(), t1.b.getY() - t1.a.getY(), t1.b.getZ() - t1.a.getZ());
        Point3D v2 = new Point3D(t1.c.getX() - t1.a.getX(), t1.c.getY() - t1.a.getY(), t1.c.getZ() - t1.a.getZ());
        Point3D normal1 = Point3D.crossProduct(v1, v2);

        // Проверяем, лежат ли все вершины второго треугольника в плоскости первого
        Point3D[] points = {t2.a, t2.b, t2.c};
        for (Point3D p : points) {
            double dot = (p.getX() - t1.a.getX()) * normal1.getX() +
                         (p.getY() - t1.a.getY()) * normal1.getY() +
                         (p.getZ() - t1.a.getZ()) * normal1.getZ();
            if (Math.abs(dot) > 1e-10) {
                return false;
            }
        }
        return true;
    }

    // Проверяет пересечение треугольников в одной плоскости
    private static boolean checkCoplanarIntersection(Triangle t1, Triangle t2) {
        // Проецируем точки на плоскость XY для упрощения вычислений
        Point3D[] proj1 = projectTo2D(new Point3D[]{t1.a, t1.b, t1.c});
        Point3D[] proj2 = projectTo2D(new Point3D[]{t2.a, t2.b, t2.c});

        // Проверяем пересечение сторон треугольников в 2D
        Point3D[] t1Points = {t1.a, t1.b, t1.c};
        Point3D[] t2Points = {t2.a, t2.b, t2.c};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (doSegmentsIntersect(
                        proj1[i], proj1[(i + 1) % 3],
                        proj2[j], proj2[(j + 1) % 3])) {
                    return true;
                }
            }
        }
        // Проверяем, находится ли одна вершина одного треугольника внутри другого
        return isPointInTriangle(proj2[0], proj1) || isPointInTriangle(proj1[0], proj2);
    }

    // Проверяет пересечение треугольника с плоскостью другого
    private static boolean checkTrianglePlaneIntersection(Triangle t1, Triangle t2) {
        // Нормаль к плоскости второго треугольника
        Point3D v1 = new Point3D(t2.b.getX() - t2.a.getX(), t2.b.getY() - t2.a.getY(), t2.b.getZ() - t2.a.getZ());
        Point3D v2 = new Point3D(t2.c.getX() - t2.a.getX(), t2.c.getY() - t2.a.getY(), t2.c.getZ() - t2.a.getZ());
        Point3D normal = Point3D.crossProduct(v1, v2);

        // Вычисляем знаки расстояний от вершин t1 до плоскости t2
        Point3D[] points = {t1.a, t1.b, t1.c};
        double[] signs = new double[3];
        for (int i = 0; i < 3; i++) {
            signs[i] = (points[i].getX() - t2.a.getX()) * normal.getX() +
                       (points[i].getY() - t2.a.getY()) * normal.getY() +
                       (points[i].getZ() - t2.a.getZ()) * normal.getZ();
        }

        // Если все вершины с одной стороны плоскости, пересечения нет
        if ((signs[0] > 0 && signs[1] > 0 && signs[2] > 0) ||
            (signs[0] < 0 && signs[1] < 0 && signs[2] < 0)) {
            return false;
        }

        // Проверяем пересечение сторон t1 с треугольником t2
        for (int i = 0; i < 3; i++) {
            Point3D p1 = points[i];
            Point3D p2 = points[(i + 1) % 3];
            double d1 = signs[i];
            double d2 = signs[(i + 1) % 3];

            // Если отрезок пересекает плоскость
            if (d1 * d2 < 0) {
                // Находим точку пересечения отрезка с плоскостью
                double t = d1 / (d1 - d2);
                Point3D intersection = new Point3D(
                    p1.getX() + t * (p2.getX() - p1.getX()),
                    p1.getY() + t * (p2.getY() - p1.getY()),
                    p1.getZ() + t * (p2.getZ() - p1.getZ())
                );

                // Проверяем, лежит ли точка пересечения внутри t2
                if (isPointInTriangle(intersection, new Point3D[]{t2.a, t2.b, t2.c})) {
                    return true;
                }
            }
        }
        return false;
    }

    // Проецирует 3D точки на 2D плоскость (XY)
    private static Point3D[] projectTo2D(Point3D[] triangle) {
        Point3D[] proj = new Point3D[3];
        for (int i = 0; i < 3; i++) {
            proj[i] = new Point3D(triangle[i].getX(), triangle[i].getY(), 0);
        }
        return proj;
    }

    // Проверяет пересечение двух отрезков в 2D
    private static boolean doSegmentsIntersect(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);

        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
            ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        }

        // Проверяем случаи, когда точки лежат на отрезке
        if (d1 == 0 && isPointOnSegment(p3, p4, p1)) return true;
        if (d2 == 0 && isPointOnSegment(p3, p4, p2)) return true;
        if (d3 == 0 && isPointOnSegment(p1, p2, p3)) return true;
        if (d4 == 0 && isPointOnSegment(p1, p2, p4)) return true;

        return false;
    }

    // Вычисляет направление для проверки пересечения отрезков
    private static double direction(Point3D pi, Point3D pj, Point3D pk) {
        return (pj.getX() - pi.getX()) * (pk.getY() - pi.getY()) -
               (pj.getY() - pi.getY()) * (pk.getX() - pi.getX());
    }

    // Проверяет, лежит ли точка на отрезке
    private static boolean isPointOnSegment(Point3D p1, Point3D p2, Point3D p) {
        return Math.min(p1.getX(), p2.getX()) <= p.getX() + 1e-10 &&
               p.getX() <= Math.max(p1.getX(), p2.getX()) + 1e-10 &&
               Math.min(p1.getY(), p2.getY()) <= p.getY() + 1e-10 &&
               p.getY() <= Math.max(p1.getY(), p2.getY()) + 1e-10;
    }

    // Проверяет, лежит ли точка внутри треугольника
    private static boolean isPointInTriangle(Point3D p, Point3D[] triangle) {
        Point3D a = triangle[0], b = triangle[1], c = triangle[2];
        double d1 = direction(a, b, p);
        double d2 = direction(b, c, p);
        double d3 = direction(c, a, p);
        return (d1 >= 0 && d2 >= 0 && d3 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0);
    }
}