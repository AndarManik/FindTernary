import java.util.ArrayList;

public class TernOpMethods {
    public  DoubleArrayList inputSpace = TernaryData.getInputSpace();
    public  DoubleArrayList outputSpace = TernaryData.getOutputSpace();

    public  double train(BiasManager nn, int task)
    {
        nn.setBias(task);
        double[] currentOp = outputSpace.get(task);

        double error = 0;
        for(int i = 0; i < currentOp.length; i++)
            error += nn.backProp(inputSpace.get(i), new double[] {currentOp[i]});
        return error;
    }

    public void score(BiasManager nn)
    {
        double totalError = 0;
        double maxError = 0;
        ArrayList<Double> l = new ArrayList<>();
        for(int epoc = 0; epoc < Math.pow(3, 9); epoc++)
        {
            int index = epoc;

            nn.setBias(index);

            double[] currentOp = outputSpace.get(index);

            double error = 0;

            for(int i = 0; i < currentOp.length; i++)
                error += nn.backProp(inputSpace.get(i), new double[] {currentOp[i]});

            System.out.println(index + " " + error);
            maxError = Math.max(error, maxError);
            totalError += error;
        }
        System.out.println(1.0 * totalError/Math.pow(3,9) + " " + maxError);
    }

    public void randomTrain(BiasManager nn, int epocMag, double rate) {
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++) {
            int index = (int) (Math.random() * Math.pow(3, 9));

            double error = train(nn, index);

            System.out.println(index + " " + error);

            nn.update(rate);

            nn.saveBias();
        }
    }
}
