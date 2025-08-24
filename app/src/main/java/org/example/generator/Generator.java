package org.example.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.tuple.Pair;
import org.example.model.Octahedron;
import org.example.model.Parallelepiped;
import org.example.model.Point3D;

public class Generator {
    /**
     * Вычисляет высоты и длину стороны октаэдров, 
     * генеририрует список с нужным количеством чисел, затем
     * уменьшает каждое число в несколько раз, чтобы сумма элементов равнялоась 1.
     * Берет корнеь 3-ей степени, чтобы (h_1) ^ 3 + ... + (h_n) ^ 3 = 1,
     * потом каждый элемент увеличиваем на нужную нам величину, чтобы сумма объемов октаэдора равнялась нужному объему,
     * когда нашел высоту, ищет сторону по формуле w_k = h_k / eps
     * @param countOctahedrons Количество октаэдров 
     * @param eps Коэффициент высоты делить на сторону фигуры (h/w)
     * @param totalVolume Суммарный объем октаэдров
     * @return Список высот фигур 
     */

    public static Pair<List<Double>, List<Double>> generateSizeOctahedron(int countOctahedrons, double eps, double totalVolume) {
        double targetSum = 3 * eps * eps * totalVolume / 2;
        List<Double> resultHeight = new ArrayList<>();
        List<Double> resultWidth = new ArrayList<>();
        double sum = 0;
        double newElement;
        
        for (int i = 0; i < countOctahedrons; i++) {
            newElement = Math.random();
            resultHeight.add(newElement);
            sum += newElement;
        }

        for (int j = 0; j < countOctahedrons; j++) {
            resultHeight.set(j, resultHeight.get(j) / sum);
            resultHeight.set(j, Math.pow(resultHeight.get(j), 1.0 / 3));
            resultHeight.set(j, resultHeight.get(j) * (Math.pow(targetSum, 1.0 / 3)));

            resultWidth.add(resultHeight.get(j) / eps);
        }

        return Pair.of(resultHeight, resultWidth);
    }

    public static List<Octahedron> generateOctahedronInParallelepiped(Parallelepiped parallelepiped, Pair<List<Double>, List<Double>> resultGenerateSizeOctahedron) {
        List<Octahedron> allOctahedrons = new ArrayList<>();
        List<Double> heights = resultGenerateSizeOctahedron.getLeft(); 
        List<Double> widths = resultGenerateSizeOctahedron.getRight();

        heights.sort(null);
        widths.sort(null);
        List<Boolean> checkingForUsageOctahedron = new ArrayList<>();

        for (int i = 0; i < heights.size(); i++) {
            checkingForUsageOctahedron.add(false);
        }

        double h = heights.get(heights.size() - 1);
        double w = widths.get(widths.size() - 1);
        double ratioForBigOctahedron = 1 + Math.random();
        h = h * ratioForBigOctahedron;
        w = w * ratioForBigOctahedron;
        
        
        Point3D nowCenterForBigOcOctahedron = new Point3D(w / 2, w / 2, h);
        int counterUsageOctahedron = 0;

        fillParallelepipedLayer(parallelepiped, heights, widths, checkingForUsageOctahedron, h, w, nowCenterForBigOcOctahedron,  allOctahedrons, counterUsageOctahedron, 
        (int)(parallelepiped.getLength() / w), (int)(parallelepiped.getWidth() / w), (int)(parallelepiped.getHeight() / (2 * h)));

        nowCenterForBigOcOctahedron.setX(w);
        nowCenterForBigOcOctahedron.setY(w);
        nowCenterForBigOcOctahedron.setZ(2 * h);

        fillParallelepipedLayer(parallelepiped, heights, widths, checkingForUsageOctahedron, h, w, nowCenterForBigOcOctahedron,  allOctahedrons, counterUsageOctahedron, 
        (int)((parallelepiped.getLength() - w / 2) / w), (int)((parallelepiped.getWidth() - w / 2) / w), (int)(parallelepiped.getHeight() / (2 * h)));

        Point3D nowCenterForTetrahedron = new Point3D(w, w / 2, 3 * h / 2);
        fillRemainingParallelepiped(parallelepiped, heights, widths, checkingForUsageOctahedron, h, w, nowCenterForTetrahedron, allOctahedrons, 
        (int)(parallelepiped.getLength() / w - 1), (int)(parallelepiped.getWidth() / w - 1), 2 * (int)(parallelepiped.getHeight() / (2 * h)) - 1);

        nowCenterForTetrahedron.setX(w / 2);
        nowCenterForTetrahedron.setY(w);
        nowCenterForTetrahedron.setZ(3 * h / 2);
        fillRemainingParallelepiped(parallelepiped, heights, widths, checkingForUsageOctahedron, h, w, nowCenterForTetrahedron, allOctahedrons, 
        (int)((parallelepiped.getLength() - w / 2) / w), (int)((parallelepiped.getWidth() - w / 2) / w), (int)(parallelepiped.getHeight() / (2 * h)));

        for (int i = 0; i < checkingForUsageOctahedron.size(); i++) {
            if (!checkingForUsageOctahedron.get(i)) {
                System.out.println("Not all using");
            }
        }

        return allOctahedrons;
    }

    // Метод, которому подается список высот и выдается максимальная высота, которая еще не использовалась 
    public static Pair<Double, Double> takeMaxUnused(List<Double> heights, List<Double> widths, List<Boolean> used, int counterUsageOctahedron) {
        int n = heights.size();

        for (int i = n - counterUsageOctahedron - 1; i >= 0; i--) {
            if (!used.get(i)) {
                used.set(i, true);
                counterUsageOctahedron++;
                return Pair.of(heights.get(i), widths.get(i));
            }
        }
        return null;
    }

    // Метод, который дает максимальный элемент и при этом меньше константы 
    public static Pair<Double, Double> findMaxBelow(List<Double> heights, List<Double> widths, List<Boolean> used, double unfilledHeight) {
        int left = 0;
        int right = heights.size() - 1;
        int candidateIndex = -1;

        // Бинарный поиск по значению
        while (left <= right) {
            int mid = (left + right) / 2;
            double midVal = heights.get(mid);

            if (midVal < unfilledHeight) {
                candidateIndex = mid; // запоминаем, но идём вправо
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if (candidateIndex == -1) {
            return null; // всё больше или равно unfilledHeight
        }

        // Идём назад, пока не найдём неиспользованный элемент
        for (int i = candidateIndex; i >= 0; i--) {
            if (!used.get(i)) {
                used.set(i, true);
                return Pair.of(heights.get(i), widths.get(i));
            }
        }

        return null; // все элементы ниже unfilledHeight уже заняты
    }

    private static void fillParallelepipedLayer(Parallelepiped parallelepiped, List<Double> heights, List<Double> widths, List<Boolean> checkingForUsageOctahedron, double h, double w, 
        Point3D nowCenterForBigOcOctahedron, List<Octahedron> allOctahedrons, int counterUsageOctahedron,
        int countBigOctahedronForParallelepipedLength, int countBigOctahedronForParallelepipedWidth, int countBigOctahedronForParallelepipedHeights
        ) {
        
        for (int counterBigOctahedronForParallelepipedHeights = 0; counterBigOctahedronForParallelepipedHeights < countBigOctahedronForParallelepipedHeights; counterBigOctahedronForParallelepipedHeights++) {
            for (int counterBigOctahedronForParallelepipedWidth = 0; counterBigOctahedronForParallelepipedWidth < countBigOctahedronForParallelepipedWidth; counterBigOctahedronForParallelepipedWidth++) {
                for (int counterBigOctahedronForParallelepipedLength = 0; counterBigOctahedronForParallelepipedLength < countBigOctahedronForParallelepipedLength; counterBigOctahedronForParallelepipedLength++) {
                    Pair<Double, Double> pairHeightWidths = takeMaxUnused(heights, widths, checkingForUsageOctahedron, counterUsageOctahedron);
                    if (pairHeightWidths == null) {
                        return;
                    }
                    
                    Double nowHeight = pairHeightWidths.getLeft();
                    Double nowWidths = pairHeightWidths.getRight();

                    Point3D nowCenterForLittleOcOctahedron = new Point3D(nowCenterForBigOcOctahedron.getX(), nowCenterForBigOcOctahedron.getY(), nowCenterForBigOcOctahedron.getZ() - h + nowHeight);
                    Octahedron octahedron = new Octahedron(nowCenterForLittleOcOctahedron, 2 * nowHeight, nowWidths, nowWidths,  Math.toRadians(90), Math.toRadians(180), Math.toRadians(90));
                    allOctahedrons.add(octahedron);
                    
                    double unfilledHeight = h - nowHeight;
                    boolean flagFotWhile = true;
                    double counterHeight = 2 * nowHeight; // Это счетчик высоты, он нужен, чтобы удробнее было высчитывать центр нового октаэдора
                    while (flagFotWhile) {
                        Pair<Double, Double> pairNowHeightWidths = findMaxBelow(heights, widths, checkingForUsageOctahedron, unfilledHeight);
                        if (pairNowHeightWidths == null) {
                            flagFotWhile = false;
                        } else {
                            nowHeight = pairNowHeightWidths.getLeft();
                            nowWidths = pairNowHeightWidths.getRight();
                            
                            unfilledHeight -= nowHeight;
                            nowCenterForLittleOcOctahedron.setZ(counterHeight + nowHeight);
                            counterHeight += nowHeight;
                            Octahedron additionalOctahedron = new Octahedron(nowCenterForLittleOcOctahedron, 2 * nowHeight, nowWidths, nowWidths,  Math.toRadians(90), Math.toRadians(180), Math.toRadians(90));
                            allOctahedrons.add(additionalOctahedron);
                        }
                    }

                    nowCenterForBigOcOctahedron.setX(nowCenterForBigOcOctahedron.getX() + w);
                }
                nowCenterForBigOcOctahedron.setX(w / 2);
                nowCenterForBigOcOctahedron.setY(nowCenterForBigOcOctahedron.getY() + w);
            }

            nowCenterForBigOcOctahedron.setX(w / 2);
            nowCenterForBigOcOctahedron.setY(w / 2);
            nowCenterForBigOcOctahedron.setZ(nowCenterForBigOcOctahedron.getZ() + 2 * h);
        }  
    }


    // nowCenterForTetrahedron = (w, w / 2, 3 * h / 2)
    public static void fillRemainingParallelepiped(Parallelepiped parallelepiped, List<Double> heights, List<Double> widths, List<Boolean> checkingForUsageOctahedron, double h, double w, 
    Point3D nowCenterForTetrahedron, List<Octahedron> allOctahedrons, 
    int countTetrahedronForParallelepipedLength, int countTetrahedronForParallelepipedWidth, int countTetrahedronForParallelepipedHeights) {
        double startPointOfX = nowCenterForTetrahedron.getX();
        double startPointOfY = nowCenterForTetrahedron.getY();

        for (int counterTetrahedronForParallelepipedHeights = 0; counterTetrahedronForParallelepipedHeights < countTetrahedronForParallelepipedHeights; counterTetrahedronForParallelepipedHeights++) {
            for (int counterTetrahedronForParallelepipedWidth = 0; counterTetrahedronForParallelepipedWidth < countTetrahedronForParallelepipedWidth; counterTetrahedronForParallelepipedWidth++){
                for (int counterTetrahedronForParallelepipedLength = 0; counterTetrahedronForParallelepipedLength < countTetrahedronForParallelepipedLength; counterTetrahedronForParallelepipedLength++) {
                    Pair<Double, Double> pairNowHeightWidths = findMaxBelow(heights, widths, checkingForUsageOctahedron, h / 2);
                    if (pairNowHeightWidths == null) {
                        return;
                    }

                    Double nowHeight = pairNowHeightWidths.getLeft();
                    Double nowWidths = pairNowHeightWidths.getRight();

                    Octahedron octahedron = new Octahedron(nowCenterForTetrahedron, 2 * nowHeight, nowWidths, nowWidths, Math.toRadians(90), Math.toRadians(180), Math.toRadians(90));
                    allOctahedrons.add(octahedron);

                    nowCenterForTetrahedron.setX(nowCenterForTetrahedron.getX() + w);
                }
                nowCenterForTetrahedron.setX(startPointOfX);
                nowCenterForTetrahedron.setY(nowCenterForTetrahedron.getY() + w);
            }
            nowCenterForTetrahedron.setX(startPointOfX);
            nowCenterForTetrahedron.setY(startPointOfY);
            nowCenterForTetrahedron.setZ(nowCenterForTetrahedron.getZ() + h);
        }
    }   
}