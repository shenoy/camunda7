package com.example.workflow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ManagementService managementService;

    public List<ProcessInstance> getProcessInstances() {

        runtimeService.startProcessInstanceByKey("my-project-process");

        return runtimeService.createProcessInstanceQuery()
            .processDefinitionKey("my-project-process")
            .list();
    }

    public ActivityInstance getActivityInstance(){
        var list = getProcessInstances();
        return runtimeService.getActivityInstance(list.get(0).getRootProcessInstanceId());
    }

    public void delete(){
//        System.out.println("deleting process instance");
        runtimeService.deleteProcessInstance("my-project-process", "some useless reason");
    }

    public List<Execution> runExecutionQuery(){

        List<ProcessInstance> pelist = getProcessInstances();
        List<Execution> elist = new ArrayList<>();
        for(ProcessInstance p :  pelist){
            List<Execution> executions = runtimeService.createExecutionQuery()
                .processInstanceId(p.getId())
                .list();
            elist.addAll(executions);
        }

        return elist;
    }

    public List<Job> getJobQuery(){
       List<Job> jobs = managementService.createJobQuery()
            .duedateHigherThan(new Date(2024,1,27))
            .list();
       return jobs;
    }

    public List<JobDefinition> getJobDefinitionQuery(){
        List<JobDefinition> jobdefinitions =  managementService.createJobDefinitionQuery()
            .processDefinitionKey("my-project-process")
            .list();
        return jobdefinitions;
    }

    public void settingVariables(){

        List<Execution> executions = runExecutionQuery();

        for(Execution e : executions){

        }

    }

}
