package task.manager.task_manager.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.function.BiConsumer;

@Configuration
public class ClientConfig {



    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    BiConsumer
}
