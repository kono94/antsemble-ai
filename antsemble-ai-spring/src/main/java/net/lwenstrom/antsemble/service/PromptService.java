package net.lwenstrom.antsemble.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromptService {

    @Qualifier("defaultChatModel")
    private final ChatModel defaultChatModel;

    public String generateStory(String character, String setting, String genre) {
        var promptText = """
                You are a creative writer. Write a short story with the following parameters:
                
                Character: {character}
                Setting: {setting}
                Genre: {genre}
                
                Create an engaging story of approximately 200-300 words that incorporates all these elements.
                """;

        var promptTemplate = new PromptTemplate(promptText);
        var prompt = promptTemplate.create(Map.of(
                "character", character,
                "setting", setting,
                "genre", genre
        ));

        return ChatClient.create(defaultChatModel)
                .prompt(prompt)
                .call()
                .content();
    }

    public String translateText(String text, String targetLanguage) {
        var promptText = """
                Translate the following text to {targetLanguage}:
                
                {text}
                
                Provide only the translation without any additional commentary.
                """;

        var promptTemplate = new PromptTemplate(promptText);
        var prompt = promptTemplate.create(Map.of(
                "text", text,
                "targetLanguage", targetLanguage
        ));

        return ChatClient.create(defaultChatModel)
                .prompt(prompt)
                .call()
                .content();
    }

    public String explainConcept(String concept, String audience) {
        var promptText = """
                You are an expert educator. Explain the concept of "{concept}" 
                in a way that is appropriate for: {audience}
                
                Use clear examples and analogies where helpful.
                Keep the explanation engaging and easy to understand.
                """;

        var promptTemplate = new PromptTemplate(promptText);
        var prompt = promptTemplate.create(Map.of(
                "concept", concept,
                "audience", audience
        ));

        return ChatClient.create(defaultChatModel)
                .prompt(prompt)
                .call()
                .content();
    }
}
