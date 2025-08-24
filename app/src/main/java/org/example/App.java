package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.forDataBase.modelForDB.User;
import org.example.forDataBase.service.UserService;
import org.example.generator.Generator;
import org.example.generator.Placement;
import org.example.model.ChartDrawer;
import org.example.model.Octahedron;
import org.example.model.Parallelepiped;
import org.example.model.Point3D;
import org.example.model.Triangle;
import org.example.workWithPointsOctahedron.PlaneIntersection;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.units.qual.s;

public class App {
    public static void main(String[] args) {
        int n = 100000;
        double sum = 0;
        Pair<List<Double>, List<Double>> result  = Generator.generateSizeOctahedron(n, 2.0,1200.0);
        Parallelepiped parallelepiped = new Parallelepiped(new Point3D(15, 10, 20), 3000, 2000, 4000);

        List<Octahedron> arr = Generator.generateOctahedronInParallelepiped(parallelepiped, result);

        String path = Paths.get("src", "main", "resources", "octahedra.txt").toString();

        try (FileWriter writer = new FileWriter(path, false)) {
            for (Octahedron octahedron : arr) {
                octahedron.FindingCoordinates();

                writer.write(octahedron.getX() + " " + octahedron.getY() + " " + octahedron.getZ() + " " + octahedron.getTheta() + " " + octahedron.getPhi() + " " + octahedron.getAlpha() + " " + octahedron.getHeight() + " " + octahedron.getWidth() + "\n\n");
            }
            writer.flush();
            System.out.println("The file was created in: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
