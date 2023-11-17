package com.example.tasklistenerexecutorbug;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

public class TaskListenerExecutorBugTests {

  @SpringBootTest
  @SuppressWarnings("JUnitMalformedDeclaration")
  abstract static class BehaviourTest {
    @Autowired private BeanFactory beanFactory;

    @Test
    void taskExecutionListenerMethodsShouldBeInvoked() {
      // wait until our test task was executed
      await().atMost(Duration.ofSeconds(5)).until(FooTaskRunner::wasServiceExecuted);

      // verify that beforeTask and afterTask was called by TaskListenerExecutor
      var softly = new SoftAssertions();
      softly.assertThat(FooTaskRunner.wasBeforeTaskExecuted()).isTrue();
      softly.assertThat(FooTaskRunner.wasAfterTaskExecuted()).isTrue();

      // the problem is that ObservationApplicationRunnerBeanPostProcessor wraps our FooTaskRunner
      // into an ObservationApplicationRunnerBeanPostProcessor and TaskListenerExecutorObjectFactory
      // can no longer detect the annotated methods
      softly.assertThat(beanFactory.getBean("fooTaskRunner")).isInstanceOf(FooTaskRunner.class);

      softly.assertAll();
    }
  }

  @Nested
  @DirtiesContext
  @TestPropertySource(properties = {"spring.cloud.task.observation.enabled=true"})
  class ConfigWithSpringCloudObservabilityTest extends BehaviourTest {}

  @Nested
  @DirtiesContext
  @TestPropertySource(properties = {"spring.cloud.task.observation.enabled=false"})
  class ConfigWithoutSpringCloudObservabilityTest extends BehaviourTest {}
}
