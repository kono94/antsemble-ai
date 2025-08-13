package net.lwenstrom.antsemble.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.lwenstrom.antsemble.model.FootballPlayer;
import net.lwenstrom.antsemble.model.TranslationRequest;
import net.lwenstrom.antsemble.model.TranslationResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptService {

    private static final ChatOptions FOOTBALL_PLAYER_CHAT_OPTIONS =
            ChatOptions.builder().temperature(0.1).build();

    private static final ChatOptions TRANSLATION_CHAT_OPTIONS =
            ChatOptions.builder().topK(10).temperature(0.1).build();

    @Qualifier("defaultChatModel")
    private final ChatModel defaultChatModel;

    @Value("classpath:/templates/football-player-generation-template.st")
    private Resource footPlayerTemplate;

    @Value("classpath:/templates/creative-writer-template.st")
    private Resource creativeWriterTemplate;

    @Value("classpath:/templates/explain-concept-template.st")
    private Resource explainConceptTemplate;

    @Value("classpath:/templates/translate-template.st")
    private Resource translateTemplate;

    public FootballPlayer generateFootballPlayer(String playerName) {
        var generatedPlayer = ChatClient.create(defaultChatModel)
                .prompt()
                .options(FOOTBALL_PLAYER_CHAT_OPTIONS)
                .user(it -> it.text(footPlayerTemplate).param("playerName", playerName))
                .call()
                .entity(FootballPlayer.class);

        if (generatedPlayer == null) {
            throw new IllegalStateException("Response could not be generated");
        }

        return generatedPlayer;
    }

    public String generateStory(String character, String setting, String genre) {
        return ChatClient.create(defaultChatModel)
                .prompt()
                .user(it -> it.text(creativeWriterTemplate)
                        .params((Map.of(
                                "character", character,
                                "setting", setting,
                                "genre", genre))))
                .call()
                .content();
    }

    public TranslationResponse translateText(TranslationRequest translationRequest) {
        return ChatClient.create(defaultChatModel)
                .prompt()
                .options(TRANSLATION_CHAT_OPTIONS)
                .user(it -> it.text(translateTemplate)
                        .params(Map.of(
                                "text", translationRequest.text(),
                                "targetLanguage", translationRequest.targetLanguage())))
                .call()
                .entity(TranslationResponse.class);
    }

    public String explainConcept(String concept, String audience) {
        return ChatClient.create(defaultChatModel)
                .prompt()
                .user(it -> it.text(explainConceptTemplate)
                        .params(Map.of(
                                "concept", concept,
                                "audience", audience)))
                .call()
                .content();
    }
}
