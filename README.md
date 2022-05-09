# pis-hospital-server

## Setup

1. OpenJDK 11
   - Download from https://www.oracle.com/java/technologies/javase-jdk11-downloads.html or install it by running:
     ```
     sudo dnf install java-11-openjdk.x86_64 
     ```

2. PostgreSQL
   - Install the required packages:
     ```
     sudo dnf install -y postgresql12 postgresql12-server postgresql12-contrib
     ```
   - Start the service:
     ```
     sudo systemctl start postgresql-12
     ```
   - Run the following commands:
     ```
     sudo -i -u postgres

     psql
     ```
   - Set up password `password` and add admin extensions
     ```
     \password postgres
   
     CREATE EXTENSION adminpack;
     ```
   - Download the latest JDBC driver (PostgreSQL JDBC 4.2 Driver, 42.2.19) from https://jdbc.postgresql.org/download.html.

3. Payara
   - Download and unzip the community Payara Server (payara-5.2021.1.zip) from https://www.payara.fish/downloads/#community.
   - Run the server by executing:
     ```
     payara5/bin/asadmin start-domain
     ```
   - Add the JDBC driver in the `asadmin` environment:
     ```
     add-library /path/to/download/jdbcdriver.jar
     ```
   - Open the Payara server at `http://localhost:4848/` and follow the instructions at https://blog.payara.fish/using-postgresql-with-payara-server until `Now, you can inject a reference...`. Set the name for JDBC Resources to `jdbc/postgresql` instead of `jdbc/postgrespool`.

4. Eclipse
   - Download and install Eclipse from https://www.eclipse.org/downloads/.
   - Navigate to Help->Install New Software, enter `http://download.eclipse.org/mpc/mars/`, and install Eclipse Marketplace
   - Install Maven plugin via Eclipse Marketplace
   
## Initial Run

1. Clone this repository and open it in Eclipse (File->Open projects from filesystem).
2. Configure Payara Server by following the instructions at https://docs.payara.fish/enterprise/docs/5.25.0/documentation/ecosystem/eclipse-plugin.html.
3. Right click on `hospital`->Run as->Run on server. Select the server and deploy the application.
4. Open the application at `http://localhost:8080`. And try to access the following endpoints (an Authorization header is required, e.g., `Authorization: Basic endcoded(admin:admin)`):
   - http://localhost:8080/hospital/resources/patients
   - http://localhost:8080/hospital/resources/departments

## Deployment Errors

- When modifying already applied migrations, it is not possible to properly deploy the application on Payara. Resolve the problem by recreating the database:
  ```
  sudo -i -u postgres
  dropdb postgres
  createdb --owner=postgres postgres
  ```

- Changes in the Java Persistence classes may not be visible after publishing. Resolve the problem by deleting the folder `generated` which contains cached data:
  ```
  cd payara5/glassfish/domains/domain1/
  rm -rf generated/
  ```
