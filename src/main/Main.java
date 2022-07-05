package main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("invalid number of arguments");
            return;
        }
        System.out.println("Welcome");

        File dataFile = new File(args[0]);
        List<Student> students = new ArrayList<>();

        try {
            Scanner dataReader = new Scanner(dataFile, StandardCharsets.UTF_8);
            dataReader.nextLine();
            while (dataReader.hasNextLine()) {
                String[] data = dataReader.nextLine().split(";");
                students.add(new Student(data));
            }
            dataReader.close();

        } catch (IOException e) {
            System.out.println("invalid argument '"+args[0]+"', file doesn't exist");
            return;
        }
        System.out.println("data imported successfully");

        Scanner inputReader = new Scanner(System.in);
        String userInput;
        do {
            System.out.print("> ");
            userInput = inputReader.nextLine();
            String[] command = userInput.split(" ");
            new Thread(() -> {
                switch (command[0]) {
                    case "create" -> {
                        if (command.length != 5) {
                            System.out.println("invalid number of argument");
                            break;
                        }
                        students.add(new Student(Arrays.copyOfRange(command, 1, 5)));
                        System.out.println("student successfully created");
                    }
                    case "read" -> {
                        if (command.length != 2) {
                            System.out.println("invalid number of argument");
                            break;
                        }
                        Optional<Student> student = students.stream().filter(s -> s.getJmbag().equals(command[1])).findFirst();
                        if (student.isEmpty()) {
                            System.out.println("no student found");
                            break;
                        }
                        System.out.println(student.get());
                    }
                    case "filter-grade" -> {
                        if (command.length != 3) {
                            System.out.println("invalid number of argument");
                            break;
                        }
                        List<Student> filteredByGrade = new ArrayList<>();
                        switch (command[2]) {
                            case "l" ->
                                    filteredByGrade = students.stream().filter(s -> s.getGrade() < Integer.parseInt(command[1])).toList();
                            case "e" ->
                                    filteredByGrade = students.stream().filter(s -> s.getGrade() == Integer.parseInt(command[1])).toList();
                            case "g" ->
                                    filteredByGrade = students.stream().filter(s -> s.getGrade() > Integer.parseInt(command[1])).toList();
                            default -> System.out.println("invalid second argument");
                        }
                        if (filteredByGrade.isEmpty()) {
                            System.out.println("no student found");
                            break;
                        }
                        System.out.println(filteredByGrade);
                    }
                    case "filter-name" -> {
                        if (command.length < 2 || command.length > 3) {
                            System.out.println("invalid number of argument");
                            break;
                        }
                        List<String> filteredByName = students.stream().filter(s -> s.getName().substring(0, 1).equalsIgnoreCase(command[1])).map(Student::getFullName).toList();
                        if (command.length == 3) {
                            switch (command[2]) {
                                case "-l" ->
                                        filteredByName = filteredByName.stream().map(String::toLowerCase).toList();
                                case "-u" ->
                                        filteredByName = filteredByName.stream().map(String::toUpperCase).toList();
                                default -> System.out.println("invalid second argument '" + command[2] + "'");
                            }
                        }
                        if (filteredByName.isEmpty()) {
                            System.out.println("no student found");
                            break;
                        }
                        System.out.println(filteredByName);
                    }
                }
                System.out.print("> ");
        }).start();
        } while (!userInput.equals("exit"));
    }
}
