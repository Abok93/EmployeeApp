package java2_project_bokrossy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class EmployeeController {

    // Creates a list of job titles
    ObservableList<String> jobList = FXCollections.observableArrayList("Director", "Manager", "Developer", "Tester",
            "Salesman");

    // Creates a list of office employees
    List<Employee> office = new ArrayList<>();

    //Maps FXML elements to their JavaFX objects
    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label deleteLabel;

    @FXML
    private Label deleteText;

    @FXML
    private Label employeeCountLabel;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private CheckBox fullTimeCheckBox;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private Label genderLabel;

    @FXML
    private TextField idField;

    @FXML
    private Label idLabel;

    @FXML
    private ComboBox<String> jobTitleComboBox;

    @FXML
    private Label jobTitleLabel;

    @FXML
    private Label listLabel;

    @FXML
    private ListView<Employee> listv;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private RadioButton otherRadioButton;

    @FXML
    private Button saveButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label titleLabel;

    //Handles the add button action
    @FXML
    void HandleAdd(ActionEvent event) {

        try {
            // Create a new employee from the input fields
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String jobTitle = jobTitleComboBox.getValue();
            boolean isFullTime = fullTimeCheckBox.isSelected();
            String gender = "";
            if (femaleRadioButton.isSelected()) {
                gender = "Female";
            } else if (maleRadioButton.isSelected()) {
                gender = "Male";
            } else if (otherRadioButton.isSelected()) {
                gender = "Other";
            }

            //validation
            if (id == 0 || name.isEmpty() || jobTitle == null || gender.isEmpty()) {
                throw new IllegalArgumentException("Please fill in all fields.");
            } else if (id < 0) {
                throw new IllegalArgumentException("ID must be a positive integer.");
            } else {
                for (Employee emp : office) {
                    if (emp.getId() == id) {
                        throw new IllegalArgumentException("ID is already in use by another employee.");
                    }

                }
            }

            // Create the new Employee object from the input fields
            Employee newEmployee = new Employee(id, name, jobTitle, isFullTime, gender);

            // Add the new employee to the office list and update the UI
            addEmployee(newEmployee);

            // Clear the input fields
            idField.clear();
            nameField.clear();
            jobTitleComboBox.getSelectionModel().clearSelection();
            fullTimeCheckBox.setSelected(false);
            genderGroup.selectToggle(null);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid integer for ID.");
            alert.showAndWait();
        } catch (IllegalArgumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void HandleClear(ActionEvent event) {
        //Clear are all fields and boxes
        idField.clear();
        nameField.clear();
        jobTitleComboBox.getSelectionModel().clearSelection();
        fullTimeCheckBox.setSelected(false);
        femaleRadioButton.setSelected(false);
        maleRadioButton.setSelected(false);
        otherRadioButton.setSelected(false);

    }

    @FXML
    void HandleDelete(ActionEvent event) {
        Employee selectedEmployee = listv.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            // Remove the selected employee from the office list and update the UI
            deleteEmployee(selectedEmployee);

        }

    }

    @FXML
    void HandleSave(ActionEvent event) {
        //Save the employees to the csv file and dispaly a confirmation message
        saveEmployees();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employees saved to file!");
        alert.showAndWait();

    }

    public void loadEmployees() {
        //load employees from the csv file add them to the office list
        File file = new File("employees.csv");
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Skip the column headers
            //Creates a new employee by reading each line of the CSV file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String jobTitle = fields[2];
                boolean isFullTime = Boolean.parseBoolean(fields[3]);
                String gender = fields[4];
                office.add(new Employee(id, name, jobTitle, isFullTime, gender));
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    public void updateUI() {
        // Update the ListView and Label based on the current office list
        listv.getItems().setAll(office);
        employeeCountLabel.setText("Total Employees: " + office.size());
    }

    private void addEmployee(Employee employee) {
        // Add a new employee to the office list and update the UI
        office.add(employee);
        updateUI();
    }

    private void deleteEmployee(Employee employee) {
        // Remove an employee from the office list and update the UI
        office.remove(employee);
        updateUI();
    }

    private void saveEmployees() {
        //save the current office list to the CSV file
        File file = new File("employees.csv");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("ID,Name,Job Title,Full Time,Gender");
            for (Employee emp : office) {

                writer.printf("%d,%s,%s,%b,%s\n", emp.getId(), emp.getName(),
                        emp.getJob(), emp.getFulltime(), emp.getGender());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        //Set up the jobTitleComboBox with the job titles from the jobList array
        jobTitleComboBox.getItems().addAll(jobList);
        jobTitleComboBox.setItems(jobList);

    }

}
