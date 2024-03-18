package com.syscode.syscodedemo.integration.util;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ComponentScan(basePackages = "com.syscode")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class IntegrationTest {
}
