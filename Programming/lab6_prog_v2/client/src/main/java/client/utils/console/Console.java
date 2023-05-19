package client.utils.console;

/**
 * Консоль для ввода команд и вывода результата
 *
 * @author belovlaska
 * @version 1.0
 */
public class Console implements ConsoleChanger {
    private static final String PS1 = "$ ";
    private static final String PS2 = "> ";

    /**
     * Выводит obj.toString() в консоль
     * @param obj Объект для печати
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит obj.toString() + \n в консоль
     * @param obj Объект для печати
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит error: obj.toString() в консоль
     * @param obj Ошибка для печати
     */
    public void printError(Object obj) {
        System.out.println("error: " + obj);
    }

    /**
     * Выводит таблицу из 2 колонок
     * @param elementLeft Левый элемент колонки.
     * @param elementRight Правый элемент колонки.
     */
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    public void ps1() {
        print(PS1);
    }

    public void ps2() {
        print(PS1);
    }

    /**
     * @return PS1 текущей консоли
     */
    public String getPS1() {
        return PS1;
    }

    /**
     * @return PS2 текущей консоли
     */
    public String getPS2() {
        return PS2;
    }
}
