package library.gpLibrary.helpers;

import library.helpers.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UIController {

    private final FileManager fileManager;
    private final BufferedReader reader;

    private final boolean useDefault;
    private List<String> data;

    public UIController(boolean useDefault,FileManager fileManager){
        this.fileManager = fileManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        this.useDefault = useDefault;
    }

    public double askNumber(String question, double defaultVal) {
        if(useDefault)
            return defaultVal;

        return Double.parseDouble( ask(question));
    }

    public String ask(String question) {

        System.out.print(question + " : ");
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not read user input");
        }
    }
    public <E extends Enum<E>> Enum<E> askEnum(String question,Class<E> enumType, E defaultVal){

        if(useDefault)
            return defaultVal;

        StringBuilder enumVals = new StringBuilder(" {");
        for (Enum<E> enumVal: enumType.getEnumConstants()) {
            enumVals.append(enumVal.toString()).append(",");
        }

        enumVals.deleteCharAt(enumVals.length());
        enumVals.append("}");

        String enumString = ask(question + enumVals);

        Enum<E> stringToEnum = null;

        for (Enum<E> candidate : enumType.getEnumConstants()) {
            if (candidate.name().equalsIgnoreCase(enumString)) {
                stringToEnum = candidate;
            }
        }
        return stringToEnum;

    }
    public boolean askBool(String message) {
        String answer = ask(message + "{Y/N}");

        return answer.toUpperCase().contains("Y");
    }

    public boolean askBool(String message,boolean defaultVal) {

        if(useDefault)
            return defaultVal;

        String answer = ask(message + "{Y/N}");

        return answer.toUpperCase().contains("Y");
    }

    public List<List<String>> splitData(List<String> data, double trainingPercentage) {

        int numTrain = (int) (((double)data.size()*trainingPercentage));

        Random numgen = new Random(1L);
        List<String> training = new ArrayList<>();

        while (training.size() != numTrain){
            int next = numgen.nextInt(data.size());

            String dataItem = data.get(next);
            data.remove(next);
            training.add(dataItem);
        }

        List<String> testing = new ArrayList<>(data);

        return Arrays.asList(training,testing);
    }


    public List<List<String>> getDataSets(int trainingSetPercentage) {

        fileManager.setupDirectories(useDefault);
        if(useDefault){ //Perform dataset split
            this.data = fileManager.getData();

            return splitData(data,trainingSetPercentage);
        }else{
            List<String> trainData = fileManager.getData(false,ask("Training set must contain"));
            List<String> testData = fileManager.getData(false,ask("Testing set must contain"));

            return Arrays.asList(trainData,testData);
        }

    }
}
