package net.lwenstrom.antsemble.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatModelConfig {

    @Bean
    @Primary
    public ChatModel defaultChatModel(
            @Qualifier("ollamaChatModel") ChatModel ollamaChatModel,
            @Qualifier("openAiChatModel") ChatModel openAiChatModel) {
        if (isModelAvailable(ollamaChatModel)) {
            return ollamaChatModel;
        }

        if (isModelAvailable(openAiChatModel)) {
            return openAiChatModel;
        }

        throw new RuntimeException("No available chat model found");
    }

    private static boolean isModelAvailable(ChatModel chatModel) {
        var testPrompt = new Prompt("test", ChatOptions.builder().maxTokens(1).build());
        try {
            chatModel.call(testPrompt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
