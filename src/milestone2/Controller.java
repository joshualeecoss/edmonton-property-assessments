package milestone2;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import static milestone2.StatMethods.*;

/**
 * FXML Controller class - initializes and populates the javafx table using data
 * loaded in from a csv file. All search functions allow for partial search 
 * results to return results.
 *
 * @author Joshua Coss
 */
public class Controller implements Initializable {

    ObservableList<String> assessmentClassList = FXCollections.observableArrayList("RESIDENTIAL", "NON-RESIDENTIAL");

    //configure the table
    @FXML
    private TableView<Data> tableView;
    @FXML
    private TableColumn<Data, Integer> accountColumn;
    @FXML
    private TableColumn<Data, String> addressColumn;
    @FXML
    private TableColumn<Data, Double> valueColumn;
    @FXML
    private TableColumn<Data, String> classColumn;
    @FXML
    private TableColumn<Data, String> neighbourhoodColumn;
    @FXML
    private TableColumn<Data, Double> latitudeColumn;
    @FXML
    private TableColumn<Data, Double> longitudeColumn;

    @FXML
    private Button resetButton;
    @FXML
    private Button searchButton;

    @FXML
    private TextField accountNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField neighbourhoodField;
    @FXML
    private ComboBox<String> assessmentClassBox;
    @FXML
    private TextArea statsArea;

    private ObservableList<Data> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up columns in the table
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("accountNum"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));
        neighbourhoodColumn.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        assessmentClassBox.setItems(assessmentClassList);

        try {
            //load data
            data = getData();
            tableView.setItems(data);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        allStats(data, statsArea);
    }

    /**
     * getData - Loads data from CSV file into an ObservableList in order for
     * the table to read
     *
     * @return ObservableList object
     * @throws FileNotFoundException
     */
    public ObservableList<Data> getData() throws FileNotFoundException {
        Scanner inFile = new Scanner(new File("Property_Assessment_Data.csv"));
        ObservableList<Data> data = FXCollections.observableArrayList();
        //skip the first line
        inFile.nextLine();

        while (inFile.hasNextLine()) {
            String line = inFile.nextLine();
            Data addition = new Data(line);
            data.add(addition);
        }
        return data;
    }

    /**
     * resetButton - when the reset button is clicked, resets all search fields
     * and sets the tableview back to the viewing all data in the csv file
     *
     * @param event
     */
    @FXML
    private void resetButton(ActionEvent event) {
        accountNumberField.clear();
        addressField.clear();
        neighbourhoodField.clear();
        assessmentClassBox.valueProperty().set(null);

        allStats(data, statsArea);
        tableView.setItems(data);
    }

    /**
     * searchButton - when the search button is clicked, searches the data based
     * on the whatever parameters are in the search fields
     *
     * @param event
     */
    @FXML
    private void searchButton(ActionEvent event) {
        ObservableList<Data> newData = FXCollections.observableArrayList();
        newData = search(newData);
        tableView.setItems(newData);
        allStats(newData, statsArea);
    }

    /**
     * isInteger - takes in a string and returns True if that string can be
     * converted to an integer, false it can't
     *
     * @param input string
     * @return boolean
     */
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception NumberFormatException) {
            return false;
        }
    }

    /**
     * accoundSearch - Searches through the data and returns a set with accounts
     * containing the search term in the accountNum field.
     *
     * @return list - set of items that contain the accountSearch term
     */
    private Set<Data> accountSearch() {
        Set<Data> list = new HashSet<>();

        String accountSearch = accountNumberField.getText();
        if (isInteger(accountSearch)) {
            for (Data entry : data) {
                if (String.valueOf(entry.getAccountNum()).contains(accountSearch)) {
                    list.add(entry);
                }
            }
        }
        return list;
    }

    /**
     * addressSearch - Searches through the data and returns a set with accounts
     * containing the search term in the address field.
     *
     * @return list - set of items that contain the addressSearch term
     */
    private Set<Data> addressSearch() {
        Set<Data> list = new HashSet<>();

        String addressSearch = addressField.getText().toLowerCase();
        for (Data entry : data) {
            if (String.valueOf(entry.getAddress().toLowerCase()).contains(addressSearch)) {
                list.add(entry);
            }
        }
        return list;
    }

    /**
     * neighbourhoodSearch - Searches through the data and returns a set with
     * accounts containing the search term in the neighbourhood field.
     *
     * @return list - set of items that contain the neighbourhoodSearch term
     */
    private Set<Data> neighbourhoodSearch() {
        Set<Data> list = new HashSet<>();

        String neighbourhoodSearch = neighbourhoodField.getText().toLowerCase();
        for (Data entry : data) {
            if (String.valueOf(entry.getNeighbourhood().toLowerCase()).contains(neighbourhoodSearch)) {
                list.add(entry);
            }
        }
        return list;
    }

    /**
     * assessmentClassSearch - Searches through the data and returns a set with
     * accounts containing the search term in the assessmentClass field.
     *
     * @return list - set of items that contain the assessmentClassSearch term
     */
    private Set<Data> assessmentClassSearch() {
        Set<Data> list = new HashSet<>();

        String assessmentClassSearch = assessmentClassBox.getValue();
        for (Data entry : data) {
            if (String.valueOf(entry.getAssessmentClass()).equalsIgnoreCase(assessmentClassSearch)) {
                list.add(entry);
            }
        }
        return list;
    }

    /**
     * addToSet - takes an inSet (data to add to set) and outSet (set to be
     * added to) and combines them, making sure to not include duplicate Data
     * items
     *
     * @param inSet - set to add to outSet
     * @param outSet - set to add inSet to
     * @return outSet - set with all items added
     */
    private Set<Data> addToSet(Set<Data> inSet, Set<Data> outSet) {
        if (outSet.isEmpty()) {
            outSet.addAll(inSet);
        } else {
            outSet.retainAll(inSet);
        }
        return outSet;
    }

    /**
     * search - uses all field search methods and combines all search results
     * into a single observable list
     *
     * @param newData - observable list of Data to add all found data to
     * @return newData
     */
    private ObservableList<Data> search(ObservableList<Data> newData) {
        Set<Data> accountSet, addressSet, neighbourhoodSet, assessmentClassSet, fullSet = new HashSet<>();

        //Search for accountnumber
        if (!accountNumberField.getText().isEmpty()) {
            accountSet = accountSearch();
            fullSet = addToSet(accountSet, fullSet);
        }
        //search for address
        if (!addressField.getText().isEmpty()) {
            addressSet = addressSearch();
            fullSet = addToSet(addressSet, fullSet);
        }
        //search for neighbourbood
        if (!neighbourhoodField.getText().isEmpty()) {
            neighbourhoodSet = neighbourhoodSearch();
            fullSet = addToSet(neighbourhoodSet, fullSet);
        }
        //search for assessment class
        if (assessmentClassBox.getValue() != null) {
            assessmentClassSet = assessmentClassSearch();
            fullSet = addToSet(assessmentClassSet, fullSet);
        }

        //add contents of set to newData observable list object
        if (!fullSet.isEmpty()) {
            newData.addAll(fullSet);
        }
        //return observable list
        return newData;
    }
}
