package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        Weather hotWeather = Weather.HOT;
        Weather coldWeather = Weather.COLD;
        //Test case 1
        System.out.println("Test case 1");
        System.out.println("--------------------");
        ArrayList<Integer> commandList = new ArrayList<>(List.of(8, 6, 4, 2, 1, 7));
        person.getDressed(hotWeather, commandList);
        //Test case 2
        System.out.println("\nTest case 2");
        System.out.println("--------------------");
        commandList = new ArrayList<>(List.of(8, 6, 3, 4, 2, 5, 1, 7));
        person = new Person();
        person.getDressed(coldWeather, commandList);
        //Test case 3
        System.out.println("\nTest case 3");
        System.out.println("--------------------");
        commandList = new ArrayList<>(List.of(8, 6, 6));
        person = new Person();
        person.getDressed(hotWeather, commandList);
        //Test case 4
        System.out.println("\nTest case 4");
        System.out.println("--------------------");
        commandList = new ArrayList<>(List.of(8, 6, 3));
        person = new Person();
        person.getDressed(hotWeather, commandList);
        //Test case 5
        System.out.println("\nTest case 5");
        System.out.println("--------------------");
        commandList = new ArrayList<>(List.of(8, 6, 3, 4, 2, 5, 7));
        person = new Person();
        person.getDressed(coldWeather, commandList);
        //Test case 6
        System.out.println("\nTest case 6");
        System.out.println("--------------------");
        commandList = new ArrayList<>(List.of(6));
        person = new Person();
        person.getDressed(coldWeather, commandList);
        
    }
}
class Person {
    FootwearStatus footwear;
    HeadwearStatus headwear;
    SockStatus socks;
    ShirtStatus shirt;
    JacketStatus jacket;
    BottomsStatus bottoms;
    String houseStatus;
    boolean pajamasOn;
    Person() {
        pajamasOn = true;
        footwear = FootwearStatus.OFF;
        headwear = HeadwearStatus.OFF;
        socks = SockStatus.OFF;
        shirt = ShirtStatus.OFF;
        jacket = JacketStatus.OFF;
        bottoms = BottomsStatus.OFF;
        houseStatus = "inside";
    }
    void getDressed(Weather weather, ArrayList<Integer> commandList) {
        for (int command : commandList) {
            if (isValid(command, weather)) {
                processCommand(command, weather);
            } else {
                System.out.println("fail");
                break;
            }
        }
    }

    void processCommand(int command, Weather weather) {
        switch (command) {
            case 1 -> {
                footwear = weather == Weather.HOT ? FootwearStatus.FLIPFLOPS : FootwearStatus.BOOTS;
                String footwear = weather == Weather.HOT ? "flip flops" : "boots";
                System.out.println(footwear);
            }
            case 2 -> {
                headwear = weather == Weather.HOT ? HeadwearStatus.SUNGLASSES : HeadwearStatus.HAT;
                String headwear = weather == Weather.HOT ? "sunglasses" : "hat";
                System.out.println(headwear);
            }
            case 3 -> {
                socks = SockStatus.ON;
                System.out.println("socks");
            }
            case 4 -> {
                shirt = ShirtStatus.SHIRT;
                System.out.println("shirt");
            }
            case 5 -> {
                jacket = JacketStatus.JACKET;
                System.out.println("jacket");
            }
            case 6 -> {
                bottoms = weather == Weather.HOT ? BottomsStatus.SHORTS : BottomsStatus.PANTS;
                String bottoms = weather == Weather.HOT ? "shorts" : "pants";
                System.out.println(bottoms);
            }
            case 7 -> {
                houseStatus = "leaving house";
                System.out.println(houseStatus);
            }
            case 8 -> {
                pajamasOn = false;
                System.out.println("Removing PJs");
            }
        }
    }
    boolean isValid(int command, Weather weather) {
        return switch (command) {
            case 1 -> canApplyFootwear(weather);
            case 2 -> canApplyHeadwear();
            case 3 -> canApplySocks(weather);
            case 4 -> canApplyShirt();
            case 5 -> canApplyJacket(weather);
            case 6 -> canApplyBottoms();
            case 7 -> canLeaveHouse(weather);
            case 8 -> true;
            default -> false;
        };
    }

    private boolean canApplyFootwear(Weather weather) {
        if (pajamasOn || footwear != FootwearStatus.OFF) {
            return false;
        } else {
            if (weather == Weather.COLD) {
                return socks == SockStatus.ON && bottoms != BottomsStatus.OFF;
            } else {
                return bottoms != BottomsStatus.OFF;
            }
        }
    }
    private boolean canApplyHeadwear() {
        if (pajamasOn || headwear != HeadwearStatus.OFF) {
            return false;
        } else {
            return shirt != ShirtStatus.OFF;
        }
    }
    private boolean canApplySocks(Weather weather) {
        if (pajamasOn || weather == Weather.HOT || socks != SockStatus.OFF) {
            return false;
        } else {
            // weather are off
            // just need to check if footwear is on
            return footwear == FootwearStatus.OFF;
        }
    }
    private boolean canApplyShirt() {
        return !pajamasOn;
    }
    private boolean canApplyJacket(Weather weather) {
        if (pajamasOn || jacket != JacketStatus.OFF) {
            return false;
        } else {
            return weather == Weather.COLD && shirt != ShirtStatus.OFF;
        }
    }
    private boolean canApplyBottoms() {
        if (pajamasOn || bottoms != BottomsStatus.OFF) {
            return false;
        } else {
            return footwear == FootwearStatus.OFF;
        }
    }
    private boolean canLeaveHouse(Weather weather) {
        if (pajamasOn || houseStatus.equals("leaving house"))  {
            return false;
        } else {
            return switch (weather) {
                case HOT -> footwear == FootwearStatus.FLIPFLOPS && headwear == HeadwearStatus.SUNGLASSES && shirt == ShirtStatus.SHIRT && bottoms == BottomsStatus.SHORTS && jacket == JacketStatus.OFF;
                case COLD -> footwear == FootwearStatus.BOOTS && headwear == HeadwearStatus.HAT && shirt == ShirtStatus.SHIRT && jacket != JacketStatus.OFF && bottoms != BottomsStatus.OFF && socks == SockStatus.ON;
            };
        }
    }
}
enum Weather {
    HOT, COLD
}
enum SockStatus {
    ON,OFF
}
enum FootwearStatus {
    OFF, BOOTS, FLIPFLOPS
}
enum HeadwearStatus {
    OFF, HAT, SUNGLASSES
}
enum ShirtStatus {
    OFF, SHIRT
}
enum JacketStatus {
    OFF, JACKET
}
enum BottomsStatus {
    OFF, PANTS, SHORTS
}