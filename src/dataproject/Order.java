package dataproject;

/*Name: Mohnish Gauni
 *ID: 991519320
 *Assignment: PROJECT
 *Date: 4/7/2019
 */

import java.io.*;
import java.util.*;
public class Order {
    private String orderID;
    private String name;
    private String address;
    private String city;
    private String product;
    private double price;
    private int quantity;
    
    public Order(String a){
        this.orderID = a;
    }
    public Order(){
        
    }
    
    public String getOrderID(){
        return this.orderID;
    }
    
    public void setOrderID(String a){
        this.orderID = a;
    }
    
    public void setName(String a){
        this.name = a;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setAddress(String a){
        this.address = a;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public void setCity(String a){
        this.city = a;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public void setProduct(String a){
        this.product = a;
    }
    
    public String getProduct(){
        return this.product;
    }
    
    public void setPrice(double a){
        this.price = a;
    }
    
    public double getPrice(){
        return this.price;
    }
    
    public void setQuantity(int a){
        this.quantity = a;
    }
    
    public int getQuantity(){
        return this.quantity;
    }
    
    
    //reads file, creates object, assigns values and pushes
    //onto list
    public LinkedList<Order> FillList(File a,
            LinkedList<Order> b) throws IOException{
        String d ="";
       
        
        FileReader fr = new FileReader(a);
        BufferedReader br = new BufferedReader(fr);
        
        d = br.readLine();
        while(d != null){
            StringTokenizer one = new StringTokenizer(d,",");
           
            Order two = new Order(one.nextToken());
            two.setName(one.nextToken());
            two.setAddress(one.nextToken());
            two.setCity(one.nextToken());
            two.setProduct(one.nextToken());       
            two.setPrice(Double.parseDouble(one.nextToken()));
            two.setQuantity(Integer.parseInt(one.nextToken()));
            b.add(two); 
           
            d = br.readLine();
        }
        
        fr.close();
        br.close();
            
        return b;
    }
    
    //grabs file and list and writes onto file based on list
    public void saveList(File a, LinkedList<Order> b)throws IOException{
        
        FileWriter fw = new FileWriter(a);
        BufferedWriter bw = new BufferedWriter(fw);
        Iterator<Order> iterator = b.iterator();
        int i = 0;
        while(iterator.hasNext()){
            i++;
            Order one = iterator.next();
            if(i == 1){
            bw.write(one.getOrderID() + "," + one.getName() + "," +
                    one.getAddress() + "," + one.getCity() + ","
                    + one.getProduct() + "," + one.getPrice() + "," + 
                    one.getQuantity());
            }else{
                 bw.write("\n" + one.getOrderID() + ","
                         + one.getName() + "," +
                    one.getAddress() + "," + one.getCity() + ","
                    + one.getProduct() + "," + one.getPrice() + "," + 
                    one.getQuantity()); 
            }
        }
        
        bw.close();
        fw.close();   
        
        
        
    }
    
}
