package com.example.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.engine.variable.value.IntegerValue;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.SerializationDataFormat;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Encoding;

public class HelloWorld implements JavaDelegate {



    @Override
    public void execute(DelegateExecution execution) throws Exception {

        RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();

        // Your Java logic for the service task goes here
//        System.out.println("Service Task Executed by MyJavaDelegate");
        execution.setVariable("variable1", 2);

        execution.setVariable("variable2", 4);

        IntegerValue variable2 = execution.getVariableTyped("variable2");

        System.out.println("printing typed variable " + variable2);

        System.out.println("getting all variables " + execution.getVariables().values());

        com.example.workflow.Order order = new com.example.workflow.Order();
        runtimeService.setVariable(execution.getId(), "order", order);
        com.example.workflow.Order retrievedOrder = (com.example.workflow.Order) runtimeService.getVariable(execution.getId(), "order");
        System.out.println("order is ......" + retrievedOrder.getName() + " execution id is " + execution.getId());

        StringValue typedStringValue = Variables.stringValue("a string value");
        runtimeService.setVariable(execution.getId(), "stringVariable", typedStringValue);

        StringValue retrievedTypedStringValue = runtimeService.getVariableTyped(execution.getId(), "stringVariable");
        String stringValue = retrievedTypedStringValue.getValue(); // equals "a string value"

        System.out.println(stringValue);

        FileValue typedFileValue = Variables
            .fileValue("addresses.txt")
            .file(new File("hello.txt"))
            .mimeType("text/plain")
            .encoding("UTF-8")
            .create();
        runtimeService.setVariable(execution.getId(), "fileVariable", typedFileValue);

        FileValue retrievedTypedFileValue = runtimeService.getVariableTyped(execution.getId(), "fileVariable");
        InputStream fileContent = retrievedTypedFileValue.getValue(); // a byte stream of the file contents
        String fileName = retrievedTypedFileValue.getFilename(); // equals "addresses.txt"
        String mimeType = retrievedTypedFileValue.getMimeType(); // equals "text/plain"
        String encoding = retrievedTypedFileValue.getEncoding(); // equals "UTF-8"

        System.out.println(fileName + " ---- " + mimeType + "-------"+ encoding + "content: " + fileContent);

       printFile(fileContent,retrievedTypedFileValue.getEncoding());



        InputStream newContent = new FileInputStream("world.txt");
       FileValue fv2=  Variables.fileValue(retrievedTypedFileValue.getFilename())
            .file(newContent)
            .encoding(encoding)
            .mimeType(mimeType).create();

        runtimeService.setVariable(execution.getId(), "fileVariable", fv2);
        FileValue retrievedTypedFileValue2 = runtimeService.getVariableTyped(execution.getId(), "fileVariable");
        printFile(retrievedTypedFileValue2.getValue(), retrievedTypedFileValue2.getEncoding());


        com.example.workflow.Order order2 = new com.example.workflow.Order();
        ObjectValue typedObjectValue = Variables.objectValue(order2)
            .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
            .create();
        runtimeService.setVariableLocal(execution.getId(), "ordertyped", typedObjectValue);

        ObjectValue retrievedTypedObjectValue = runtimeService.getVariableTyped(execution.getId(), "ordertyped");
        com.example.workflow.Order retrievedOrder2 = (com.example.workflow.Order) retrievedTypedObjectValue.getValue();
        System.out.println(retrievedOrder2.getName());


        // returns true
        boolean isDeserialized = retrievedTypedObjectValue.isDeserialized();
        System.out.println(isDeserialized);

// returns the format used by the engine to serialize the value into the database
        String serializationDataFormat = retrievedTypedObjectValue.getSerializationDataFormat();
        System.out.println(serializationDataFormat);

// returns the serialized representation of the variable; the actual value depends on the serialization format used
        String serializedValue = retrievedTypedObjectValue.getValueSerialized();
        System.out.println(serializedValue);

// returns the class com.example.Order
        Class<com.example.workflow.Order> valueClass = (Class<Order>) retrievedTypedObjectValue.getObjectType();
        System.out.println(valueClass);

// returns the String "com.example.Order"
        String valueClassName = retrievedTypedObjectValue.getObjectTypeName();
        System.out.println(valueClassName);


        VariableMap variables =
            Variables.createVariables()
                .putValueTyped("order", Variables.objectValue(order).create())
                .putValueTyped("string", Variables.stringValue("a string value"))
                .putValueTyped("stringTransient", Variables.stringValue("foobar", true));
        runtimeService.setVariablesLocal(execution.getId(),  variables);


        }


        void printFile(InputStream fileContent, String encoding){
            try (Scanner scanner = new Scanner(fileContent, encoding)) {
                // Read the content from the InputStream and print it line by line
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                }
            } catch (Exception e) {
                // Handle encoding exception
                e.printStackTrace();
            } finally {
                // Close the InputStream to release resources
                try {
                    fileContent.close();
                } catch (IOException e) {
                    // Handle IO exception
                    e.printStackTrace();
                }
            }
        }
}
