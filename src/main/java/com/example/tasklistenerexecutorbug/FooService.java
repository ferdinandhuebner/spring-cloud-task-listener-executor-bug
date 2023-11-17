package com.example.tasklistenerexecutorbug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FooService {
  private static final Logger LOG = LoggerFactory.getLogger(FooService.class);

  public void work(Long executionId) {
    LOG.info("working with execution id {}", executionId);
  }
}
