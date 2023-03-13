import java.util.ArrayList; //import ArrayList class
import java.util.Scanner;  //Import Scanner class to read user input
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
public class StudentManagement
{
    // Declare a private static field called studentList that is an ArrayList of Student objects
    private static ArrayList<Student> studentList = new ArrayList<>();

    //Declare a filename to read and write to
    private static final String FILENAME = "students.txt"; // file name to read and write student data

    public static void main (String[] args)
    {
        Scanner scanner = new Scanner(System.in); // create scanner object to read user input
        
        int option = 0;
        //loadStudentData(FILENAME);
        while ( option != 5)
        {
            printMenu();
            option = scanner.nextInt();
            switch (option)
            {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    updateStudent(scanner);
                    break;       
                case 3:
                    removeStudent(FILENAME,scanner);
                    break;
                case 4:
                    viewStudentList(FILENAME);
                    break;
                case 5:
                    System.out.println("Exiting program..");
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
        
            }   
        }
        //saveStudentData(); // save data to file
        scanner.close();
    }

    //Define a method called printMenu() that displays the options available to the user
    private static void printMenu()
    {
        System.out.println("\nStudent Managment System");
        System.out.println("\n1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Remove Student");
        System.out.println("4. View Student List");
        System.out.println("5. Exit");
        System.out.println("Enter your choice: ");
        return;
    }

    //Define a method called addStudent() that add new student to the studentList 
    private static void addStudent(Scanner scanner)
    {
        System.out.print("\nEnter name: ");
        String name = scanner.next();
        System.out.print("\nEnter id: ");
        int id = scanner.nextInt();
        System.out.print("\nEnter major: ");
        String major = scanner.next();

        //Create a new student object with name,id,major
        studentList.add(new Student(name,id,major));
        System.out.println("Sucessfully added student.");

        saveToFile(FILENAME);

    }

    //Define a method caleed updateStudent() to update student information given student's name
    private static void updateStudent(Scanner scanner)
    {
        if(studentList.isEmpty())
        {
            System.out.println("No student to update.");
            return;
        }
        //Ask for student name and id
        System.out.println("Enter student's name: ");
        String name = scanner.nextLine();
        System.out.println("Enter student's id: ");
        int id = scanner.nextInt();
        //scanner.nextLine();
        boolean found = false;
    

    //Loop through the studentList ArrayList to find the student matching the name
        for (Student student : studentList)
        {
            if((student.getName().equals(name)) && (student.getId()==id))
            {
                System.out.println("Found!");
                System.out.print("Enter new major: ");
                String major = scanner.nextLine();
                scanner.nextLine();
                student.setMajor(major);
                System.out.println("Update sucessfully");
                found = true;
                break;
            }    
        }
        if(found == false)
        {
            System.out.println("No Student Found.");
        }
    }

    //Define a method that remove a student from student list
    private static void removeStudent (String fileName, Scanner scanner)
    {
        File file = new File(fileName);
        if(!file.exists())
        {
            System.out.println("No Student to remove.");
            return;
        }
        //Ask for student name and id
        System.out.println("Enter student's name: ");
        String name = scanner.next();
        scanner.nextLine();
        System.out.println("Enter student's id: ");
        int id = scanner.nextInt();
        boolean found = false;

        // for (Student student : studentList)
        // {
        //     if((student.getName().equals(name)) && (student.getId()==id))
        //     {
        //         System.out.println("Found!");
        //         studentList.remove(student);
        //         System.out.println("Student removed successfully.");
        //         found = true;
        //         break;
        //     }    
        // }
        // if(found == false)
        // {
        //     System.out.println("No Student Found.");
        // }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
         FileWriter writer = new FileWriter(fileName, true)) 
         {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                String studentName = parts[0];
                int studentId = Integer.parseInt(parts[1]);

                if (studentName.equals(name) && studentId == id) 
                {
                    found = true;
                    continue;
                }

            writer.write(line + System.lineSeparator());
        }

        if (found) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
    }

    private static void viewStudentList(String fileName) 
    {
        try
        {
            //create new file object with specified file name
            File file = new File(fileName);
            //check if the file already exist
            if(!file.exists())
            {
                System.out.println("Creating new data file...");
                file.createNewFile();
            }

            Scanner myReader = new Scanner (file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
              }
            myReader.close();
        }
        catch (IOException e) 
        {
            // Handle any IOExceptions that may occur while reading from the file
            System.out.println("Error: Could not load student data from file.");
            return;
        } 
    }

    private static void saveToFile(String fileName)
    {
        try
        {
            //create new file object with specified file name
            File file = new File(fileName);
            //check if the file already exist
            if(!file.exists())
            {
                System.out.println("Creating new data file...");
                file.createNewFile();
            }
            
            FileWriter writer = new FileWriter(fileName);
            for( Student student : studentList)
            {
                writer.write(student.getName()+ "," + student.getId()
                        + "," + student.getMajor() + "\n");
            }
            writer.close();
        }
        catch (IOException e) 
        {
            // Handle any IOExceptions that may occur while reading from the file
            System.out.println("Error: Could not load student data from file.");
            return;
        } 
    }
}


class Student
{
    private String name;
    private int id;
    private String major;

    // Constructor to initialize a new Student object
    public Student(String name, int id, String major)
    {
        this.name = name;
        this.id = id;
        this.major = major;
    }

    // Getter methods to access the private fields of the Student object
    public String getName() 
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    public String getMajor()
    {
        return major;
    }

    public void setMajor(String major)
    {
        this.major = major;
    }
}
