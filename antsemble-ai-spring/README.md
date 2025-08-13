## Lessons Learned

### Structured Output

1. Only adds description when `@JsonPropertyDescription` is given. 
   Does not work with swagger's `@schema` and description (why not?! In the future?!)
2. `@JsonProperty(required = true)` should always be set
3. When using `.entity()` and generally wanting to add JSON structured output, Spring AI adds `strict(true)`
   automatically as seen in the source code. (We always want forced JSON output and not pure LLM probabilistic output)
   