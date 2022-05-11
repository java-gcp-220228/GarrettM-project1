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
- ~~Support on GCP~~ Backend is now on GCP

## Getting Started
The backend code is all on a GCP compute instance, deprecated instructions are now removed. The SQL instrutions will remain a part of this repository.
- Clone is repository
- Navigate to the front end folder, open the index.html file. It should open in your web browser of choice.
## Usage
- Login with any of these provided users "Username: manhunter; Password: martian" "Username: kidflash; Password: gozoom" "Username: robin; Password: nightwing".
- Recommend doing first. kidflash and robin are Employees, they can view all of their respective reimbursement requests. They can also filter their requests based on the status of said requests. There is a second page to submit requests, here they can fill out all appropriate details pertaining to the reimbursement request, and upload an image to said request. 
- Recommended doing second. manhunter is a Finance Manager, logged into this account you can see and filter all existing reimbursement requests. There is a second page to view all pending reimbursement requests, here you can approve or deny them.
- On all pages, you are able to log out.
## License
- This project uses the following license: [MIT License](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt)
