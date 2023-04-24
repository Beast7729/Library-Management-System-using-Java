import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.System;

class Books {

    public Books(int i, String n, String an) {
        this.id = i;
        this.name = n;
        this.Author_Name = an;
    }

    String name;
    int id;
    String Author_Name;
    int borrowedid = -1;
}

class user {
    int borrowedBooks = 0;

    public user(String un, String pass) {
        this.username = un;
        this.password = pass;
        System.out.println("User added successfully");
    }

    String username;
    String password;
    Books Taken[] = new Books[5];
}

public class LibMain {
    static int bookss = 1;
    static int userss = 1;
    static Books availableBooks[] = new Books[100];
    static user users[] = new user[100];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static public int menu() throws NumberFormatException, IOException {
        System.out.println("******************************");
        System.out.println("Welcum To CodySins Library");
        System.out.println("1.Login");// 1
        System.out.println("2.New User");// 2
        System.out.println("3.Exit");// 3
        System.out.println("Enter Your Choice:");
        int choice = Integer.parseInt(br.readLine());
        return choice;

    }

    static public int afterLoginUser() throws NumberFormatException, IOException {
        System.out.println("Logged In Succesfully!");
        System.out.println("1.Borrow Books");// 4
        System.out.println("2.Check Borrowed books");// 5
        System.out.println("3.Exit");// 6
        System.out.println("Enter Your Choice:");
        int choice = 3 + Integer.parseInt(br.readLine());
        return choice;
    }

    static public int afterLoginAdmin() throws NumberFormatException, IOException {
        System.out.println("Welcum To CodySins Library");
        System.out.println("1.List of users");// 7
        System.out.println("2.Available Books");// 8
        System.out.println("3.Add New Books");// 9
        System.out.println("4.Return Books");// 10
        System.out.println("5.Exit");// 11
        System.out.println("Enter Your Choice:");
        int choice = 6 + Integer.parseInt(br.readLine());
        return choice;

    }

    static public int login() throws NumberFormatException, IOException {
        System.out.println("Welcum To CodySins Library");
        System.out.println("Login");
        System.out.print("UserId:");
        int uid = Integer.parseInt(br.readLine());
        System.out.print("Password:");
        String pwd = br.readLine();

        if (uid == 999) {
            if (pwd.equals("admin")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();

                choosenOperation(afterLoginAdmin());

            }
        } else if (users[uid].password.compareTo(pwd) == 0) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            choosenOperation(afterLoginUser());
            return uid;
        } else {
            login();
        }
        return -2;

    }

    static public void availableBooks() throws IOException {
        System.out.println("Available Books");
        System.out.println("Bookid \t Book Name \t BookAuthor \t  ");

        for (int i = 0; i < bookss; i++) {
            Books a = availableBooks[i];
            if (a.borrowedid == -1) {
                System.out.println(a.id + " \t" + a.name + " \t" + a.Author_Name + "\t ");

            }

        }
        System.out.println("Press Enter to Continue");
        br.readLine();

    }

    static public void printusers() throws IOException {
        System.out.println("Active Users");
        System.out.println("Username \t Taken Books \t  ");

        for (int i = 0; i < userss; i++) {
            user a = users[i];
            int t = 0;
            for (int j = 0; j < 5; j++) {
                if (a.Taken[j] == null) {
                    break;
                }
                t++;

            }
            System.out.println(a.username + "\t" + t);

        }

        br.readLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    static public void borrowedBooks(int uid) throws IOException {
        System.out.println("Bookid \t Book Name \t BookAuthor \t  ");
        for (int i = 0; i < users[uid].borrowedBooks; i++) {
            Books b = users[uid].Taken[i];
            System.out.println(b.id + " \t" + b.name + " \t" + b.Author_Name + "\t ");
        }
        System.out.println("Press Enter to Continue");

        br.readLine();

    }

    static public void addnewBooks() throws NumberFormatException, IOException {
        int id;
        String name, authorname;
        System.out.print("Enter Book id:");
        id = Integer.parseInt(br.readLine());
        System.out.print("Enter Book Name:");
        name = br.readLine();
        System.out.print("Enter Book Author Name:");
        authorname = br.readLine();
        Books b1 = new Books(bookss, name, authorname);
        availableBooks[bookss] = b1;
        bookss++;
        afterLoginAdmin();

    }

    static public void adduser() throws IOException {
        String username, paswd;
        System.out.println("Enter Username:");
        username = br.readLine();
        System.out.println("Enter Password:");
        paswd = br.readLine();
        user u1 = new user(username, paswd);
        users[userss] = u1;
        System.out.println(users[userss] + " " + userss);
        System.out.println("Your user id is :" + userss + "\n press enter to continue");
        br.readLine();
        userss++;
    }

    static public void borrowbook(int uid) throws IOException {
        availableBooks();
        System.out.println("Enter Book ID to Request Borrow:");
        int iddd = Integer.parseInt(br.readLine());
        user temp = users[uid];
        temp.Taken[temp.borrowedBooks] = availableBooks[iddd];
        availableBooks[iddd].borrowedid = uid;
        temp.borrowedBooks++;

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void removebook(int uid, int bid) {
        users[uid].Taken[bid] = null;
        users[uid].borrowedBooks -= 1;
        availableBooks[bid].borrowedid = -1;
        int i = 0;
        for (i = 0; i < 5; i++) {
            if (users[uid].Taken[i] == null) {
                if (i < 5) {
                    break;
                }
            }
        }
        switch (i) {
            case 0:
                users[uid].Taken[0] = users[uid].Taken[1];
                users[uid].Taken[1] = users[uid].Taken[2];
                users[uid].Taken[2] = users[uid].Taken[3];
                users[uid].Taken[3] = users[uid].Taken[4];
                break;
            case 1:
                users[uid].Taken[1] = users[uid].Taken[2];
                users[uid].Taken[2] = users[uid].Taken[3];
                users[uid].Taken[3] = users[uid].Taken[4];
                break;

            case 2:
                users[uid].Taken[2] = users[uid].Taken[3];
                users[uid].Taken[3] = users[uid].Taken[4];
                break;
            case 3:
                users[uid].Taken[3] = users[uid].Taken[4];
                break;
            default:
                System.out.println("Error +" + i);
                break;
        }
    }

    public static void returnbook() throws IOException {
        printusers();
        System.out.println("Enter User to see books:");
        int uidd = Integer.parseInt(br.readLine());
        borrowedBooks(uidd);
        System.out.println("Enter Bk id which is returned:");
        int biid = Integer.parseInt(br.readLine());
        removebook(uidd, biid);
        System.out.println("submitted succesfully!");
    }

    static public void choosenOperation(int choice) throws NumberFormatException, IOException {
        int loggedin = 0;
        switch (choice) {
            case 1:
                loggedin = login();
                System.out.println(loggedin);

                if (loggedin == -2) {
                    while (true) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        int c = afterLoginAdmin();
                        choosenOperation(c);

                    }
                } else if (loggedin > 0) {
                    while (true) {
                        int c = afterLoginUser();
                        choosenOperation(c);

                    }

                }

                break;
            case 2:
                adduser();
                int ch = menu();
                choosenOperation(ch);
                break;
            case 3:
                System.exit(0);
                break;
            case 4:
                borrowbook(loggedin);
                break;
            case 5:
                borrowedBooks(loggedin);

                break;
            case 6:
                int chh = menu();
                choosenOperation(chh);

                break;
            case 7:
                printusers();

                break;
            case 8:
                availableBooks();
                break;
            case 9:
                addnewBooks();
                break;
            case 10:
                returnbook();
                break;
            case 11:
                int chhh = menu();
                choosenOperation(chhh);

                break;
            default:
                System.out.println("Enter Valid choice!!");
                break;
        }

    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        users[0] = new user("abc", "xyz");
        availableBooks[0] = new Books(0, "Book1", "AuthorABC");

        while (true) {

            int choice = menu();
            choosenOperation(choice);
        }

    }
}
