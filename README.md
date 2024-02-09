# Consult books based country and genre

API REST created with Spring web and [Spring AI](https://docs.spring.io/spring-ai/reference/index.html) which use the [llama2 7B](https://ollama.ai/library/llama2) small language model of Meta for consult a book or books based in country and genre.
This model can be runned locally with [ollama](https://github.com/ollama/ollama).

#### 1. Simple generation
Request
> GET http://localhost:8080/ai/books \
> **params:** country=_USA_&genre=_Biography_

Response
```json
{
    "completion": " Sure! I can suggest several books for you. One popular biography about an American writer is \"Becoming\" by Michelle Obama, which tells the story of her life from childhood to becoming First Lady of the United States. Another one is \"Steve Jobs\" by Walter Isaacson, which explores the life and work of the tech icon and his impact on the world. You might also be interested in \"I Am Malala\" by Malala Yousafzai, which tells the story of a young woman who fought for girls' education in Pakistan and survived an assassination attempt by the Taliban.\n"
}
```

#### 2. Generation with output
Request
> GET http://localhost:8080/ai/books/output \
> **params:** country=_France_&genre=_Science fiction_

Response
```json
{
  "name": "Voyage au centre de la Terre",
  "description": "A Journey to the Center of the Earth",
  "author": "Jules Verne"
}
```

### Running locally

1. Start container
> docker-compose up -d 
2. Run llama2 model locally
> docker exec -it ollama ollama run llama2
3. Run application and execute service changing the two params 