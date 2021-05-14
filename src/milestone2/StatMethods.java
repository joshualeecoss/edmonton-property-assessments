package milestone2;

import java.text.DecimalFormat;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.TextArea;

/**
 * This class is contains the various methods used in the Main class
 *
 * @author Joshua Coss
 */
public class StatMethods {

    /**
     * printStats - Prints the output of each descriptive stat
     *
     * @param assVal The list of assessed values that the descriptive stat
     * methods are being performed upon
     * @param formatter Currency converter
     */
    public static void printStats(List<Double> assVal, TextArea textArea) {
        DecimalFormat largeNumberFormat = new DecimalFormat(",###");
        DecimalFormat currencyFormat = new DecimalFormat("$,###");
        textArea.setText("Statistics of Assessed Values:\n\n"
                + "Number of properties: " + largeNumberFormat.format(n(assVal)) + "\n"
                + "Min: " + currencyFormat.format(min(assVal)) + "\n"
                + "Max: " + currencyFormat.format(max(assVal)) + "\n"
                + "Range: " + currencyFormat.format(range(assVal)) + "\n"
                + "Mean: " + currencyFormat.format(mean(assVal)) + "\n"
                + "Median: " + currencyFormat.format(median(assVal)) + "\n"
                + "Standard Deviation: " + currencyFormat.format(stdev(assVal))
        );
    }

    /**
     * allStats - Gets and performs stat calculations on all assessed values in
     * the dataset
     *
     * @param data Dataset derived from the csv file
     * @param formatter Currency converter
     * @param search Searcher object to parse through the data
     */
    public static void allStats(ObservableList<Data> data, TextArea textArea) {
        if (data.size() > 1) {
            List<Double> assVal = new ArrayList<>();
            for (Data entry : data) {
                assVal.add(entry.getRawValue());
            }
            printStats(assVal, textArea);
        } else {
            textArea.setText("");
        }
    }

    /**
     * n - calculates and returns the number of values in the given list
     *
     * @param data the list of values being counted
     * @return the number of values in the given list
     */
    public static int n(List<Double> data) {
        int size = data.size();
        return size;
    }

    /**
     * min - returns the smallest entry in the list
     *
     * @param data the list of values being searched
     * @return smallest value in the given list
     */
    public static double min(List<Double> data) {
        Double min = data.get(0);
        for (int i = 1; i < n(data); i++) {
            if (data.get(i) <= min) {
                min = data.get(i);
            }
        }
        return min;
    }

    /**
     * max - returns the largest entry in the list
     *
     * @param data the list of values being searched
     * @return largest value in the given list
     */
    public static double max(List<Double> data) {
        Double max = data.get(0);
        for (int i = 1; i < n(data); i++) {
            if (data.get(i) >= max) {
                max = data.get(i);
            }
        }
        return max;
    }

    /**
     * range - returns the difference between the largest entry in the list and
     * the smallest entry in the list
     *
     * @param data the list of values being manipulated
     * @return difference between max and min
     */
    public static double range(List<Double> data) {
        double difference = (max(data) - min(data));
        return difference;
    }

    /**
     * mean - calculates and returns the mean (average) of values in the given
     * list
     *
     * @param data the list of values being manipulated
     * @return mean of values in the list
     */
    public static double mean(List<Double> data) {
        double sum = 0;
        for (int i = 0; i < n(data); i++) {
            sum = sum + data.get(i);
        }
        return (sum / n(data));
    }

    /**
     * stdev - calculates and returns the standard deviation of values in the
     * given list
     *
     * @param data the list of values being manipulated
     * @return standard deviation of values in the list
     */
    public static double stdev(List<Double> data) {
        double mean = mean(data);
        double stdev = 0;
        double length = n(data);

        for (int i = 0; i < length; i++) {
            stdev = stdev + Math.pow(data.get(i) - mean, 2);
        }
        return Math.sqrt(stdev / length);
    }

    /**
     * median - calculates and returns the median of values in the given list
     *
     * @param data the list of values being manipulated
     * @return median of values in the list
     */
    public static double median(List<Double> data) {
        int length = n(data);
        int mid = length / 2;
        double median = 0;
        Collections.sort(data); //sorts the list in ascending order
        if (length % 2 == 0) {
            median = (data.get(mid) + data.get(mid) + 1) / 2;
        } else if (length % 2 != 0) {
            median = data.get(mid);
        }
        return median;
    }
}
