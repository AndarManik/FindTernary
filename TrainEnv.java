import java.util.ArrayList;
import java.util.Scanner;

public class TrainEnv {
    public static TernOpMethods tom = new TernOpMethods();
    public static BiasManager nn = new BiasManager(new int[]{2, 12, 12, 1}, 0.5, (int) Math.pow(3, 9));
    public static Scanner sc = new Scanner(System.in);
    public static DoubleArrayList score = tom.getScoreList(nn);

    public static void main(String[] args) {
        trainMethod();
    }



    public static int trainMethod()
    {
        System.out.println("1: train 2: score 3: bias train 4: randomize bias 5: restart");
        switch(sc.nextInt()){
            case 1: trainCommand();
                break;
            case 2: scoreCommand();
                break;
            case 3: biasTrainCommand();
                break;
            case 4: randomizeBiasCommand();
                break;
            case 5: restartCommand();
                break;
        }

        return trainMethod();
    }


    public static void trainCommand()
    {
        System.out.println("int epocMag, double rate");
        tom.randomTrain(nn, sc.nextInt(), sc.nextDouble());
    }

    public static void scoreCommand() {
        score = tom.getScoreList(nn).sort(1);
        System.out.println("int numberOfEntries");
        int numberOfEntries = sc.nextInt();
        for(int i = 0; i < numberOfEntries; i++)
            System.out.println((int) score.get(i)[0] + " " + score.get(i)[1]);
    }

    public static void biasTrainCommand()
    {
        System.out.println("int epocMag, double rate, int numberOfEntries");
        int epocMag = sc.nextInt();
        double rate = sc.nextDouble();
        int numberOfEntries = sc.nextInt();
        for(int i = 0; i < numberOfEntries; i++)
            tom.trainBias(nn, (int)score.get(i)[0], epocMag, rate);
    }

    public static void restartCommand()
    {
        nn = new BiasManager(new int[]{2, 12, 12, 1}, 0.5, (int) Math.pow(3, 9));
    }

    public static void randomizeBiasCommand()
    {
        System.out.println("int task");
        nn.randomizeBias(sc.nextInt());
    }
}