package net.lwenstrom.antsemble.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.model.SpringAIModels;

@Getter
@RequiredArgsConstructor
public enum Provider {
    OPENAI(SpringAIModels.OPENAI),
    OLLAMA(SpringAIModels.OLLAMA);

    private final String name;
}
