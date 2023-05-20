import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javax.swing.*;
import java.util.Calendar;
import java.util.HashMap;

public class ProjectController {

    @FXML
    private GridPane dateTable;
    @FXML
    private ComboBox<String> month;
    @FXML
    private ComboBox<String> year;

    private HashMap<Calendar, String> hMap;
    private Calendar tempDate;

    @FXML
    void loadCalander(ActionEvent event) {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(year.getValue()), Integer.parseInt(month.getValue()) - 1, 1);
        int numOdDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDayOfMonth = c.get(Calendar.DAY_OF_WEEK);
        int numOfDayInMonth = 1;
        Button[][] buttons = new Button[6][7];
        for (Node n : dateTable.getChildren())
            if (n instanceof Button)
                buttons[GridPane.getRowIndex(n) - 1][GridPane.getColumnIndex(n)] = ((Button) n);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j].setVisible(false);
            }
        }
        for (int i = startDayOfMonth - 1; i < 7; i++) {
            buttons[0][i].setVisible(true);
            buttons[0][i].setText(numOfDayInMonth + "");
            numOfDayInMonth++;
        }
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 7 && numOfDayInMonth <= numOdDays; j++) {
                buttons[i][j].setVisible(true);
                buttons[i][j].setText(numOfDayInMonth + "");
                numOfDayInMonth++;
            }
        }
    }

    @FXML
    public void initialize()
    {
        hMap  = new HashMap<Calendar, String>();
        iniCombox();
        iniGridPane();
        tempDate = Calendar.getInstance();
    }

    private void iniGridPane() {
        for(Node n : dateTable.getChildren()) {
            if(n instanceof Button) {
                ((Button)n).setText("...");
                ((Button)n).setVisible(false);
            }
        };
    }

    private void iniCombox()
    {
        final int START_YEAR = 2020, END_YEAR = 2022, MONTH = 12;
        for (int i = START_YEAR; i <= END_YEAR ; i++) {
            year.getItems().add(i+"");
        }
        year.setValue("2020");
        for (int i = 1; i <= MONTH; i++) {
            month.getItems().add(i+"");
        }
        month.setValue("1");
    }

    @FXML
    void showTasks(ActionEvent event) {
        int dayInMonth = Integer.parseInt(((Button)event.getSource()).getText());
        tempDate.set(Integer.parseInt(year.getValue()), Integer.parseInt(month.getValue())-1, dayInMonth);
        int editTasks = 1;//NOT ok pressed
        if(hMap.containsKey(tempDate))
        {
            editTasks = JOptionPane.showConfirmDialog(null,
                    hMap.get(tempDate) + "\nWould you like to add or change your tasks for this day?");
            if(editTasks == JOptionPane.OK_OPTION)
            {
                String newTasks = JOptionPane.showInputDialog("Here you can change or add assignments and tasks",
                                                                hMap.get(tempDate));
                hMap.replace(tempDate, hMap.get(tempDate), newTasks);
            }
        }
        else
        {
            editTasks = JOptionPane.showConfirmDialog(null,
                    hMap.get(tempDate) + "\nThere is no tasks. Would you like to add tasks for this day?");
            if(editTasks == JOptionPane.OK_OPTION)
            {
                String newTasks = JOptionPane.showInputDialog("Here you can add assignments and tasks");
                hMap.put(tempDate, newTasks);
            }
        }
    }
}