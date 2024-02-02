package com.example.workflow;

import camundajar.impl.scala.App;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
//  @Autowired
//  SomeService someService;

  @Autowired
  RuntimeService runtimeService;

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);

  }

//  public void printVariables(){
////    System.out.println("-------------------");
//    System.out.println(someService.getProcessInstances());
//  }

//  public void deleteProcessInstance(){
//    someService.delete();
//  }

  @Override
  public void run(String... args) throws Exception {
//    printVariables();
//    someService.runExecutionQuery();
//    System.out.println(someService.getActivityInstance());
//    System.out.println("jobs------------------------->" + someService.getJobDefinitionQuery());
    runtimeService.startProcessInstanceByKey("my-project-process");

  }
}