package feeling.books.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
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


    @Autowired
    public BooksService(ChatClient chatClient,
                        @Value("classpath:/prompts/system-qa.st") Resource systemResource,
                        @Value("classpath:/prompts/user-prompt.st") Resource userResource){
        this.chatClient = chatClient;
        this.systemResource = systemResource;
        this.userResource = userResource;
    }


    public String retrieve(String country, String genre){
        PromptTemplate userTemplate = new PromptTemplate(userResource);
        Message userMessage = userTemplate.createMessage(Map.of("country", country, "genre", genre));

        SystemPromptTemplate systemTemplate = new SystemPromptTemplate(systemResource);
        Message systemMessage = systemTemplate.createMessage();

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        Generation generation = chatClient.call(prompt).getResult();
        return generation.getOutput().getContent();
    }
}
