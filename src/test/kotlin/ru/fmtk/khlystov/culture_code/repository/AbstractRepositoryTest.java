package ru.fmtk.khlystov.culture_code.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

@EnableConfigurationProperties
@ComponentScan({"ru.fmtk.khlystov.culture_code.config"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractRepositoryTest {
}
