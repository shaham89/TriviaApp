package com.example.triviaapp.chatgpt;

import android.os.Build;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Build;
import android.util.Log;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class chatApi {


        public static void runGPT() {
            String token = "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";
            OpenAiService service = new OpenAiService(token);

            System.out.println("\nCreating completion...");
            CompletionRequest completionRequest = CompletionRequest.builder()
                    .model("ada")
                    .prompt("Somebody once told me the world is gonna roll me")
                    .echo(true)
                    .user("testing")
                    .n(3)
                    .build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
            }


            System.out.println("Streaming chat completion...");
            final List<ChatMessage> messages = new ArrayList<>();
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are a dog and will speak as such.");
            messages.add(systemMessage);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo")
                    .messages(messages)
                    .n(1)
                    .maxTokens(50)
                    .logitBias(new HashMap<>())
                    .build();

            service.streamChatCompletion(chatCompletionRequest)
                    .doOnError(Throwable::printStackTrace)
                    .blockingForEach(System.out::println);

            service.shutdownExecutor();

            int x = 5;
        }


}
