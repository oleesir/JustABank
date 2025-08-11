# JustABank 

- This is a Java-based microservices banking system designed to manage core financial operations such as user authentication, account management,transactions and email notifications. The system is built using Spring Boot and follows a clean, scalable, and secure microservices architecture.

---





# 🏗️ Architecture Overview
The application is composed of four independent microservices:

## 🔐 Auth Service
- Handles user registration, login, and token validation.

- Issues and validates JWT tokens for securing communication between services.

- Uses Spring Security and integrates with the gateway for role-based access control.

## 💳 Account Service
- Manages user bank accounts.

- Supports operations like creating, updating, and changing the status of accounts (OPEN, SUSPENDED, CLOSED).

- Trusts security headers set by the gateway and populates the SecurityContext accordingly.

## 💸 Transaction Service
- Handles debit, credit, and transfer operations.

- Verifies account ownership and status via inter-service calls to the Account Service.

- Communicates securely using WebClient and propagates authentication headers for authorization.

## 🌐 API Gateway
- Serves as the entry point for all client requests.

- Routes requests to the appropriate downstream service.

- Implements custom JWT authentication filters to validate tokens and forward identity headers (x-userId, x-role) downstream.

- Centralized exception handling for unauthorized access.


---

## 🔐 Security

- JWT is validated at the **gateway level**.
- Downstream services **trust headers** and **populate the `SecurityContextHolder`**.
- Protected endpoints use `@PreAuthorize` to restrict access by role (e.g. `USER`,`STAFF`, `ADMIN`).
- Unauthorized access returns a JSON error message.

---

## 🧱 Services Overview

| Service             | Description                       |
|---------------------|-----------------------------------|
| **auth-service**     | Handles user authentication and registration |
| **account-service**  | Manages user accounts and balances |
| **transaction-service** | Handles deposits, withdrawals, and transfers |
| **email-service**     | Sends transactional emails       |

---


## 🧰 Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Web, Security, JPA
- Spring WebClient
- Protocol Buffers (Protobuf)
- PostgreSQL
- bitnami/kafka
- Docker
- Maven

---


## 📦 Prerequisites

Before you start, make sure you have installed:

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Project setup

```bash
$ git clone https://github.com/oleesir/JustABank.git
$ cd JustABank
```

### Build Springboot app
```
$ mvn clean install
```

### Build and start
```
$ docker compose up --build
```

### Stop all containers
```
$ docker compose down
```
---


### 🛠️ Troubleshooting
- Port conflicts: Ensure ports 4000-4004, 5001-5003, 9092, and 9094 are not in use.

- Kafka connection issues: Make sure services use kafka:9092 as the bootstrap server (internal Docker network).

- If container already exists, remove with:

```
$ docker rm -f <container-name>
```


## 🚦 Microservice Endpoints

### 🔐 `auth-service`
- `POST localhost:4000/api/v1/auth/register` — Register a new user
<pre>
  {
    "firstName":"John",
    "lastName":"Doe",
    "password":"password1234",
    "address":"toronto canada",
    "email":"just+test770@gmail.com"
}
</pre>

<pre> User registered successfully</pre>
- `POST localhost:4000/api/v1/auth/login` — Authenticate user
<pre>{
    "email":"just+test770@gmail.com",
     "password":"olisa1234"
    
}</pre>

 <pre>{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OTU2YWM2NS1hNDJiLTQ4ZDctYmYyZi0wZjg5N2YzZDBmMjMiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaXNzIjoiT2xpc2EiLCJhdWQiOiJqdXN0QUJhbmsiLCJpYXQiOjE3NTQzNTMwMjAsImV4cCI6MTc1NDQzOTQyMH0.a5U84Nd7gstjlY-RIuxYnKaolJW4Ut_yUEN1sJGh2E4"
}</pre>
- `GET localhost:4000/api/v1/auth/validate` — Validate token 
 <pre>{
    "id": "4956ac65-a42b-48d7-bf2f-0f897f3d0f23",
    "role": "ROLE_USER"
}</pre>
- `GET localhost:4000/api/v1/auth/user/{id}` — Get authenticated user in down stream service


---

---

### 🏦 `account-service`
- `POST localhost:4000/api/v1/accounts/new_account` — Create an account
<pre>{
"accountType":"checking"
}
</pre>

<pre>
{
"id": "1c1de80d-b2a9-432b-9f83-fda1a67e66c6",
"accountNumber": "15805447",
"userId": "4956ac65-a42b-48d7-bf2f-0f897f3d0f23",
"balance": 0.0,
"accountType": "CHECKING",
"accountStatus": "ACTIVE",
"timeStamp": "2025-08-05T00:22:27.473+00:00"
}
</pre>


- `GET localhost:4000/api/v1/accounts/{userId}` — Get account info

<pre>
{
    "id": "ec93e4e1-64ef-4743-a6f2-d8d40485b4ec",
    "accountNumber": "88947619",
    "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
    "balance": 0.00,
    "accountType": "CHECKING",
    "accountStatus": "ACTIVE",
    "timeStamp": "2025-08-05T15:10:01.973+00:00"
}
</pre>


- `PATCH localhost:4000/api/v1/accounts/{id}` — Update account status by admin

<pre>
{
 "accountStatus":"suspended"    
}
</pre>

<pre>
{
    "id": "ec93e4e1-64ef-4743-a6f2-d8d40485b4ec",
    "accountNumber": "88947619",
    "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
    "balance": 0.00,
    "accountType": "CHECKING",
    "accountStatus": "SUSPENDED",
    "timeStamp": "2025-08-05T15:10:01.973+00:00"
}
</pre>

- `GET localhost:4000/api/v1/accounts/my_accounts` - Get all accounts owned by a user
<pre>
{
    "pageNum": 0,
    "pageSize": 2,
    "totalPages": 5,
    "content": [
        {
            "id": "342b003a-f7cf-4352-b9e9-84dc633a0649",
            "accountNumber": "33033482",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "balance": 0.00,
            "accountType": "CHECKING",
            "accountStatus": "ACTIVE",
            "timeStamp": "2025-08-05T15:06:42.221+00:00"
        },
        {
            "id": "5872a5f2-ec23-4706-949f-3ea86e005117",
            "accountNumber": "45225453",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "balance": 0.00,
            "accountType": "CHECKING",
            "accountStatus": "ACTIVE",
            "timeStamp": "2025-08-05T14:45:23.498+00:00"
        }
    ]
}
</pre>


- `GET localhost:4000/api/v1/accounts?{pageNum}&{pageSize}` - Get all accounts by admin
<pre>
{
    "pageNum": 0,
    "pageSize": 3,
    "totalPages": 5,
    "content": [
        {
            "id": "ec93e4e1-64ef-4743-a6f2-d8d40485b4ec",
            "accountNumber": "88947619",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "balance": 0.00,
            "accountType": "CHECKING",
            "accountStatus": "SUSPENDED",
            "timeStamp": "2025-08-05T15:10:01.973+00:00"
        },
        {
            "id": "342b003a-f7cf-4352-b9e9-84dc633a0649",
            "accountNumber": "33033482",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "balance": 0.00,
            "accountType": "CHECKING",
            "accountStatus": "ACTIVE",
            "timeStamp": "2025-08-05T15:06:42.221+00:00"
        },
        {
            "id": "5872a5f2-ec23-4706-949f-3ea86e005117",
            "accountNumber": "45225453",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "balance": 0.00,
            "accountType": "CHECKING",
            "accountStatus": "ACTIVE",
            "timeStamp": "2025-08-05T14:45:23.498+00:00"
        }
    ]
}
</pre>

- `DELETE localhost:4000/api/v1/accounts/{id}` - Delete account by admin
<pre>
Successfully deleted.
</pre>



### 💸 `transaction-service`
- `POST localhost:4000/api/v1/transactions/deposit_or_withdraw` — Make deposit or withdrawal from an account
 <pre>
{
    "accountNumber": 82094155,
    "amount": 1000.00,
    "transactionPurpose": "DEPOSIT",
    "description": "add some money to savings"

}
</pre>

<pre>
{
    "userId": "8a923125-0dbf-4f34-8d20-6307d2da5d7f",
    "accountNumber": 82094155,
    "recipientAccountNumber": null,
    "amount": 1000.00,
    "transactionType": "CREDIT",
    "transactionPurpose": "DEPOSIT",
    "description": "add some money to savings",
    "referenceCode": "oL4icbDx98SY",
    "timeStamp": "2025-08-05T15:10:07.413+00:00"
}
</pre>


- `POST localhost:4000/api/v1/transactions/transfer` — Make transfer to other accounts
 <pre>
{
     "amount": 1000.00,
    "accountNumber": 25902109,
    "recipientAccountNumber": 33033482,
    "description":"transfer."

}
</pre>

<pre>
{
   "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
    "accountNumber": 25902109,
    "recipientAccountNumber": 33033482,
    "amount": 1000.00,
    "transactionType": "DEBIT",
    "transactionPurpose": "INTERNAL_TRANSFER",
    "description": "transfer.",
    "referenceCode": "MyNa3GuDon0Z",
    "timeStamp": "2025-08-05T17:52:15.816+00:00"
}
</pre>


- `get localhost:4000/api/v1/transactions/{id}` — Get a single transaction


<pre>
{
    "id": "9167b768-129c-4eb8-86bd-004497a39c9e",
    "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
    "amount": 1000.00,
    "transactionType": "CREDIT",
    "transactionPurpose": "INTERNAL_TRANSFER",
    "accountNumber": 25902109,
    "recipientAccountNumber": 33033482,
    "description": "transfer.",
    "referenceCode": "MyNa3GuDon0Z",
    "timeStamp": "2025-08-05T17:52:15.801+00:00"
}
</pre>

- `GET localhost:4000/api/v1/transactions/my_transactions?pageNum=1&pageSize=3` — View users transactions for an account

<pre>
{
    "pageNum": 0,
    "pageSize": 3,
    "totalPages": 2,
    "content": [
        {
            "id": "71a9cfad-343e-42ca-8478-ddd04bee5dd4",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "amount": 1000.00,
            "transactionType": "DEBIT",
            "transactionPurpose": "INTERNAL_TRANSFER",
            "accountNumber": 25902109,
            "recipientAccountNumber": 33033482,
            "description": "transfer.",
            "referenceCode": "MyNa3GuDon0Z",
            "timeStamp": "2025-08-05T17:52:15.805+00:00"
        },
        {
            "id": "9167b768-129c-4eb8-86bd-004497a39c9e",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "amount": 1000.00,
            "transactionType": "CREDIT",
            "transactionPurpose": "INTERNAL_TRANSFER",
            "accountNumber": 25902109,
            "recipientAccountNumber": 33033482,
            "description": "transfer.",
            "referenceCode": "MyNa3GuDon0Z",
            "timeStamp": "2025-08-05T17:52:15.801+00:00"
        },
        {
            "id": "c57083d9-e6e0-4706-bd8e-56371e3f687e",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "amount": 1000.00,
            "transactionType": "DEBIT",
            "transactionPurpose": "TRANSFER",
            "accountNumber": 25902109,
            "recipientAccountNumber": 82094155,
            "description": "transfer.",
            "referenceCode": "4meP9L6Xi2zU",
            "timeStamp": "2025-07-31T12:48:34.470+00:00"
        }
    ]
}
</pre>



- `GET localhost:4000/api/v1/transactions?pageNum=1&pageSize=3` — Admin can view users transactions.
<pre>
{
    "pageNum": 0,
    "pageSize": 3,
    "totalPages": 7,
    "content": [
        {
            "id": "233b7780-4ba2-4049-b422-f0deb543df4c",
            "userId": "8a923125-0dbf-4f34-8d20-6307d2da5d7f",
            "amount": 1000.00,
            "transactionType": "CREDIT",
            "transactionPurpose": "DEPOSIT",
            "accountNumber": 82094155,
            "recipientAccountNumber": null,
            "description": "add some money to savings",
            "referenceCode": "EACCQglaiFB0",
            "timeStamp": "2025-08-05T18:06:29.338+00:00"
        },
        {
            "id": "71a9cfad-343e-42ca-8478-ddd04bee5dd4",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "amount": 1000.00,
            "transactionType": "DEBIT",
            "transactionPurpose": "INTERNAL_TRANSFER",
            "accountNumber": 25902109,
            "recipientAccountNumber": 33033482,
            "description": "transfer.",
            "referenceCode": "MyNa3GuDon0Z",
            "timeStamp": "2025-08-05T17:52:15.805+00:00"
        },
        {
            "id": "9167b768-129c-4eb8-86bd-004497a39c9e",
            "userId": "3a524892-d70b-4163-9075-f9ccc34485a7",
            "amount": 1000.00,
            "transactionType": "CREDIT",
            "transactionPurpose": "INTERNAL_TRANSFER",
            "accountNumber": 25902109,
            "recipientAccountNumber": 33033482,
            "description": "transfer.",
            "referenceCode": "MyNa3GuDon0Z",
            "timeStamp": "2025-08-05T17:52:15.801+00:00"
        }
    ]
}
</pre>




