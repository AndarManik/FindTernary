import java.util.ArrayList;

public class TernaryData {

    public static DoubleArrayList getInputSpace(){
        DoubleArrayList inputSpace = new DoubleArrayList();

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                inputSpace.add(normalize(new double[] {i, j}));

        return inputSpace;
    }

    public static DoubleArrayList getOutputSpace(){
        DoubleArrayList outputSpace = new DoubleArrayList();

        for(int i = 0; i < Math.pow(3, 9); i++)
        {
            int value = i;

            double[] output = new double[9];

            for(int j = 0; j < 9; j++, value /= 3)
                output[j] = value % 3;

            outputSpace.add(normalize(output));
        }

        return outputSpace;
    }

    public DoubleArrayList getOutIndex()
    {
        DoubleArrayList outIndex = new DoubleArrayList();
        for(int i = 0; i < Math.pow(3, 9); i++)
            outIndex.add(i);
        return outIndex;
    }

    public ArrayList<DoubleArrayList> getSymGroup()
    {
        ArrayList<DoubleArrayList> symGroup = new ArrayList<>();
        DoubleArrayList outputSpace = getOutputSpace();
        DoubleArrayList indexes = getOutIndex();

        while(outputSpace.size() > 0)
        {
            DoubleArrayList curGroup = new DoubleArrayList();
            DoubleArrayList sym = getSymIndexes(outputSpace.get(0));
            for(int opVal = 0; opVal < sym.size(); opVal++) {
                double[] op = remove((int)sym.get(opVal)[0], outputSpace, indexes);
                if(op != null)
                    curGroup.add(op);
            }
            symGroup.add(curGroup);
        }

        return symGroup;
    }

    public double[] remove(int opVal, DoubleArrayList outputSpace, DoubleArrayList indexes) {
        int index = find(opVal, indexes);
        if(index == -1)
            return null;
        double[] ret = outputSpace.get(index);
        outputSpace.remove(index);
        indexes.remove(index);
        return ret;
    }

    public int find(int opVal, DoubleArrayList indexes) {
        for(int i = 0; i < indexes.size(); i++)
            if(indexes.get(i)[0] == opVal)
                return i;
        return -1;
    }


    public void removeSym(DoubleArrayList outputSpace)
    {
        for(int i = outputSpace.size() - 1; i >= 0; i--)
        {
            DoubleArrayList indexes = getSymIndexes(outputSpace.get(i));
            boolean isThere = true;
            for(int j = 0; j < indexes.size(); j++) {
                if (isThere && indexes.get(j)[0] < i) {
                    outputSpace.remove(i);
                    isThere = false;
                }
            }
        }
    }


    public DoubleArrayList getSymIndexes(double[] input)
    {
        DoubleArrayList symList = new DoubleArrayList();
        for(int i = 0; i < 4; i++)
        {
            input = rotate(input);
            symList.add(input.clone());
        }
        for(int i = 0; i < input.length; i++)
            input[i] *= -1;
        for(int i = 0; i < 4; i++)
        {
            input = rotate(input);
            symList.add(input.clone());
        }

        DoubleArrayList output = new DoubleArrayList();
        for(int i = 0; i < symList.size(); i++)
            output.add(indexOf(symList.get(i)));

        return output;
    }

    public int indexOf(double[] input)
    {
        int value = 0;
        for(int i = 0 ; i < input.length; i++)
            value += (input[i] + 1) * Math.pow(3, i);
        return value;
    }

    public double[] rotate(double[] input)
    {
        double[] output = new double[input.length];
        int[] code = {7,4,1,8,5,2,9,6,3};
        for(int i = 0; i < output.length; i++)
            output[i] = input[code[i] - 1];
        return output;
    }

    public static double[] normalize(double[] input)
    {
        double[] output = new double[input.length];

        for(int i = 0; i  < input.length; i++)
            output[i] = input[i] - 1;

        return output;
    }
}
