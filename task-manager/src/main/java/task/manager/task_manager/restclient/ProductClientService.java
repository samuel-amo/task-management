package task.manager.task_manager.restclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductClientService {

    private final RestClient restClient;

    public ProductClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse getProductWithRestClient(Long productId) {
        String url = "/products/" + productId;
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(ProductResponse.class);
    }
}
