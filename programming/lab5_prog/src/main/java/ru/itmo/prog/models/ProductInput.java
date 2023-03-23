package ru.itmo.prog.models;

import ru.itmo.prog.exceptions.*;
import ru.itmo.prog.utils.InputSteamer;
import ru.itmo.prog.utils.consoleShell.Console;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

public class ProductInput{

    private final long PRICE_LIMIT = 0;
    private final long HEIGHT_LIMIT = 0;
    private final Console console;

    public ProductInput(Console console) {
        this.console = console;
    }

    public Product make() throws IncorrectScriptException, InvalidFormException, InvalidValueException {
        var product = new Product(
                inputName(),
                inputCoordinates(),
                LocalDateTime.now(),
                inputPrice(),
                inputUnitOfMeasureType(),
                inputPerson());
        if(!product.validate()) throw new InvalidFormException();
        return product;
    }

    private String inputName() throws IncorrectScriptException {
        String name;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите название продукта:");
                console.ps2();

                name = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Название не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Название не может быть пустым!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }
    private Coordinates inputCoordinates() throws IncorrectScriptException, InvalidFormException {
        Coordinates coordinates = new Coordinates(inputX(), inputY());
        if(!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }

    private long inputY() throws IncorrectScriptException {
        long y;
        var fileMode = InputSteamer.getFileMode();
        while (true){
            try {
                console.println("Введите координату Y:");
                console.ps2();
                var strY = InputSteamer.getScanner().nextLine().trim();
                if(fileMode) console.println(strY);
                y = Long.parseLong(strY);
                break;
            }
            catch (NoSuchElementException exception){
                console.printError("Координата Y не распознана!");
                if(fileMode) throw new IncorrectScriptException();
            }
            catch (NumberFormatException exception){
                console.printError("Координата Y должна быть представлена целым числом!");
                if(fileMode) throw new IncorrectScriptException();
            }
            catch (IllegalStateException exception){
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    private double inputX() throws IncorrectScriptException {
        double x;
        var fileMode = InputSteamer.getFileMode();
        while (true){
            try {
                console.println("Введите координату X:");
                console.ps2();
                var strX = InputSteamer.getScanner().nextLine().trim();
                if(fileMode) console.println(strX);
                x = Double.parseDouble(strX);
                break;
            }
            catch (NoSuchElementException exception){
                console.printError("Координата X не распознана!");
                if(fileMode) throw new IncorrectScriptException();
            }
            catch (NumberFormatException exception){
                console.printError("Координата X должна быть представлена числом с плавающей точкой!");
                if(fileMode) throw new IncorrectScriptException();
            }
            catch (IllegalStateException exception){
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    private Long inputPrice() throws IncorrectScriptException {
        long price;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите цену продукта (или null):");
                console.ps2();
                var strPrice = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strPrice);
                if(strPrice.equals("null") || strPrice.isEmpty()) {
                    return null;
                }
                price = Long.parseLong(strPrice);
                if (price <= PRICE_LIMIT) throw new InvalidValueException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цена продукта не распознана!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Цена продукта должна быть представлена целым числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (InvalidValueException exception) {
                console.printError("Цена продукта должна быть больше нуля!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return price;
    }

    private UnitOfMeasure inputUnitOfMeasureType() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();
        UnitOfMeasure unitOfMeasure;
        String strUnitOfMeasure;
        while (true){
            try {
                console.println("Список мер весов - " + UnitOfMeasure.names());
                console.println("Введите меру весов: ");
                console.ps2();
                strUnitOfMeasure = InputSteamer.getScanner().nextLine().trim();
                if(fileMode) console.println(strUnitOfMeasure);
                if(strUnitOfMeasure.isEmpty()) throw new MustBeNotEmptyException();
                unitOfMeasure = UnitOfMeasure.valueOf(strUnitOfMeasure.toUpperCase());
                break;
            }catch (NoSuchElementException exception) {
                console.printError("Мера весов не распознана!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Меры весов нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            }catch (MustBeNotEmptyException exception) {
                console.printError("Мера весов не может быть пустым!");
                if (fileMode) throw new IncorrectScriptException();
            }catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return unitOfMeasure;
    }
    private Person inputPerson() throws IncorrectScriptException, InvalidFormException {
        console.println("Введите no, чтобы не создавать владельца. Введите yes, чтобы создать владельца");
        while (true) {
            String inputStr = InputSteamer.getScanner().nextLine().trim();
            if (inputStr.equals("no"))
                return null;
            else if (inputStr.equals("yes")) {
                Person owner = new Person(
                        inputNameOfPerson(),
                        inputBirthday(),
                        inputHeight(),
                        inputEyeColor(),
                        inputHairColor());
                if (!owner.validate()) throw new InvalidFormException();
                return owner;
            }
            else
                console.println("Попробуйте еще раз");
        }
    }

    private Color inputHairColor() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();
        Color hairColor;
        while (true) {
            try {
                console.println("Список цветов - " + Color.names());
                console.println("Введите цвет волос: ");
                console.ps2();
                var strHairColor = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strHairColor);
                if (strHairColor.isEmpty()) throw new MustBeNotEmptyException();
                hairColor = Color.valueOf(strHairColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цвет волос не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Цвета волос нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Цвет волос не может быть пустым!");
                if(fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return hairColor;
    }

    private Color inputEyeColor() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();
        Color eyeColor;
        while (true) {
            try {
                console.println("Список цветов - " + Color.names());
                console.println("Введите цвет глаз (или null): ");
                console.ps2();
                var strEyeColor = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strEyeColor);
                if (strEyeColor.isEmpty() || strEyeColor.equals("null")) return null;
                eyeColor = Color.valueOf(strEyeColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цвет глаз не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("Цвета глаз нет в списке!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return eyeColor;
    }

    private long inputHeight() throws IncorrectScriptException {
        long height;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите рост:");
                console.ps2();
                var strPrice = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strPrice);
                height = Long.parseLong(strPrice);
                if (height <= HEIGHT_LIMIT) throw new InvalidValueException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Рост не распознан!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Рост должен быть представлен целым числом!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (InvalidValueException exception) {
                console.printError("Рост должен быть больше нуля!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return height;
    }

    private ZonedDateTime inputBirthday() throws IncorrectScriptException {
        return new ZonedDateTimeForm(console).adapt();
    }

    private String inputNameOfPerson() throws IncorrectScriptException {
        String name;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Введите имя владельца:");
                console.ps2();

                name = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Имя не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }
}


