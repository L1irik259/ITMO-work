package org.example.model;

// Этот класс отвечает за то, что в нем хранятся методы по нахождению координат октаэдора 
public class PlaneIntersection {
    /**
     * Вычисляет координаты точки в 3D-пространстве, где вектор от начала координат
     * имеет заданную длину и образует заданные углы с плоскостями XY, XZ, ZY.
     * Углы задаются в радианах.
     * @param angleXY Угол с плоскостью XY в радианах
     * @param angleXZ Угол с плоскостью XZ в радианах
     * @param angleZY Угол с плоскостью ZY в радианах
     * @param length Длина вектора
     * @return Массив [x, y, z] с координатами точки или null, если решение невозможно
     */
    public static Point3D getPointCoordinates(double angleXY, double angleXZ, double angleZY, double length) {
        // Вычисляем sin углов, так как угол с нормалью = 90° - угол с плоскостью
        double sinThetaXY = Math.sin(angleXY); // z/|r| = sin(angleXY)
        double sinThetaXZ = Math.sin(angleXZ); // y/|r| = sin(angleXZ)
        double sinThetaZY = Math.sin(angleZY); // x/|r| = sin(angleZY)

        // Вычисляем координаты
        double xAnswer = length * sinThetaZY;
        double yAnswer = length * sinThetaXZ;
        double zAnswer = length * sinThetaXY;

        return new Point3D (xAnswer, yAnswer, zAnswer);
    }

    /**
     * Вычисляет координаты точки E для вектора OE с заданной длиной и углами.
     * @param length длина вектора OE
     * @param theta азимутальный угол (в радианах, в плоскости XY)
     * @param phi полярный угол (в радианах, от оси Z)
     * @return Point3D координаты точки E
     * @throws IllegalArgumentException если длина отрицательная
     */
    public static Point3D computeVectorPoint(double length, double theta, double phi) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative");
        }

        // Вычисляем координаты в сферической системе
        double x = length * Math.sin(phi) * Math.cos(theta);
        double y = length * Math.sin(phi) * Math.sin(theta);
        double z = length * Math.cos(phi);

        return new Point3D(x, y, z);
    }

    /**
     * Вычисляет две точки на плоскости XY, такие что x/y = r и расстояние до центра = k
     * Где r = -1 * y_E / x_E
     *
     * @param r отношение x/y
     * @param k расстояние от начала координат
     * @return массив из двух точек, каждая точка — массив [x, y, 0], это координаты базиса плоскости, на которой будет лежать точки A, B, C, D
     */
    public static Point3D[] findHeightdPoints(double r) {
        // Вычисляем знаменатель sqrt(r^2 + 1)
        double denom = Math.sqrt(r * r + 1);

        // Находим y и x
        double x1 =  1 / denom;
        double y1 =  r * x1;

        double x2 = -1 / denom;
        double y2 =  r * x2;

        return new Point3D[] {
            new Point3D (x1, y1, 0),
            new Point3D (x2, y2, 0)
        };
    }


    // находит координаты точки, которая задает перпендекулярный вектору OH
    public static Point3D findIntersectionPoint(double a1, double b1, double c1, 
                                              double a2, double b2, double c2) {
        // Находим векторное произведение нормалей для получения направления линии пересечения
        // Линия пересечения: (x, y, z) = t * (vx, vy, vz)
        double vx = b1 * c2 - b2 * c1; // x-компонента вектора
        double vy = c1 * a2 - c2 * a1; // y-компонента вектора
        double vz = a1 * b2 - a2 * b1; // z-компонента вектора

        // Нормируем вектор (x, y, z), чтобы длина была равна 1
        // Длина вектора: sqrt(vx² + vy² + vz²)
        double length = Math.sqrt(vx * vx + vy * vy + vz * vz);
        
        // Координаты точки на единичной сфере
        double x = vx / length;
        double y = vy / length;
        double z = vz / length;

        return new Point3D (x, y, z);
    }

    // Функция для нахождения точки T, такой что угол HOT = alpha
    public static Point3D findPointWithAngle(Point3D H, Point3D P, double alpha, double length) {
        // Проверка расстояния до начала координат
        double normH = Math.sqrt(H.getX() * H.getX() + H.getY() * H.getY() + H.getZ() * H.getZ());

        // Проверка корректности угла alpha (0 <= alpha <= pi)
        if (alpha < 0 || alpha > Math.PI) {
            throw new IllegalArgumentException("Угол alpha должен быть в диапазоне [0, π]!");
        }

        // Вычисление координат точки T
        // OT = cos(alpha) * OH + sin(alpha) * OP
        double x_t = Math.cos(alpha) * H.getX() + Math.sin(alpha) * P.getX();
        double y_t = Math.cos(alpha) * H.getY() + Math.sin(alpha) * P.getY();
        double z_t = Math.cos(alpha) * H.getZ() + Math.sin(alpha) * P.getZ();

        // Проверка нормы OT
        double normT = Math.sqrt(x_t * x_t + y_t * y_t + z_t * z_t);

        // Проверка, что T лежит на плоскости (нормаль = OH × OP)
        double nx = H.getY() * P.getZ() - H.getZ() * P.getY();
        double ny = H.getZ() * P.getX() - H.getX() * P.getZ();
        double nz = H.getX() * P.getY() - H.getY() * P.getX();
        double planeCheck = nx * x_t + ny * y_t + nz * z_t;
        if (Math.abs(planeCheck) > 1e-10) {
            throw new RuntimeException("Ошибка: точка T не лежит на плоскости!");
        }

        // Проверка угла HOT
        double dotHT = H.getX() * x_t + H.getY() * y_t + H.getZ() * z_t;
        double cosAngle = dotHT / (normH * normT); // normH = normT = 1
        cosAngle = Math.max(-1.0, Math.min(1.0, cosAngle));

        return new Point3D(x_t * length, y_t * length, z_t * length);
    }

    // Основная функция для поиска точек A и B с заданной длиной векторов TA и TB
    public static Point3D[] findPointsABCD(Point3D normal, Point3D T, double length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        
        // Вектор OT - это сама точка T
        Point3D OT = T;
        
        // Находим вектор TA, перпендикулярный OT и лежащий в плоскости
        Point3D TA = Point3D.crossProduct(OT, normal);
        
        // Проверяем, что TA не нулевой
        double taLength = Math.sqrt(TA.getX() * TA.getX() + TA.getY() * TA.getY() + TA.getZ() * TA.getZ());
        if (taLength < 1e-10) {
            throw new IllegalArgumentException("Cannot find perpendicular vector (OT and normal are parallel or zero)");
        }
        
        // Нормализуем TA и масштабируем на заданную длину
        TA = Point3D.normalize(TA);
        Point3D A = new Point3D(
            T.getX() + length * TA.getX(),
            T.getY() + length * TA.getY(),
            T.getZ() + length * TA.getZ()
        );
        
        // Для TB берем противоположный вектор -TA, чтобы он лежал в плоскости и был перпендикулярен OT
        Point3D TB = new Point3D(-TA.getX(), -TA.getY(), -TA.getZ());
        
        // Масштабируем TB на заданную длину и вычисляем точку B
        Point3D B = new Point3D(
            T.getX() + length * TB.getX(),
            T.getY() + length * TB.getY(),
            T.getZ() + length * TB.getZ()
        );
        
        return new Point3D[]{A, B};
    }
}