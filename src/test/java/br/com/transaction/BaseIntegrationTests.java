package br.com.transaction;

import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.TransactionGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Tag("integration-test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTests extends BaseTest {

    private static final String BASE_URL = "http://localhost:8080/";

    protected static final String BASE_URL_ACCOUNTS = BASE_URL.concat("accounts/");

    protected static final String BASE_URL_TRANSACTIONS = BASE_URL.concat("transactions/");

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AccountGateway accountGatewayMock;

    @MockBean
    protected TransactionGateway transactionGateway;

    protected ResultActions requestGet(final UUID accountId, final URI path) throws Exception {
        return this.mockMvc.perform(get(path));
    }

    protected <T> ResultActions requestPost(final T body, final URI path) throws Exception {
        return this.mockMvc.perform(post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(body))
            .accept(MediaType.APPLICATION_JSON));
    }

}
