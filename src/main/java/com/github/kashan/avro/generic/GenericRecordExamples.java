package com.github.kashan.avro.generic;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecordBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericRecordExamples {
    public static void main(String[] args) {

        final Logger LOGGER = LoggerFactory.getLogger(GenericRecordExamples.class);


    //Step 0: Define the schema
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                "     \"type\": \"record\",\n" +
                "     \"namespace\": \"com.example\",\n" +
                "     \"name\": \"Customer\",\n" +
                "     \"doc\": \"Avro Schema for our Customer\",     \n" +
                "     \"fields\": [\n" +
                "       { \"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of Customer\" },\n" +
                "       { \"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last Name of Customer\" },\n" +
                "       { \"name\": \"age\", \"type\": \"int\", \"doc\": \"Age at the time of registration\" },\n" +
                "       { \"name\": \"height\", \"type\": \"float\", \"doc\": \"Height at the time of registration in cm\" },\n" +
                "       { \"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight at the time of registration in kg\" },\n" +
                "       { \"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the user is enrolled in marketing emails\" }\n" +
                "     ]\n" +
                "}");

    //step 1:  Create a generic record
        GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
        customerBuilder.set("first_name", "Kashan");
        customerBuilder.set("last_name", "Ali");
        customerBuilder.set("age", 25);
        customerBuilder.set("height", 170f);
        customerBuilder.set("weight", 80.5f);
        //automated_email will get the default value that is "true"'

        GenericData.Record customer = customerBuilder.build();
        LOGGER.info("customer");
        System.out.println(customer);

    //Step 2: Write that generic record to a file
    //Step 3: Read a generic record from a file
    //Step 4: Interpret as a generic record


    }
}
