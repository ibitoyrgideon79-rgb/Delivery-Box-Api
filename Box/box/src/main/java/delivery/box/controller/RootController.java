package delivery.box.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "service", "box",
                "status", "ok",
                "endpoints", List.of(
                        "/api/boxes",
                        "/api/boxes/{boxId}/load",
                        "/api/boxes/{boxId}/items",
                        "/api/boxes/available",
                        "/api/boxes/{boxId}/battery",
                        "/h2-console"
                )
        );
    }
}

