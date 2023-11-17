package com.example.tasklistenerexecutorbug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class TaskListenerExecutorBugApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskListenerExecutorBugApplication.class, args);
  }
}
