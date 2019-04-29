package dataproject;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/*Name: Mohnish Gauni
 *ID: 991519320
 *Assignment: PROJECT
 *Date: 4/7/2019
 */



//also for search have second file output that will dynamically populate all
//search hits, and when not in search dynamically display all entries

public class DataProject extends Application {

    private LinkedList<Order> orderList = new LinkedList<>();
    private TextField orderID = new TextField();
    private TextField name = new TextField();
    private TextField address = new TextField();
    private TextField city = new TextField();
    private TextField product = new TextField();
    private TextField price = new TextField();
    private TextField quantity = new TextField();
    private TextField navigation = new TextField();
    private TextField search = new TextField();
    private Label navError = new Label();
    private Label idError = new Label();
    private Label nameError = new Label();
    private Label cityError = new Label();
    private Label priceError = new Label();
    private Label quantityError = new Label();
    private Label blank = new Label();
    private Label blank2 = new Label();
    private Label blank3 = new Label();
    private Label blank4 = new Label();
    private Label blank5 = new Label();
    private Label blank6 = new Label();
    private Label blank7 = new Label();
    private Label blank8 = new Label();
    private Label blank9 = new Label();
    private Label blank10 = new Label();
    private Label blank11 = new Label();
    private Label blank12 = new Label();
    private Label Order = new Label("Order:");
    private Label Name = new Label("Name:");
    private Label Address = new Label("Address:");
    private Label City = new Label("City");
    private Label Product = new Label("Product");
    private Label Price = new Label("Price");
    private Label Quantity = new Label("Quantity:");
    private Label link = new Label(">>>");
    private Label Search = new Label("Search >>>");
    private Button btnNext = new Button("Next");
    private Button btnPrev = new Button("Previous");
    private Button btnFirst = new Button("First Entry");
    private Button btnLast = new Button("Last Entry");
    private Button btnMod = new Button("Modify Entry");
    private Button btnDel = new Button("Delete Entry");
    private Button btnAdd = new Button("Add Entry");
    private Button btnUpd = new Button("Update");
    private Button btnSort = new Button("Sort");
    private RadioButton IDradio = new RadioButton("ID");
    private RadioButton nameRadio = new RadioButton("Name");
    private RadioButton addressRadio = new RadioButton("Address");
    private RadioButton cityRadio = new RadioButton("City");
    private RadioButton productRadio = new RadioButton("Product");
    private RadioButton priceRadio = new RadioButton("Price");
    private RadioButton quantityRadio = new RadioButton("Quantity");
    private Image image = new Image("dataproject/closeButton.png", 
            40, 40, false, false);
    private ImageView close = new ImageView(image);

    private String searchID = "";
    private int counter = 0;
    private int ctrl = 0;

    @Override
    public void start(Stage primaryStage) {
        //creates file if it doesn't exist, populates list, 
        //sets buttons, sets initial data displayed
        //sets textbased nav bar
        //sets stage/close function
        try {
            File one = new File("order.dat");
            if (!one.exists()) {
                if (one.createNewFile()) {
                }
            }
            Order two = new Order();
            orderList = two.FillList(one, orderList);
            setButtons();
            setInitial();
            setNav();
            setClose(one,two);
        }catch (IOException e) {

        }

        Scene scene = new Scene(setPane(), 1500, 700);        
        scene.setFill(Color.TRANSPARENT); 
        scene.getStylesheets().add("dataproject/dataCss.css");        
        primaryStage.setTitle("Database");
        primaryStage.setScene(scene);       
        primaryStage.initStyle(StageStyle.TRANSPARENT);        
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void setClose(File one, Order two){
        //custom close button, on click asks for confirmation
        //if received will save data and exit
        close.setOnMouseClicked(e ->{
        Alert dlgconfirmation = new Alert(Alert.AlertType.CONFIRMATION);    
        DialogPane dialogPane = dlgconfirmation.getDialogPane();
        dlgconfirmation.initStyle(StageStyle.TRANSPARENT); 
        dialogPane.getStylesheets().add("dataproject/dataCss.css");
        dlgconfirmation.setTitle("Confirm");
        dlgconfirmation.setHeaderText("Confirmation");
        dlgconfirmation.setContentText("Do you want to Exit? "
                + "\nExiting will Save Changes!");
        Optional<ButtonType> result = dlgconfirmation.showAndWait();
        if(result.get() == ButtonType.OK){
        
             try {
                two.saveList(one, orderList);
            } catch (IOException d) {
                System.out.println(d.getMessage());
            }
        
            Platform.exit();
        }else{
            
        }
        });
        
    }
    
    public void setNav() {
        
        //sets text-based numeric page navigation in the right 
        //hand corner
        //has input validation (only takes int)
        navigation.setOnMouseClicked(e -> {
            navigation.clear();
        });

        navigation.setOnKeyPressed(e -> {

            if (isInt(navigation.getText()) && 
                    Integer.parseInt(navigation.getText()) 
                    < orderList.size()
                    && Integer.parseInt(navigation.getText()) > -1 &&
                    e.getCode() == KeyCode.ENTER) {
                counter = Integer.parseInt(navigation.getText());
                orderID.setText(orderList.get(counter).getOrderID());
                name.setText(orderList.get(counter).getName());
                address.setText(orderList.get(counter).getAddress());
                city.setText(orderList.get(counter).getCity());
                product.setText(orderList.get(counter).getProduct());
                price.setText(Double.toString(
                        (orderList.get(counter).getPrice())));
                quantity.setText(Integer.toString(
                        (orderList.get(counter).getQuantity())));
                navigation.setText(Integer.toString(counter) 
                        + "/" + Integer.toString(orderList.size() - 1));
                navError.setText("");
            } else if (isInt(navigation.getText()) 
                    && e.getCode() == KeyCode.ENTER
                    && (Integer.parseInt(navigation.getText()
                    ) >= orderList.size() || Integer.parseInt(
                            navigation.getText()) < 0)) {
                navError.setText("Invalid entry");
            } else if (!isInt(navigation.getText()) 
                    && e.getCode() == KeyCode.ENTER) {

                navError.setText("#'s Only");
                 navigation.setText(Integer.toString(counter) 
                        + "/" + Integer.toString(orderList.size() - 1));
            }

        });
        
        navigation.setId("nav");
    }

  
    public void setInitial() {
        //explained above, sets initial page
        orderID.setText(orderList.get(counter).getOrderID());
        name.setText(orderList.get(counter).getName());
        address.setText(orderList.get(counter).getAddress());
        city.setText(orderList.get(counter).getCity());
        product.setText(orderList.get(counter).getProduct());
        String thePrice = Double.toString(
                (orderList.get(counter).getPrice()));
        price.setText(thePrice);
        quantity.setText(Integer.toString(
                (orderList.get(counter).getQuantity())));
        navigation.setText(Integer.toString(counter) 
                + "/" + Integer.toString(orderList.size() - 1));
    }

    public void setButtons() {
        //explained above, sets buttons/functions
        btnNext.setOnAction(new NextPage());
        btnPrev.setOnAction(new PrevPage());
        btnFirst.setOnAction(new FirstPage());
        btnLast.setOnAction(new LastPage());
        btnMod.setOnAction(new ModifyPage());
        btnDel.setOnAction(new DeletePage());
        btnAdd.setOnAction(new AddPage());
        btnUpd.setOnAction(new UpdatePage());
        btnSort.setOnAction(new SortList());
        search.setOnKeyPressed(new SearchList());

        ToggleGroup group = new ToggleGroup();

        IDradio.setToggleGroup(group);
        nameRadio.setToggleGroup(group);
        addressRadio.setToggleGroup(group);
        cityRadio.setToggleGroup(group);
        productRadio.setToggleGroup(group);
        priceRadio.setToggleGroup(group);
        quantityRadio.setToggleGroup(group);
        IDradio.setSelected(true);

    }

    public GridPane setPane() {
        //sets node positions
        GridPane pane = new GridPane();
        
        pane.add(close, 9, 0);
        
        pane.add(orderID, 4, 1);
        pane.add(idError, 4, 2);
        pane.add(Order, 3,1);
       
        
        pane.add(name, 4, 3);
        pane.add(nameError, 4, 4);
        pane.add(Name, 3,3);
        
        pane.add(address, 4, 5);
        pane.add(Address, 3, 5);
        pane.add(blank2, 4,6);
        
        pane.add(city, 4, 7);
        pane.add(cityError, 4, 8);
        pane.add(City, 3, 7);
        
        pane.add(product, 4, 9);
        pane.add(Product, 3, 9);
        pane.add(blank3, 4, 10);
        
        pane.add(price, 4, 11);
        pane.add(priceError, 4, 12);
        pane.add(Price, 3, 11);
        
        
        pane.add(quantity, 4, 13);
        pane.add(quantityError, 4, 14);
        pane.add(Quantity, 3, 13);
        pane.add(blank11, 4, 15);
        
        pane.add(btnPrev, 2, 16);
        pane.add(btnNext, 4, 16);
        pane.add(blank4, 2, 17);
        
        pane.add(btnFirst, 2, 18);
        pane.add(btnLast, 4, 18);
        pane.add(blank5, 2, 19);
        
        pane.add(btnAdd, 2, 20);
        pane.add(link, 3, 20);
        pane.add(btnUpd, 4, 20);
        pane.add(blank6, 2, 21);
        
        pane.add(btnMod, 2, 22);
        pane.add(btnDel, 4, 22);
        pane.add(blank7,2, 23);
        
        pane.add(btnSort, 2, 24);
        pane.add(blank8, 1,0);
        
        pane.add(blank9, 5, 0);
        pane.add(blank10, 6, 0);
        
        pane.add(IDradio, 7, 15);
        pane.add(nameRadio, 7, 16);
        pane.add(addressRadio, 7, 17);
        pane.add(cityRadio, 7, 18);
        pane.add(productRadio, 8, 15);
        pane.add(priceRadio, 8, 16);
        pane.add(quantityRadio, 8, 17);
        pane.add(Search, 7, 19);
        pane.add(search, 8, 19);
        
        pane.add(navigation, 8, 1);
        pane.add(navError, 8, 2);
        pane.add(blank12, 9, 1);
        navigation.setMaxWidth(70);
                    
        pane.setId("grid");
        navError.setId("navText");
        return pane;
    }

    public class NextPage implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            //increments counter and sets page according 
            //to counter value
            ctrl = 0;
            if (counter < orderList.size() - 1) {
                counter++;
                orderID.setText(orderList.get(counter).getOrderID());
                name.setText(orderList.get(counter).getName());
                address.setText(orderList.get(counter).getAddress());
                city.setText(orderList.get(counter).getCity());
                product.setText(orderList.get(counter).getProduct());

                price.setText(Double.toString(
                        (orderList.get(counter).getPrice())));
                quantity.setText(Integer.toString(
                        (orderList.get(counter).getQuantity())));
                navigation.setText(Integer.toString(counter) 
                        + "/" + Integer.toString(orderList.size() - 1));
                idError.setText("");
                nameError.setText("");
                cityError.setText("");
                priceError.setText("");
                quantityError.setText("");
            }
        }
    }

    public class PrevPage implements EventHandler<ActionEvent> {
        
        //decrements the counter variable, sets 
        //page according to counter
        @Override
       
        public void handle(ActionEvent e) {
             ctrl = 0;
            if (counter > 0) {
                counter--;
                orderID.setText(orderList.get(counter).getOrderID());
                name.setText(orderList.get(counter).getName());
                address.setText(orderList.get(counter).getAddress());
                city.setText(orderList.get(counter).getCity());
                product.setText(orderList.get(counter).getProduct());
                price.setText(Double.toString(
                        (orderList.get(counter).getPrice())));
                quantity.setText(Integer.toString(
                        (orderList.get(counter).getQuantity())));
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                idError.setText("");
                nameError.setText("");
                cityError.setText("");
                priceError.setText("");
                quantityError.setText("");
            }

        }

    }

    public class FirstPage implements EventHandler<ActionEvent> {
        
        //sets counter to 0 and sets page according to counter
        //coulve used getFirst() but would've affected the
        //dynamic textfield navBar
        @Override
        public void handle(ActionEvent e) {
            ctrl = 0;
            counter = 0;
            orderID.setText(orderList.get(counter).getOrderID());
            name.setText(orderList.get(counter).getName());
            address.setText(orderList.get(counter).getAddress());
            city.setText(orderList.get(counter).getCity());
            product.setText(orderList.get(counter).getProduct());
            price.setText(Double.toString(
                    (orderList.get(counter).getPrice())));
            quantity.setText(Integer.toString(
                    (orderList.get(counter).getQuantity())));
            navigation.setText(Integer.toString(counter) 
                    + "/" + Integer.toString(orderList.size() - 1));
            idError.setText("");
            nameError.setText("");
            cityError.setText("");
            priceError.setText("");
            quantityError.setText("");
        }

    }

    public class LastPage implements EventHandler<ActionEvent> {
        
        //sets counter to last index, sets page
        //accoridng to counter
        //coulve used getLast() but would've affected the
        //dynamic textfield navBar
        
        @Override
        public void handle(ActionEvent e) {
            ctrl = 0;
            counter = orderList.size() - 1;
            orderID.setText(orderList.get(counter).getOrderID());
            name.setText(orderList.get(counter).getName());
            address.setText(orderList.get(counter).getAddress());
            city.setText(orderList.get(counter).getCity());
            product.setText(orderList.get(counter).getProduct());
            price.setText(Double.toString(
                    (orderList.get(counter).getPrice())));
            quantity.setText(Integer.toString(
                    (orderList.get(counter).getQuantity())));
            navigation.setText(Integer.toString(counter)
                    + "/" + Integer.toString(orderList.size() - 1));
            idError.setText("");
            nameError.setText("");
            cityError.setText("");
            priceError.setText("");
            quantityError.setText("");
        }

    }

    public class SortList implements EventHandler<ActionEvent> {
        
        //dumps list into array, sorts ID
        //by ascending order by storing array into temp, assigning
        //larger value to previous index and reinserting temp value
        //then dumps array back onto list
        
        @Override
        public void handle(ActionEvent e) {

            Order[] array = new Order[orderList.size()];
            Order[] temp = new Order[orderList.size()];
            int count = orderList.size();

            for (int i = 0; i < array.length; i++) {
                array[i] = orderList.get(i);

            }
            for (int i = 0; i < count; i++) {
                orderList.remove(0);
            }

            for (int i = 0; i < array.length; i++) {

                for (int j = i + 1; j < array.length; j++) {

                    if (Integer.parseInt(array[i].getOrderID()
                    ) > Integer.parseInt(array[j].getOrderID())) {

                        temp[i] = array[i];
                        array[i] = array[j];
                        array[j] = temp[i];
                    }
                }
            }
            for (int i = 0; i < array.length; i++) {
                orderList.add(array[i]);
            }

            counter = 0;
            orderID.setText(orderList.get(counter).getOrderID());
            name.setText(orderList.get(counter).getName());
            address.setText(orderList.get(counter).getAddress());
            city.setText(orderList.get(counter).getCity());
            product.setText(orderList.get(counter).getProduct());
            price.setText(Double.toString(
                    (orderList.get(counter).getPrice())));
            quantity.setText(Integer.toString(
                    (orderList.get(counter).getQuantity())));
            navigation.setText(Integer.toString(counter)
                    + "/" + Integer.toString(orderList.size() - 1));
            idError.setText("");
            nameError.setText("");
            cityError.setText("");
            priceError.setText("");
            quantityError.setText("");
        }

    }

    public class ModifyPage implements EventHandler<ActionEvent> {
        
        //grabs textField values and stores them at that index value
        //has confirmation alerts, if ok will proceed else cancels
        //also contains input validation if modification
        //is not appropriate data type; populates hidden label
        
        @Override
        public void handle(ActionEvent e) {
            Alert dlgconfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION);
            Alert dlgmessage = new Alert(
                    Alert.AlertType.INFORMATION);
            
            DialogPane dialogPane = dlgconfirmation.getDialogPane();
            dlgconfirmation.initStyle(StageStyle.TRANSPARENT);
            dlgmessage.initStyle(StageStyle.TRANSPARENT);
            dialogPane.getStylesheets().add(
                    "dataproject/dataCss.css");
            DialogPane dialogPane1 = dlgmessage.getDialogPane();
            dialogPane1.getStylesheets().add(
                    "dataproject/dataCss.css");
            
            String message = "";
            dlgconfirmation.setTitle("Confirm");
            dlgconfirmation.setHeaderText("Confirmation");
            dlgconfirmation.setContentText(
                    "Are you sure you want to modify entry?");
            Optional<ButtonType> result = dlgconfirmation.showAndWait();

            if (result.get() == ButtonType.OK && isInt(orderID.getText())
                    && isInt(quantity.getText())
                    && isDouble(price.getText())
                    && isName(name.getText())
                    && isName(city.getText())) {
                message = "Entry Modified.";

                orderList.get(counter).setOrderID(orderID.getText());
                orderList.get(counter).setName(name.getText());
                orderList.get(counter).setAddress(address.getText());
                orderList.get(counter).setCity(city.getText());
                orderList.get(counter).setProduct(product.getText());
                orderList.get(counter).setPrice(
                        Double.parseDouble(price.getText()));
                orderList.get(counter).setQuantity(
                        Integer.parseInt(quantity.getText()));
                idError.setText("");
                nameError.setText("");
                cityError.setText("");
                priceError.setText("");
                quantityError.setText("");

            }

            if (result.get() == ButtonType.OK 
                    && !isInt(quantity.getText())) {
                message = "Entry not modified.";
                quantity.clear();

                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                quantityError.setText("Must be Integer ex. 123");
                quantity.requestFocus();
            }
            if (result.get() == ButtonType.OK && isInt(quantity.getText())) {
                quantityError.setText("");
            }

            if (result.get() == ButtonType.OK && !isDouble(price.getText())) {
                message = "Entry not modified.";
                price.clear();

                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                priceError.setText("Must be Double ex. 13.5");
                price.requestFocus();
            }

            if (result.get() == ButtonType.OK 
                    && isDouble(price.getText())) {
                priceError.setText("");
            }

            if (result.get() == ButtonType.OK && !isName(city.getText())) {
                message = "Entry not modified.";
                city.clear();
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                cityError.setText("Must be String ex. Abc");
                city.requestFocus();
            }

            if (result.get() == ButtonType.OK && isName(city.getText())) {
                cityError.setText("");
            }

            if (result.get() == ButtonType.OK && !isName(name.getText())) {
                message = "Entry not modified.";
                name.clear();
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                nameError.setText("Must be String ex. Abc Xyz");
                name.requestFocus();
            }

            if (result.get() == ButtonType.OK
                    && isName(name.getText())) {
                nameError.setText("");
            }

            if (result.get() == ButtonType.OK 
                    && !isInt(orderID.getText())) {
                message = "Entry not modified.";
                orderID.clear();

                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                idError.setText("Must be Integer ex. 123");
                orderID.requestFocus();
            }
            if (result.get() == ButtonType.OK 
                    && isInt(orderID.getText())) {
                idError.setText("");
            }

            if (result.get() == ButtonType.CANCEL) {
                message = "Entry not modified.";
                orderID.setText(orderList.get(counter).getOrderID());
                name.setText(orderList.get(counter).getName());
                address.setText(orderList.get(counter).getAddress());
                city.setText(orderList.get(counter).getCity());
                product.setText(orderList.get(counter).getProduct());
                price.setText(Double.toString(
                        (orderList.get(counter).getPrice())));
                quantity.setText(Integer.toString(
                        (orderList.get(counter).getQuantity())));
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                idError.setText("");
            }

            dlgmessage.setContentText(message);
            dlgmessage.show();

        }

    }

    public class DeletePage implements EventHandler<ActionEvent> {
            
        //deletes list item at current index
        //provides alert prompt to confirm deletion
        //page is populated by proceeding index after deletion
        //if cancelled, original values are retained, list
        //intact
        
        @Override
        public void handle(ActionEvent e) {
            Alert dlgconfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            Alert dlgmessage = new Alert(Alert.AlertType.INFORMATION);
            String message = "";
            dlgconfirmation.setTitle("Confirm");
            dlgconfirmation.setHeaderText("Confirmation");
            dlgconfirmation.setContentText(
                    "Are you sure you want to Delete entry?");
            DialogPane dialogPane = dlgconfirmation.getDialogPane();
            dlgconfirmation.initStyle(StageStyle.TRANSPARENT);
            dlgmessage.initStyle(StageStyle.TRANSPARENT);
            dialogPane.getStylesheets().add("dataproject/dataCss.css");
            DialogPane dialogPane1 = dlgmessage.getDialogPane();
            dialogPane1.getStylesheets().add("dataproject/dataCss.css");
            Optional<ButtonType> result = dlgconfirmation.showAndWait();
            

            if (result.get() == ButtonType.OK && ctrl == 0) {
                message = "Entry Deleted.";
                
                
                orderList.remove(counter);
                if(counter == orderList.size()){
                    counter--;
                }
                orderID.setText(orderList.get(counter).getOrderID());
                name.setText(orderList.get(counter).getName());
                address.setText(orderList.get(counter).getAddress());
                city.setText(orderList.get(counter).getCity());
                product.setText(orderList.get(counter).getProduct());
                price.setText(Double.toString(
                        (orderList.get(counter).getPrice())));
                quantity.setText(Integer.toString(
                        (orderList.get(counter).getQuantity())));
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));

            } else {
                message = "Entry not deleted.";
                orderID.setText(orderList.get(counter).getOrderID());
                name.setText(orderList.get(counter).getName());
                address.setText(orderList.get(counter).getAddress());
                city.setText(orderList.get(counter).getCity());
                product.setText(orderList.get(counter).getProduct());
                price.setText(Double.toString(
                        (orderList.get(counter).getPrice())));
                quantity.setText(Integer.toString(
                        (orderList.get(counter).getQuantity())));
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));

                idError.setText("");
                nameError.setText("");
                cityError.setText("");
                priceError.setText("");
                quantityError.setText("");
            }

            dlgmessage.setContentText(message);
            dlgmessage.show();

        }

    }

    public class AddPage implements EventHandler<ActionEvent> {
        
        //clears fields for the user to input data
        //this one I stuck to the req. as close as possible
        //the add literally only clears the fields, and it's
        //the update button that pushes data onto object
        //and appends object onto list
        //contains a control variable to you cannot be in
        //the add process AND delete an entry (minor glitch resolve)
        
        @Override
        public void handle(ActionEvent e) {
            
            if (ctrl == 0) {
                orderID.setText("");
                name.setText("");
                address.setText("");
                city.setText("");
                product.setText("");
                price.setText("");
                quantity.setText("");
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                ctrl = 1;
                orderID.requestFocus();

            }
        }

    }

    public class UpdatePage implements EventHandler<ActionEvent> {
        
       //an extension of the add button
       //creates object, stores field onto object
       //pushes object onto list
        
        
        @Override
        public void handle(ActionEvent e) {
            Alert dlgconfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION);
            Alert dlgmessage = new Alert(
                    Alert.AlertType.INFORMATION);
            String message = "";
            dlgconfirmation.setTitle("Confirm");
            dlgconfirmation.setHeaderText("Confirmation");
            dlgconfirmation.setContentText(
                    "Are you sure you want to Add entry?");
            DialogPane dialogPane = dlgconfirmation.getDialogPane();
            dlgconfirmation.initStyle(StageStyle.TRANSPARENT);
            dlgmessage.initStyle(StageStyle.TRANSPARENT);
            dialogPane.getStylesheets().add("dataproject/dataCss.css");
            DialogPane dialogPane1 = dlgmessage.getDialogPane();
            dialogPane1.getStylesheets().add("dataproject/dataCss.css");
            Optional<ButtonType> result = dlgconfirmation.showAndWait();

            if (result.get() == ButtonType.OK && isInt(orderID.getText())
                    && isInt(quantity.getText())
                    && isDouble(price.getText())
                    && isName(name.getText())
                    && isName(city.getText())) {
                message = "Entry Modified.";

                message = "Entry Added.";
                Order one = new Order();

                one.setOrderID(orderID.getText());
                one.setName(name.getText());
                one.setAddress(address.getText());
                one.setCity(city.getText());
                one.setProduct(product.getText());
                one.setPrice(Double.parseDouble(price.getText()));
                one.setQuantity(Integer.parseInt(quantity.getText()));
                orderList.add(one);
                idError.setText("");
                nameError.setText("");
                cityError.setText("");
                priceError.setText("");
                quantityError.setText("");
                ctrl = 0;
                counter = orderList.size() - 1;
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));

            }

            if (result.get() == ButtonType.OK
                    && !isInt(quantity.getText())) {
                message = "Entry not modified.";
                quantity.clear();

                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                quantityError.setText("Must be Integer ex. 123");
                quantity.requestFocus();
            }
            if (result.get() == ButtonType.OK 
                    && isInt(quantity.getText())) {
                quantityError.setText("");
            }

            if (result.get() == ButtonType.OK 
                    && !isDouble(price.getText())) {
                message = "Entry not modified.";
                price.clear();

                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                priceError.setText("Must be Double ex. 13.5");
                price.requestFocus();
            }

            if (result.get() == ButtonType.OK 
                    && isDouble(price.getText())) {
                priceError.setText("");
            }

            if (result.get() == ButtonType.OK 
                    && !isName(city.getText())) {
                message = "Entry not modified.";
                city.clear();
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                cityError.setText("Must be String ex. Abc");
                city.requestFocus();
            }

            if (result.get() == ButtonType.OK 
                    && isName(city.getText())) {
                cityError.setText("");
            }

            if (result.get() == ButtonType.OK 
                    && !isName(name.getText())) {
                message = "Entry not modified.";
                name.clear();
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                nameError.setText("Must be String ex. Abc Xyz");
                name.requestFocus();
            }

            if (result.get() == ButtonType.OK 
                    && isName(name.getText())) {
                nameError.setText("");
            }

            if (result.get() == ButtonType.OK 
                    && !isInt(orderID.getText())) {
                message = "Entry not modified.";
                orderID.clear();

                navigation.setText(Integer.toString(counter) 
                        + "/" + Integer.toString(orderList.size() - 1));
                idError.setText("Must be Integer ex. 123");
                orderID.requestFocus();
            }
            if (result.get() == ButtonType.OK 
                    && isInt(orderID.getText())) {
                idError.setText("");
            }

            if (result.get() == ButtonType.CANCEL) {
                message = "Entry not Added.";
                orderID.setText(orderList.get(counter).getOrderID());
                name.setText(orderList.get(counter).getName());
                address.setText(orderList.get(counter).getAddress());
                city.setText(orderList.get(counter).getCity());
                product.setText(orderList.get(counter).getProduct());
                price.setText(Double.toString(
                        (orderList.get(counter).getPrice())));
                quantity.setText(Integer.toString(
                        (orderList.get(counter).getQuantity())));
                navigation.setText(Integer.toString(counter)
                        + "/" + Integer.toString(orderList.size() - 1));
                ctrl = 0;
            }

        }

    }

    public class SearchList implements EventHandler<KeyEvent> {
        
        //based on radio value, searches for that particular value
        //ignores case
        //input validation is handled generally here
        //is keypressed on enter
        //if value not found, alert is provided
        
        @Override
        public void handle(KeyEvent e) {
            if (IDradio.isSelected()) {
                searchID = "id";
            } else if (nameRadio.isSelected()) {
                searchID = "name";
            } else if (addressRadio.isSelected()) {
                searchID = "address";
            } else if (cityRadio.isSelected()) {
                searchID = "city";
            } else if (productRadio.isSelected()) {
                searchID = "product";
            } else if (priceRadio.isSelected()) {
                searchID = "price";
            } else {
                searchID = "quantity";
            }

            if (searchID.equals("id") 
                    && e.getCode() == KeyCode.ENTER) {
                boolean exists = true;
                for (int i = 0; i < orderList.size(); i++) {

                    if (orderList.get(i).getOrderID(
                    ).equalsIgnoreCase(search.getText())) {
                        orderID.setText(
                                orderList.get(i).getOrderID());
                        name.setText(orderList.get(i).getName());
                        address.setText(orderList.get(i).getAddress());
                        city.setText(orderList.get(i).getCity());
                        product.setText(orderList.get(i).getProduct());
                        price.setText(Double.toString(
                                (orderList.get(i).getPrice())));
                        quantity.setText(Integer.toString(
                                (orderList.get(i).getQuantity())));
                        counter = i;
                        navigation.setText(Integer.toString(counter)
                                + "/" 
                                + Integer.toString(orderList.size() - 1));
                        exists = true;
                        search.clear();
                        orderID.requestFocus();
                        idError.setText("");
                        nameError.setText("");
                        cityError.setText("");
                        priceError.setText("");
                        quantityError.setText("");
                        break;
                    } else {
                        exists = false;
                    }

                }
                if (!exists) {
                    Alert dlgerror = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = dlgerror.getDialogPane();
                    dlgerror.initStyle(StageStyle.TRANSPARENT);
                    dialogPane.getStylesheets().add(
                            "dataproject/dataCss.css");
            

                    dlgerror.setTitle("Error");
                    dlgerror.setHeaderText("Error");
                    dlgerror.setContentText("Entry Not Found!");
                    dlgerror.show();

                    orderID.setText(orderList.get(counter).getOrderID());
                    name.setText(orderList.get(counter).getName());
                    address.setText(orderList.get(counter).getAddress());
                    city.setText(orderList.get(counter).getCity());
                    product.setText(orderList.get(counter).getProduct());
                    price.setText(Double.toString(
                            (orderList.get(counter).getPrice())));
                    quantity.setText(Integer.toString(
                            (orderList.get(counter).getQuantity())));
                    navigation.setText(Integer.toString(counter)
                            + "/" + Integer.toString(
                                    orderList.size() - 1));
                    search.clear();
                    search.requestFocus();
                    idError.setText("");
                    nameError.setText("");
                    cityError.setText("");
                    priceError.setText("");
                    quantityError.setText("");
                }

            }

            if (searchID.equals("name") 
                    && e.getCode() == KeyCode.ENTER) {
                boolean exists = true;
                for (int i = 0; i < orderList.size(); i++) {

                    if (orderList.get(i).getName(
                    ).equalsIgnoreCase(search.getText())) {
                        orderID.setText(orderList.get(i).getOrderID());
                        name.setText(orderList.get(i).getName());
                        address.setText(orderList.get(i).getAddress());
                        city.setText(orderList.get(i).getCity());
                        product.setText(orderList.get(i).getProduct());
                        price.setText(Double.toString(
                                (orderList.get(i).getPrice())));
                        quantity.setText(Integer.toString(
                                (orderList.get(i).getQuantity())));
                        counter = i;
                        navigation.setText(Integer.toString(counter)
                                + "/" + Integer.toString(
                                        orderList.size() - 1));
                        exists = true;
                        search.clear();
                        orderID.requestFocus();
                        break;
                    } else {
                        exists = false;
                    }

                }
                if (!exists) {
                    Alert dlgerror = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = dlgerror.getDialogPane();
                    dlgerror.initStyle(StageStyle.TRANSPARENT);
                    dialogPane.getStylesheets().add(
                            "dataproject/dataCss.css");    
                    dlgerror.setTitle("Error");
                    dlgerror.setHeaderText("Error");
                    dlgerror.setContentText("Entry Not Found!");
                    dlgerror.show();

                    orderID.setText(orderList.get(counter).getOrderID());
                    name.setText(orderList.get(counter).getName());
                    address.setText(orderList.get(counter).getAddress());
                    city.setText(orderList.get(counter).getCity());
                    product.setText(orderList.get(counter).getProduct());
                    price.setText(Double.toString(
                            (orderList.get(counter).getPrice())));
                    quantity.setText(Integer.toString(
                            (orderList.get(counter).getQuantity())));
                    navigation.setText(Integer.toString(counter)
                            + "/" + Integer.toString(
                                    orderList.size() - 1));
                    search.clear();
                    search.requestFocus();
                }
            }

            if (searchID.equals("address") 
                    && e.getCode() == KeyCode.ENTER) {
                boolean exists = true;
                for (int i = 0; i < orderList.size(); i++) {

                    if (orderList.get(i).getAddress(
                    ).equalsIgnoreCase(search.getText())) {
                        orderID.setText(orderList.get(i).getOrderID());
                        name.setText(orderList.get(i).getName());
                        address.setText(orderList.get(i).getAddress());
                        city.setText(orderList.get(i).getCity());
                        product.setText(orderList.get(i).getProduct());
                        price.setText(Double.toString(
                                (orderList.get(i).getPrice())));
                        quantity.setText(Integer.toString(
                                (orderList.get(i).getQuantity())));
                        counter = i;
                        navigation.setText(Integer.toString(counter)
                                + "/" + Integer.toString(
                                        orderList.size() - 1));
                        exists = true;
                        search.clear();
                        orderID.requestFocus();
                        break;

                    } else {
                        exists = false;
                    }

                }
                if (!exists) {
                    Alert dlgerror = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = dlgerror.getDialogPane();
                    dlgerror.initStyle(StageStyle.TRANSPARENT);
                    dialogPane.getStylesheets().add(
                            "dataproject/dataCss.css");
                    dlgerror.setTitle("Error");
                    dlgerror.setHeaderText("Error");
                    dlgerror.setContentText("Entry Not Found!");
                    dlgerror.show();

                    orderID.setText(orderList.get(counter).getOrderID());
                    name.setText(orderList.get(counter).getName());
                    address.setText(orderList.get(counter).getAddress());
                    city.setText(orderList.get(counter).getCity());
                    product.setText(orderList.get(counter).getProduct());
                    price.setText(Double.toString(
                            (orderList.get(counter).getPrice())));
                    quantity.setText(Integer.toString(
                            (orderList.get(counter).getQuantity())));
                    navigation.setText(Integer.toString(counter) 
                            + "/" + Integer.toString(
                                    orderList.size() - 1));
                    search.clear();
                }
            }

            if (searchID.equals("city")
                    && e.getCode() == KeyCode.ENTER) {
                boolean exists = true;
                for (int i = 0; i < orderList.size(); i++) {

                    if (orderList.get(i).getCity(
                    ).equalsIgnoreCase(search.getText())) {
                        orderID.setText(orderList.get(i).getOrderID());
                        name.setText(orderList.get(i).getName());
                        address.setText(orderList.get(i).getAddress());
                        city.setText(orderList.get(i).getCity());
                        product.setText(orderList.get(i).getProduct());
                        price.setText(Double.toString(
                                (orderList.get(i).getPrice())));
                        quantity.setText(Integer.toString(
                                (orderList.get(i).getQuantity())));
                        counter = i;
                        navigation.setText(Integer.toString(counter)
                                + "/" + Integer.toString(
                                        orderList.size() - 1));
                        exists = true;
                        search.clear();
                        orderID.requestFocus();
                        break;

                    } else {
                        exists = false;
                    }

                }
                if (!exists) {
                    Alert dlgerror = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = dlgerror.getDialogPane();
                    dlgerror.initStyle(StageStyle.TRANSPARENT);
                    dialogPane.getStylesheets().add(
                            "dataproject/dataCss.css");
                    dlgerror.setTitle("Error");
                    dlgerror.setHeaderText("Error");
                    dlgerror.setContentText("Entry Not Found!");
                    dlgerror.show();

                    orderID.setText(orderList.get(counter).getOrderID());
                    name.setText(orderList.get(counter).getName());
                    address.setText(orderList.get(counter).getAddress());
                    city.setText(orderList.get(counter).getCity());
                    product.setText(orderList.get(counter).getProduct());
                    price.setText(Double.toString(
                            (orderList.get(counter).getPrice())));
                    quantity.setText(Integer.toString(
                            (orderList.get(counter).getQuantity())));
                    navigation.setText(Integer.toString(counter)
                            + "/" + Integer.toString(
                                    orderList.size() - 1));
                    search.clear();
                }
            }

            if (searchID.equals("product")
                    && e.getCode() == KeyCode.ENTER) {
                boolean exists = true;
                for (int i = 0; i < orderList.size(); i++) {

                    if (orderList.get(i).getProduct(
                    ).equalsIgnoreCase(search.getText())) {
                        orderID.setText(orderList.get(i).getOrderID());
                        name.setText(orderList.get(i).getName());
                        address.setText(orderList.get(i).getAddress());
                        city.setText(orderList.get(i).getCity());
                        product.setText(orderList.get(i).getProduct());
                        price.setText(Double.toString(
                                (orderList.get(i).getPrice())));
                        quantity.setText(Integer.toString(
                                (orderList.get(i).getQuantity())));
                        counter = i;
                        navigation.setText(Integer.toString(counter)
                                + "/" + Integer.toString(
                                        orderList.size() - 1));
                        exists = true;
                        search.clear();
                        orderID.requestFocus();
                        break;
                    } else {
                        exists = false;
                    }

                }
                if (!exists) {
                    Alert dlgerror = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = dlgerror.getDialogPane();
                    dlgerror.initStyle(StageStyle.TRANSPARENT);
                    dialogPane.getStylesheets().add(
                            "dataproject/dataCss.css");
                    dlgerror.setTitle("Error");
                    dlgerror.setHeaderText("Error");
                    dlgerror.setContentText("Entry Not Found!");
                    dlgerror.show();

                    orderID.setText(orderList.get(counter).getOrderID());
                    name.setText(orderList.get(counter).getName());
                    address.setText(orderList.get(counter).getAddress());
                    city.setText(orderList.get(counter).getCity());
                    product.setText(orderList.get(counter).getProduct());
                    price.setText(Double.toString(
                            (orderList.get(counter).getPrice())));
                    quantity.setText(Integer.toString(
                            (orderList.get(counter).getQuantity())));
                    navigation.setText(Integer.toString(counter)
                            + "/" + Integer.toString(
                                    orderList.size() - 1));
                    search.clear();
                }
            }

            if (searchID.equals("price") 
                    && e.getCode() == KeyCode.ENTER) {
                boolean exists = true;
                for (int i = 0; i < orderList.size(); i++) {
                   
                    if(isDouble(search.getText())){
                    if (orderList.get(i).getPrice() 
                            == Double.parseDouble(search.getText()) 
                            ) {
                        orderID.setText(orderList.get(i).getOrderID());
                        name.setText(orderList.get(i).getName());
                        address.setText(orderList.get(i).getAddress());
                        city.setText(orderList.get(i).getCity());
                        product.setText(orderList.get(i).getProduct());
                        price.setText(Double.toString(
                                (orderList.get(i).getPrice())));
                        quantity.setText(Integer.toString(
                                (orderList.get(i).getQuantity())));
                        counter = i;
                        navigation.setText(Integer.toString(counter)
                                + "/" + Integer.toString(
                                        orderList.size() - 1));
                        exists = true;
                        search.clear();
                        orderID.requestFocus();
                        break;
                    } else {
                     
                        exists = false;
                    }
                    }else{
                        exists = false;
                    }

                }
                if (!exists) {
                    Alert dlgerror = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = dlgerror.getDialogPane();
                    dlgerror.initStyle(StageStyle.TRANSPARENT);
                    dialogPane.getStylesheets().add(
                            "dataproject/dataCss.css");
                    dlgerror.setTitle("Error");
                    dlgerror.setHeaderText("Error");
                    dlgerror.setContentText("Entry Not Found!");
                    dlgerror.show();

                    orderID.setText(orderList.get(counter).getOrderID());
                    name.setText(orderList.get(counter).getName());
                    address.setText(orderList.get(counter).getAddress());
                    city.setText(orderList.get(counter).getCity());
                    product.setText(orderList.get(counter).getProduct());
                    price.setText(Double.toString(
                            (orderList.get(counter).getPrice())));
                    quantity.setText(Integer.toString(
                            (orderList.get(counter).getQuantity())));
                    navigation.setText(Integer.toString(counter)
                            + "/" + Integer.toString(
                                    orderList.size() - 1));
                    search.clear();
                }
            }

            if (searchID.equals("quantity") 
                    && e.getCode() == KeyCode.ENTER) {
                boolean exists = true;
                for (int i = 0; i < orderList.size(); i++) {
                   
                    if(isInt(search.getText())){
                    if (orderList.get(i).getQuantity()
                            == Integer.parseInt(search.getText())) {
                        orderID.setText(orderList.get(i).getOrderID());
                        name.setText(orderList.get(i).getName());
                        address.setText(orderList.get(i).getAddress());
                        city.setText(orderList.get(i).getCity());
                        product.setText(orderList.get(i).getProduct());
                        price.setText(Double.toString(
                                (orderList.get(i).getPrice())));
                        quantity.setText(Integer.toString(
                                (orderList.get(i).getQuantity())));
                        counter = i;
                        navigation.setText(Integer.toString(counter)
                                + "/" + Integer.toString(
                                        orderList.size() - 1));
                        search.clear();
                        orderID.requestFocus();
                        exists = true;
                        break;
                    } else {
                        exists = false;
                        }
                    }else{
                        exists = false;
                    }

                }
                if (!exists) {
                    Alert dlgerror = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = dlgerror.getDialogPane();
                    dlgerror.initStyle(StageStyle.TRANSPARENT);
                    dialogPane.getStylesheets().add(
                            "dataproject/dataCss.css");
                    dlgerror.setTitle("Error");
                    dlgerror.setHeaderText("Error");
                    dlgerror.setContentText("Entry Not Found!");
                    dlgerror.show();

                    orderID.setText(orderList.get(counter).getOrderID());
                    name.setText(orderList.get(counter).getName());
                    address.setText(orderList.get(counter).getAddress());
                    city.setText(orderList.get(counter).getCity());
                    product.setText(orderList.get(counter).getProduct());
                    price.setText(Double.toString(
                            (orderList.get(counter).getPrice())));
                    quantity.setText(Integer.toString(
                            (orderList.get(counter).getQuantity())));
                    navigation.setText(Integer.toString(counter)
                            + "/" + Integer.toString(
                                    orderList.size() - 1));
                    search.clear();
                }
            }

        }

    }

    public boolean isInt(String a) {
        //used in input validation, ensures integer only 
        //using regex
        String match = "-?[0-9]+";
        return a.matches(match);
    }

    public boolean isDouble(String a) {
        //used in input validation, ensures double only
        //searchs for 1+ nums, then decimal, then 1+ nums
        //using regex
        String match = "[0-9]+.?[0-9]+";
        return a.matches(match);
    }

    public boolean isName(String a) {
        //used in input validation, ensures String &
        //one space only followed by more string
        //using regex
        String match = "[A-Za-z]+ ?[A-Za-z]+";
        return a.matches(match);
    }

}
