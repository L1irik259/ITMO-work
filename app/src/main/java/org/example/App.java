package org.example;

import org.example.model.Octahedron;
import org.example.model.PlaneIntersection;
import org.example.model.Point3D;
import org.example.model.Triangle;

public class App {
    public static void main(String[] args) {
        // // Пример использования: все углы по π/4, расстояние от начала — 5
        // double[] result = PlaneIntersection.findIntersectionPoint(0, 0, 0, 1.04719, 0.7853, 0.5236, 5);
        // Point3D result = PlaneIntersection.getPointCoordinates(Math.asin(1 / Math.sqrt(3)), Math.asin(1 / Math.sqrt(3)), Math.asin(1 / Math.sqrt(3)), 5);

        // // Выводим результат в консоль, округляя до 4 знаков
        // System.out.printf("Intersection point at distance k: (%.4f, %.4f, %.4f)%n", result.getX(), result.getY(), result.getZ());

        // Point3D result3 = PlaneIntersection.getPointCoordinates(0.785398, 0.601266, 0.438078, 7.07);
        // System.out.printf("Intersection point at distance k: (%.4f, %.4f, %.4f)%n", result3.getX(), result3.getY(), result3.getZ());
        
        // Point3D result4 = PlaneIntersection.findIntersectionPoint(1, 2, 3, -2, 1, 0);
        // System.out.printf("(%.4f, %.4f, %.4f)%n", result4.getX(), result4.getY(), result4.getZ());


        // Point3D[] result5 = PlaneIntersection.findHeightdPoints(-0.5);
        // System.out.printf("(%.4f, %.4f, %.4f)%n", result5[0].getX(), result5[0].getY(), result5[0].getZ());
        // System.out.printf("(%.4f, %.4f, %.4f)%n", result5[1].getX(), result5[1].getY(), result5[1].getZ());

        // Point3D H = new Point3D(result5[0].getX(), result5[0].getY(), result5[0].getZ());
        // Point3D P = new Point3D(result4.getX(), result4.getY(), result4.getZ());    
        // Point3D T = PlaneIntersection.findPointWithAngle(H, P, 0.523599, 5);
        // System.out.printf("H --- (%.4f, %.4f, %.4f)%n", H.getX(), H.getY(), H.getZ());
        // System.out.printf("T --- (%.4f, %.4f, %.4f)%n", T.getX(), T.getY(), T.getZ());

        // Point3D Plane = new Point3D(1, 2, 3);
        // Point3D[] AB = PlaneIntersection.findPointsABCD(Plane, T, 3);
        // System.out.printf("A --- (%.4f, %.4f, %.4f)%n", AB[0].getX(), AB[0].getY(), AB[0].getZ());
        // System.out.printf("B --- (%.4f, %.4f, %.4f)%n", AB[1].getX(), AB[1].getY(), AB[1].getZ());

        // Point3D T2 = new Point3D(-T.getX(), -T.getY(), -T.getZ());
        // Point3D[] CD = PlaneIntersection.findPointsABCD(Plane, T2, 3);
        // System.out.printf("C --- (%.4f, %.4f, %.4f)%n", CD[0].getX(), CD[0].getY(), CD[0].getZ());
        // System.out.printf("D --- (%.4f, %.4f, %.4f)%n", CD[1].getX(), CD[1].getY(), CD[1].getZ());


        // Octahedron octahedron = new Octahedron(0, 0, 0, 7.48, 10, 6, 0.93026, 0.56391, 0.27052);
        // octahedron.FindingCoordinates(0.93026, 0.56391, 0.27052, 10, 6, 7.48, 0.523599);
        // System.out.println("E = " + octahedron.getPointE());
        // System.out.println("A = " + octahedron.getPointA());
        // System.out.println("B = " + octahedron.getPointB());
        // System.out.println("C = " + octahedron.getPointC());
        // System.out.println("D = " + octahedron.getPointD());
        // System.out.println("F = " + octahedron.getPointF());
        


        // Triangle[] tri1 = {
        //     new Triangle(-5.29735,3.68279,4.22272),
        //     new Triangle(-4.84393,6.34011,0),
        //     new Triangle(2.55571, -5.13617, 0)
        // };

        // Triangle[] tri2 = {
        //     new Triangle(1.43002,-0.83542,3.42157),
        //     new Triangle(-1.34859,3.42124,1),
        //     new Triangle(-2.52965,-3.59162,1.35964)
        // };

        Point3D p1 = new Point3D(-5.29735, 3.68279, 4.22272);
        Point3D p2 = new Point3D(-4.84393, 6.34011, 0);
        Point3D p3 = new Point3D(2.55571, -5.13617, 0);
        Triangle triangle1 = new Triangle(p1, p2, p3);

        // Создаем второй треугольник
        Point3D p4 = new Point3D(1.43002, -0.83542, 3.42157);
        Point3D p5 = new Point3D(-1.34859, 3.42124, 1);
        Point3D p6 = new Point3D(-2.52965, -3.59162, 1.35964);
        Triangle triangle2 = new Triangle(p4, p5, p6);

        System.out.println(Triangle.doTrianglesIntersect(triangle1, triangle2));
    }
}



// T --- (2.9766, -3.7293, 1.4940)
// A --- (0.7033, -4.9217, 3.0467)
// B --- (5.2498, -2.5370, -0.0586)
// C --- (0.7033, -4.9217, 3.0467)
// C --- (-0.7033, 4.9217, -3.0467)
// D --- (-5.2498, 2.5370, 0.0586)
