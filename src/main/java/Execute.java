import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Execute {

    private static NeuralNetwork neuralNetwork = new NeuralNetwork();

    public static void main(String[] args) {

//        // number of input, hidden and output nodes
//        int inputNodes = 784;
//        int hiddenNodes = 200;
//        int outputNodes = 10;
//
//        // learning rate
//
//        double learningRate = 0.1;
//
//        // create instance of neural network
//        neuralNetwork.init(inputNodes, hiddenNodes, outputNodes, learningRate, "numbers");
//
//        try {
//
//            // train the neural network
//
//
//            // epochs is the number of times the training data set is used for training
//
//            int epochs = 5;
//
//            int mnistLabel = 0;
//            double[] inputs = new double[inputNodes];
//            double[] targets = new double[outputNodes];
//
//            for (int p=0; p<epochs; p++) {
//
//                // load the mnist training data CSV file into a BufferReader
//                String fileName = "C:/Users/bruha/Downloads/mnist_train.csv";
//                BufferedReader brTrain = new BufferedReader(new FileReader(fileName));
//                // go through all records in the training data set
//
//                while(brTrain.ready()) {
//                    // split the record by the ',' commas
//                    String[] oneRow = brTrain.readLine().split(",", -1);
//
//                    for(int j=0; j<inputNodes+1; j++) {
//                        if (j==0) {
//                            mnistLabel = Integer.parseInt(oneRow[j]);
//                        } else {
//                            double input = Integer.parseInt(oneRow[j]);
//                            // scale and shift the inputs
//
//                            input = (input / 255.0*0.99) + 0.01;
//                            inputs[j-1] = input;
//                        }
//                    }
//                    // create the target output values (all 0.01, except the desired label which is 0.99)
//                    for (int i=0; i<outputNodes; i++) {
//                        targets[i] = 0.01;
//                    }
//                    targets[mnistLabel] = 0.99;
//
//                    neuralNetwork.train(inputs, targets);
//                }
//            }
//
//
//            neuralNetwork.saveNetworkData();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        double[] input = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,84,185,159,151,60,36,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,222,254,254,254,254,241,198,198,198,198,198,198,198,198,170,52,0,0,0,0,0,0,0,0,0,0,0,0,67,114,72,114,163,227,254,225,254,254,254,250,229,254,254,140,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,17,66,14,67,67,67,59,21,236,254,106,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,83,253,209,18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22,233,255,83,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,129,254,238,44,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,249,254,62,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,133,254,187,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,205,248,58,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,126,254,182,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,75,251,240,57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,19,221,254,166,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,203,254,219,35,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,38,254,254,77,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,31,224,254,115,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,133,254,254,52,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,61,242,254,254,52,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,121,254,254,219,40,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,121,254,207,18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        double[] outputs = neuralNetwork.query(input, "numbers");

        System.out.println("Изображение содержит цифру "+neuralNetwork.detectNeurosNumber(outputs));

    }
}
