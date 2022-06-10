package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TaskManager {
    static final String[] FIRSTMENU = {"add", "remove", "list", "exit"};
    static final String FILE_NAME = "tasks.csv";
    static String[][] tasks;


    private static void displayOriginalMenu(String[] tab) {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
        for (int i = 0; i < tab.length; i++) {
            System.out.println(tab[i]);
        }

    }

    public static void main(String[] args) {

        tasks = loadFileToTab((FILE_NAME));
        displayOriginalMenu(FIRSTMENU);


        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();


            switch (input.toLowerCase()) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks);
                    break;
                case "list":
                    showTab(tasks);
                    break;
                case "exit":
                    //save to file
                    System.out.println(ConsoleColors.RED + "Bye, Bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option");
            }
        }
    }

    private static void removeTask(String[][] tab) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove");
        int index = Integer.parseInt(scanner.nextLine());

        while( index >= tab.length) {
            System.out.println("Choose the correct index");
            index = scanner.nextInt();

            }
        tasks = ArrayUtils.remove(tasks,index);
        System.out.println("Task has been succesfully deleted");
    }


    private static void showTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.printf("%-30s", tab[i][j]);
            }
            System.out.println();
        }
    }


    public static void addTask() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task desciption");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String date = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();
        while (!isImportant.equalsIgnoreCase("true") && !isImportant.equalsIgnoreCase("false")) {
            System.out.println("Choose correct option: true/false");
            isImportant = scanner.nextLine();
        }
            tasks = Arrays.copyOf(tasks, tasks.length + 1);
            tasks[tasks.length - 1] = new String[3];
            tasks[tasks.length - 1][0] = description;
            tasks[tasks.length - 1][1] = date;
            tasks[tasks.length - 1][2] = isImportant;
        }


        public static String[][] loadFileToTab(String fileName) {


            Path filePath = Paths.get(fileName);

            if (!Files.exists(filePath)) {
                System.out.println("File does not exist");
            }

            String[][] tab = null;
            try {

                List<String> strings = Files.readAllLines(filePath);
                tab = new String[strings.size()][strings.get(0).split(",").length];

                for (int i = 0; i < strings.size(); i++) {
                    String[] split = strings.get(i).trim().split("," + " ");
                    for (int j = 0; j < split.length; j++) {
                        tab[i][j] = split[j];
                    }
                }

            } catch (IOException e) {
                System.out.println("File not found" + e.getMessage());
            }
            return tab;
        }
    }



