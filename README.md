# ATM Simulator – Java Project

A command-line ATM Simulator developed using Core Java to demonstrate ATM banking operations, object-oriented programming, exception handling, collections, and transaction management.

 📌 Project Overview

The ATM Simulator is a menu-driven Java application that simulates the basic operations of an Automated Teller Machine (ATM). It allows users to perform banking transactions through a command-line interface (CLI).

The project includes account authentication, balance inquiry, cash withdrawal, cash deposit, fund transfer, and transaction history. It also provides administrative functionality to manage accounts and monitor transactions.

This project is developed for learning and demonstrating Core Java programming concepts.


  Features
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

##  Requirements

- Java Development Kit (JDK 8 or later, depending on the code).
- Git (optional, for version control).
- Windows, Linux, or macOS terminal.

Verify Java installation:

```bash
java -version
javac -version
```



## 💳 Banking Operations

| Operation | Description |
|---|---|
| Balance Inquiry | Displays the available account balance. |
| Cash Withdrawal | Deducts the requested amount after validation. |
| Cash Deposit | Adds the deposited amount to the account balance. |
| Fund Transfer | Transfers money between supported bank accounts. |
| Transaction History | Displays previously recorded transactions. |
| Admin Operations | Provides administrative functions available in the application. |

##  Basic ER Diagram

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

##  Important Java Concepts

- **Encapsulation:** Protects account data and controls access to balances and PINs.
- **Abstraction:** Defines essential ATM operations while hiding implementation details.
- **Interfaces:** Specify common operations that implementing classes must provide.
- **Exception Handling:** Handles invalid input, insufficient balance, and other errors.
- **Collections:** Store and manage account or transaction data when collections are used.
- **File Handling / Database:** Can be used for persistent storage if implemented.
- **Transaction Validation:** Ensures that invalid amounts and unsupported operations are rejected.


.



## 👨‍💻 Author

**Project:** ATM Simulator  
**Developer:** Ibrar Ansari  
**Project Type:** Core Java Mini Project  
**Interface:** Command-Line Application

# Education purpose

