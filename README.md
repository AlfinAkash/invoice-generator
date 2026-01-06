# Invoice Generator Application

A Spring Boot–based Invoice Generator application that provides REST APIs to create, retrieve, and manage invoices using MongoDB. The application also supports PDF invoice generation.

---

##  Tech Stack

* **Java**: 21
* **Spring Boot**: 4.0.1
* **MongoDB**:Local MongoDB
* **Spring Data MongoDB**
* **OpenPDF**: PDF generation
* **Maven**: Build tool

---

##  Project Structure

```
invoice-generator
│── src/main/java
│   └── com.techlambdas.invoice_generator
│       ├── controller
│       │   └── InvoiceController.java
│       ├── service
│       │   └── InvoiceService.java
│       ├── repository
│       │   └── InvoiceRepository.java
│       ├── model
│       │   └── Invoice.java
│       ├── exception
│       │   └── InvoiceNotFoundException.java
│       └── InvoiceGeneratorApplication.java
│
│── src/main/resources
│   ├── application.properties
│
│── pom.xml
│── README.md
```

---

## Screenshots

### Create Invoice
![Create Invoice](images/CreateInvoice.png)

### Get All Invoice
![Get All Invoice](images/GetAllInvoice.png)

### GetInvoiceById
![Get Invoice By Id](images/GetInvoiceById.png)

### UpdateInvoiceById
![Get All Invoice](images/UpdateINvoiceById.png)

### DeleteInvoiceById
![Delete Invoice By Id](images/DeleteInvoiceById.png)

### GetInvoicePdfById
![Get Invoice Pdf By Id](images/GetInvoicePdfById.png)


##  Configuration

Update **`application.properties`** with your MongoDB connection details:

```properties
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
invoice.pdf.author=TechLambdas Pvt Ltd

```


---

## ▶️ Running the Application

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

---


## 👤 Author

**AlfinAkash**

