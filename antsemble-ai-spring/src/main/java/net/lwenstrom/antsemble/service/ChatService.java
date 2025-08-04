package net.lwenstrom.antsemble.service;

import lombok.RequiredArgsConstructor;
import net.lwenstrom.antsemble.model.FootballPlayer;
import net.lwenstrom.antsemble.model.Provider;
import org.jetbrains.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final @Qualifier("openAiChatModel") ChatModel openAiChatModel;
    private final @Qualifier("ollamaChatModel") ChatModel ollamaChatModel;
    private final @Qualifier("defaultChatModel") ChatModel defaultChatModel;

    private static final ChatOptions FOOTBALL_PLAYER_CHAT_OPTIONS = ChatOptions.builder().temperature(0.1).build();

    public String chatWithOpenAI(String message) {
        return ChatClient.create(openAiChatModel)
                .prompt()
                .system("You are a helpful AI assistant specialized in providing accurate and informative responses.")
                .user(message)
                .call()
                .content();
    }

    public String chatWithOllama(String message) {
        return ChatClient.create(ollamaChatModel)
                .prompt()
                .system("You are a helpful AI assistant. Provide clear and concise responses.")
                .user(message)
                .call()
                .content();
    }

    public String chatWithDefault(String message) {
        return ChatClient.create(defaultChatModel)
                .prompt()
                .user(message)
                .call()
                .content();
    }

    public Flux<String> streamChat(String message, @Nullable Provider provider) {
        var model = switch (provider) {
            case OLLAMA -> ollamaChatModel;
            case OPENAI -> openAiChatModel;
            case null -> defaultChatModel;
        };

        return ChatClient.create(model)
                .prompt()
                .system("You are a helpful AI assistant providing streaming responses.")
                .user(message)
                .stream()
                .content();
    }

    public FootballPlayer generateFootballPlayer(String playerName) {
        var promptText = """
                Generate realistic football player statistics for {playerName}.
                
                Create detailed attributes based on what you know about this player or generate realistic stats if unknown.
                Consider typical characteristics for their position and playing style.
                """;

        var promptTemplate = new PromptTemplate(promptText);
        var prompt = promptTemplate.create(Map.of("playerName", playerName), FOOTBALL_PLAYER_CHAT_OPTIONS);

        var generatedPlayer = ChatClient.create(defaultChatModel)
                .prompt(prompt)
                .call()
                .entity(FootballPlayer.class);

        if (generatedPlayer == null) {
            throw new IllegalStateException("Response could not be generated");
        }

        return generatedPlayer;
    }

    public String analyzeWithContext(String topic, String context) {
        var promptText = """
                You are an expert analyst. Given the following context and topic, provide a detailed analysis.
                
                Context: {context}
                Topic: {topic}
                
                Please provide a comprehensive analysis considering the given context.
                """;

        var promptTemplate = new PromptTemplate(promptText);
        var prompt = promptTemplate.create(Map.of(
                "context", context,
                "topic", topic
        ));

        return ChatClient.create(defaultChatModel)
                .prompt(prompt)
                .call()
                .content();
    }

    public String compareModels(String question) {
        var openAiResponse = chatWithOpenAI(question);
        var ollamaResponse = chatWithOllama(question);

        return String.format("""
                ## OpenAI Response:
                %s
                
                ## Ollama Response:
                %s
                
                ## Comparison Complete
                """, openAiResponse, ollamaResponse);
    }
}
