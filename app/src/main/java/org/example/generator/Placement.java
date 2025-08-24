package org.example.generator;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.example.model.Octahedron;
import org.example.model.Parallelepiped;

// Класс, чтобы распределять фигуры в параллелепипед 
public class Placement {
    Parallelepiped parallelepiped;

    public Placement(Parallelepiped parallelepiped) {
        this.parallelepiped = parallelepiped;
    }

    public List<Octahedron> placementInParallelepiped(Pair<List<Double>, List<Double>> octahedronParameters) {
        List<Double> heights = octahedronParameters.getLeft(); 
        List<Double> widths = octahedronParameters.getRight();
        List<Octahedron> octahedrons = new ArrayList();
        double maxSide;
        double x, y, z;
        double theta, phi, alpha;
        Octahedron nowOctahedron;

        for (int i = 0; i < heights.size() ; i++) {
            maxSide = Math.max(heights.get(i), widths.get(i));
            x = Placement.randomDouble(parallelepiped.getLength() - maxSide);
            y = Placement.randomDouble(parallelepiped.getHeight() - maxSide);
            z = Placement.randomDouble(parallelepiped.getWidth() - maxSide);

            theta = Math.toRadians(Placement.randomDouble(180));
            phi = Math.toRadians(Placement.randomDouble(180));
            alpha = Math.toRadians(Placement.randomDouble(180));

            nowOctahedron = new Octahedron(x, y, z, heights.get(i), widths.get(i), widths.get(i), theta, phi, alpha);
            nowOctahedron.FindingCoordinates();
            boolean flag = true;

            while (flag) {
                flag = false;
                for (int j = 0; j < octahedrons.size(); j++) {
                    if (nowOctahedron.checkingIntersection(octahedrons.get(j))) {    
                        x = Placement.randomDouble(parallelepiped.getLength() - maxSide);
                        y = Placement.randomDouble(parallelepiped.getHeight() - maxSide);
                        z = Placement.randomDouble(parallelepiped.getWidth() - maxSide);

                        nowOctahedron.setX(x);
                        nowOctahedron.setY(y);
                        nowOctahedron.setZ(z);

                        flag = true;
                        break;
                    }
                }
            }

            octahedrons.add(nowOctahedron);
        }

        return octahedrons;
    }

    public static double randomDouble(double k) {
        return Math.random() * k;
    }
}
