package com.oibsip.reservation.util;

import java.util.Random;

public class PNRGenerator {

    private static final Random RANDOM = new Random();

    public static String generatePNR() {
        int number =
                100000 + RANDOM.nextInt(900000);

        return "CRX" + number;
    }
}
