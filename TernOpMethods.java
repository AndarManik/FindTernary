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

    public DoubleArrayList getScoreList(BiasManager nn)
    {
        DoubleArrayList scoreList = new DoubleArrayList();
        for(int epoc = 0; epoc < Math.pow(3, 9); epoc++)
        {
            int index = epoc;
            nn.setBias(index);
            double[] currentOp = outputSpace.get(index);
            double error = 0;
            for(int i = 0; i < currentOp.length; i++)
                error += nn.backProp(inputSpace.get(i), new double[] {currentOp[i]});
            scoreList.add(new double[] {index, error});
        }
        nn.nn.clear();

        return scoreList;
    }


    public double randomTrain(BiasManager nn, int epocMag, double rate) {
        double expAvg = 0;
        System.out.println("|-----------------------------|");
        System.out.print("|");
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++) {
            int index = (int) (Math.random() * Math.pow(3, 9));

            double error = train(nn, index);
            expAvg *= 0.9;
            expAvg += 0.1 * error;

            nn.update(rate);

            nn.saveBias();

            if(epoc % (int) (Math.pow(10,epocMag) / 29) == (int) (Math.pow(10,epocMag) / 29) - 1)
                System.out.print("-");
        }
        System.out.println("|");
        System.out.println(expAvg);

        return expAvg;
    }

    public double trainBias(BiasManager nn, int task, int epocMag, double rate) {
        nn.setBias(task);
        double[] currentOp = outputSpace.get(task);
        double error = 0;

        System.out.println("|-----------------------------|");
        System.out.print("|");
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++) {
            error = 0;

            for(int i = 0; i < currentOp.length; i++)
                error += nn.backProp(inputSpace.get(i), new double[] {currentOp[i]});

            nn.updateBias(rate);

            if(epoc % (int) (Math.pow(10,epocMag) / 29) == (int) (Math.pow(10,epocMag) / 29) - 1)
                System.out.print("-");
        }
        nn.saveBias();
        System.out.println("|");
        System.out.println(error);
        return error;
    }
}
