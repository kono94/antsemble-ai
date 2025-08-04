package net.lwenstrom.antsemble.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromptService {

    @Qualifier("defaultChatModel")
    private final ChatModel defaultChatModel;

    @Value("classpath:/templates/creative-writer-template.st")
    private Resource creativeWriterTemplate;

    @Value("classpath:/templates/explain-concept-template.st")
    private Resource explainConceptTemplate;

    @Value("classpath:/templates/translate-template.st")
    private Resource translateTemplate;

    public String generateStory(String character, String setting, String genre) {
        return ChatClient.create(defaultChatModel)
                .prompt()
                .user(it -> it.text(creativeWriterTemplate).params((Map.of(
                        "character", character,
                        "setting", setting,
                        "genre", genre
                ))))
                .call()
                .content();
    }

    public String translateText(String text, String targetLanguage) {
        return ChatClient.create(defaultChatModel)
                .prompt()
                .user(it -> it.text(translateTemplate).params(Map.of(
                        "text", text,
                        "targetLanguage", targetLanguage
                )))
                .call()
                .content();
    }

    public String explainConcept(String concept, String audience) {
        return ChatClient.create(defaultChatModel)
                .prompt()
                .user(it -> it.text(explainConceptTemplate).params(Map.of(
                        "concept", concept,
                        "audience", audience
                )))
                .call()
                .content();
    }
}
