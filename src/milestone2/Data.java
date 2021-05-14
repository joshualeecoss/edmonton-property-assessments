package milestone2;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * This class is meant to take a csv line and store the data in variables
 *
 * @author Joshua Coss
 */
public class Data implements Serializable {

    DecimalFormat formatter = new DecimalFormat("$,###");
    private int accountNum;
    private String suite, houseNum, streetName, garage, neighbourhoodID;
    private String neighbourhood, ward;
    private double assessedValue;
    private double latitude, longitude;
    private double[] assessmentPercentage;
    private String[] assessmentClass;
    private String[] token;
    private String address;

    /**
     * Creates a Data object based on a string being handed to it
     *
     * @param csv line from csv file
     */
    public Data(String csv) {
        token = csv.split(",", -1);

        accountNum = Integer.parseInt(token[0]);
        suite = token[1];
        houseNum = token[2];
        streetName = token[3];
        garage = token[4];
        neighbourhoodID = token[5];
        neighbourhood = token[6];
        ward = token[7];
        assessedValue = Double.parseDouble(token[8]);
        latitude = Double.parseDouble(token[9]);
        longitude = Double.parseDouble(token[10]);
        assessmentPercentage = new double[3];
        for (int i = 0; i < 3; i++) {
            if (token[12 + i].equals("")) {
                token[12 + i] = "0";
                assessmentPercentage[i] = Double.parseDouble(token[12 + i]);
            } else {
                assessmentPercentage[i] = Double.parseDouble(token[12 + i]);
            }
        }
        assessmentClass = new String[3];
        for (int i = 0; i < 3; i++) {
            assessmentClass[i] = token[15 + i];
        }

        address = houseNum + " " + streetName;
        if (suite.length() > 0) {
            address = suite + "-" + address;
        }
    }

    /**
     * Creates a deep copy of the data object
     *
     * @param clone Data
     */
    public Data(Data clone) {
        accountNum = clone.accountNum;
        suite = clone.suite;
        houseNum = clone.houseNum;
        streetName = clone.streetName;
        garage = clone.garage;
        neighbourhoodID = clone.neighbourhoodID;
        neighbourhood = clone.neighbourhood;
        ward = clone.ward;
        assessedValue = clone.assessedValue;
        latitude = clone.latitude;
        longitude = clone.longitude;
    }

    /**
     * getAssessmentClass - returns a string of all assessment classes
     * associated with Data object, converting each to either "RESIDENTIAL" or
     * "NON-RESIDENTIAL"
     *
     * @return string of assessment classes
     */
    public String getAssessmentClass() {
        String showClass = assessmentClass[0];
        for (int i = 1; i < 3; i++) {
            if (!assessmentClass[i].equals("")) {
                showClass = showClass + ", " + assessmentClass[i];
            }
        }
        if (showClass.toLowerCase().contains("residential")) {
            showClass = "RESIDENTIAL";
        } else {
            showClass = "NON-RESIDENTIAL";
        }
        return showClass;
    }

    /**
     * getAssessedValue - returns the formatted assessed value of the Data
     * object
     *
     * @return formatted assessed value
     */
    public String getAssessedValue() {
        return formatter.format(assessedValue);
    }

    /**
     * getRawValue - returns the unformatted assessed value of the Data object
     *
     * @return unformatted assessed value
     */
    public Double getRawValue() {
        return assessedValue;
    }

    /**
     * getAccountNum - returns the account number of the Data object
     *
     * @return account number
     */
    public int getAccountNum() {
        return accountNum;
    }

    /**
     * getAddress - returns the address of the data object
     *
     * @return suite number, house number, and street name
     */
    public String getAddress() {
        String address = houseNum + " " + streetName;
        if (suite.length() > 0) {
            address = suite + "-" + address;
        }
        return address;
    }

    /**
     * getAssessmentPercentage - returns deep copy of assessmentPercentage array
     *
     * @return array of assessmentPercentage
     */
    public Double[] getAssessmentPercentage() {
        Double[] apct = new Double[3];
        for (int i = 0; i < 3; i++) {
            apct[i] = assessmentPercentage[i];
        }
        return apct;
    }

    /**
     * getNeighbourhood - returns the neighbourhood of the data object
     *
     * @return neighbourhood and ward
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * getLatitude - returns the latitude of the data object
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * getLongitude - returns the longitude of the data object
     *
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * hasGarage - checks to see if the Data object has a garage
     *
     * @return - boolean
     */
    public boolean hasGarage() {
        return garage.equals("Y");
    }

    /**
     * hashCode - creates new hashcode for Data object
     *
     * @return hash int
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.accountNum;
        return hash;
    }

    /**
     * equals - compares 2 instances of data, an instance of data and an int, or
     * an instance of data and a string
     *
     * @param o object being compared with data object
     * @return boolean value
     */
    @Override
    public boolean equals(Object o) {
        // If 2 data objects are being compared
        if (o instanceof Data) {
            Data other = (Data) o;
            return other.accountNum == accountNum;
            // If a data object and an integer are being compared
        } else if (o instanceof Integer) {
            int other = (Integer) o;
            return other == accountNum;
            // If a data object and a string are being compared, ignoring case
        } else if (o instanceof String) {
            String other = (String) o;
            return other.toLowerCase().equals(neighbourhood.toString().toLowerCase());
        }
        return false;
    }
}
