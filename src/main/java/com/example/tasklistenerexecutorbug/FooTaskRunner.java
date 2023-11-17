package com.example.tasklistenerexecutorbug;

import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.stereotype.Component;

@Component
public class FooTaskRunner implements ApplicationRunner {

  private static final Logger LOG = LoggerFactory.getLogger(FooTaskRunner.class);
  private static final AtomicBoolean beforeTaskWasExecuted = new AtomicBoolean(false);
  private static final AtomicBoolean afterTaskWasExecuted = new AtomicBoolean(false);
  private static final AtomicBoolean serviceWasExecuted = new AtomicBoolean(false);

  private final FooService fooService;

  private Long executionId;

  public FooTaskRunner(FooService fooService) {
    this.fooService = fooService;
    beforeTaskWasExecuted.set(false);
    afterTaskWasExecuted.set(false);
    serviceWasExecuted.set(false);
  }

  @BeforeTask
  public void beforeTask(TaskExecution taskExecution) {
    LOG.info("beforeTask was called");
    beforeTaskWasExecuted.set(true);
    executionId = taskExecution.getExecutionId();
  }

  public static boolean wasBeforeTaskExecuted() {
    return beforeTaskWasExecuted.get();
  }

  @AfterTask
  public void afterTask(TaskExecution taskExecution) {
    LOG.info("afterTask was called");
    afterTaskWasExecuted.set(true);
  }

  public static boolean wasAfterTaskExecuted() {
    return afterTaskWasExecuted.get();
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    fooService.work(executionId);
    serviceWasExecuted.set(true);
  }

  public static boolean wasServiceExecuted() {
    return serviceWasExecuted.get();
  }
}
