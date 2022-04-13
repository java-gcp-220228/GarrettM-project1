# Expense Reimbursement System (ERS)

## Description
A system for adding and managing Employee Reimbursements.

## Technologies Used
- PostgresSQL
- Javalin
- Junit
- Mockito
- HTML
- JavaScript

## Features
Features already implemented
- Login functionality, the login will lead the user to the appropriate page based on their role within the company
- Filtering, all users can filter the reimbursements they see based on whether they are Pending, Reject, Accepted, or Everything
- Normal Employees can submit new requests for reimbursement, this includes a photograph of their receipt
- Finance Managers can choose to Accept or Reject pending requests.

To-do list:
- Password Hashing
- Support on GCP

## Getting Started
In its current state, the program runs locally. A copy of the SQL commands is included [Here](https://github.com/java-gcp-220228/GarrettM-project1/blob/main/SQL%20commands.md). You will want to have dBeaver and IntelliJ installed, though you can build and launch the code from a CLI.
- Clone this repository
- Open your SQL editor. Make sure environment variables are set up. Run the [linked](https://github.com/java-gcp-220228/GarrettM-project1/blob/main/SQL%20commands.md) commands to create and populate the tables, the tables will be set up now. This uses PostgresSQL
- Open the backend folder (named java) in IntelliJ, run the driver class. Alternately you can run this as a jar file
  - Open the backend parent folder (named java). 
  - Open that page in your CLI (like GitBash) run the command *./gradlew build*
  - Navigate in GitBash to Build/lib. There will be a jar file named java.1.0.SNAPSHOT.jar run type in and run *java -jar java-1.0-SNAPSHOT.jar* to run the jar file.
- Navigate to the front end folder, open the index.html file. It should open in your web browser.
## Usage
- Login with any of these provided users "Username: manhunter; Password: martian" "Username: kidflash; Password: gozoom" "Username: robin; Password: nightwing".
- Recommend doing first. kidflash and robin are Employees, they can view all of their respective reimbursement requests. They can also filter their requests based on the status of said requests. There is a second page to submit requests, here they can fill out all appropriate details pertaining to the reimbursement request, and upload an image to said request. 
- Recommended doing second. manhunter is a Finance Manager, logged into this account you can see and filter all existing reimbursement requests. There is a second page to view all pending reimbursement requests, here you can approve or deny them.
- On all pages, you are able to log out.
## License
- This project uses the following license: [MIT License](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt)
