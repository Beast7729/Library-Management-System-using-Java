import java.io.*;
import java.lang.System;

class Books {

    String name;
    String authorname;
    int id;
    int borrowedid = -1;

    public Books(int i, String n, String an) {
        this.id = i;
        this.name = n;
        this.authorname = an;
    }
}

class user {
    int borrowedBooks = 0;

    String username;
    String password;

    Books Taken[] = new Books[5];

    public user(String un, String pwd) {
        this.username = un;
        this.password = pwd;
    }
}

public class LibraryManagement {
    static int books = 1;
    static int userss = 1;
    static Books availableBooks[] = new Books[100];
    static user users[] = new user[100];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static public int menu() throws Exception {
        System.out.println("****************************");
        System.out.println("Welcome to CODYSINS Library");
        System.out.println("1.Login");// 1
        System.out.println("2.New User");// 2
        System.out.println("3.Exit");// 3
        System.out.println("Enter your choice:");
        int choice = Integer.parseInt(br.readLine());
        return choice;
    }

    static public int afterloginUser() throws Exception {
        System.out.println("Login Successfull!");
        System.out.println("1.Borrow Books");// 4
        System.out.println("2.Check Borrowed Books");// 5
        System.out.println("3.Exit");// 6
        System.out.println("Enter your choice:");
        int choice = Integer.parseInt(br.readLine());
        choice = choice + 3;
        return choice;
    }

    static public int afterloginadmin() throws Exception {
        System.out.println("Login Successfull!");
        System.out.println("1.List of users");// 7
        System.out.println("2.Available Books");// 8
        System.out.println("3.Add new Book");// 9
        System.out.println("4. Return Book");// 10
        System.out.println("5.Exit");// 11
        System.out.println("Enter your choice:");
        int choice = Integer.parseInt(br.readLine());
        choice = choice + 6;

        return choice;
    }

    static public int login() throws Exception {
        System.out.println("Welcome to Cody Sins Library!");
        System.out.println("Login Karo");
        System.out.println("UserID:");
        int uid = Integer.parseInt(br.readLine());
        System.out.println("Password:");
        String pwd = br.readLine();

        if (uid == 999) {
            if (pwd.equals("admin")) {
                System.out.println("\033[H\033[2J");
                System.out.flush();
                choosenOperation(afterloginadmin());
            }
            ;
        } else if (users[uid].password.compareTo(pwd) == 0) {
            System.out.println("\033[H\033[2J");
            System.out.flush();
            choosenOperation(afterloginUser());
            return uid;
        } else {
            login();
        }
        return -2;

    }

    static public void availableBooks() throws Exception {
        System.out.println("Available Books");
        System.out.println("Bookid--Book Name--Book Author");

        for (int i = 0; i < books; i++) {
            Books a = availableBooks[i];
            if (a.borrowedid == -1) {
                System.out.println(a.id + "--" + a.name + "--" + a.authorname);
            }
        }
        System.out.println("press Enter to Continue ");
        br.readLine();
    }

    static public void printusers() throws IOException {
        System.out.println("*****************Active Users*******************");
        System.out.println("Username--Taken Books");

        for (int i = 0; i < userss; i++) {
            user a = users[i];
            int t = 0;
            for (int j = 0; j < 5; j++) {
                if (a.Taken[j] == null) {
                    break;
                }
                t++;
            }
            System.out.println(a.username + "--" + t);

        }
        System.out.println("press Enter to Continue");
        br.readLine();
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    static public void borrowedBooks(int uid) throws Exception {
        System.out.println("Bookid--BookName--BookAuthor");

        for (int i = 0; i < users[uid].borrowedBooks; i++) {
            Books b = users[uid].Taken[i];
            System.out.println(b.id + "--" + b.name + "--" + b.authorname);
        }
        System.out.println("Press Enter to Continue");
        br.readLine();
    }

    static public void addnewBooks() throws Exception {
        String name, authorname;
        System.out.println("Enter Book Name:");
        name = br.readLine();
        System.out.println("Enter Book Author Name:");
        authorname = br.readLine();
        Books b1 = new Books(books, name, authorname);
        availableBooks[books] = b1;
        books++;
        afterloginUser();

    }

    static public void adduser() throws Exception {
        String username, pwd;
        System.out.print("Enter Username:");
        username = br.readLine();
        System.out.print("Enter Password:");
        pwd = br.readLine();
        user u1 = new user(username, pwd);
        users[userss] = u1;
        System.out.println("Your User ID is :" + userss);
        br.readLine();
        userss++;

    }

    public static void borrowbook(int uid) throws Exception {
        availableBooks();
        System.out.println("Enter Book ID to Request Borrow");
        int iddd = Integer.parseInt(br.readLine());
        user temp = users[uid];
        temp.Taken[temp.borrowedBooks] = availableBooks[iddd];
        availableBooks[iddd].borrowedid = uid;
        users[uid].borrowedBooks++;
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void removebook(int uid, int bid) {
        users[uid].Taken[bid] = null;
        users[uid].borrowedBooks -= 1;
        availableBooks[bid].borrowedid = -1;
        int i = 0;
        for (i = 0; i < 5; i++) {
            if (users[uid].Taken[i] == null) {
                break;
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
                System.out.println("Error+" + i);

                break;

        }
    }

    public static void returnBook() throws Exception {
        printusers();
        System.out.println("Enter User to see Books");
        int uidd = Integer.parseInt(br.readLine());
        borrowedBooks(uidd);
        System.out.println("Enter Bk id to return");
        int biid = Integer.parseInt(br.readLine());
        removebook(uidd, biid);
        System.out.println("Submitted succesfully!");
    }

    static void choosenOperation(int choice) throws Exception {
        int loggedin = 0;
        switch (choice) {
            case 1:
                loggedin = login();

                if (loggedin == -2) {
                    while (true) {
                        System.out.println("\033[H\033[2J");
                        System.out.flush();
                        int c = afterloginadmin();
                        choosenOperation(c);
                    }
                } else if (loggedin >= 0) {
                    while (true) {
                        int c = afterloginUser();
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
                returnBook();
                break;
            case 11:
                int chhh = menu();
                choosenOperation(chhh);
                break;
            default:
                System.out.println("Enter Valid Choice!");

        }
    }

    public static void main(String[] args) throws Exception {
        users[0] = new user("abc", "xyz");
        availableBooks[0] = new Books(books, "Book1", "BookAuthor");
        while (true) {
            int choice = menu();
            choosenOperation(choice);
        }
    }
}
