package ru;

import java.util.Random;
import java.util.concurrent.Phaser;

public class Philosopher implements Runnable {
    static private final int TIME_TO_EAT = 500;
    private final Phaser phaser;
    private final String name;
    private final int timeToThink;

    public Philosopher(String name, Phaser phaser) {
        this.name = name;
        this.timeToThink = new Random().nextInt(500, 1500);
        this.phaser = phaser;
        phaser.register();
    }

    void think() {
        System.out.println(name + " is thinking!");
        try {
            Thread.sleep(timeToThink);
        } catch (InterruptedException e) {
            System.out.println(name + " is interrupted!");
        }
    }

    static synchronized void haveLunch() {
        try {
            Thread.sleep(TIME_TO_EAT);
        } catch (InterruptedException e) {
            System.out.println("Is interrupted!");
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            haveLunch();
            System.out.println(name + " had lunch " + (i + 1) + " time(s)");
            think();
            if (i == 2) {
                phaser.arriveAndDeregister();
            } else {
                phaser.arriveAndAwaitAdvance();
            }
        }

    }
}
