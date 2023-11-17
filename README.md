# Spring Cloud: observability vs. task execution listener

This sample project demonstrates an incompatibility with task observability and task execution listener
implementations - either by implementing the `TaskExecutionListener` interface or using methods annotated
with `@BeforeTask`, `@AfterTask` or `@FailedTask`.

If your task execution listener implements the `ApplicationRunner` interface and task observability is enabled,
`ObservationApplicationRunnerBeanPostProcessor` wraps the task execution listener and the listener methods are no longer
invoked.
