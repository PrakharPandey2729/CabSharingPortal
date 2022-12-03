import java.util.Scanner;

public class LoginPage implements LoginFeatures{
    private boolean isLoggedIn = false, isAdmin = false;
    private Student student;
    private final String admin_username, admin_password;
    public LoginPage(){
        admin_username = "admin";
        admin_password = "abcd";
    }
    public boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Username");
        String username = sc.nextLine();
        System.out.println("Enter Password");
        String password = sc.nextLine();
        try{
            student = StudentDb.queryDetails(username);
            this.verify(username, password);
        }
        catch(UsernameDoesNotExistException e) {
            System.out.println(e.getMessage());
            System.out.println("Would you like to create a new user?(0/1)");
            if (Integer.parseInt(sc.nextLine()) == 1) {
                this.register();
            }
        }
        catch(IncorrectPasswordException e){
            System.out.println(e.getMessage());
            System.out.println("Would you like to try again?(0/1)");
            if(Integer.parseInt(sc.nextLine()) == 1) {
                this.login();
            }
        }
        if(this.isLoggedIn)
            System.out.println("Successfully logged in!");
        return this.isLoggedIn;
    }

    private boolean verify(String username, String password) throws IncorrectPasswordException, UsernameDoesNotExistException{
        String db_password = StudentDb.queryPassword(username);
        this.isLoggedIn = db_password.equals(password);
        if(!this.isLoggedIn){
            throw new IncorrectPasswordException("Wrong password entered for this user!");//TODO: Define this exception
        }
        return this.isLoggedIn;
    }
    public boolean register(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter New Username(ID Number)");
        String username = sc.nextLine();
        System.out.println("Enter Password");
        String password = sc.nextLine();
        try {
            StudentDb.addToPasswords(username, password);
        }
        catch(UserAlreadyExistsException e){
            System.out.println(e.getMessage());
            System.out.println("Would you like to try again?(0/1)");
            if (Integer.parseInt(sc.nextLine()) == 1) {
                this.register();
            }
            else{
                this.isLoggedIn = false;
                return false;
            }
        }
        System.out.println("Enter Your Name");
        String name = sc.nextLine();
        System.out.println("Enter Your Contact Number");
        String contactNumber = sc.nextLine();
        System.out.println("Enter Your Email Address");
        String email = sc.nextLine();
        try {
            StudentDb.addDetails(username, name, contactNumber, email);
        }
        catch (UserAlreadyExistsException e) {
            //ignored. guaranteed unique user since we've already done that check
        }
        System.out.println("Successfully Registered!");
        this.isLoggedIn = true;
        try {
            student = StudentDb.queryDetails(username);
        }
        catch (UsernameDoesNotExistException e) {
            //ignored, since we know username exists because we just added it
        }
        return true;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean admin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Admin Username");
        String username = sc.nextLine();
        System.out.println("Enter Admin Password");
        String password = sc.nextLine();
        try{
            this.verifyAdmin(username, password);
        }
        catch(IncorrectPasswordException e){
            System.out.println(e.getMessage());
            System.out.println("Would you like to try again?(0/1)");
            if(Integer.parseInt(sc.nextLine()) == 1) {
                this.admin();
            }
        }
        this.isAdmin = this.isLoggedIn;
        return this.isLoggedIn;
    }

    private boolean verifyAdmin(String username, String password) throws IncorrectPasswordException{
        this.isLoggedIn = username.equals(admin_username) && password.equals(admin_password);
        if(!this.isLoggedIn){
            throw new IncorrectPasswordException("Wrong admin username or password entered!");//TODO: Define this exception
        }
        return this.isLoggedIn;
    }
    public Student getStudent() {
        return student;
    }

    public void logout(){
        this.isLoggedIn = false;
        this.isAdmin = false;
    }
}