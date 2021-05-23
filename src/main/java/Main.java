import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    private static final LongAdder sumTax = new LongAdder();

    public static void main(String[] args) {
        Thread shop1 = new Thread(null, getShop(), "Магазин 1");
        Thread shop2 = new Thread(null, getShop(), "Магазин 2");
        Thread shop3 = new Thread(null, getShop(), "Магазин 3");

        shop1.start();
        shop2.start();
        shop3.start();

        try {
            shop3.join();
            shop2.join();
            shop1.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Выручка по трём магазинам: \n"
                + shop1.getName() + ", " + shop2.getName() + ", " + shop3.getName() +".\n"
                + "Равна: " + sumTax.sum() + " рублей.");

    }
    /**
     * Инициализирует список в 12 элементов имеющий "случайные" положительные
     * числа от 100 до 12000. И добавляет их в LongAdder для получения итоговой суммы.
     * @return
     */
    public static Runnable getShop() {
        return () -> {
            int sales = 12;
            List<Long> dailyRevenue = new ArrayList<>();
            for(int i = 0; i < sales; i++) {
                dailyRevenue.add((long) (Math.random() * 12000) + 100);
            }
//            Проверка. 1 поток = 1000L => 3 потока = 3000L
//            dailyRevenue.add(200L);
//            dailyRevenue.add(300L);
//            dailyRevenue.add(500L);
            for(long i : dailyRevenue) {
                sumTax.add(i);
            }
            Thread.currentThread().interrupt();
        };
    }
}
