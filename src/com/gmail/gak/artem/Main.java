package com.gmail.gak.artem;

import tmpproject.sorter.Sorter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        students.add(new Student("Bbbbbb", "Bbbbbb", 18, "9548678dsfa5"));
        students.add(new Student("Aaaaaa", "Aaaaaa", 21, "3247732hfdsh"));
        students.add(new Student("Dddddd", "Dddddd", 22, "736545hjhf32"));
        students.add(new Student("Cccccc", "Cccccc", 14, "854735kdds82"));

        try {
            Class cls = Sorter.get(Student.class);
            Comparator<Student> cmp = (Comparator<Student>) cls.newInstance();
            students.sort(cmp);
            System.out.println(students.toString());
        } catch (IllegalAccessException | InstantiationException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
