# Java-Project-Library-Management-System
Library Management System
This project is a Java-based Library Management System (LMS) designed for managing the various aspects of a library, such as student registration, book management, issue and return processes, and data modification. The system allows users to register, issue books, return books, and modify or delete data. The backend is connected to an Oracle database, and the frontend is built using Java Swing.

Project Files Description
1. l1.java
This is the main entry point of the application, where the execution of the program begins. It initializes the GUI interface and provides the user with options to navigate to different functionalities of the Library Management System, such as student registration, book management, issue/return of books, and data modifications.

2. home.java
The home.java file represents the home screen of the application. It provides navigation buttons that allow users to access other sections of the system, such as student registration, book registration, and more. It acts as the primary interface for the user to interact with the system.

3. student.java
This file manages the student-related operations, such as registering new students into the system. It includes features to input and store student details such as name, student ID, and other relevant information. It interfaces with the Oracle database to ensure proper storage and retrieval of student data.

4. book.java
The book.java file handles the management of books in the library. It allows users to add new books to the system, register details like book name, book ID, publisher, price, and other relevant information. It also connects to the Oracle database to store book data and retrieve it as necessary.

5. detail.java
This file manages the display of book and student details. It provides functionality to view detailed information about a specific book or student. This includes displaying the current status of books, student records, and information related to issued books. The file ensures that users can access important data in a clear and structured format.

6. issue.java
The issue.java file handles the process of issuing books to students. It captures the student ID, book ID, issue date, and due date for book issuance. It updates the database with the issuance details and ensures that the system tracks which books are issued and to whom.

7. returnbook.java
This file manages the return process of issued books. It records the student ID, book ID, and return date when a student returns a book. It updates the database to mark the book as returned and calculates any overdue fees, if applicable.

8. modifydata.java
The modifydata.java file provides functionality to modify student and book records. It allows authorized users to update student information, book details, and other system-related data. This ensures that the database remains accurate and up-to-date with any changes in the system.

9. deletedata.java
The deletedata.java file enables the deletion of student or book records from the system. It allows authorized users to remove data from the database when a student graduates or a book is no longer available in the library. This file ensures proper deletion of records and maintains database integrity.

Features
Student Registration: Allows users to register new students and store their details.
Book Management: Add, view, and manage books in the library, including registering new books.
Issue Books: Issue books to students by recording book ID, student ID, issue date, and due date.
Return Books: Handle book returns, including tracking overdue books and calculating fines.
Modify Records: Update student and book information as needed.
Delete Records: Remove student or book records when necessary.

Technology Used
Java: For backend logic and GUI using Java Swing.
Oracle Database: For data storage, using JDBC for connectivity.
Swing: For frontend GUI components.

Setup Instructions
Download or clone the repository.
Ensure you have Java JDK 8 or higher installed.
Setup your Oracle Database and configure the connection using JDBC.
Compile and run the Java files using your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
Ensure the necessary libraries, like ojdbc14.jar, are available for database connection.


Run following oracle table:
1. student table:
   CREATE TABLE STUDENTDATA (
    STUDENT_ID NUMBER NOT NULL,
    NAME VARCHAR2(100) NOT NULL,
    BRANCH VARCHAR2(50) NOT NULL,
    SEMESTER VARCHAR2(20) NOT NULL,
    ROLL_NO NUMBER NOT NULL,
    DIVISION VARCHAR2(2) NOT NULL,
    ADDRESS VARCHAR2(255) NOT NULL,
    GENDER VARCHAR2(10) NOT NULL
);

2.book table:
CREATE TABLE BOOKDATA (
    BOOK_ID VARCHAR2(20) NOT NULL,
    BOOK_NAME VARCHAR2(255) NOT NULL,
    AUTHOR VARCHAR2(255) NOT NULL,
    PUBLISHER VARCHAR2(255) NOT NULL,
    PRICE NUMBER(10, 2) NOT NULL,
    PUBLICATION_YEAR NUMBER
); 

3.issue table:
CREATE TABLE ISSUE (
    BOOK_ID VARCHAR2(20) NOT NULL,
    STUDENT_ID NUMBER NOT NULL,
    ISSUE_DATE DATE NOT NULL,
    DUE_DATE DATE NOT NULL,
    RETURN_DATE DATE
);   


Conclusion
This Library Management System provides an easy-to-use interface for managing students, books, and transactions. With functionalities like student registration, book issue/return, and data modification/deletion, the system aims to streamline library operations and ensure efficient data management.
