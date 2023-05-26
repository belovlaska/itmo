package client.commands;


import client.utils.console.Console;

/**
 * Команда 'exit'. Завершает выполнение.
 * @author belovlaska
 */
public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Using: '" + getName() + "'");
            return false;
        }

        console.println("Finishing execution...");
        console.println("""
                ---Thaks for using. This flower for u <3---\s
                ____________$$$$$$\s
                ___________$$$$$$$$\s
                ___$$$$$$$__$$$$$$__$$$$$$\s
                __$$$$$$$$$_ $$$$$_ $$$$$$$$\s
                ___$$$$$$$$$_$$$$$_$$$$$$$$\s
                ____$$$$$$$$_ $$$$_$$$$$$$\s
                ________$$$$$_$$$_$$$$$\s
                _$$$$$$$$$_ $____$_$$$$$$$$$\s
                $$$$$$$$$$ $______$ $$$$$$$$$$\s
                $$$$$$$$$$$______ $$$$$$$$$$$\s
                _$$$$$$$$$_$_____$_$$$$$$$$$\s
                _________$$$$$_$$$_$$$$$\s
                _____$$$$$$$$_ $$$$_$$$$$$$\s
                ____$$$$$$$$$_$$$$$_$$$$$$$$\s
                ___$$$$$$$$$_ $$$$$_ $$$$$$$$\s
                ____$$$$$$$__$$$$$$__$$$$$$\s
                ____________$$$$$$$$_$$\s
                _____________$$$$$$_ $$\s
                ______$$$$$_________$$\s
                _____$$$$$$$_______ $$\s
                ___$$$$$$$$$$$_____$$\s
                _____ $$$$$$$$$___ $$\s
                ________$$$$$$$__$$\s
                __________$$$$$_$$\s
                ___________$$$$$$\s
                ____________$$$$\s
                _____________$$\s
                ____________$$____$$$$$$$\s
                ___________ $$___$$$$$$$$$$\s
                ___________$$__$$$$$$$$\s
                ___________$$_$$$$$$\s
                ___________$$_$$$$$\s
                ___________$$$$$$\s
                ___________$$$$\s
                ___________$$\s
                """.indent(1));
        return true;
    }
}