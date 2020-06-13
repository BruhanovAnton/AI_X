import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NeuralNetwork {

    public double[][] wih;
    public double[][] who;
    public int iNodes;
    public int hNodes;
    public int oNodes;
    public double lr;
    String filename;
    File networkData;
    int cout = 0;
    boolean deserialisation = false;

    // initialise the neural network
    public void init(int inputNodes, int hiddenNodes, int outputNodes, double learningRate, String filename) {

        this.filename = filename;
        networkData = new File(filename+".txt");

        // set number of nodes in each input, hidden, output layer
        this.iNodes = inputNodes;
        this.hNodes = hiddenNodes;
        this.oNodes = outputNodes;

        this.wih = new double[hNodes][iNodes];
        this.who = new double[oNodes][hNodes];

        File f = new File(filename+".txt");
        if(f.exists()) {
            if(deserialisation != true){
                getNetworkData(filename);
                deserialisation = true;
            }
        }

        for (int i=0; i<hNodes; i++) {
            for(int j=0; j<iNodes; j++) {
                wih[i][j] = ((Math.random()-0.5D)*2.0D / Math.sqrt(iNodes));
            }
        }

        for (int i=0; i<oNodes; i++) {
            for(int j=0; j<hNodes; j++) {
                who[i][j] = ((Math.random()-0.5D)*2.0D / Math.sqrt(hNodes));
            }
        }

        // learning rate
        this.lr = learningRate;
    }
    // train the neural network

    public void train(double[] inputsList, double[] targetsList) {

        double[] inputs = inputsList;
        double[] targets = targetsList;

        double[] hiddenInputs = new double[hNodes];
        double[] hiddenOutputs = new double[hNodes];

        for (int i=0; i< hNodes; i++) {
            // calculate signals into hidden layer

            double hiBias = 0.0D;
            for (int j=0; j< iNodes; j++) {
                hiBias += wih[i][j]*inputs[j];
            }
            hiddenInputs[i] = hiBias;
            // calculate the signals emerging from hidden layer

            hiddenOutputs[i] = this.sigmoid(hiddenInputs[i]);
        }
        double[] finalInputs = new double[oNodes];
        double[] finalOutputs = new double[oNodes];

        for (int i=0; i< oNodes; i++) {
            // calculate signals into final output layer

            double fiBias = 0.0D;
            for (int j=0; j< hNodes; j++) {
                fiBias += (who[i][j]*hiddenOutputs[j]);
            }
            finalInputs[i] = fiBias;
            // calculate the signals emerging from final ouput layer

            finalOutputs[i] = this.sigmoid(finalInputs[i]);
        }

        // output layer error is the (target - actual)

        double[] outputErrors = new double[oNodes];
        for (int i=0; i<oNodes; i++) {
            outputErrors[i] = targets[i] - finalOutputs[i];
        }

        // hidden layer error is the outputErrors, split by weights, recombined at hidden nodes

        double[] hiddenErrors = new double[hNodes];
        for (int i=0; i<hNodes; i++) {
            double errors = 0.0D;
            for(int j=0; j<oNodes; j++) {
                errors = errors + (who[j][i] * outputErrors[j]);
            }
            hiddenErrors[i] = errors;
        }

        // update the weights for the links between the hidden and output layers

        double[] whoAdj = new double[oNodes];
        for (int i=0; i< oNodes; i++) {
            whoAdj[i] = outputErrors[i] * finalOutputs[i] * (1.0-finalOutputs[i]);
        }
        for (int i=0; i< oNodes; i++) {
            for (int j=0; j< hNodes; j++) {
                who[i][j] += lr * whoAdj[i] * hiddenOutputs[j];
            }
        }

        // update the weights for the links between the input and hidden layers

        double[] wihAdj = new double[hNodes];
        for (int i=0; i< hNodes; i++) {
            wihAdj[i] = hiddenErrors[i] * hiddenOutputs[i] * (1.0-hiddenOutputs[i]);
        }
        for (int i=0; i< hNodes; i++) {
            for (int j=0; j< iNodes; j++) {
                wih[i][j] += lr * wihAdj[i] * inputs[j];
            }
        }
        System.out.println("Тренировка №"+(cout+=1));
    }

    // query the neural network

    public double[] query(double[] inputsList, String filename) {

        double[] inputs = inputsList;

        File f = new File(filename+".txt");
        if(f.exists()) {
            if(deserialisation != true){
                getNetworkData(filename);
                deserialisation = true;
            }

        }

        double[] hiddenInputs = new double[hNodes];
        double[] hiddenOutputs = new double[hNodes];

        for (int i=0; i< hNodes; i++) {
            // calculate signals into hidden layer

            double hiBias = 0.0D;
            for (int j=0; j< iNodes; j++) {
                hiBias += wih[i][j]*inputs[j];
            }
            hiddenInputs[i] = hiBias;
            // calculate the signals emerging from hidden layer

            hiddenOutputs[i] = this.sigmoid(hiddenInputs[i]);
        }

        double[] finalInputs = new double[oNodes];
        double[] finalOutputs = new double[oNodes];
        for (int i=0; i< oNodes; i++) {
            // calculate signals into final output layer

            double fiBias = 0.0D;
            for (int j=0; j< hNodes; j++) {
                fiBias += who[i][j]*hiddenOutputs[j];
            }
            finalInputs[i] = fiBias;
            // calculate the signals emerging from final output layer

            finalOutputs[i] = this.sigmoid(finalInputs[i]);
        }
        return finalOutputs;
    }

    // sigmoid function
    private double sigmoid(double x) {
        return 1.0D / (1.0D + Math.exp(-1.0D * x));
    }

    public void saveNetworkData(){
        List<double[][]> matrices = new ArrayList<double[][]>();
        matrices.add(wih);
        matrices.add(who);
        try {
            FileWriter fw = new FileWriter(networkData);
            fw.write(iNodes+"\n");
            fw.write(hNodes+"\n");
            fw.write(oNodes+"\n");

            for(double[][] matrix : matrices) {
                fw.write(matrix.length + "\n");
                fw.write(matrix[0].length + "\n");

                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        fw.write(matrix[i][j] + " ");
                    }
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getNetworkData(String fileName){
        double[][] matrix = null;
        double[][] matrix2 = null;
        int count = 0;
        try {
            String wih_lines = "";
            String wih_columns = "";
            String wih_data = "";

            String who_lines = "";
            String who_columns = "";
            String who_data = "";
            Scanner myReader = new Scanner(new File(fileName+".txt"));
            while (myReader.hasNextLine()) {
                iNodes = Integer.parseInt(myReader.nextLine());
                hNodes = Integer.parseInt(myReader.nextLine());
                oNodes = Integer.parseInt(myReader.nextLine());

                wih_lines = myReader.nextLine();
                wih_columns = myReader.nextLine();
                wih_data = myReader.nextLine();

                who_lines = myReader.nextLine();
                who_columns = myReader.nextLine();
                who_data = myReader.nextLine();
            }
            matrix =  new double[Integer.parseInt(wih_lines)][Integer.parseInt(wih_columns)];
            String[] matrixValues = wih_data.split(" ");
            for(int i=0; i< matrix.length; i++){
                for(int j = 0; j < matrix[0].length; j++){
                    matrix[i][j] = Double.valueOf(matrixValues[count]);
                    count+=1;
                }
            }
            wih = matrix;

            count = 0;
            matrix2 =  new double[Integer.parseInt(who_lines)][Integer.parseInt(who_columns)];
            String[] matrixValues2 = who_data.split(" ");
            for(int i=0; i< matrix2.length; i++){
                for(int j = 0; j < matrix2[0].length; j++){
                    matrix2[i][j] = Double.valueOf(matrixValues2[count]);
                    count+=1;
                }
            }
            who = matrix2;
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Integer detectNeurosNumber(double[] neurosValues){
        double maxValue = 0;
        Integer neurosNumber;

        for(int i = 0; i < neurosValues.length; i++){
            if(neurosValues[i] > maxValue){
                maxValue = neurosValues[i];
            }
        }

        for(int j = 0; j < neurosValues.length; j++){
            if(neurosValues[j] == maxValue){
                return j;
            }
        }
        return null;
    }


}
