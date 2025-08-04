package net.lwenstrom.antsemble.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballPlayer {
    
    @JsonPropertyDescription("The full name of the football player")
    private String name;
    
    @JsonPropertyDescription("Player's speed rating from 1-100")
    private Integer speed;
    
    @JsonPropertyDescription("Player's agility rating from 1-100")
    private Integer agility;
    
    @JsonPropertyDescription("Player's game knowledge and tactical awareness rating from 1-100")
    private Integer gameKnowledge;
    
    @JsonPropertyDescription("Player's physical power and strength rating from 1-100")
    private Integer power;
    
    @JsonPropertyDescription("Player's primary position on the field")
    private String position;
    
    @JsonPropertyDescription("Player's age in years")
    private Integer age;
    
    @JsonPropertyDescription("Player's nationality")
    private String nationality;
    
    @JsonPropertyDescription("Brief description of the player's playing style")
    private String playingStyle;
}
