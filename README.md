### CS-401_Group Project Requirements
### Software Requirements Specification

1. Purpose
This document outlines the requirements for a Large Banking System.

      1.1. Scope
       
              This document provides proposed requirements for a Large Banking System. This software will support countless people 
              and interactions. The primary objectives for this project is to provide a GUI for bank employees and users to interact 
              with their accounts. The bank has a server that stores all the data and is retrieved through the GUI. The system
              includes features such as Data storage, Account management, transaction operations, scalability. It operates over
              TCP/IP and is compatible with operating systems that supports the Java Virtual Machine.

      1.2. Definitions, Acronyms, Abbreviations 
              
              SU - SuperUser, refers to the class of user with more permissions and functionalities.
              GUI - Graphical user interface.
              User - A regular account user. Has the ability to draw or add funds from their account.
              Joint Account - Multiple users can share the same account.
              Client - Module for client code.
              Server - Module for server code.
              Message - Module for data transfer between client and server.
              JVM - Java Virtual Machine.

      1.3. References and Use Cases:
      
            UseCases.txt for use cases.

      1.4. Overview 

              The large banking system will provide users with an interface to deposit, withdraw and transfer funds 
              from banking accounts, which includes checking and/or savings accouts. On top of that, the customer will 
              be able to see their account(s), and the associated balances.  
              Teller's interface is similar to a customer interface, with the added capabilities of being able to add, edit and 
              remove users to bank account.

              The application will be able to be used by customer's at home, which will be connected to the bank server
              through TCP/IP network. Additionally, the software will have protection in form of authentication and data safety. 




2. Overall Description

    2.1. Product Perspective 
    
        The Large Banking System is an application that should be designed to operate within a local network among multiple devices. It should provide Server and Client applications. One server should support multiple client applications. The system has several interconnected modules such as The Operator Module, Account Module, Server Module, Message Module and the Client Module. The system should be designed to be scalable, usable with various operating systems through JVM and operational over TCP/IP.
    2.2. Product Architecture 
              
              The system will be organized into 5 major modules: the Operator module, the Account module, 
              the Server module, the Message, and the Client module. Note: System architecture should follow 
              standard OO design practices. 
       
      2.3. Product Functionality/Features 
       
              The high-level features of the system are as follows (see section 3 of this document for more 
              detailed requirements that address these features): 
              
              2.3.1. Users would be able to login to the system, and conduct actions such as:
                     a. Transfer funds to other accounts.
                     b. Manage and see current account balance and information. 
                     c. Make Virtual Deposits.
                     d. view Transaction history
              2.3.2. Users are able to, with the help of a teller, do the following actions:
                     a. Withdraw money. 
                     b. Add, remove or edit access to their accounts. 
                     c. Make a deposit. 
                     d close account.
              2.3.3. System features include:
                     a. Secure communication over TCP/IP.
                     b. Data storage in a secure text file.
                     c. Real-time applications such as deposit, retrieve money.
                     d. scalable
                     f. compatible
                     g. light-weight

    2.4. Constraints 
       
              2.4.1. The software will be made in Java, hence the software will need to keep in mind the amount 
              of objects and keep it to a minimum at all times. 
              2.4.2. The software will be made for home use as well, hence we will need to keep in mind that 
              internet connection may be unstable/interrupted during use, and proper measures will need to be taken care of. 
              2.4.3. The system must ensure data integrity and available, it must not get corrupted.
              2.4.4 The system must not let users in if their credentials are invalid.
              2.4.5 The system GUI's must be simple and user friendly.
              2.4.6 The system must have protections against attempted fraud.


    2.5. Assumptions and Dependencies
        
        2.5.1. It is assumed that for client-teller interactions, both parties are physically together, 
              and that the customers are able to provide credentials and proof of identities to the tellers. 
        2.5.2. It is assumed that in-person deposits are monitored and assisted by a teller such that 
              any increase in bank balance is already verified to be correct. 
        2.5.3. It is assumed that there are no transfer fees for any transfer action.


3. Specific Requirements 
    
    3.1. Functional Requirements:
        
        3.1.1. Common Requirements: 

            3.1.1.1 There should be a standard way of storing information in the txt data file/s. //this is technical
        
        3.1.2. Operator Module Requirements: 
        3.1.2. Regular User Sub-Module Requirements:
            3.1.2.1 ...

        3.1.2. Super User (Admin) Module Requirements: 
        
            3.1.2.1 The superuser will have the ability to create new accounts. //they are bankers, not for bankers
            3.1.2.2 If a checking account has not been active in the last 6 months, the superuser has the ability to deactivate the said account. 
            3.1.2.3 If an account has a negative balance or a history of overdrafts, the superuser will have the ability to declare the account closed.
            3.1.2.4 The superuser has the ability to assign roles that have appropriate 
                     permissions
       
       3.1.3 Account Module Requirements:
       
              3.1.3.1 The account will have the date of creation.
              3.1.3.2 The account will have a way to determine that a period of time passed. 
              3.1.3.3 The account will have a checking account balance.
              3.1.3.4 The account will have a separate savings account balance.
              3.1.3.5 The account will have a way to deposit funds.
              3.1.3.6 The account will have a way to extract funds.
              3.1.3.7 The account will have a way to destroy itself upon SU’s request.
              3.1.3.8 Each account will have a unique serial ID used to identify the account.
              3.1.3.9 Serial ID is generated upon account initialization.
              3.1.3.10 Serial ID has a pattern for identification.
              3.1.3.11 Serial ID will be used to authenticate an Account, along with a user 
              generated passcode, which is initiated during account creation. (currently 
              disallowing changing passcode)
              3.1.3.12 The removal of the last User in an account will result in its closure.
              3.1.3.13 Each registered user gains access to the account through 
              authentication with the account's reference list. 
              3.1.3.14 Only the SU can remove or add users from accounts.
              3.1.3.15 Multiple Users may be registered to the same account.
              3.1.3.16 Users can have multiple accounts.

              3.1.3. Checking Account Sub-Module Requirements: 
                     3.1.3.1 The checking account has no limit on the number of withdrawals.
                     3.1.3.2 The checking account has a $5 maintenance fee per month.

              3.1.3. Saving Account Sub-Module Requirements:
                     3.1.3.1 Savings account has a withdrawal limit of 6 monthly withdrawals without a fee.
                     3.1.3.2 If the user withdraws more than 6 times, they will be charged a $5 fee for every withdrawal.
                     3.1.3.3 The savings account will have a 0.10% annual increase rate.

              3.1.3. Checking Account Sub-Module Requirements: 
                     3.1.3.1 The checking account has no limit on the number of withdrawals.
                     3.1.3.2 The checking account has a $5 maintenance fee per month.

              3.1.3. Saving Account Sub-Module Requirements:
                     3.1.3.1 Savings account has a withdrawal limit of 6 monthly withdrawals without a fee.
                     3.1.3.2 If the user withdraws more than 6 times, they will be charged a $5 fee for every withdrawal.
                     3.1.3.3 The savings account will have a 0.10% annual increase rate.

       3.1.4 Message Module Requirements:
              3.1.4.1 Message between client and Server Modules through the internet will be encapsulated. 
              3.1.4.2 Encapsulated data will be referred to a package.
              3.1.4.3 Package will contain data, current address, receiving address.
              3.1.4.4 Package will accept different type of data.
              3.1.4.5 Package is able to be decapsulated by the recipient.
              3.1.4.6 There will be a processor, a sender, and a receiver on both side of the 
              connection.
                     3.1.4.6.1 Processor can encapsulate data into package or decapsulate package 
                     into data.
                     3.1.4.6.2 Sender will let processor create package from passed data and send 
                     package.
                     3.1.4.6.3 Receiver will listen on a designated port for packages and send them to 
                     processor to get data.
                     3.1.4.6.4 Processor, Sender, and Receiver can handle multiple packages 
                     synchronously.

       3.1.5 Client Module Requirements:
              3.1.5.1 Client will allow Operators to log in using ID and passcode.
              3.1.5.2 Client will be able to differentiate user log in and superUser log 

       3.1.6 Server Module Requirements:
              3.1.6.1 The data must be stored within the server application so that it has direct access.
              3.1.6.2 The server must restart if data is corrupted.
              3.1.6.3 The server should have the ability to transfer funds between differerent accounts.


      3.2. External Interface Requirements 
              
              3.2.1 The system must operate over a local network using TCP/IP.
              3.2.2 The system must support multiple users simultaniously.
              3.2.3 The system must be scalable to any number of users at any time.
              3.2.4 The system must store all it's data "securely" in a text file.
              3.2.5 The system must be able to work in common operating systems such as Windows, Linux and MacOS. 

      3.3. Internal Interface Requirements 
    
         3.3.1 Server and Client must have Interface to send and receive requests.
            3.3.2 The Operator module is connected to a GUI through which all operations take place.
              3.3.3 The Superuser Subclass of Operator has slightly different GUI to accomodate for additional functionalities.
              3.3.4 The Server and Client use Message module for bi-communication. 
              3.3.5 The Account module must provide an interface for the Operator module to perform 
              account-related operations such as creating, updating, and deleting accounts.
              3.3.6 The Message module must provide an interface for encapsulating and decapsulating data.
              3.3.7 The Server module must provide an interface for handling authentication requests from the Client module.
              3.3.8 The Client module must provide an interface for users to input their credentials and receive authentication status (GUI).
              3.3.9 The Server, client and account modules must provide an interface for handling deposits, withdrawals, and transfers.
              3.3.10 The account module must procide an interface for keeping data recieved from the server temporarily to show to user.
              3.3.11 The message module must provide an interface for error handling between the client and server modules.
              3.3.12 The account module must provide a way for applying interest rates, account penalties, monthly fees etc.

4. Non-Functional Requirements 

    4.1. Security and Privacy Requirements :
            
            4.1.1. User will need to be authenticated before getting access to any sensitive information
       such as bank balance, account information and account actions. 
            4.1.2. A sequential ordering of server request and response need to be created to prevent 
       inappropriate sequence of actions that affects the balance. 
            4.1.3. Proper authentication methods need to be in place to prevent users from being able 
       to access credentials of other users in the system. 
            4.1.4. Passwords and other authentication information needs to be processed server side, 
       that is that any comparisons are done in the server and only a response is returned. 
            4.1.5 The data will be stored in a text file.

    4.2. Environmental Requirements 
       
        4.2.1. The software needs to be “adaptive” and light weight as the software will be used 
       by customers in a variety of environments.
        4.2.2. The software needs to be internet friendly, which means that information sent to the 
       server should be checked and verified to be complete and whole before being sent. 
        4.2.3 The system must be deployable on common operating systems, such as Windows, Linux and MacOS.
        4.2.4 The system is dependent on JVM 22 being available. 

    4.3. Performance Requirements 
       
        4.3.1 The system should be fast enough for the user to not experience a delay greater than 3 
       seconds.
        4.3.2 Most operations such as adding or retrieving cash are constant time operations and should be 
       executed in reasonable time. Meaning the algorithm should take O(1) time.
        4.3.3 The system should not crash when under load, Example - multiple users try to access it.
        4.3.4 The system should be able to handle any unexpected exceptions without crashing the app. 
---

Use Cases:

Operator Use Cases:

Use Case ID: O001
Use Case Name: Deposit cash
Relevant Requirements: 3.1.3.5
Primary Actor: 
    - Operator Module
Pre-condition:
    - The operator is authenticated.
    - Operator is authorized to operate on that account.
Post-condition: 
    - The cash amount is deposited to the account in which the operator is authorized.
Basic Flow: 
    1. The operator log in to the account.
    2. The operator requests to deposit some balance amount.
    3. The client module uses the message module to send the request.
    4. The server module receives the request.
    5. The server module invoke correct account's methods to change its balance.
    6. The interaction is logged.
Alternate Flows: 
    1. The operator tries to deposit a non-supported inputs, non-positive numeric inputs, or decided undesirable amounts.
    2. Client module intercept input.
    3. Client module deny action.
Exceptions: 
    - The server may fail to receive or fulfill the request due to reasons such as data corruption or connection issues.
Related Use Cases: 
    - Log in, Withdraw cash

---

Use Case ID: O002
Use Case Name: Withdraw cash
Relevant Requirements: 3.1.2.4, 3.1.3.1, 3.1.3.6
Primary Actor: 
    - Operator Module
Pre-condition: 
    - The operator is authenticated.
    - Operator is authorized to operate on that account.
Post-condition: 
    - The retrieved cash must be subtracted from the account balance.
Basic Flow: 
    1. The operator log in to the account.
    2. The operator requests to withdraw some balance amount.
    3. The client module uses the message module to send the request.
    4. The server module receives the request.
    5. The server module invoke correct account's methods to withdraw.
    6. The interaction is logged.
Alternate Flows: 
    1. The operator tries to deposit a non-supported inputs, non-positive numeric inputs, or decided undesirable amounts.
    2. Client module intercept input.
    3. Client module deny action.
Exceptions: 
    - The server may fail to receive or fulfill the request due to reasons such as data corruption or connection issues.
    - If balance is lowere than withdraw request, server will deny request.
Related Use Cases: 
    - Deposit cash

---

Use Case ID: O003
Use Case Name: Transferring request 
Relevant Requirements: 3.3.9, 3.1.2.2
    - Operator can request transfer fund between accounts.
Primary Actor:
    - Operator Module
Pre-condition:
    - Authenticated Operator access on Account is currently authorized.
Post-condition:
    - The transfer is finalized, balance on both account reflect the change.
Basic Flow:
    1. The operator initiates a transfer money request.
    2. The operator is prompted to enter the account they wish to transfer money from.
    3. The operator enters the desired amount to transfer.
    4. Client call message to send request to Server using that data.
    5. The Server receives request.
    6. The Server call Account's transfer method.
    7. Server fulfill request to Client.
    8. Client invoke correct GUI showing result.
Alternate Flows:
    1. Account's transfer method deny due to lack balance or minimum balance unfulfilled.
    2. Server deny request.
    3. Client trigger correct response.
    4. Events logged.
Exceptions:
    - The operator has met the account transfer limit.
    - Account lack balance
    - Account locked
    - Minimum amount not met.
Related Use Cases: 
    Account tranfer
---

Client and Server section: 

Use Case ID: CS001
Use Case Name: Retrieve message from server
Relevant Requirements: 3.1.4.1, 3.1.4.2, 3.1.
Primary Actor: 
    - Message Module
Pre-condition:
    - The information requested is available in the bank account.
Post-condition: 
    - The information asked for will be made available to the client.
Basic Flow:
    1. The client makes a request using the message module.
    2. The server processes the request and sends a response to the client.
    3. The client receives the response and uses it as needed.
Alternate Flows: 
Exceptions: 
    - The server is down.
    - The server doesn't exist.
Related Use Cases:
    - Retrieve cash, deposit cash, M001

---

Use Case ID: CS002
Use Case Name: Send message to server
Relevant Requirements: 3.1.4.1, 3.1.4.2, 3.1.4.3,  3.1.4.5.
Primary Actor: 
    - Message Module
Pre-condition:
    - The message to send has been initialized with proper information.
Post-condition: 
    - The message is sent to the server.
Basic Flow:
    1. The operator makes a request (e.g., deposit cash).
    2. The request is packed and sent via the message module.
    3. The request is received by the server module and unpacked.
Alternate Flows: 
    - None
Exceptions:
    - This module is likely entirely private with the sole purpose of moving back and forth between the server and the client.
Related Use Cases: 
    - Retrieve cash, deposit cash, M002

---

Use Case ID: CS001
Use Case Name: Log in
Relevant Requirements: 3.1.2.1
Primary Actor: 
    - Client Module
Pre-condition: 
    - The operator is not authenticated.
Post-condition: 
    - The operator is authenticated.
Basic Flow: 
    1. Client show operator a log in interface.
    2. The operator enters their authenticator.
    3. Client invoke message to send request to Server using authenticator.
    4. Server pass authenticator to Operator modules to authenticate.
    5. Once success, a read-only version of the authenticated Operator is return to Client.
    3. The operator is authenticated.
Alternate Flows: 
    1. The authentication failed.
    2. Server denies request.
    3. Client invoke correct GUI response.
    4. Events are logged

Exceptions: 
    - If operator doesn't exist, response same as authentication failed.
Related Use Cases: 
    - Account authorization

---

Messaging Module Use Cases:

Use Case ID: M001
Use Case Name: Sending Message
Relevant Requirements: 3.1.4.2, 3.1.4.3, 3.1.4.4, 3.1.4.5, 3.1.4.5.1, 3.1.4.5.4
Primary Actor: Sender 
Pre-condition: 
    Sender is called and provided data, current address, receiving address are provided (3.1.4.3)
Post-condition: 
    Package is sent
Basic Flow: 
    1. Sender is called and fork a Sender child
    2. Child call Processor to encapsulate a package
    3. Processor return package ready to be sent
    4. Child read receiving address on package and make a connection
    5. Once connection is made, package is sent
    6. Kill Child
Alternate Flows:      
Exceptions: 
    1. If Sender is envoked with lacking arguements, throw error according to UI 
    2. The server is not connected to the internet. 
    3. The client is not connected to the internet. 
Related Use Cases: Receiving Message, Encapsulate data

---

Use Case ID: M002
Use Case Name: Receiving Message
Relevant Requirements: 3.1.4.2, 3.1.4.3, 3.1.4.4, 3.1.4.6, 3.1.4.6.1, 3.1.4.6.3
Primary Actor: Receiver
Pre-condition: 
    Receiver sit on designated network and listen for connections
Post-condition: 
    package received and decapsulated
Basic Flow: 
    1. Receiver fork a child upon handling an incoming package on listened port
    2. Assign the child to handle that request
    3. Child receive package from connection
    4. Child call Processor to decapsulate the package
    5. Child returns encapsulated data
    6. Kill Child.
Alternate Flows: 
Exceptions:   
    1. The server is not connected to the internet. 
    2. The client is not connected to the internet. 
    3. The server returns an unrecognized response. 
Related Use Cases: Sending Message, Decapsulate data

Use Case ID: M003
Use Case Name: Encapsulate data
Relevant Requirements:  
Primary Actor: Processor
Pre-condition: Processor get called with data
Post-condition: Processor returns a package
Basic Flow:
    1. Processor get called with data
    2. Processor fork a child to process data
    3. Child create a package
    4. Kill Child, return package
Alternate Flows: 
Exceptions: 
Related Use Cases: Sending Message

Use Case ID: M004
Use Case Name: Decapsulate data
Relevant Requirements: 3.1.4.4, 3.1.4.5.1 .
Primary Actor: Processor
Pre-condition: Processor get called with a package
Post-condition: Processor returns encapsulated data
Basic Flow: 
    1. Processor get called with package
    2. Processor fork a child to process package
    3. Child parse and tokenize package
    4. Kill Child, return data
Alternate Flows: 
Exceptions: 
Related Use Cases: M002

---

Account use cases:

Use Case ID: A001
Use Case Name: Account authorization
Relevant Requirements: 3.1.2.1 .
Primary Actor: 
    - Operator module
Pre-condition: 
    - The operator has input their credentials on the client.
Post-condition: 
    - The operator is authenticated.
Basic Flow: 
    1. Operator select interface to access account.
    2. Client invoke Message to handle request to the Server for Authorization.
    3. The server invoke Account method to check Operator authorization.
    4. Once authorized, Server fulfill request.
    5. Events are logged.
Alternate Flows: 
    1. Authorization denied.
    2. Server deny request.
    3. Client invoke GUI response.
    4. Events are logged.
Exceptions: 
    - Account dont exist, invoke correct client response.
Related Use Cases: 
    - Log in

---

Use Case ID: A002
Use Case Name: Account transfer
Relevant Requirements: 
    - recived a request of transfering
Primary Actor: 
    - Account module
Pre-condition: 
    - Account is accessing by authorized operator.
    - Server invoke account transfer method.
Post-condition: 
    - Account transferred, balance update.
Basic Flow: 
    1. Checking current balance against transfer amount.
    2. Checking transfer constraint.
    3. Account initialized correct destination accounts.
    4. Update balance from both accounts to reflect change.
    5. Update new balance to files.
Alternate Flows:
Exceptions: 
    - Account dont exist, invoke correct client response.
    - Transfer constraint invoked, abort
Related Use Cases: 
    - Transfering request

Use Case ID: A003
Use Case Name: Creating an account
Relevant Requirements:
    - A user wants to create an account to be able to store
    their money in either a checkings or savings.
Primary Actor:
    - Client Module
    - Server Module
Pre-condition:
    - The user is in position where it is beneficial to create
    an account.
Post-condition:
    - The user has now made an account and has gained access to
further pursue a bank account.
Basic Flow:
    1. The user initiates an account creation request.
    2. The user provides necessary personal information.
    3. The user's identification begins to be verified.
    4. The superuser verifies the user's information.
    5. The user now has an account.
Alternate Flows:
    1. The user enters any type of wrong information during
    the process of wanting to create an account. So the user is
    prompted to make changes to the incorrect information.
    2. The user's request to create an account was rejected.
Exceptions:
    1. The user provides invalid personal information.
    2. The user's ID and password are already taken so the user
    must think of a different ID and password.
    3. The ID and or password don't meet bank's security requirements.
    4. The user doesn't meet the bank's requirements in order to
    create an account. (Ex: Age).
    Related Use Cases:
    Logging in, get authorized, send message to server, retrieve message
    from server

---

Use Case ID: A004
Use Case Name: Deleting an account
Relevant Requirements:
    - A user's account has reached a point in which it is not needed anymore.
Primary Actor:
    - Client Module
    - Server Module
Pre-condition:
    - The user's funds have been removed from the account and
    has been verified for deletion of account.
Post-condition:
    - The user's account has been deleted.
Basic Flow:
    1. Initialize deleting an account.
    2. User is authenticated to proceed with the deleting
    process.
    3. The account's information is under review and the user
    is notified of deleting an account.
    4. Funds are removed from the account, if any.
    5. The account has been deleted.
Alternate Flows:
    1. The user makes the decision of not wanting to delete
    their account.
    2. The account can't be deleted if the user has any type
    of outstanding charges.
Exceptions:
    1. The user fails to authenticate credentials.
    2. There are outstanding charges to the user's account.
    3. The server or client run into technical issues
    throughout the deletion process.
    4. The user's account has already been deleted.
    5. The user's account can't be deleted if the account's
    current status is locked, or restricted.
Related Use Cases:
    - Log in ,Unauthorized user

---

Use Case ID: A005
Use Case Name: Add a user to an account
Relevant Requirements: 3.1.3.15, 3.1.3.16.
Primary Actor: Account Module 
Pre-condition: An account exists and a user exists.
Post-condition: The account and user is connected and the user has access to the account.
Basic Flow: 
    1. The primary holder of the account logs-in from the Teller's interface. 
    2. The user provides the user ID of the user that is to be added. 
    3. The to be added user logs in through the teller interface. 
Alternate Flows: 
    none
Exceptions: 
    1. The user does not have the required access to the account. 
    2. The user is already added to the account. 
Related Use Cases:

Use Case ID: A006
Use Case Name: Remove a user from an account. 
Relevant Requirements: 3.1.3.15, 3.1.3.16.
Primary Actor: Client Module.
Pre-condition: An additional user is a part of an account.
Post-condition: The user is removed from the account. 
Basic Flow: 
    1. The primary holder of the account logs-in from the Teller's interface. 
    2. The user provides the user ID of the user that is to be deleted.
    3. The user's access to the account is erased. 
Alternate Flows: 
Exceptions: 
    1. The user does not have the required access to the account. 
    2. The user is already removed from the account. 
Related Use Cases:

---

Use Case ID: A007
Use Case Name: Accounts Module, Client Modules, 
Relevant Requirements: 3.1.2.4.3, 3.1.2.3.5 .
Primary Actor: Operator
Pre-condition: 
    1. The customer must be logged into the system with valid credentials.
    2. The customer must have at least one bank account registered.
    3. The customer initiates a request to view their transaction history.
Post-condition: 
    The transaction history is presented to the customer.
Basic Flow: 
    1. The customer navigates to the "Account Summary" or "Transactions" section.
    2. The system displays a default view of the most recent transactions for the selected account.
Alternate Flows: 
    No Transactions: If the account has no transactions for the selected date range, the system will display a “No transactions found” message.
    Filter Error: If the filters entered by the customer are invalid (e.g., future date), the system prompts the operator to correct the input.
Exceptions: 
    System downtime or unavailability might prevent transaction data from being retrieved.
    If there’s a lag in the backend systems, some recent transactions may not appear immediately.
Related Use Cases:

---
