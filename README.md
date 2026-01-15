# Invoice Generator Application

Invoice Generator Microservice: A Spring Boot service providing REST APIs to create, retrieve, and manage invoices using MongoDB. Supports dynamic PDF invoice generation and is container-ready for microservices deployment.

---

##  Tech Stack

* **Java**: 21
* **Spring Boot**: 4.0.1
* **MongoDB**:Local MongoDB
* **Spring Data MongoDB**
* **OpenPDF**: PDF generation
* **Maven**: Build tool
* **Docker**
* **GitHub Actions (CI/CD)**

---

## Docker & CI/CD Support

This project uses **GitHub Actions** to automatically **build and push the Docker image to Docker Hub** whenever new code is pushed to the `main` branch.

### Automated Workflow

**Trigger:**  
- Push to the `main` branch

**Actions Performed:**  
1. Checkout source code  
2. Build Docker image  
3. Push Docker image to Docker Hub

### Docker Image Repository

```
alfinakash/invoice-generator
```

### Latest Tag

```bash
docker login
docker push alfinakash/invoice-generator:latest
```

### Pull Docker Image from Docker Hub

```
docker pull alfinakash/invoice-generator:latest
```

Run Application Using Docker

```
docker run -p 8080:8080 \
  -e MONGODB_URI=<your_mongodb_uri> \
  alfinakash/invoice-generator:latest
```

Application will be available at:

```
http://localhost:8080
```
### Manual command for Docker build and Push to hub

```
docker build --no-cache -t alfinakash/invoice-generator:latest .
```

```
docker push alfinakash/invoice-generator:latest
```

---

## Deployment

| Property     | Value |
|-------------|-------|
| Environment | Production |
| Status      | Live |
| Base URL    | **https://invoice-generator-backend-service.onrender.com** |

> Replace `http://localhost:8080` with the production base URL when accessing APIs.

---

## Invoice PDF

[Invoice Pdf](images/Invoice-Pdf.pdf)

---

## API Endpoints

POST `/api/v1/invoices`

GET `/api/v1/invoices`

GET `/api/v1/invoices/{id}`

PUT `/api/v1/invoices/{id}`

DELETE `/api/v1/invoices/{id}`

GET `/api/v1/invoices/{id}/pdf`


<!--
- ### Request Body

```json
{
  "company": {
    "name": "Zoho Corporation Private Limited",
    "address": "DLF IT Park, Manapakkam, Chennai, Tamil Nadu 600089, India",
    "gstNumber": "33AAACZ2890K1ZP"
  },
  "customer": {
    "name": "AlfinAkash A",
    "email": "alfinakash@gmail.com",
    "address": "Tirunelveli, Tamil Nadu, India"
  },
  "items": [
    { "itemName": "Zoho One Subscription (1 Year)", "quantity": 10, "rate": 4500, "taxPercentage": 18 }
  ]
}

```
-->
---

## Configuration
#### Application Properties
```
spring.application.name=invoice-generator
server.port=8080
spring.data.mongodb.uri=mongodb://localhost:27017
spring.jackson.time-zone=UTC
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
logging.level.org.springframework.web=INFO
logging.level.org.springframework.data.mongodb=INFO
invoice.pdf.title=INVOICE
invoice.pdf.author=Azyrex

```

---

## Running the Application

### Prerequisites

* Java 21 installed
* MongoDB Atlas account or local MongoDB running

### Run Command

```bash
./mvnw clean spring-boot:run
```

Application will start at:

```
http://localhost:8080
```



<!-- ## Author

**[AlfinAkash](https://github.com/AlfinAkash)** -->




