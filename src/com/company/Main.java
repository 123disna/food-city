package com.company;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.JDBC4PreparedStatement;
import com.mysql.jdbc.PreparedStatement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        	new load();
        	new login();
             //new manageSeller();
             //new updateAdmin();
    }
}
class load extends JFrame{
    JProgressBar bar=new JProgressBar();
    JLabel load_label,read;
    load(){

        load_label=new JLabel();
        load_label.setIcon(new ImageIcon("C:\\Users\\Trio\\IdeaProjects\\foodCity\\first.png"));
        add(load_label);

        bar.setValue(0);
        bar.setStringPainted(true);
        bar.setBackground(Color.white);
        bar.setForeground(Color.YELLOW);
        bar.setBounds(0,650,1200,30);
        load_label.add(bar);

        read=new JLabel();
        read.setBounds(600,500,150,40);
        read.setFont(new Font("Arial",Font.ITALIC,40));
        read.setForeground(Color.WHITE);
        load_label.add(read);


        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        fill();
        dispose();

    }
    public void fill(){
        int count=0;
        while (count<=100) {
            bar.setValue(count);
            read.setText(String.valueOf(count)+"%");
            try{
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.getMessage();
            }
            count++;
        }
    }

}
class login extends JFrame{
    JComboBox role;
    JTextField user;
    JPasswordField pass;
    JLabel back;
    JButton login_btn,clear_btn;
    Border brd=BorderFactory.createLineBorder(Color.red,3);
    String roless[]={" ","Admin","Seller"};
    login(){
        back=new JLabel();
        back.setIcon(new ImageIcon("C:\\Users\\Trio\\IdeaProjects\\foodCity\\foodLogin.png"));
        add(back);

        role=new JComboBox(roless);
        role.setBounds(850,290,300,35);
        role.setBorder(brd);
        role.setForeground(Color.BLACK);
        role.setFont(new Font("Arial",NORMAL,20));
        back.add(role);

        user=new JTextField();
        user.setBounds(850,370,300,35);
        user.setFont(new Font("Arial",NORMAL,20));
        user.setBorder(brd);
        user.setForeground(Color.BLACK);
        back.add(user);

        pass=new JPasswordField();
        pass.setBounds(850,460,300,35);
        pass.setFont(new Font("Arial",NORMAL,20));
        pass.setForeground(Color.BLACK);
        pass.setBorder(brd);
        back.add(pass);

        login_btn=new JButton("Login");
        login_btn.setBounds(650,550,120,40);
        login_btn.setBackground(Color.RED);
        login_btn.setForeground(Color.WHITE);
        login_btn.setFont(new Font("Arial",Font.BOLD,25));
        back.add(login_btn);

        clear_btn=new JButton("Clear");
        clear_btn.setBounds(870,550,120,40);
        clear_btn.setBackground(Color.RED);
        clear_btn.setForeground(Color.WHITE);
        clear_btn.setFont(new Font("Arial",Font.BOLD,25));
        back.add(clear_btn);

        login_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enter();
            }
        });
        clear_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                role.setSelectedItem(null);
                user.setText(null);
                pass.setText(null);
            }
        });

        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    void enter() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
                String qu = "select * from login where roles=? and username=? and password=?";
                PreparedStatement prp = (PreparedStatement) con.prepareStatement(qu);
                String rol = (String) role.getSelectedItem();
                prp.setString(1, rol);
                prp.setString(2, user.getText());
                prp.setString(3, pass.getText());
                ResultSet rs = prp.executeQuery();
                if (rs.next() == true) {
                    if (rs.getString("roles").equals("Admin")) {
                        dispose();
                        new manageProduct();
                    } else if (rs.getString("roles").equals("Seller")) {
                        dispose();
                        new billing();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!");
                }
                con.close();
            } catch (ClassNotFoundException | SQLException en) {
                System.err.println("Got an exception !");
                System.err.println(en.getMessage());
            }
    }

}
class manageProduct extends JFrame {
    JLabel back, id, name, cate, price, quantity, products,seller,category,logOut;
    JTextField text_id, text_name, text_price, text_quantity;
    JComboBox text_cate;
    JButton add_btn, edit, clear, delete;
    Font font = new Font("arial", Font.BOLD, 25);
    Font text_font = new Font("arial", NORMAL, 25);
    String categories[] = {" ", "Bakery", "Beverage", "Deli", "Meat", "Seafood"};
    Border brd = BorderFactory.createLineBorder(Color.BLACK, 3);

    DefaultTableModel model = new DefaultTableModel();
    JTable product_table = new JTable(model);
    JScrollPane jsp = new JScrollPane(product_table);
    int q;
    manageProduct() {
        back = new JLabel();
        back.setIcon(new ImageIcon("C:\\Users\\Trio\\IdeaProjects\\foodCity\\manageProducts.png"));
        add(back);

        seller=new JLabel("Seller");
        seller.setBounds(5,200,100,50);
        seller.setForeground(Color.WHITE);
        seller.setFont(font);
        back.add(seller);

        category=new JLabel("Category");
        category.setBounds(5,250,150,50);
        category.setForeground(Color.WHITE);
        category.setFont(font);
        back.add(category);

        logOut=new JLabel("Logout");
        logOut.setBounds(25,550,150,50);
        logOut.setForeground(Color.WHITE);
        logOut.setFont(font);
        back.add(logOut);

        id = new JLabel("PRODID");
        id.setBounds(170, 90, 100, 40);
        id.setFont(font);
        id.setForeground(Color.RED);
        back.add(id);

        text_id = new JTextField();
        text_id.setBounds(340, 90, 250, 30);
        text_id.setForeground(Color.BLACK);
        text_id.setFont(text_font);
        text_id.setBorder(brd);
        back.add(text_id);

        name = new JLabel("NAME");
        name.setBounds(170, 130, 100, 40);
        name.setFont(font);
        name.setForeground(Color.RED);
        back.add(name);

        text_name = new JTextField();
        text_name.setBounds(340, 130, 250, 30);
        text_name.setForeground(Color.BLACK);
        text_name.setFont(text_font);
        text_name.setBorder(brd);
        back.add(text_name);

        cate = new JLabel("CATEGORY");
        cate.setBounds(170, 170, 150, 40);
        cate.setFont(font);
        cate.setForeground(Color.RED);
        back.add(cate);

        text_cate = new JComboBox(categories);
        text_cate.setBounds(340, 170, 250, 35);
        text_cate.setForeground(Color.BLACK);
        text_cate.setBackground(Color.WHITE);
        text_cate.setBorder(brd);
        text_cate.setFont(text_font);
        back.add(text_cate);

        quantity = new JLabel("QUANTITY");
        quantity.setBounds(760, 90, 150, 40);
        quantity.setFont(font);
        quantity.setForeground(Color.RED);
        back.add(quantity);

        text_quantity = new JTextField();
        text_quantity.setBounds(920, 90, 250, 30);
        text_quantity.setFont(text_font);
        text_quantity.setBorder(brd);
        text_quantity.setForeground(Color.black);
        back.add(text_quantity);

        price = new JLabel("PRICE");
        price.setBounds(760, 130, 100, 40);
        price.setFont(font);
        price.setForeground(Color.RED);
        back.add(price);

        text_price = new JTextField();
        text_price.setBounds(920, 130, 250, 30);
        text_price.setFont(text_font);
        text_price.setBorder(brd);
        text_price.setForeground(Color.black);
        back.add(text_price);

        add_btn = new JButton("Add");
        add_btn.setBounds(220, 240, 150, 40);
        add_btn.setBorder(brd);
        add_btn.setForeground(Color.WHITE);
        add_btn.setBackground(Color.RED);
        add_btn.setFont(font);
        back.add(add_btn);

        edit = new JButton("Edit");
        edit.setBounds(470, 240, 150, 40);
        edit.setBorder(brd);
        edit.setForeground(Color.WHITE);
        edit.setBackground(Color.RED);
        edit.setFont(font);
        back.add(edit);

        delete = new JButton("Delete");
        delete.setBounds(720, 240, 150, 40);
        delete.setBorder(brd);
        delete.setForeground(Color.WHITE);
        delete.setBackground(Color.RED);
        delete.setFont(font);
        back.add(delete);

        clear = new JButton("Clear");
        clear.setBounds(970, 240, 150, 40);
        clear.setBorder(brd);
        clear.setForeground(Color.WHITE);
        clear.setBackground(Color.RED);
        clear.setFont(font);
        back.add(clear);

        products = new JLabel("PRODUCTS LIST");
        products.setBounds(550, 290, 250, 40);
        products.setForeground(Color.RED);
        products.setFont(font);
        back.add(products);

        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterData();
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editData();
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearData();
            }
        });

        seller.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new manageSeller();
            }
        });
        category.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new manageCategory();
            }
        });
        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        //create table
        model.addColumn("PRODID");
        model.addColumn("NAME");
        model.addColumn("CATEGORY");
        model.addColumn("QUANTITY(kg/l/piece)");
        model.addColumn("PRICE(Rs)");
        jsp.setBounds(170, 320, 1000, 320);
        jsp.setVisible(true);
        back.add(jsp);
        showProducts();

        product_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model= (DefaultTableModel) product_table.getModel();
                int myIndex=product_table.getSelectedRow();
                text_id.setText(model.getValueAt(myIndex,0).toString());
                text_name.setText(model.getValueAt(myIndex,1).toString());
                text_cate.setSelectedItem(model.getValueAt(myIndex,2).toString());
                text_quantity.setText(model.getValueAt(myIndex,3).toString());
                text_price.setText(model.getValueAt(myIndex,4).toString());

            }
        });

        setSize(1200, 700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    void enterData() {
        if (text_id.getText().isEmpty() || text_name.getText().isEmpty() || text_cate.getSelectedItem().toString().isEmpty() || text_quantity.getText().isEmpty() || text_price.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter all of data");
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
                String query = "insert into products(id,name,category,quantity,price)" + "Values(?,?,?,?,?)";
                PreparedStatement prp = (PreparedStatement) con.prepareStatement(query);
                prp.setInt(1, Integer.parseInt(text_id.getText()));
                prp.setString(2, text_name.getText());
                prp.setString(3, (String) text_cate.getSelectedItem());
                prp.setFloat(4, Float.parseFloat(text_quantity.getText()));
                prp.setDouble(5, Double.parseDouble(text_price.getText()));
                prp.execute();

                JOptionPane.showMessageDialog(null, "saved");
                showProducts();
                con.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void editData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            String query = "UPDATE products SET name=?,category=?,quantity=?,price=? where id=?";
            PreparedStatement prp = (PreparedStatement) con.prepareStatement(query);
            prp.setString(1, text_name.getText());
            prp.setString(2, (String) text_cate.getSelectedItem());
            prp.setFloat(3, Float.parseFloat(text_quantity.getText()));
            prp.setDouble(4, Double.parseDouble(text_price.getText()));
            prp.setInt(5, Integer.parseInt(text_id.getText()));
            prp.executeUpdate();
            JOptionPane.showMessageDialog(null, "updated");
            showProducts();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void deleteData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            String query = "DELETE from products where id=?";
            PreparedStatement prp = (PreparedStatement) con.prepareStatement(query);
            prp.setInt(1, Integer.parseInt(text_id.getText()));
            prp.execute();

            JOptionPane.showMessageDialog(null, "Deleted");
            showProducts();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void clearData() {
        text_id.setText(null);
        text_price.setText(null);
        text_quantity.setText(null);
        text_cate.setSelectedItem(null);
        text_name.setText(null);
    }
    void showProducts(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            Statement stm;
            stm = con.createStatement();
            String query="SELECT * from products";
            ResultSet rs=stm.executeQuery(query);
            ResultSetMetaData proData=rs.getMetaData();
            q=proData.getColumnCount();
            model.setRowCount(0);
            while (rs.next()){
                Vector columnData=new Vector();
                for(int i=1;i<=q;i++){
                    columnData.add(rs.getInt("id"));
                    columnData.add(rs.getString("name"));
                    columnData.add(rs.getString("category"));
                    columnData.add(rs.getFloat("quantity"));
                    columnData.add(rs.getFloat("price"));
                }
                model.addRow(columnData);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
}
class manageSeller extends JFrame{
    JLabel back,id,name,password,gender,sellers,updateAd;
    JTextField text_id,text_name;
    JPasswordField text_password;
    JComboBox gender_box;
    JButton add_btn,edit,clear,delete;
    Font font=new Font("arial",Font.BOLD,25);
    Font text_font=new Font("arial",NORMAL,25);
    String sex[]={" ","Female","Male","Intersex"};
    Border brd=BorderFactory.createLineBorder(Color.BLACK,3);

    DefaultTableModel model=new DefaultTableModel();
    JTable seller_table=new JTable(model);
    JScrollPane jsp=new JScrollPane(seller_table);
    int q;
    manageSeller(){
        back=new JLabel();
        back.setIcon(new ImageIcon("C:\\Users\\Trio\\IdeaProjects\\foodCity\\manageSeller.png"));
        add(back);

        updateAd=new JLabel("NewAdmin");
        updateAd.setBounds(5,250,200,50);
        updateAd.setForeground(Color.YELLOW);
        updateAd.setFont(font);
        back.add(updateAd);

        id=new JLabel("SELLERID");
        id.setBounds(170,90,150,40);
        id.setFont(font);
        id.setForeground(Color.RED);
        back.add(id);

        text_id=new JTextField();
        text_id.setBounds(340,90,250,30);
        text_id.setForeground(Color.BLACK);
        text_id.setFont(text_font);
        text_id.setBorder(brd);
        back.add(text_id);

        name=new JLabel("NAME");
        name.setBounds(170,130,100,40);
        name.setFont(font);
        name.setForeground(Color.RED);
        back.add(name);

        text_name=new JTextField();
        text_name.setBounds(340,130,250,30);
        text_name.setForeground(Color.BLACK);
        text_name.setFont(text_font);
        text_name.setBorder(brd);
        back.add(text_name);

        password=new JLabel("PASSWORD");
        password.setBounds(760,90,150,40);
        password.setFont(font);
        password.setForeground(Color.RED);
        back.add(password);

        text_password=new JPasswordField();
        text_password.setBounds(920,90,250,30);
        text_password.setFont(text_font);
        text_password.setBorder(brd);
        text_password.setForeground(Color.black);
        back.add(text_password);

        gender=new JLabel("GENDER");
        gender.setBounds(760,130,150,40);
        gender.setFont(font);
        gender.setForeground(Color.RED);
        back.add(gender);

        gender_box=new JComboBox(sex);
        gender_box.setBounds(920,130,250,35);
        gender_box.setForeground(Color.BLACK);
        gender_box.setBackground(Color.WHITE);
        gender_box.setBorder(brd);
        gender_box.setFont(text_font);
        back.add(gender_box);

        add_btn=new JButton("Add");
        add_btn.setBounds(220,200,150,40);
        add_btn.setBorder(brd);
        add_btn.setForeground(Color.WHITE);
        add_btn.setBackground(Color.RED);
        add_btn.setFont(font);
        back.add(add_btn);

        edit=new JButton("Edit");
        edit.setBounds(470,200,150,40);
        edit.setBorder(brd);
        edit.setForeground(Color.WHITE);
        edit.setBackground(Color.RED);
        edit.setFont(font);
        back.add(edit);

        delete=new JButton("Delete");
        delete.setBounds(720,200,150,40);
        delete.setBorder(brd);
        delete.setForeground(Color.WHITE);
        delete.setBackground(Color.RED);
        delete.setFont(font);
        back.add(delete);

        clear=new JButton("Clear");
        clear.setBounds(970,200,150,40);
        clear.setBorder(brd);
        clear.setForeground(Color.WHITE);
        clear.setBackground(Color.RED);
        clear.setFont(font);
        back.add(clear);

        sellers=new JLabel("SELLERS LIST");
        sellers.setBounds(550,250,250,40);
        sellers.setForeground(Color.RED);
        sellers.setFont(font);
        back.add(sellers);

        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterData();
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editData();
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearData();
            }
        });
        updateAd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new updateAdmin();
            }
        });

        //create table
        model.addColumn("Seller Id");
        model.addColumn("Seller Name");
        model.addColumn("Password");
        model.addColumn("Gender");
        jsp.setBounds(170,290,1000,370);
        jsp.setVisible(true);
        back.add(jsp);
        showSeller();

        seller_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model= (DefaultTableModel) seller_table.getModel();
                int myIndex=seller_table.getSelectedRow();
                text_id.setText(model.getValueAt(myIndex,0).toString());
                text_name.setText(model.getValueAt(myIndex,1).toString());
                text_password.setText(model.getValueAt(myIndex,2).toString());
                gender_box.setSelectedItem(model.getValueAt(myIndex,3).toString());

            }
        });

        setSize(1200,700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void enterData(){
        if(text_id.getText().isEmpty()||text_name.getText().isEmpty()||text_password.getText().isEmpty()||gender_box.getSelectedItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter all of data");
        }
        else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
                String query = "insert into manageseller(id,name,password,gender)" + "Values(?,?,?,?)";
                PreparedStatement prp = (PreparedStatement) con.prepareStatement(query);
                prp.setInt(1, Integer.parseInt(text_id.getText()));
                prp.setString(2, text_name.getText());
                prp.setString(3, text_password.getText());
                prp.setString(4, (String) gender_box.getSelectedItem());
                prp.execute();

                JOptionPane.showMessageDialog(null, "saved");
                showSeller();
                con.close();
                showSeller();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
    void editData(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            String query="UPDATE manageseller SET name=?,password=?,gender=? where id=?";
            PreparedStatement prp= (PreparedStatement) con.prepareStatement(query);
            prp.setString(1,text_name.getText());
            prp.setString(2,text_password.getText());
            prp.setString(3, (String) gender_box.getSelectedItem());
            prp.setInt(4, Integer.parseInt(text_id.getText()));
            prp.executeUpdate();
            JOptionPane.showMessageDialog(null,"updated");
            showSeller();
            con.close();
        }catch (Exception e){
            e.getMessage();
        }
    }
    void deleteData(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            String query="DELETE from manageseller where id=?";
            PreparedStatement prp= (PreparedStatement) con.prepareStatement(query);
            prp.setInt(1, Integer.parseInt(text_id.getText()));
            prp.execute();

            JOptionPane.showMessageDialog(null,"Deleted");
            showSeller();
            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }
    void clearData(){
        text_id.setText(null);
        text_name.setText(null);
        text_password.setText(null);
        gender_box.setSelectedItem(null);
    }
    void showSeller(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            Statement stm;
            stm = con.createStatement();
            String query = "SELECT * from manageseller";
            ResultSet rs=stm.executeQuery(query);
            ResultSetMetaData selData=rs.getMetaData();
            q=selData.getColumnCount();
            model.setRowCount(0);
            while (rs.next()){
                Vector columnData=new Vector();
                for(int i=1;i<=q;i++){
                    columnData.add(rs.getInt("id"));
                    columnData.add(rs.getString("name"));
                    columnData.add(rs.getString("password"));
                    columnData.add(rs.getString("gender"));
                }
                model.addRow(columnData);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
}
class manageCategory extends JFrame{
    JLabel back,id,name,description,category,product,seller,logOut;
    JTextField text_id,text_name,text_des;
    JButton add_btn,edit,clear,delete;
    Font font=new Font("arial",Font.BOLD,25);
    Font text_font=new Font("arial",NORMAL,25);
    Border brd=BorderFactory.createLineBorder(Color.BLACK,3);

    DefaultTableModel model=new DefaultTableModel();
    JTable cat_table=new JTable(model);
    JScrollPane jsp=new JScrollPane(cat_table);
    int q;
    manageCategory(){
        back=new JLabel();
        back.setIcon(new ImageIcon("C:\\Users\\Trio\\IdeaProjects\\foodCity\\manageCate.png"));
        add(back);

        seller=new JLabel("Seller");
        seller.setBounds(5,200,100,50);
        seller.setForeground(Color.WHITE);
        seller.setFont(font);
        back.add(seller);

        product=new JLabel("Products");
        product.setBounds(5,250,150,50);
        product.setForeground(Color.WHITE);
        product.setFont(font);
        back.add(product);

        logOut=new JLabel("Logout");
        logOut.setBounds(25,550,150,50);
        logOut.setForeground(Color.WHITE);
        logOut.setFont(font);
        back.add(logOut);

        id=new JLabel("CATID");
        id.setBounds(170,90,150,40);
        id.setFont(font);
        id.setForeground(Color.RED);
        back.add(id);

        text_id=new JTextField();
        text_id.setBounds(280,90,250,30);
        text_id.setForeground(Color.BLACK);
        text_id.setFont(text_font);
        text_id.setBorder(brd);
        back.add(text_id);

        name=new JLabel("NAME");
        name.setBounds(760,90,150,40);
        name.setFont(font);
        name.setForeground(Color.RED);
        back.add(name);

        text_name=new JTextField();
        text_name.setBounds(860,90,250,30);
        text_name.setForeground(Color.BLACK);
        text_name.setFont(text_font);
        text_name.setBorder(brd);
        back.add(text_name);

        description=new JLabel("DESCRIPTION");
        description.setBounds(430,150,180,40);
        description.setFont(font);
        description.setForeground(Color.RED);
        back.add(description);

        text_des=new JTextField();
        text_des.setBounds(610,150,250,30);
        text_des.setForeground(Color.BLACK);
        text_des.setFont(text_font);
        text_des.setBorder(brd);
        back.add(text_des);

        add_btn=new JButton("Add");
        add_btn.setBounds(220,200,150,40);
        add_btn.setBorder(brd);
        add_btn.setForeground(Color.WHITE);
        add_btn.setBackground(Color.RED);
        add_btn.setFont(font);
        back.add(add_btn);

        edit=new JButton("Edit");
        edit.setBounds(470,200,150,40);
        edit.setBorder(brd);
        edit.setForeground(Color.WHITE);
        edit.setBackground(Color.RED);
        edit.setFont(font);
        back.add(edit);

        delete=new JButton("Delete");
        delete.setBounds(720,200,150,40);
        delete.setBorder(brd);
        delete.setForeground(Color.WHITE);
        delete.setBackground(Color.RED);
        delete.setFont(font);
        back.add(delete);

        clear=new JButton("Clear");
        clear.setBounds(970,200,150,40);
        clear.setBorder(brd);
        clear.setForeground(Color.WHITE);
        clear.setBackground(Color.RED);
        clear.setFont(font);
        back.add(clear);

        category=new JLabel("CATEGORIES LIST");
        category.setBounds(550,250,250,40);
        category.setForeground(Color.RED);
        category.setFont(font);
        back.add(category);

        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterData();
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editData();
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearData();
            }
        });

        //create category table
        model.addColumn("Id");
        model.addColumn("Category Name");
        model.addColumn("Description");
        jsp.setBounds(170,290,1000,350);
        jsp.setVisible(true);
        back.add(jsp);
        showCategory();

        cat_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model= (DefaultTableModel) cat_table.getModel();
                int myIndex=cat_table.getSelectedRow();
                text_id.setText(model.getValueAt(myIndex,0).toString());
                text_name.setText(model.getValueAt(myIndex,1).toString());
                text_des.setText(model.getValueAt(myIndex,2).toString());
            }
        });

        seller.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new manageSeller();
            }
        });
        product.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new manageProduct();
            }
        });
        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });

        setSize(1200,700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }



    void enterData(){
        if(text_id.getText().isEmpty()||text_name.getText().isEmpty()||text_des.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter all of data");
        }
        else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
                String query = "insert into managecate(id,name,description)" + "Values(?,?,?)";
                PreparedStatement prp = (PreparedStatement) con.prepareStatement(query);
                prp.setInt(1, Integer.parseInt(text_id.getText()));
                prp.setString(2, text_name.getText());
                prp.setString(3, text_des.getText());
                prp.execute();

                JOptionPane.showMessageDialog(null, "saved");
                con.close();
                showCategory();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
    void editData(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            String query="UPDATE managecate SET name=?,description=? where id=?";
            PreparedStatement prp= (PreparedStatement) con.prepareStatement(query);
            prp.setString(1,text_name.getText());
            prp.setString(2,text_des.getText());
            prp.setInt(3, Integer.parseInt(text_id.getText()));
            prp.executeUpdate();
            JOptionPane.showMessageDialog(null,"updated");
            showCategory();
            con.close();
        }catch (Exception e){
            e.getMessage();
        }
    }
    void deleteData(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            String query="DELETE from managecate where id=?";
            PreparedStatement prp= (PreparedStatement) con.prepareStatement(query);
            prp.setInt(1, Integer.parseInt(text_id.getText()));
            prp.execute();

            JOptionPane.showMessageDialog(null,"Deleted");
            showCategory();
            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }
    void clearData(){
        text_id.setText(null);
        text_name.setText(null);
        text_des.setText(null);
    }
    void showCategory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            Statement stm;
            stm = con.createStatement();
            String query = "SELECT * from managecate";
            ResultSet rs = stm.executeQuery(query);
            ResultSetMetaData catData = rs.getMetaData();
            q = catData.getColumnCount();
            model.setRowCount(0);
            while (rs.next()) {
                Vector columnData = new Vector();
                for (int i = 1; i <= q; i++) {
                    columnData.add(rs.getInt("id"));
                    columnData.add(rs.getString("name"));
                    columnData.add(rs.getString("description"));
                }
                model.addRow(columnData);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
class updateAdmin extends JFrame{
    JLabel back,id,name;
    JTextField text_name;
    JPasswordField text_id;
    JButton update,clear;
    Font font=new Font("arial",Font.BOLD,30);
    Font f=new Font("arial",NORMAL,25);
    Border brd=BorderFactory.createLineBorder(Color.BLACK,3);
    Border brd1=BorderFactory.createLineBorder(Color.white,3);
    updateAdmin(){
        back=new JLabel();
        back.setIcon(new ImageIcon("C:\\Users\\Trio\\IdeaProjects\\foodCity\\update.png"));
        add(back);

        id=new JLabel("PASSWORD");
        id.setBounds(150,200,200,50);
        id.setFont(font);
        id.setForeground(Color.BLACK);
        back.add(id);

        text_id=new JPasswordField();
        text_id.setBounds(340,200,250,40);
        text_id.setFont(f);
        text_id.setBorder(brd);
        text_id.setBackground(Color.RED);
        text_id.setForeground(Color.white);
        back.add(text_id);

        name=new JLabel("NAME");
        name.setBounds(150,270,200,50);
        name.setFont(font);
        name.setForeground(Color.BLACK);
        back.add(name);

        text_name=new JTextField();
        text_name.setBounds(340,270,250,40);
        text_name.setFont(f);
        text_name.setBorder(brd);
        text_name.setBackground(Color.RED);
        text_name.setForeground(Color.white);
        back.add(text_name);

        update=new JButton("Update");
        update.setBorder(brd1);
        update.setBounds(200,400,150,45);
        update.setForeground(Color.BLACK);
        update.setFont(font);
        update.setBackground(Color.red);
        back.add(update);

        clear=new JButton("Clear");
        clear.setBorder(brd1);
        clear.setBounds(400,400,150,45);
        clear.setForeground(Color.BLACK);
        clear.setFont(font);
        clear.setBackground(Color.red);
        back.add(clear);

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
                    String query="UPDATE login SET username=?,password=? where roles=?";
                    PreparedStatement prp= (PreparedStatement) con.prepareStatement(query);
                    prp.setString(1,text_name.getText());
                    prp.setString(2,text_id.getText());
                    prp.setString(3,"Admin");
                    prp.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Updated");
                    con.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text_id.setText(null);
                text_name.setText(null);
            }
        });

        setSize(700,700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
class billing extends JFrame{
    JLabel back,name,quantity,list,totalBill;
    JTextField text_name,text_quantity;
    JButton addBill,clear_btn,print_btn;
    Font font=new Font("arial",Font.BOLD,25);
    Font text_font=new Font("arial",NORMAL,25);
    Border brd=BorderFactory.createLineBorder(Color.BLACK,3);

    //product list
    DefaultTableModel model = new DefaultTableModel();
    JTable product_table = new JTable(model);
    JScrollPane jsp = new JScrollPane(product_table);

    JTextArea bill_label;
    int q;
    double price=0.0,onePr,totalPrice=0.0;
    billing(){
        back=new JLabel();
        back.setIcon(new ImageIcon("C:\\Users\\Trio\\IdeaProjects\\foodCity\\billing.png"));
        add(back);

        name = new JLabel("NAME");
        name.setBounds(170, 150, 150, 40);
        name.setFont(font);
        name.setForeground(Color.RED);
        back.add(name);

        text_name = new JTextField();
        text_name.setBounds(300, 150, 200, 30);
        text_name.setForeground(Color.BLACK);
        text_name.setFont(text_font);
        text_name.setBorder(brd);
        back.add(text_name);

        list=new JLabel("PRODUCTS LIST");
        list.setBounds(750,90,300,50);
        list.setForeground(Color.RED);
        list.setFont(new Font("Arial",Font.BOLD,30));
        back.add(list);

        quantity = new JLabel("QUANTITY");
        quantity.setBounds(170, 210, 150, 40);
        quantity.setFont(font);
        quantity.setForeground(Color.RED);
        back.add(quantity);

        text_quantity = new JTextField();
        text_quantity.setBounds(300, 210, 200, 30);
        text_quantity.setForeground(Color.BLACK);
        text_quantity.setFont(text_font);
        text_quantity.setBorder(brd);
        back.add(text_quantity);

        addBill= new JButton("Add To Bill");
        addBill.setBounds(170, 280, 170, 40);
        addBill.setBorder(brd);
        addBill.setForeground(Color.WHITE);
        addBill.setBackground(Color.RED);
        addBill.setFont(font);
        back.add(addBill);

        clear_btn=new JButton("Clear");
        clear_btn.setBounds(380,280,120,40);
        clear_btn.setBorder(brd);
        clear_btn.setForeground(Color.WHITE);
        clear_btn.setBackground(Color.RED);
        clear_btn.setFont(font);
        back.add(clear_btn);

        bill_label=new JTextArea();
        bill_label.setBounds(650,330,500,270);
        bill_label.setBorder(brd);
        bill_label.setFont(new Font("Arial",Font.BOLD,17));
        bill_label.setForeground(Color.BLACK);
        back.add(bill_label);

        totalBill=new JLabel();
        totalBill.setBounds(800,600,200,40);
        totalBill.setForeground(Color.red);
        totalBill.setFont(font);
        back.add(totalBill);

        print_btn=new JButton("Print");
        print_btn.setBounds(500,400,150,40);
        print_btn.setBackground(Color.RED);
        print_btn.setForeground(Color.white);
        print_btn.setFont(font);
        back.add(print_btn);

        //create table
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("CATEGORY");
        model.addColumn("QUANTITY");
        model.addColumn("PRICE(Rs)");
        jsp.setBounds(600, 150, 550, 150);
        jsp.setVisible(true);
        back.add(jsp);
        showProducts();

        product_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model= (DefaultTableModel) product_table.getModel();
                int myIndex=product_table.getSelectedRow();
                text_name.setText(model.getValueAt(myIndex,1).toString());
            }
        });
       addBill.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               txtPrint();
           }
       });
       clear_btn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               text_name.setText(null);
               text_quantity.setText(null);
           }
       });
       print_btn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   bill_label.print();
               }catch (Exception exc){
                   exc.getMessage();
               }
           }
       });
        setSize(1200,700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void showProducts(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/foodcity", "root", "");
            Statement stm;
            stm = con.createStatement();
            String query="SELECT * from products";
            ResultSet rs=stm.executeQuery(query);
            ResultSetMetaData proData=rs.getMetaData();
            q=proData.getColumnCount();
            model.setRowCount(0);
            while (rs.next()){
                Vector columnData=new Vector();
                for(int i=1;i<=q;i++){
                    columnData.add(rs.getInt("id"));
                    columnData.add(rs.getString("name"));
                    columnData.add(rs.getString("category"));
                    columnData.add(rs.getFloat("quantity"));
                    columnData.add(rs.getFloat("price"));
                }
                model.addRow(columnData);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
    int i=0;
    void txtPrint(){
        int count=Integer.parseInt(text_quantity.getText());
        onePr= Double.parseDouble((model.getValueAt(product_table.getSelectedRow(),4).toString()));
        price=count*onePr;

        if(text_quantity.getText().isEmpty() || text_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Missing requirements");
        }else {
            //totalPrice+=price;
            i++;
            if (i == 1) {
                bill_label.setText(bill_label.getText() + "\n" + "                         ******FAMILY POINT******\n");
                bill_label.setText(bill_label.getText() + "     Product" + "\tprice" + "\tQuantity" + "\tTotal" + "\n");
                bill_label.setText(bill_label.getText() + "     " + text_name.getText() + "\t" + onePr + "\t" + text_quantity.getText() + "\t" + price);
                totalPrice+=price;
                i++;
            } else {
                bill_label.setText(bill_label.getText() +"\n"+"     " + text_name.getText() + "\t" + onePr + "\t" + text_quantity.getText() + "\t" + price);
                totalPrice+=price;
            }
          totalBill.setText("Rs."+String.valueOf(totalPrice));
        }
    }


}

