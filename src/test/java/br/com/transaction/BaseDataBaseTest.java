package br.com.transaction;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Tag("integration-test")
@DataJpaTest
public class BaseDataBaseTest extends BaseTest {
}
