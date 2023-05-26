package client.utils.console;

/**
 * интерфейс логирования
 *
 * @author belovlaska
 * @version 1.0
 */
public interface ConsoleChanger {
    void print(Object obj);
    void println(Object obj);
    void printError(Object obj);
    void printTable(Object column1, Object column2);
    String getPS1();
    String getPS2();
    void ps1();
    void ps2();
}
