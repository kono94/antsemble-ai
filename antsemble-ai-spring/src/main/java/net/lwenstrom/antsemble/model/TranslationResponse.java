package net.lwenstrom.antsemble.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.v3.oas.annotations.media.Schema;

public record TranslationResponse(
        @JsonProperty(required = true)
                @JsonPropertyDescription("The text translated to the target language")
                @Schema(description = "The text translated to the target language", example = "Bonjour le monde")
                String translatedText,
        @JsonProperty(required = true)
                @JsonPropertyDescription("The detected language of the user input text")
                @Schema(description = "The detected language of the user input text", example = "English")
                String detectedInputTextLanguage) {}
