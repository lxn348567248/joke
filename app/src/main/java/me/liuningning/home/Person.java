package me.liuningning.home;

public class Person {



    private String name;
    private int age;
    private Integer height;

    public Person() {

    }

    public Person(String name, int age, Integer height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}
