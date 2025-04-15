package ru.inno.javapro.homework2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Stuff> stuffsTask3and4 = List.of(
                new Stuff("Петр", 25, Position.Рабочий),
                new Stuff("Глеб", 35, Position.Инженер),
                new Stuff("Сергей", 34, Position.Инженер),
                new Stuff("Михаил", 33, Position.Инженер),
                new Stuff("Максим", 32, Position.Инженер),
                new Stuff("Елена", 45, Position.Бухгалтер),
                new Stuff("Иван", 45, Position.Директор)
        );

        List<String> stringsTask5 = List.of("One", "Two", "Three", "Four");

        String textTask6and7 = "вы помните вы всё конечно помните как я стоял приблизившись к стене" +
                " взволнованно ходили вы по комнате и что-то резкое в лицо бросали мне";

        String[] stringsTask8 =
                {"Я сегодня до зари встану", "По широкому пройду полю пятоеслово", "Что-то с памятью моей стало",
                        "Всё что было не словослово"};

        System.out.println("задание 1: " + task1(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13)));
        System.out.println("задание 2: " + task2(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13)));
        System.out.println("задание 3: " + task3(stuffsTask3and4));
        System.out.println("задание 4: " + task4(stuffsTask3and4));
        System.out.println("задание 5: " + task5(stringsTask5));
        System.out.println("задание 6: " + task6(textTask6and7));
        task7(Arrays.asList(textTask6and7.split(" ")));
        System.out.println("задание 8: " +task8(stringsTask8));
    }

    private static Integer task1(List<Integer> list) {
        return list
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce((first, second) -> second)
                .orElseThrow();
    }

    private static Integer task2(List<Integer> list) {
        return list
                .stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce((first, second) -> second)
                .orElseThrow();
    }

    private static List<String> task3(List<Stuff> list) {
        return list
                .stream()
                .filter(stuff -> Position.Инженер.equals(stuff.position()))
                .sorted((o1, o2) -> o2.age() - o1.age())
                .map(Stuff::name)
                .limit(3)
                .toList();
    }

    private static Double task4(List<Stuff> list) {
        return list
                .stream()
                .filter(stuff -> Position.Инженер.equals(stuff.position()))
                .map(Stuff::age)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElseThrow();
    }

    private static String task5(List<String> list) {
        return list
                .stream()
                .sorted((o1, o2) -> o2.length() - o1.length())
                .limit(1)
                .findFirst()
                .orElseThrow();
    }

    private static Map<String, Long> task6(String text) {
        return Arrays.stream(text.split(" "))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }

    private static void task7(List<String> list) {
        System.out.println(list
                .stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .toList());
    }

    private static String task8(String[] strings) {
        return Arrays.stream(strings)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .sorted((o1, o2) -> o2.length() - o1.length())
                .limit(1)
                .findFirst()
                .orElseThrow();
    }
}
