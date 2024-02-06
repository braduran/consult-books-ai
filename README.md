# Consult a book from country and genre

Simple REST service created with Spring web and [Spring AI](https://github.com/spring-projects/spring-ai) which use the [phi](https://ollama.ai/library/phi) small language model of Microsoft for consult with a short prompt a book or books based in country and genre.
This model can be runned locally with [ollama](https://github.com/ollama/ollama).

### Service definition
Request
> GET http://localhost:8080/ai/books \
> **params:** country=_USA_&genre=_Biography_

Response
```json
{
    "completion": " Sure! I can suggest several books for you. One popular biography about an American writer is \"Becoming\" by Michelle Obama, which tells the story of her life from childhood to becoming First Lady of the United States. Another one is \"Steve Jobs\" by Walter Isaacson, which explores the life and work of the tech icon and his impact on the world. You might also be interested in \"I Am Malala\" by Malala Yousafzai, which tells the story of a young woman who fought for girls' education in Pakistan and survived an assassination attempt by the Taliban.\n"
}
```

### Running locally

1. Start container
> docker-compose up -d 
2. Run phi model locally
> docker exec -it ollama ollama run phi
3. Run application and execute service changing the two params 