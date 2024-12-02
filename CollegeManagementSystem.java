import java.util.*;

class Student {
    int studentId;
    String name;
    List<String> courses; // To store registered courses
    Student next;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.courses = new ArrayList<>();
        this.next = null;
    }
}

class StudentLinkedList {
    Student head;
    Set<Integer> studentIds; // To keep track of unique IDs

    public StudentLinkedList() {
        head = null;
        studentIds = new HashSet<>();
    }

    public void addStudent(int studentId, String name) {
        if (studentIds.contains(studentId)) {
            System.out.println("Error: Student ID must be unique. This ID is already in use.");
            return;
        }

        Student newStudent = new Student(studentId, name);
        if (head == null) {
            head = newStudent;
        } else {
            Student temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newStudent;
        }
        studentIds.add(studentId);
        System.out.println("Student added: " + name);
    }

    public Student findStudent(int studentId) {
        Student temp = head;
        while (temp != null) {
            if (temp.studentId == studentId) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public void removeStudent(int studentId) {
        if (head == null) {
            System.out.println("No students available.");
            return;
        }

        if (head.studentId == studentId) {
            head = head.next;
            studentIds.remove(studentId);
            System.out.println("Student with ID " + studentId + " removed.");
            return;
        }

        Student temp = head;
        while (temp.next != null && temp.next.studentId != studentId) {
            temp = temp.next;
        }

        if (temp.next == null) {
            System.out.println("Student not found.");
        } else {
            temp.next = temp.next.next;
            studentIds.remove(studentId);
            System.out.println("Student with ID " + studentId + " removed.");
        }
    }

    public void printStudents() {
        if (head == null) {
            System.out.println("No students available.");
            return;
        }

        Student temp = head;
        while (temp != null) {
            System.out.println("Student ID: " + temp.studentId + ", Name: " + temp.name + ", Courses: " + temp.courses);
            temp = temp.next;
        }
    }
}

class AttendanceStack {
    Stack<String> stack;

    public AttendanceStack() {
        stack = new Stack<>();
    }

    public void recordAttendance(String date, List<Integer> students) {
        StringBuilder record = new StringBuilder(date + ": ");
        students.forEach(id -> record.append(id).append(" "));
        stack.push(record.toString());
        System.out.println("Attendance recorded.");
    }

    public void showRecordedAttendance() {
        if (stack.isEmpty()) {
            System.out.println("No attendance records available.");
            return;
        }
        System.out.println("Attendance Records:");
        stack.forEach(System.out::println);
    }
}

class Course {
    int courseId;
    String courseName;
    int availableSeats;
    List<Student> registeredStudents; // To store registered students

    public Course(int courseId, String courseName, int availableSeats) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.availableSeats = availableSeats;
        this.registeredStudents = new ArrayList<>();
    }
}

class CourseArray {
    List<Course> courses;

    public CourseArray() {
        courses = new ArrayList<>();
    }

    public void addCourse(int courseId, String courseName, int availableSeats) {
        for (Course course : courses) {
            if (course.courseId == courseId) {
                System.out.println("Error: Course ID must be unique. This ID is already in use.");
                return;
            }
        }

        Course course = new Course(courseId, courseName, availableSeats);
        courses.add(course);
        System.out.println("Course added: " + courseName);
    }

    public Course findCourse(int courseId) {
        for (Course course : courses) {
            if (course.courseId == courseId) {
                return course;
            }
        }
        return null;
    }

    public void removeCourse(int courseId) {
        Course courseToRemove = null;
        for (Course course : courses) {
            if (course.courseId == courseId) {
                courseToRemove = course;
                break;
            }
        }

        if (courseToRemove == null) {
            System.out.println("Course not found.");
            return;
        }

        // Remove the course and update students
        for (Student student : courseToRemove.registeredStudents) {
            student.courses.remove(courseToRemove.courseName);
        }
        courses.remove(courseToRemove);
        System.out.println("Course removed successfully.");
    }

    public void printCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course course : courses) {
            System.out.println("Course ID: " + course.courseId + ", Course Name: " + course.courseName + ", Seats: " + course.availableSeats);
        }
    }

    public void showRegisteredStudents() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course course : courses) {
            System.out.println("\nCourse ID: " + course.courseId + ", Course Name: " + course.courseName);
            if (course.registeredStudents.isEmpty()) {
                System.out.println("No students registered for this course.");
            } else {
                System.out.println("Registered Students:");
                for (Student student : course.registeredStudents) {
                    System.out.println("- Student ID: " + student.studentId + ", Name: " + student.name);
                }
            }
        }
    }
}

public class CollegeManagementSystem {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            StudentLinkedList studentList = new StudentLinkedList();
            AttendanceStack attendanceStack = new AttendanceStack();
            CourseArray courseArray = new CourseArray();

            while (true) {
                System.out.println("\nCollege Management System Menu:");
                System.out.println("1. Add Student");
                System.out.println("2. Remove Student");
                System.out.println("3. Record Attendance");
                System.out.println("4. Add Course");
                System.out.println("5. Remove Course");
                System.out.println("6. View All Students");
                System.out.println("7. View All Courses");
                System.out.println("8. Register Student for Course");
                System.out.println("9. Show Registered Students in Courses");
                System.out.println("10. Show Recorded Attendance");
                System.out.println("11. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Student ID: ");
                        int studentId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Student Name: ");
                        String studentName = scanner.nextLine();
                        studentList.addStudent(studentId, studentName);
                    }
                    case 2 -> {
                        System.out.print("Enter Student ID to remove: ");
                        int studentId = scanner.nextInt();
                        studentList.removeStudent(studentId);
                    }
                    case 3 -> {
                        System.out.print("Enter Attendance Date (yyyy-mm-dd): ");
                        String date = scanner.next();
                        System.out.print("Enter Number of Students to mark attendance for: ");
                        int numStudents = scanner.nextInt();
                        List<Integer> students = new ArrayList<>();
                        for (int i = 0; i < numStudents; i++) {
                            System.out.print("Enter Student ID: ");
                            students.add(scanner.nextInt());
                        }
                        attendanceStack.recordAttendance(date, students);
                    }
                    case 4 -> {
                        System.out.print("Enter Course ID: ");
                        int courseId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Course Name: ");
                        String courseName = scanner.nextLine();
                        System.out.print("Enter Available Seats: ");
                        int availableSeats = scanner.nextInt();
                        courseArray.addCourse(courseId, courseName, availableSeats);
                    }
                    case 5 -> {
                        System.out.print("Enter Course ID to remove: ");
                        int courseId = scanner.nextInt();
                        courseArray.removeCourse(courseId);
                    }
                    case 6 -> studentList.printStudents();
                    case 7 -> courseArray.printCourses();
                    case 8 -> {
                        System.out.print("Enter Student ID to register: ");
                        int studentId = scanner.nextInt();
                        System.out.print("Enter Course ID: ");
                        int courseId = scanner.nextInt();
                        Student student = studentList.findStudent(studentId);
                        Course course = courseArray.findCourse(courseId);
                        if (student != null && course != null && course.availableSeats > 0) {
                            student.courses.add(course.courseName);
                            course.registeredStudents.add(student);
                            course.availableSeats--;
                            System.out.println("Student registered for the course.");
                        } else {
                            System.out.println("Either student or course not found, or no seats available.");
                        }
                    }
                    case 9 -> courseArray.showRegisteredStudents();
                    case 10 -> attendanceStack.showRecordedAttendance();
                    case 11 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}
