package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.app.GameApplication;

public class ReferenceExample {

    static class Dog {

        private int name;

        public Dog (int name) {
            this.name = name;
        }

        public void setName(int name) {
            this.name = name;
        }

        public int getName() {
            return name;
        }
    }
    public static void main(String[] args) {
        Dog dog = new Dog(1);

        for (int i=0; i < 20; i++) {
            fuckDog(dog, i);
        }

        System.out.println(dog.getName());
    }

    public static void fuckDog(Dog dog, int name) {
        dog.setName(name);
    }
}
