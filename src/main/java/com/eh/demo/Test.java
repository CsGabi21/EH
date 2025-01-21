package com.eh.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter);

        System.out.println(formattedDateTime);
    }

}
