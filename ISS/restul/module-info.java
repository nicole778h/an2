module com.example.iss_45 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.persistence;
    requires org.jboss.logging;
    requires java.logging;
    requires java.xml.bind;
    requires com.fasterxml.classmate;

    opens com.example.iss_45 to javafx.graphics, javafx.fxml, javafx.base;
    exports com.example.iss_45;
    opens com.example.iss_45.controller to javafx.graphics, javafx.fxml, javafx.base;
    exports com.example.iss_45.controller;

     //Export the com.example.iss_45.repository.org package
    exports com.example.iss_45.repository.org to org.hibernate.orm.core;
    opens com.example.iss_45.domain to org.hibernate.orm.core;

    requires net.bytebuddy;
}
