# XML Uploader Project

## Overview

The **XML Uploader** is a Java-based Spring application designed to process XML files. It validates, parses, and stores the parsed data into a MariaDB database. 
### Key Features
- Upload XML files via a web interface.
- Validate XML files against an XSD schema.
- Parse and process XML data.
- Store parsed data in a database (e.g., books).


---


## Setup and Installation

### Prerequisites

Before you can run the project, ensure you have the following installed:

- Java 17+
- Maven 3+
- MariaDB

### Clone the Repository

```bash
git clone https://github.com/videjoost/XmlUploader.git
cd xml-uploader
```

### Configuration

1. **Database Configuration**: Update the `application.properties` file with your database credentials. This is located in `src/main/resources/`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/xmluploader
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# Hibernate configurations
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

2. **XML Schema (XSD)**: Ensure your XML follows the correct structure as defined by the schema (`book.xsd`). You can modify or add new XML structures in the `/resources/` folder as needed.

### Building the Project

Run the following Maven command to package the project:

```bash
mvn clean install
```

### Running the Application

Start the application using the following Maven command:

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:9080`.

---

## Usage

### 1. **Uploading XML Files**

Visit the homepage at `http://localhost:9080/` to upload XML files for processing.

The files should conform to the required XML structure and will be validated and parsed upon upload.

### 2. **Supported XML Structure**

The application currently supports processing XML files with a `<catalog>` root tag and multiple `<book>` items. Each book must have attributes such as `id`, `author`, `title`, `genre`, etc.

Example of a supported XML structure:

```xml
<?xml version="1.0"?>
<catalog>
   <book id="bk101">
      <author>Gambardella, Matthew</author>
      <title>XML Developer's Guide</title>
      <genre>Computer</genre>
      <price>44.95</price>
      <publish_date>2000-10-01</publish_date>
      <description>An in-depth look at creating applications with XML.</description>
   </book>
</catalog>
```
---

## Design Patterns Used

### Factory Pattern
- The **XmlProcessorFactory** dynamically creates different processors based on the XML type. For instance, the `BookXmlProcessor` handles book-related XML, while future processors can be added for different structures.

### Strategy Pattern
- The **XmlValidator** uses different validation strategies for XML validation.

### Service Layer
- The business logic, including processing XML uploads, validation, and database interaction, is contained within service classes (e.g., `XmlProcessingService`).

---
## Contact

For any questions, reach out at:

- **Email**: videjoost@gmail.com
- **GitHub**: [videjoost](https://github.com/videjoost)

