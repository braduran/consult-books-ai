package com.braduran.chat;

import com.braduran.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BooksService {

    private final Logger logger = LoggerFactory.getLogger(BooksService.class);
    private final ChatClient chatClient;

    private final Resource systemResource;
    private final Resource userResource;
    private final Resource userOutputResource;


    @Autowired
    public BooksService(ChatClient chatClient,
                        @Value("classpath:/prompts/system-qa.st") Resource systemResource,
                        @Value("classpath:/prompts/user-prompt.st") Resource userResource,
                        @Value("classpath:/prompts/user-output.st") Resource userOuputResource){
        this.chatClient = chatClient;
        this.systemResource = systemResource;
        this.userResource = userResource;
        this.userOutputResource = userOuputResource;
    }


    public String retrieve(String country, String genre){
        var userTemplate = new PromptTemplate(userResource);
        Message userMessage = userTemplate.createMessage(Map.of("country", country, "genre", genre));

        var systemTemplate = new SystemPromptTemplate(systemResource);
        Message systemMessage = systemTemplate.createMessage();

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        Generation generation = chatClient.call(prompt).getResult();
        return generation.getOutput().getContent();
    }

    public Book retrieveWithOutput(String country, String genre){
        var outputParser = new BeanOutputParser<>(Book.class);

        var userPromptTemplate = new PromptTemplate(userOutputResource);
        var prompt = userPromptTemplate.create(Map.of("genre", genre, "country", country,
                "format", outputParser.getFormat()));
        var chatResponse = chatClient.call(prompt);

        return outputParser.parse(chatResponse.getResult().getOutput().getContent());
    }
}
