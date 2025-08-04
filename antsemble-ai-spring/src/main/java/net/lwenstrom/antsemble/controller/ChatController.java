package net.lwenstrom.antsemble.controller;

import lombok.RequiredArgsConstructor;
import net.lwenstrom.antsemble.model.FootballPlayer;
import net.lwenstrom.antsemble.model.Provider;
import net.lwenstrom.antsemble.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/openai")
    public String chatWithOpenAI(@RequestParam String message) {
        return chatService.chatWithOpenAI(message);
    }

    @GetMapping("/ollama")
    public String chatWithOllama(@RequestParam String message) {
        return chatService.chatWithOllama(message);
    }

    @GetMapping("/default")
    public String chatWithDefault(@RequestParam String message) {
        return chatService.chatWithDefault(message);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> streamChat(@RequestParam String message, 
                                  @RequestParam(required = false) Provider provider) {
        return chatService.streamChat(message, provider);
    }

    @GetMapping("/player/{name}")
    public FootballPlayer generatePlayer(@PathVariable String name) {
        return chatService.generateFootballPlayer(name);
    }

    @PostMapping("/analyze")
    public String analyzeWithContext(@RequestParam String topic, 
                                   @RequestBody String context) {
        return chatService.analyzeWithContext(topic, context);
    }

    @GetMapping("/compare")
    public String compareModels(@RequestParam String question) {
        return chatService.compareModels(question);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Spring AI Chat Service is running! Available endpoints:\n" +
               "- /api/chat/openai?message=hello\n" +
               "- /api/chat/ollama?message=hello\n" +
               "- /api/chat/default?message=hello\n" +
               "- /api/chat/stream?message=hello&provider=openai\n" +
               "- /api/chat/player/{name}\n" +
               "- /api/chat/analyze?topic=topic (POST with context in body)\n" +
               "- /api/chat/compare?question=question";
    }
}
