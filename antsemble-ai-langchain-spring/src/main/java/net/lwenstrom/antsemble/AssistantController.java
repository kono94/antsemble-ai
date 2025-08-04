package net.lwenstrom.antsemble;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssistantController {

    private final Assistant assistant;

    public AssistantController(Assistant assistant) {
        this.assistant = assistant;
    }

    @GetMapping("/model")
    public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
        return assistant.chat(message);
    }
}