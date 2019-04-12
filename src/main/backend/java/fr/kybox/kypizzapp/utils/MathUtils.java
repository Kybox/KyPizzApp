package fr.kybox.kypizzapp.utils;

public class MathUtils {

    public static double getTaxAmountRounded(double price, int quantity, double tax) {

        double amount = (price * quantity) * (tax / 100);
        return Math.round(amount * 100D) / 100D;
    }
}
