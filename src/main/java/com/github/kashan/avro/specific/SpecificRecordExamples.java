package com.github.kashan.avro.specific;

import com.ericsson.rdi_tk.schema.events.avro.RdiDatastreamParserPmEvent;
import com.ericsson.rdi_tk.schema.events.avro.RdiDatastreamTransformedCounter;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificRecordExamples {

    public static void main(String[] args) {


        RdiDatastreamParserPmEvent.Builder rdiDsParserEventBuilder = RdiDatastreamParserPmEvent.newBuilder();

        List<Long> multipleValue= new ArrayList<Long>();
        multipleValue.add(0, (long) 1);
        List<Long> uncompressedValue = new ArrayList<Long>();
        uncompressedValue.add(0, (long) 1);


        RdiDatastreamTransformedCounter transformCounter = new RdiDatastreamTransformedCounter();

        Map<CharSequence,RdiDatastreamTransformedCounter> mapCounterStorage = new HashMap<CharSequence, RdiDatastreamTransformedCounter>() {{
            put("x", transformCounter);
            put("a", transformCounter);
        }};

        Map<CharSequence,CharSequence> mapAttributesStorage = new HashMap<CharSequence,CharSequence>() {{
            put("x", "y");
            put("a", "b");
        }};

        rdiDsParserEventBuilder.setCountersStorage(mapCounterStorage);
        rdiDsParserEventBuilder.setCustomerGlobalId("SwissCom");
        rdiDsParserEventBuilder.setFdn("fdn");
        rdiDsParserEventBuilder.setAttributesStorage(mapAttributesStorage);
        rdiDsParserEventBuilder.setSoftwareVersion("v1");
        rdiDsParserEventBuilder.setRopBeginTimestamp((long)1221);
        rdiDsParserEventBuilder.setRopLength((long)1221);

        RdiDatastreamParserPmEvent rdiDatastreamParserPmEvent = rdiDsParserEventBuilder.build();
        System.out.println("\nsuccessfully created the dummy parserOutput object");
        System.out.println(rdiDatastreamParserPmEvent.toString());


        // write it out to a file
        final DatumWriter<RdiDatastreamParserPmEvent> datumWriter = new SpecificDatumWriter<>(RdiDatastreamParserPmEvent.class);

        try (DataFileWriter<RdiDatastreamParserPmEvent> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(rdiDatastreamParserPmEvent.getSchema(), new File("lossless_schema_write.avro"));
            dataFileWriter.append(rdiDatastreamParserPmEvent);
            System.out.println("\nsuccessfully wrote avro file");
        } catch (IOException e){
            e.printStackTrace();
        }

        // read it from the file
        final File file = new File("lossless_schema_write.avro");
        final DatumReader<RdiDatastreamParserPmEvent> datumReader = new SpecificDatumReader<>(RdiDatastreamParserPmEvent.class);
        final DataFileReader<RdiDatastreamParserPmEvent> dataFileReader;
        try {
            System.out.println("\nReading our specific record");
            dataFileReader = new DataFileReader<>(file, datumReader);
            while (dataFileReader.hasNext()) {
                RdiDatastreamParserPmEvent readLosslessObject = dataFileReader.next();
                System.out.println(readLosslessObject.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
