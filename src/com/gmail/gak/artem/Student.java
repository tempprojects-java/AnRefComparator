package com.gmail.gak.artem;

import java.util.Objects;

public class Student {
    private String name;
    private String surname;
    private int age;
    private String rcard;
    private double gpa;

    public Student(String name, String surname, int age, String rcard, double gpa) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.rcard = rcard;
        this.gpa = gpa;
    }

    public Student(String name, String surname, int age, String rcard) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.rcard = rcard;
    }

    public Student(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                Double.compare(student.gpa, gpa) == 0 &&
                Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) &&
                Objects.equals(rcard, student.rcard);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, age, rcard, gpa);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", rcard='" + rcard + '\'' +
                ", gpa=" + gpa +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRcard() {
        return rcard;
    }

    public void setRcard(String rcard) {
        this.rcard = rcard;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
