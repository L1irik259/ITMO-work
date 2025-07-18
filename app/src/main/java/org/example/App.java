package org.example;

import org.example.model.Octahedron;
import org.example.model.PlaneIntersection;
import org.example.model.Point3D;
import org.example.model.Triangle;

public class App {
    public static void main(String[] args) {
        Octahedron octahedron = new Octahedron(0, 0, 0, 7.48, 10, 6, Math.toRadians(45), Math.toRadians(30), 0.523599);
        octahedron.FindingCoordinates();
        System.out.println("E = " + octahedron.getPointE());
        System.out.println("A = " + octahedron.getPointA());
        System.out.println("B = " + octahedron.getPointB());
        System.out.println("C = " + octahedron.getPointC());
        System.out.println("D = " + octahedron.getPointD());
        System.out.println("F = " + octahedron.getPointF());

        // System.out.println("\n");
        // double length = 5.0;
        // double theta = Math.toRadians(45); // угол к Y
        // double phi = Math.toRadians(30);   // угол в XZ

        // Point3D pointE = PlaneIntersection.computeVectorPoint(length, theta, phi);

        // System.out.printf("E = (%.2f, %.2f, %.2f)%n", pointE.getX(), pointE.getY(), pointE.getZ());
    }
}



// T = (2.9766, -3.7293, 1.4940)
// A = (0.7033, -4.9217, 3.0467)
// B = (5.2498, -2.5370, -0.0586)
// C = (0.7033, -4.9217, 3.0467)
// C = (-0.7033, 4.9217, -3.0467)
// D = (-5.2498, 2.5370, 0.0586)
