/*
 * Name: Mohammed Rashid
 * Assignment: HW11 Serialization
 */

import java.io.*;
import java.text.*;
import java.util.*;

// The Person class is a class which implements Serialization and contains four attributes, a constructor, getters, setters, and a toString method.

class Person implements Serializable {
    private String name;
    private String phoneNumber;
    private Date dateOfBirth;
    private String emailAddress;

    public Person(String name, String phoneNumber, Date dateOfBirth, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPhone Number: " + phoneNumber + "\nDate of Birth: " + dateOfBirth + "\nEmail Address: " + emailAddress + "\n";
    }
}

/*
The Program class manages a list of Person objects and provides a menu for the following operations:

Add information: Add a person to the list and update the file.
Retrieve information: Read the .ser file, load persons list, and display the contents.
Delete information: Remove a person from the list by email and update the file.
Update information: Modify a person's attributes by email and update the file.
Exit: Close the program.
*/

public class Program {
    private static final String FILE_NAME = "persons.ser";
    private static List<Person> persons = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private static Date getDateFromString(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in format MM/DD/YYYY.");
            return null;
        }
    }

    private static void addInformation() {
        System.out.println("\nAdd Information");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Date of Birth (MM/dd/yyyy): ");
        String dateOfBirthStr = scanner.nextLine();
        Date dateOfBirth = getDateFromString(dateOfBirthStr);
        System.out.print("Email Address: ");
        String emailAddress = scanner.nextLine();
        persons.add(new Person(name, phoneNumber, dateOfBirth, emailAddress));
        System.out.println("Information added successfully.");
    }

    private static void writeToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("persons.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(persons);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Data written to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static List<Person> readFromFile() {
        List<Person> temp_persons = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("persons.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            temp_persons = (List<Person>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return temp_persons;
    }

    private static void displayInformation() {
        if (persons.isEmpty()) {
            System.out.println("No information available.");
        } else {
            System.out.println("Information of all persons:");
            for (Person person : persons) {
                System.out.println(person);
            }
        }
    }

    private static void deleteInformation() {
        System.out.println("Enter the email address of the person to delete:");
        String emailAddress = scanner.nextLine();
        boolean found = false;
        for (Iterator<Person> iterator = persons.iterator(); iterator.hasNext();) {
            Person person = iterator.next();
            if (person.getEmailAddress().equals(emailAddress)) {
                iterator.remove();
                System.out.println("Person deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No person found with email address " + emailAddress + ".");
        }
    }

    private static void updateInformation() {
        System.out.println("Enter the email address of the person to update:");
        String emailAddress = scanner.nextLine();
        boolean found = false;
        for (Person person : persons) {
            if (person.getEmailAddress().equals(emailAddress)) {
                System.out.println("Enter the new name of the person:");
                String name = scanner.nextLine();
                System.out.println("Enter the new phone number of the person:");
                String phoneNumber = scanner.nextLine();
                System.out.println("Enter the new date of birth of the person (MM/dd/yyyy):");
                String dateOfBirthStr = scanner.nextLine();
                Date dateOfBirth = getDateFromString(dateOfBirthStr);
                person.setName(name);
                person.setPhoneNumber(phoneNumber);
                person.setDateOfBirth(dateOfBirth);
                System.out.println("Person updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No person found with email address " + emailAddress + ".");
        }
    }

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1) Add information");
            System.out.println("2) Retrieve information");
            System.out.println("3) Delete information");
            System.out.println("4) Update information");
            System.out.println("5) Exit");
            System.out.print("Enter your choice (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    addInformation();
                    writeToFile();
                    break;
                case 2:
                    persons = readFromFile();
                    displayInformation();
                    break;
                case 3:
                    deleteInformation();
                    writeToFile();
                    break;
                case 4:
                    updateInformation();
                    writeToFile();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }

        } while (choice != 5);

        scanner.close();
    }
}