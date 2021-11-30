import java.util.ArrayList;

public class SimpleRandomTrain{

    public static void main(String[] args) {
        BiasManager nn = new BiasManager(new int[]{2, 12, 12, 1}, 0.5, (int) Math.pow(3, 9));
        TernOpMethods tom = new TernOpMethods();

        tom.randomTrain(nn, 7, 0.01);
        tom.score(nn);
    }


}