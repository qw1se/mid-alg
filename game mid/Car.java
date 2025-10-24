// Импортируем класс Random для генерации случайных чисел
import java.util.Random;

// Класс Car описывает одну машину на дороге
public class Car {

    // Позиция машины по оси X
    private int x;
    // Позиция машины по оси Y (полоса)
    private final int y;
    // Текущая скорость машины (может быть отрицательной — едет влево)
    private int speed;
    // Ширина машины в пикселях
    private final int width = 100;
    // Высота машины в пикселях
    private final int height = 50;
    // Левая граница дороги
    private final int ROAD_START = 50;
    // Правая граница дороги
    private final int ROAD_END = 650;

    // Конструктор — задаём начальную позицию и скорость машины
    public Car(int startX, int laneY, int carSpeed) {
        this.x = startX;      // сохраняем стартовую координату X
        this.y = laneY;       // сохраняем координату Y (ряд)
        this.speed = carSpeed; // сохраняем скорость машины
    }

    // Метод move() отвечает за движение машины по экрану
    public void move() {
        x += speed; // сдвигаем машину на величину скорости

        // Если машина едет вправо и достигла правого края дороги
        if (speed > 0 && x + width > ROAD_END) {
            x = ROAD_END - width; // ставим её ровно у края
            speed = -speed;       // меняем направление движения (влево)
        }
        // Если машина едет влево и достигла левого края дороги
        else if (speed < 0 && x < ROAD_START) {
            x = ROAD_START; // ставим у левого края
            speed = -speed; // меняем направление (вправо)
        }
    }

    // Метод randomizeSpeed() — случайно меняет скорость машины
    public void randomizeSpeed(Random rand) {
        // Выбираем случайную скорость от 1 до 5 и случайное направление
        int newSpeed = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(5));
        this.speed = newSpeed; // присваиваем машине новую скорость
    }

    // Проверяем столкновение лягушки и машины
    public boolean checkCollision(Frog frog) {
        // Проверяем пересечение прямоугольников лягушки и машины
        return frog.getX() < x + width &&
               frog.getX() + frog.TILE_SIZE > x &&
               frog.getY() < y + height &&
               frog.getY() + frog.TILE_SIZE > y;
    }

    // Геттеры — возвращают координаты и размеры машины
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
