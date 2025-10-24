// Импортируем библиотеки для графики и управления клавиатурой
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Класс GamePanel — основное игровое поле
public class GamePanel extends JPanel implements Runnable, KeyListener {

    // Объект лягушки
    private Frog frog;
    // Список машин
    private List<Car> cars;
    // Генератор случайных чисел
    private Random rand = new Random();

    // Конструктор создаёт лягушку и машины
    public GamePanel() {
        frog = new Frog(350, 450); // ставим лягушку внизу экрана
        cars = new ArrayList<>();  // создаём список машин

        int numLanes = 8;     // количество рядов с машинами
        int laneStartY = 400; // координата Y первой полосы
        int laneSpacing = 40; // расстояние между полосами
        int startX = 50;      // начальный X для машин

        // Цикл создаёт по 2 машины в каждом из 8 рядов
        for (int i = 0; i < numLanes; i++) {
            int laneY = laneStartY - i * laneSpacing; // вычисляем Y полосы
            for (int j = 0; j < 2; j++) { // две машины на полосу
                int xPos = startX + j * 300; // позиция машины по X
                int speed = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(5)); // случайная скорость
                cars.add(new Car(xPos, laneY, speed)); // добавляем машину в список
            }
        }

        // Включаем управление клавишами
        this.addKeyListener(this);
        this.setFocusable(true);
        // Запускаем игровой цикл в отдельном потоке
        new Thread(this).start();
    }

    // Главный игровой цикл
    @Override
    public void run() {
        while (true) { // бесконечный цикл
            updateGame(); // обновляем состояние игры
            repaint();    // перерисовываем экран
            try { Thread.sleep(10); } catch (InterruptedException e) {} // пауза 10 мс
        }
    }

    // Метод updateGame() — обновляет все элементы игры
    private void updateGame() {
        // Двигаем машины и случайно меняем их скорость
        for (Car car : cars) {
            car.move(); // движение машины
            if (rand.nextInt(1000) < 5) { // случайный шанс изменить скорость
                car.randomizeSpeed(rand);
            }
        }

        // Проверяем столкновения лягушки с машинами
        for (Car car : cars) {
            if (car.checkCollision(frog)) { // если столкнулись
                frog.reset(350, 450); // возвращаем лягушку на старт
                break; // прекращаем проверку
            }
        }

        // Если лягушка дошла до верха — сбрасываем
        if (frog.getY() <= 50) frog.reset(350, 450);
    }

    // Обработка нажатий клавиш
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); // получаем код клавиши
        if (key == KeyEvent.VK_UP) frog.moveUp();    // вверх
        if (key == KeyEvent.VK_DOWN) frog.moveDown(); // вниз
        if (key == KeyEvent.VK_LEFT) frog.moveLeft(); // влево
        if (key == KeyEvent.VK_RIGHT) frog.moveRight(); // вправо
    }

    // Неиспользуемые методы интерфейса KeyListener
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    // Метод paintComponent() рисует все элементы на экране
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g); // очищаем экран

        // Рисуем рамку поля
        g.setColor(java.awt.Color.BLACK);
        g.drawRect(50, 50, 600, 400);

        // Серый прямоугольник — дорога
        g.setColor(java.awt.Color.GRAY);
        g.fillRect(50, 100, 600, 350);

        // Рисуем все машины
        g.setColor(java.awt.Color.RED);
        for (Car car : cars)
            g.fillRect(car.getX(), car.getY(), car.getWidth(), car.getHeight());

        // Рисуем лягушку
        g.setColor(java.awt.Color.GREEN);
        g.fillRect(frog.getX(), frog.getY(), frog.TILE_SIZE, frog.TILE_SIZE);
    }

    // Метод main() — точка входа в игру
    public static void main(String[] args) {
        JFrame frame = new JFrame("Frogger Clone - Хаотичные машины"); // создаём окно
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // при закрытии окна — выход
        frame.add(new GamePanel()); // добавляем игровую панель
        frame.setSize(700, 500); // задаём размер окна
        frame.setVisible(true); // показываем окно
    }
}
