package Sendmail;
 
import java.sql.*;
import javax.sql.DataSource;
 

public class UserIO {

    private PreparedStatement preparedStatement;
    private Statement stmnt;
    private Connection con;
    private DataSource dataSource;
    private ResultSet resultSet;

 

//    private Connection getConnection() throws Exception {
//        String driver = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/colorschemeauthentication";
//        String username = "root";
//        String password = "";
//        Class.forName(driver);
//        Connection connection = DriverManager.getConnection(url, username, password);
//        return connection;
//    }
 

    private int getRowCount() throws Exception {
        String query = "SELECT UserID FROM users";
        ResultSet resultSet;
        con = connection.Connect.makeCon();
        stmnt = con.createStatement();
        resultSet = stmnt.executeQuery(query);
        resultSet.last();
        int i = resultSet.getRow();
        con.close();
        return i;
    }

    private int getMaxUserID() throws Exception {
        String query = "SELECT MAX(UserID) FROM users";
        ResultSet resultSet;
        con =  connection.Connect.makeCon();
        stmnt = con.createStatement();
        resultSet = stmnt.executeQuery(query);
        resultSet.next();
        int i = resultSet.getInt("Max(UserID)");
        con.close();
        return i;
    }
///////////////////////////////////////////////////////////
////////////////////////ADD User///////////////////////////////////
///////////////////////////////////////////////////////////

    public void addUser(User user) throws Exception {
        String query = "INSERT INTO users VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        con =  connection.Connect.makeCon();
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, (getMaxUserID() + 1));
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setString(6, user.getBirthDate());
        preparedStatement.setString(7, user.getPhone());
        preparedStatement.setString(8, user.getAddress());
        preparedStatement.setString(9, user.getAddress2());
        preparedStatement.setString(10, user.getCity());
        preparedStatement.setString(11, user.getZip());
        preparedStatement.setString(12, user.getCountry());
        preparedStatement.setString(13, user.getGender());
        preparedStatement.setInt(14, user.getEmailVerified());
        preparedStatement.setString(15, user.getRegistrationDate());
        preparedStatement.setString(16, user.getVerificationCode());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        con.close();
    }

    public boolean UpdateUser(int userid, String firstname, String lastname, String email, String pwd, String dob, String phone, String address1, String address2, String city, String zip, String county, String gender) throws Exception {
        String query = "UPDATE users set  UserFirstName = ? , UserLastName = ? , UserEmail = ? , UserPassword = ? , UserBirthDate = ? , UserPhone = ?, UserAddress = ? , UserAddress2 = ? , UserCity = ? , UserZip = ? , UserCountry = ? , UserGender = ?  where  UserID = ? ";
        boolean flag = false;
        try {
            con = connection.Connect.makeCon();
            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, pwd);
            preparedStatement.setString(5, dob);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, address1);
            preparedStatement.setString(8, address2);
            preparedStatement.setString(9, city);
            preparedStatement.setString(10, zip);
            preparedStatement.setString(11, county);
            preparedStatement.setString(12, gender);
            preparedStatement.setInt(13, userid);
            preparedStatement.executeUpdate();
            flag = true;
            preparedStatement.close();
            con.close();
        } catch (Exception ex) {
            flag = false;
            System.out.println("error -UserIO.java line=106 " + ex.toString());
        }
        return flag;
    }
///////////////////////////////////////////////////////////
///////////////////////Details of users ////////////////////////////////////
///////////////////////////////////////////////////////////

    public String getUserStringData(String columnName, String search_columnName, String search_data) throws Exception {

        String query = "SELECT " + columnName + " FROM users WHERE " + search_columnName + "='" + search_data + "'";
        String resultItem = null;
        con =  connection.Connect.makeCon();
        stmnt = con.createStatement();
        resultSet = stmnt.executeQuery(query);
        if (resultSet.next()) {
            resultItem = (String) resultSet.getObject(columnName);
        }
        con.close();
        return resultItem;
    }
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////

    public int getUserIntData(String columnName, String search_columnName, String search_data) throws Exception {

        String query = "SELECT " + columnName + " FROM users WHERE " + search_columnName + "='" + search_data + "'";
        int resultItem;
        con =  connection.Connect.makeCon();
        stmnt = con.createStatement();
        resultSet = stmnt.executeQuery(query);
        resultSet.next();
        resultItem = (Integer) resultSet.getObject(columnName);

        con.close();
        return resultItem;
    }
    
     public String deteData(String username) throws Exception {

        String query = "delete  FROM colorratingtable  WHERE UserName='" + username + "'";
        String resultItem;
        con =  connection.Connect.makeCon();
        stmnt = con.createStatement();
        resultItem= ""+ stmnt.executeQuery(query);
         
        con.close();
        return resultItem;
    }
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////

     public User getEmailByUsername(String userid) throws Exception {
        String query = "SELECT * FROM signup WHERE uname='"
                + userid + "'";
        User user = new User();
        con = connection.Connect.makeCon();
        stmnt = con.createStatement();
        resultSet = stmnt.executeQuery(query);
        if (resultSet.next()) {
            user.setUserID((String) resultSet.getObject("UserID"));
            user.setFirstName((String) resultSet.getObject("FirstName"));
            user.setLastName((String) resultSet.getObject("LastName"));
            user.setEmail((String) resultSet.getObject("EmailID"));
            user.setPassword((String) resultSet.getObject("pass"));
            user.setBirthDate((String) resultSet.getObject("DOB"));
            user.setPhone((String) resultSet.getObject("ContactNo"));
            user.setAddress((String) resultSet.getObject("Address"));
            user.setAddress2((String) resultSet.getObject("Address"));
            user.setCity((String) resultSet.getObject("City"));
            user.setZip((String) resultSet.getObject("PinCode"));
            user.setCountry((String) resultSet.getObject("Country"));
            user.setGender((String) resultSet.getObject("Gender"));
           // user.setEmailVerified((Integer) resultSet.getObject("0"));
           // user.setRegistrationDate((String) resultSet.getObject("0"));
          //  user.setVerificationCode((String) resultSet.getObject("0"));
        } 

        resultSet.close();
        con.close();
        return user;
    }
    public User getUser(String userEmail) throws Exception {
        String query = "SELECT * FROM signup WHERE EmailID='"
                + userEmail + "'";
        User user = new User();
        con =  connection.Connect.makeCon();
        stmnt = con.createStatement();
        resultSet = stmnt.executeQuery(query);
        if (resultSet.next()) {
            user.setUserID((String) resultSet.getObject("UserID"));
            user.setFirstName((String) resultSet.getObject("FirstName"));
            user.setLastName((String) resultSet.getObject("LastName"));
            user.setEmail((String) resultSet.getObject("EmailID"));
            user.setPassword((String) resultSet.getObject("pass"));
            user.setBirthDate((String) resultSet.getObject("DOB"));
            user.setPhone((String) resultSet.getObject("ContactNo"));
            user.setAddress((String) resultSet.getObject("Address"));
            user.setAddress2((String) resultSet.getObject("Address"));
            user.setCity((String) resultSet.getObject("City"));
            user.setZip((String) resultSet.getObject("PinCode"));
            user.setCountry((String) resultSet.getObject("Country"));
            user.setGender((String) resultSet.getObject("Gender"));
           // user.setEmailVerified((Integer) resultSet.getObject("0"));
           // user.setRegistrationDate((String) resultSet.getObject("0"));
          //  user.setVerificationCode((String) resultSet.getObject("0"));
        } 

        resultSet.close();
        con.close();
        return user;
    }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    public void updateUserData(String columnName, String search_columnName, String search_data, String dataString, Integer dataInteger) throws Exception {

        String query = "UPDATE users SET " + columnName + "=? WHERE " + search_columnName + "='" + search_data + "'";
        con =  connection.Connect.makeCon();
        preparedStatement = con.prepareStatement(query);
        if (dataString != null) {
            preparedStatement.setString(1, dataString);
        } else if (dataInteger != null) {
            preparedStatement.setInt(1, dataInteger);
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
        con.close();
    }

    public static void main(String[] arg) throws Exception {
        try {
            
            Connection c =  connection.Connect.makeCon();
            System.out.println("connect :" + c.toString());
        } catch (Exception ex) {
            System.out.println("error catch: " + ex.toString());
        }

    }
}
