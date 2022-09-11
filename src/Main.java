import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static Product[] product = {
            new Product("Хлеб", 100),
            new Product("Яблоки", 200),
            new Product("Молоко", 300)
    };

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Basket card = new Basket(product);
        int productNumber;
        int productCount;

        Scanner scanner = new Scanner(System.in);

        File basketFile = new File("basket.bin");
        if (basketFile.exists()) {
            System.out.println("Загрузить корзину(нажмите enter)? ");
            if (scanner.nextLine().equals("")) {
                card = Basket.loadFromBinFile(basketFile);
            }

            while (true) {
                System.out.println("Список доступных товаров для покупки");

                for (int i = 0; i < product.length; i++) {
                    System.out.println((i + 1) + ". " + product[i].getName() + " " + product[i].getPrice() + " pуб/шт");
                }

                System.out.println("Выберите товар и количество или введите `end`");
                String inputString = scanner.nextLine(); // "1 10"
                String[] parts = inputString.split(" ");
                if (parts.length == 2) {
                    try {
                        productNumber = Integer.parseInt(parts[0]);
                        productCount = Integer.parseInt(parts[1]);
                        if (productNumber <= 0 || productNumber > product.length) {
                            System.out.print("Неверный номер товара");
                            continue;
                        }
                        if (productCount <= 0) {
                            continue;
                        }
                        card.addToCart(productNumber - 1, productCount);
                        card.saveBin(basketFile);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Необходимо указать два целых числа");
                    }
                } else if (inputString.equals("end")) {
                    break;
                }
                System.out.println("Укажите следующую пару чисел");
            }
            scanner.close();
            card.printCart();
        }
    }
}