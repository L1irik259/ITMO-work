package org.example.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    /**
     * Вычисляет высоты у октаэдров
     * Первые n-1 элементов находятся с помощью рандома, последнее значение вычисляется
     * @param countOctahedrons Количество октаэдров 
     * @param eps Коэффициент высоты делить на сторону фигуры (h/w)
     * @param totalVolume Суммарный объем октаэдров
     * @return Список высот фигур 
     */
    public static List<Double> generateNumbers(int countOctahedrons, double eps, double totalVolume) {
        List<Double> result = new ArrayList<>();
        Random random = new Random();

        // Проверяем корректность входных данных
        if (countOctahedrons <= 0 || eps <= 0) {
            throw new IllegalArgumentException("countOctahedrons и eps должны быть положительными: countOctahedrons=" + countOctahedrons + ", eps=" + eps);
        }

        // Целевая сумма кубов: 3 * eps * totalVolume
        double targetSum = 3 * eps * totalVolume;
        if (targetSum < 0) {
            throw new IllegalArgumentException("3 * eps * totalVolume должно быть неотрицательным: 3 * eps * totalVolume = " + targetSum);
        }

        double sumOfCubes = 0.0;
        for (int i = 0; i < countOctahedrons - 1; i++) {
            // Ограничиваем максимальный куб для текущего элемента
            double maxCube = targetSum - sumOfCubes;
            double maxElement = Math.cbrt(maxCube);
            double x = random.nextDouble() * maxElement;
            result.add(x);
            sumOfCubes += x * x * x;
        }

        // Вычисляем последний элемент
        double remaining = targetSum - sumOfCubes;
        double lastElement = Math.cbrt(remaining);
        result.add(lastElement);

        return result;
    }

    public static List<Double> generateNumbers1(int countOctahedrons, double eps, double totalVolume) {
        double targetSum = 3 * eps * totalVolume;
        List<Double> result = new ArrayList<>();
        double sum = 0;
        double newElement;
        
        for (int i = 0; i < countOctahedrons; i++) {
            newElement = Math.random();
            result.add(newElement);
            sum += newElement;
        }

        for (int j = 0; j < countOctahedrons; j++) {
            result.set(j, result.get(j) / sum);
            result.set(j, Math.pow(result.get(j), 1.0 / 3));
            result.set(j, result.get(j) * (Math.pow(targetSum, 1.0 / 3)));
        }

        return result;
    }
}
