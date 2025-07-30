package org.example.workWithPointsOctahedron;

import org.example.model.Octahedron;
import org.example.model.Triangle;

public class CheckingIntersectionOctahedra {
    // Один из методов, который помогает узнать, есть ли пересечерия у октаэдоров 
    // Берем по 8 треугольников у каждого октаэдора и проверяем их треугольники на пересечения
    // true -- не пеерсекаются, все хорошо 
    // false -- пересекаются
    public boolean checkingIntersectionOctahedra(Octahedron octahedron1, Octahedron octahedron2) {
        Triangle[] trianglesoctahedron1 = {
            new Triangle(octahedron1.getPointA(), octahedron1.getPointB(), octahedron1.getPointE()), // ABE
            new Triangle(octahedron1.getPointA(), octahedron1.getPointB(), octahedron1.getPointF()), // ABF
            new Triangle(octahedron1.getPointB(), octahedron1.getPointC(), octahedron1.getPointE()), // BCE
            new Triangle(octahedron1.getPointB(), octahedron1.getPointC(), octahedron1.getPointF()), // BCF
            new Triangle(octahedron1.getPointC(), octahedron1.getPointD(), octahedron1.getPointE()), // CDE
            new Triangle(octahedron1.getPointC(), octahedron1.getPointD(), octahedron1.getPointF()), // CDF
            new Triangle(octahedron1.getPointD(), octahedron1.getPointA(), octahedron1.getPointE()), // DAE
            new Triangle(octahedron1.getPointD(), octahedron1.getPointA(), octahedron1.getPointF())  // DAF                     
        };

        Triangle[] trianglesoctahedron2 = {
            new Triangle(octahedron2.getPointA(), octahedron2.getPointB(), octahedron2.getPointE()), // ABE
            new Triangle(octahedron2.getPointA(), octahedron2.getPointB(), octahedron2.getPointF()), // ABF
            new Triangle(octahedron2.getPointB(), octahedron2.getPointC(), octahedron2.getPointE()), // BCE
            new Triangle(octahedron2.getPointB(), octahedron2.getPointC(), octahedron2.getPointF()), // BCF
            new Triangle(octahedron2.getPointC(), octahedron2.getPointD(), octahedron2.getPointE()), // CDE
            new Triangle(octahedron2.getPointC(), octahedron2.getPointD(), octahedron2.getPointF()), // CDF
            new Triangle(octahedron2.getPointD(), octahedron2.getPointA(), octahedron2.getPointE()), // DAE
            new Triangle(octahedron2.getPointD(), octahedron2.getPointA(), octahedron2.getPointF())  // DAF   
        };

        for (int i = 0; i < trianglesoctahedron1.length; i++) {
            for (int j = 0; j < trianglesoctahedron2.length; j++) {
                if (Triangle.doTrianglesIntersect(trianglesoctahedron1[i], trianglesoctahedron2[j])) {
                    return false;
                }
            }
        }

        return true;
    }
}
