public class SchoolShootingException extends Exception{
    public SchoolShootingException(){
        super("Ты не можешь выстрелить больше раз, чем патронов в твоем пистолете!");
    }
}
