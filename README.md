# ATM_Simulator
It is CLI based Application that simulate the working of ATM , with using of OPPS concept , it is build by using java cor
# ATM Simulator – Core Java Project

A command-line ATM Simulator developed using Core Java to demonstrate ATM banking operations, object-oriented programming, exception handling, collections, and transaction management.

 📌 Project Overview

The ATM Simulator is a menu-driven Java application that simulates the basic operations of an Automated Teller Machine (ATM). It allows users to perform banking transactions through a command-line interface (CLI).

The project includes account authentication, balance inquiry, cash withdrawal, cash deposit, fund transfer, and transaction history. It also provides administrative functionality to manage accounts and monitor transactions.

This project is developed for learning and demonstrating Core Java programming concepts.

 🎯 Objectives

- Simulate real-world ATM operations.
- Implement secure PIN-based authentication.
- Apply Object-Oriented Programming (OOP) principles.
- Demonstrate exception handling and input validation.
- Maintain transaction records and account balances.
- Understand modular Java project development.

 ✨ Features
 Customer Features
- Login using account details and PIN.
- Check account balance.
- Withdraw money.
- Deposit money.
- Transfer money to another bank account.
- View transaction history.
- Logout securely.

# Administrator Features
- Admin login and authentication.
- Manage customer accounts.
- View account information.
- Monitor transaction logs.

*Note: Features depend on the functionality implemented in the current source code.*

##  Technologies Used

- **Programming Language:** Java
- **Interface:** Command-Line Interface (CLI)
- **Java Concepts:** Classes, Objects, Encapsulation, Inheritance (if implemented), Polymorphism (if implemented), Abstraction, Exception Handling, Collections, and Multithreading (if implemented).
- **Development Tools:** JDK, VS Code / IntelliJ IDEA / Eclipse, Windows PowerShell.
- **Version Control:** Git and GitHub.

## 📂 Project Structure

```text
ATM-Simulator/
├── src/
│   └── atm/
│       ├── Main.java
│       ├── admin/
│       ├── exceptions/
│       └── interfaces/
├── README.md
└── .gitignore
```

The package and file structure shown above is illustrative. Update it to match the actual project folders and classes.

## ⚙️ Requirements

- Java Development Kit (JDK 8 or later, depending on the code).
- Git (optional, for version control).
- Windows, Linux, or macOS terminal.

Verify Java installation:

```bash
java -version
javac -version
```

##  How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/Zabra5241/ATM_Simulator.git
```

### 2. Navigate to the project directory

```bash
cd ATM_Simulator
```

### 3. Compile the Java source files

For the package structure used in this project, compile from the project root:

```powershell
javac -d out src\atm\Main.java src\atm\admin\*.java src\atm\exceptions\*.java src\atm\interfaces\*.java
```

If your project has additional packages or source files, include them in the compilation command.

### 4. Run the application

```powershell
java -cp out atm.Main
```

Ensure that `Main.java` declares `package atm;` and that the package paths match the actual source structure.

## 💳 Banking Operations

| Operation | Description |
|---|---|
| Balance Inquiry | Displays the available account balance. |
| Cash Withdrawal | Deducts the requested amount after validation. |
| Cash Deposit | Adds the deposited amount to the account balance. |
| Fund Transfer | Transfers money between supported bank accounts. |
| Transaction History | Displays previously recorded transactions. |
| Admin Operations | Provides administrative functions available in the application. |

## 🗃️ Basic ER Diagram

The following conceptual entities describe a typical ATM Simulator design:

```text
+------------------+
|     CUSTOMER     |
+------------------+
| customer_id (PK) |
| name             |
| account_number   |
| PIN              |
+------------------+
         |
         | 1
         |
         | N
+------------------+
|   TRANSACTION    |
+------------------+
| transaction_id   |
| account_number   |
| transaction_type |
| amount           |
| date_time        |
| status           |
+------------------+
         |
         | Associated with
         |
+------------------+
|     ACCOUNT      |
+------------------+
| account_number   |
| balance          |
| account_type     |
+------------------+
```

This is a conceptual diagram, not a verified representation of the project's actual classes or storage implementation. Adapt it to the implemented design.

## 🧠 Important Java Concepts

- **Encapsulation:** Protects account data and controls access to balances and PINs.
- **Abstraction:** Defines essential ATM operations while hiding implementation details.
- **Interfaces:** Specify common operations that implementing classes must provide.
- **Exception Handling:** Handles invalid input, insufficient balance, and other errors.
- **Collections:** Store and manage account or transaction data when collections are used.
- **File Handling / Database:** Can be used for persistent storage if implemented.
- **Transaction Validation:** Ensures that invalid amounts and unsupported operations are rejected.

## 🔒 Security Considerations

This project is intended for educational use. A production banking application would require stronger controls, including secure PIN hashing, encrypted communication, persistent and auditable transaction records, authorization, and concurrency-safe balance updates.

Do not use real banking credentials or PINs in this project.

##  Future Enhancements

- Integrate a MySQL database using JDBC.
- Add persistent customer and transaction records.
- Implement secure authentication and PIN updates.
- Add receipt generation.
- Improve fund transfer validation and transaction rollback.
- Add automated unit tests.
- Introduce a graphical user interface or web interface.

## 🎓 Learning Outcomes

By developing this project, students can understand:

- Practical application of Core Java.
- Object-oriented software design.
- Modular programming using packages.
- Exception handling and input validation.
- Banking transaction workflows.
- Git and GitHub version control.

## 👨‍💻 Author

**Project:** ATM Simulator  
**Developer:** Ibrar Ansari  
**Project Type:** Core Java Mini Project  
**Interface:** Command-Line Application

# Education purpose

