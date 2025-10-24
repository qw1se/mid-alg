// Класс Frog описывает лягушку — игрока
public class Frog {

    // Размер клетки, на которую лягушка двигается
    public final int TILE_SIZE = 40;
    // Координата X лягушки
    private int x;
    // Координата Y лягушки
    private int y;

    // Конструктор задаёт начальные координаты
    public Frog(int startX, int startY) {
        this.x = startX; // сохраняем X
        this.y = startY; // сохраняем Y
    }

    // Методы для получения текущих координат
    public int getX() { return x; }
    public int getY() { return y; }

    // Методы перемещения лягушки
    public void moveUp() { y -= TILE_SIZE; }    // вверх
    public void moveDown() { y += TILE_SIZE; }  // вниз
    public void moveLeft() { x -= TILE_SIZE; }  // влево
    public void moveRight() { x += TILE_SIZE; } // вправо

    // Метод reset() возвращает лягушку на стартовую позицию
    public void reset(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
}
