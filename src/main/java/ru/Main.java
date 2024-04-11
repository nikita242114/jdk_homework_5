package ru;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Phaser;

/* Задание 1. В качестве задачи предлагается решить
классическую задачу про обедающих философов
Условие:
● Есть пять философов (потоки), которые могут либо
обедать (выполнение кода) либо размышлять
(ожидание).
● Каждый философ должен пообедать три раза. Каждый
прием пищи длиться 500 миллисекунд
● После каждого приема пищи философ должен
размышлять
● Не должно возникнуть общей блокировки
● Философы не должны есть больше одного раза подряд
*/
public class Main {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);
        List<String> names = Arrays.asList("Herodotus", "Euclid", "Pythagoras", "Socrates", "Aristotle");
        List<String> eats = Arrays.asList("breakfast", "breakfast", "dinner");

        for (String name : names) {
            new Thread(new Philosopher(name, phaser)).start();
        }
        System.out.println("_______________".repeat(7));
        for (String eat : eats) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("_______________".repeat(7));
            System.out.println("Philosophers had " + eat + "!");
        }
        phaser.arriveAndDeregister();
        if (phaser.isTerminated()) {
            System.out.println("_______________".repeat(7));
            System.out.println("All philosophers are fed at last!");
        }
    }
}