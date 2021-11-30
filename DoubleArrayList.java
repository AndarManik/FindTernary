import java.util.ArrayList;

public class DoubleArrayList
{
    public ArrayList<double[]> list = new ArrayList<>();
    public DoubleArrayList(){}

    public int size() {return list.size();}

    public void add(double[] input){list.add(input);}

    public void add(int val) {add(new double[] {val});}

    public double[] get(int index) {return list.get(index);}

    public double[] remove(int index)
    {
        double[] output = get(index);
        list.remove(index);
        return output;
    }
}
