package sample;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CustomerController {
    private  static final String url="jdbc:mysql://localhost:3306/ demo_practical_jdbc?useSSL=false";
    private static final Properties props=new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField salaryTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updateBtn1;

    @FXML
    void saveBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();
        String name=nameTxt.getText();
        String address=addressTxt.getText();
        String salary=salaryTxt.getText();

         try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "INSERT INTO customer(id,name, address,salary)" + "VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setDouble(4, Double.parseDouble(salary));

            int affectedRows=pstm.executeUpdate();
            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
                idTxt.setText(" ");
                nameTxt.setText(" ");
                addressTxt.setText(" ");
                salaryTxt.setText(String.valueOf(" "));
            }

        }
    }

    @FXML
    void deleteBtnClick(ActionEvent event) throws SQLException {

        String idd=idTxt.getText();
        PreparedStatement pstm;
        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "DELETE FROM customer WHERE id=?";
            pstm = connection.prepareStatement(sql);

        pstm.setString(1, idd);
        int affectdrows=pstm.executeUpdate();

            if(affectdrows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
                idTxt.setText(" ");
                nameTxt.setText(" ");
                addressTxt.setText(" ");
                salaryTxt.setText(String.valueOf(" "));
            }

        }
    }
    String idd;
    @FXML
    void txtClick(ActionEvent event) throws SQLException {
         idd=idTxt.getText();
        try (Connection con = DriverManager.getConnection(url, props)) {
            String sql = "SELECT * FROM customer WHERE id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, idd);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
           //   String cus_id = resultSet.getString(1);
                String cus_name = resultSet.getString(2);
                String cus_address = resultSet.getString(3);
                double cus_salary = resultSet.getDouble(4);

                nameTxt.setText(cus_name);
                addressTxt.setText(cus_address);
                salaryTxt.setText(String.valueOf(cus_salary));

            }
        }

    }

    @FXML
    void updateBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();
        String name=nameTxt.getText();
        String address=addressTxt.getText();
        String salary=salaryTxt.getText();

        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql="UPDATE customer SET id = ?,name = ?,address = ?,salary = ? WHERE id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,address);
            preparedStatement.setDouble(4,Double.parseDouble(salary));
            preparedStatement.setString(5,idd);

            int affectedrows=preparedStatement.executeUpdate();

            if(affectedrows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated!").show();

            }

            idTxt.setText(" ");
            nameTxt.setText(" ");
            addressTxt.setText(" ");
            salaryTxt.setText(" ");

        }

    }

    @FXML
    void initialize() {
        assert addressTxt != null : "fx:id=\"addressTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert salaryTxt != null : "fx:id=\"salaryTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'customer.fxml'.";

    }

}
