package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.model.Point3D;
import org.example.model.Triangle;

public class AppTest {

    @Test
    public void testIntersectingTriangles1() {
        // Треугольники, которые пересекаются в 3D
        Triangle t1 = new Triangle(
            new Point3D(0, 0, 0),
            new Point3D(4, 0, 0),
            new Point3D(0, 4, 0)
        );
        Triangle t2 = new Triangle(
            new Point3D(1, 1, -1),
            new Point3D(1, 1, 1),
            new Point3D(3, 3, 0)
        );
        assertTrue(Triangle.doTrianglesIntersect(t1, t2), "Triangles should intersect");
    }

    @Test
    public void testIntersectingTriangles2() {
        // Треугольники, которые пересекаются в 3D
        Triangle t1 = new Triangle(
            new Point3D(-5.29735, 3.68279, 4.22272),
            new Point3D(-4.84393, 6.34011, 0),
            new Point3D(2.55571, -5.13617, 0)
        );
        Triangle t2 = new Triangle(
            new Point3D(1.43002, -0.83542, 3.42157),
            new Point3D(-1.34859, 3.42124, 1),
            new Point3D(-2.52965, -3.59162, 1.35964)
        );
        assertTrue(Triangle.doTrianglesIntersect(t1, t2), "Triangles should intersect");
    }

    @Test
    public void testNonIntersectingTriangles() {
        // Треугольники, которые не пересекаются (далеко друг от друга)
        Triangle t1 = new Triangle(
            new Point3D(0, 0, 0),
            new Point3D(1, 0, 0),
            new Point3D(0, 1, 0)
        );
        Triangle t2 = new Triangle(
            new Point3D(10, 10, 10),
            new Point3D(11, 10, 10),
            new Point3D(10, 11, 10)
        );
        assertFalse(Triangle.doTrianglesIntersect(t1, t2), "Triangles should not intersect");
    }

    @Test
    public void testCoplanarIntersectingTriangles() {
        // Треугольники в одной плоскости, пересекающиеся
        Triangle t1 = new Triangle(
            new Point3D(0, 0, 0),
            new Point3D(4, 0, 0),
            new Point3D(0, 4, 0)
        );
        Triangle t2 = new Triangle(
            new Point3D(1, 1, 0),
            new Point3D(5, 1, 0),
            new Point3D(1, 5, 0)
        );
        assertTrue(Triangle.doTrianglesIntersect(t1, t2), "Coplanar triangles should intersect");
    }

    @Test
    public void testTrianglesWithSharedVertex() {
        // Треугольники с общей вершиной, но без пересечения ребра
        Triangle t1 = new Triangle(
            new Point3D(0, 0, 0),
            new Point3D(2, 0, 0),
            new Point3D(0, 2, 0)
        );
        Triangle t2 = new Triangle(
            new Point3D(0, 0, 0),
            new Point3D(0, 0, 2),
            new Point3D(-2, 0, 0)
        );
        assertFalse(Triangle.doTrianglesIntersect(t1, t2), "Triangles with shared vertex should not intersect");
    }

    @Test
    public void testDegenerateTriangle() {
        // Один из треугольников вырожденный (все точки на одной прямой)
        Triangle t1 = new Triangle(
            new Point3D(0, 0, 0),
            new Point3D(1, 1, 1),
            new Point3D(2, 2, 2)
        );
        Triangle t2 = new Triangle(
            new Point3D(0, 0, 3),
            new Point3D(1, 0, 3),
            new Point3D(0, 1, 3)
        );
        assertTrue(Triangle.doTrianglesIntersect(t1, t2), "Degenerate triangle should not intersect with normal triangle");
    }
}