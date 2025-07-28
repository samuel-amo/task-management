package task.manager.task_manager.restclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final ProductClientService productClientService;

    public OrderController(ProductClientService productClientService) {
        this.productClientService = productClientService;
    }

    @GetMapping("/client/restclient/products/{id}")
    public ProductResponse getProductViaRestClient(@PathVariable Long id) {
        return productClientService.getProductWithRestClient(id);
    }
}
