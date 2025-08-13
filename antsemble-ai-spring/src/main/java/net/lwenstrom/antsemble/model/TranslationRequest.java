package net.lwenstrom.antsemble.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "TranslationRequest", description = "Request body for translating text into a target language.")
public record TranslationRequest(
        @Schema(description = "Text to translate.", example = "Hello world")
                @NotBlank(message = "text must not be blank")
                @Size(max = 10000, message = "text must be at most {max} characters long")
                String text,
        @Schema(
                        description = "Target language as a casual representation that an LLM can understand"
                                + "(e.g. 'french', 'german') or an IETF BCP 47 or ISO 639-1 code (e.g., 'de', 'en-GB')",
                        example = "french")
                @NotBlank(message = "targetLanguage must not be blank")
                String targetLanguage) {}
