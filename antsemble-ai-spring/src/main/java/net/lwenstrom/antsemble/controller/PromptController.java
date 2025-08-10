package net.lwenstrom.antsemble.controller;

import lombok.RequiredArgsConstructor;
import net.lwenstrom.antsemble.model.FootballPlayer;
import net.lwenstrom.antsemble.service.PromptService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prompts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @GetMapping("/player/{name}")
    public FootballPlayer generatePlayer(@PathVariable String name) {
        return promptService.generateFootballPlayer(name);
    }

    @GetMapping("/story")
    public String generateStory(
            @RequestParam String character, @RequestParam String setting, @RequestParam String genre) {
        return promptService.generateStory(character, setting, genre);
    }

    @PostMapping("/translate")
    public String translateText(@RequestParam String targetLanguage, @RequestBody String text) {
        return promptService.translateText(text, targetLanguage);
    }

    @GetMapping("/explain")
    public String explainConcept(@RequestParam String concept, @RequestParam String audience) {
        return promptService.explainConcept(concept, audience);
    }
}
