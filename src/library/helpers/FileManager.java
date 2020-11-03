package library.helpers;

import u17112631.travellingSalesman.TSPDataConverter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileManager {

    private final TSPDataConverter converter;
    public String baseDirectory;
    public String baseDataDirectory;
    public String fileName;
    private final BufferedReader reader;

    public FileManager(TSPDataConverter converter){
        baseDirectory = System.getProperty("user.dir") + "\\";
        baseDataDirectory = "data" + "\\";
        fileName = "fileName.csv";
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.converter = converter;
    }

    private void selectBaseDirectory() {
        System.out.println("Current base directory is : \"" + baseDirectory + "\"");
        readFilesInDirectory(baseDirectory);

        System.out.println("New base directory : ");
        String input;
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to set base directory");
        }

        if(!input.equals("")) baseDirectory = input;
    }

    private void selectFileDirectory() {
        System.out.println("Current data directory is : \"" + baseDirectory + "\"");
        readFilesInDirectory(baseDirectory);

        System.out.println("New data directory : ");
        String input;

        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to set file directory");
        }

        if(!input.equals("")) baseDataDirectory = input;
    }

    public void setupDirectories(boolean useDefault,String fileName) {

        if(!useDefault){
            String input = "";

            System.out.println("Current base directory is : \"" + baseDirectory + "\"");
            System.out.println("Current data directory is : \"" + baseDirectory + baseDataDirectory);

            System.out.println("Would you like to change the base directory? {Y/N}");
            try {
                input = reader.readLine().toUpperCase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to read input");
            }

            if(input.equals("Y")){
                selectBaseDirectory();
            }

            System.out.println("Would you like to change the data directory? {Y/N}");

            try {
                input = reader.readLine().toUpperCase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to read input");
            }

            if(input.equals("Y")){
                selectFileDirectory();
            }
        }

        List<String> files = getFilesInDirectory(baseDirectory + baseDataDirectory);
        if(fileName.isEmpty()){
            System.out.println("Please select the target file number: ");

            for (int i = 0; i < files.size(); i++) {
                System.out.println("[" + (i + 1) +"]" + " " + files.get(i));
            }

            int fileNumber = 0;
            try {
                fileNumber = Integer.parseInt(reader.readLine());

                fileName = files.get( fileNumber - 1);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not parse file selection");
            }
            System.out.println("Using source : " + baseDirectory + baseDataDirectory + fileName);
        }else{
            this.fileName = fileName;
        }


    }


    public List<String> getData(boolean skipHeader,String specifics){

        Scanner myReader = getFileReader();

        if (skipHeader)
            myReader.nextLine();

        return readFile(myReader,specifics);
    }

    public TSPDataConverter loadDataConverter(String fileName){

        setupDirectories(true,fileName);
        Scanner myReader = getFileReader();

        converter.loadData(readFile(myReader,""));

        return converter;
    }
    private List<String> readFile(Scanner myReader,String mustContain) {

        List<String> lines = new ArrayList<>();

        if(mustContain.isEmpty()){
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if(data.contains(mustContain))
                    lines.add(data);
            }
        }else{
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
        }

        myReader.close();
        return lines;
    }

    private Scanner getFileReader() {
        File myObj = new File(baseDirectory + baseDataDirectory + fileName);
        Scanner myReader;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to read input file");
        }
        return myReader;
    }

    private List<String> getFilesInDirectory(String directory) {

        List<String> files = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(x -> files.add(x.toString().replace(directory,"")) );
        } catch (IOException e) {
            System.out.println("Unable to preview directory:" + e.getMessage());
        }

        return files;
    }

    private void readFilesInDirectory(String directory) {

        System.out.println("Files in " + directory);

        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(x -> System.out.println(x.toString().replace(directory,"")));
        } catch (IOException e) {
            System.out.println("Unable to preview directory:" + e.getMessage());
        }
    }

    public List<String> getData() {
        return getData(false,"");
    }
}
