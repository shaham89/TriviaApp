package com.example.triviaapp.chatgpt;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.triviaapp.R;
import com.example.triviaapp.customClasses.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class chatApi {

        public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        public static OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        public static Request getRequest(int numberOfQuestions, String subject){
                JSONObject jsonBody = new JSONObject();

                //String model = "text-davinci-003";

                try {

//                        jsonBody.put("model", model);
//                        jsonBody.put("prompt", "Give me " + numberOfQuestions + " random trivia questions about " + subject + ", with 4 options for each, and mark the correct answer. Parse the questions in a json format");
//                        jsonBody.put("max_tokens", 200);
//                        jsonBody.put("temperature", 1);

                        jsonBody = new JSONObject("{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"Give me " + numberOfQuestions + " random trivia questions about " + subject + ", with 4 options for each, and mark the correct answer. Parse the questions in a json format\"}]}");

                } catch (JSONException e) {
                        e.printStackTrace();
                }

                byte[] decodedBytes = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        decodedBytes = Base64.getDecoder().decode("QmVhcmVyIHNrLTF5dHRqZXhUdmt3VWVPdEpqd3oxVDNCbGJrRkpoOUJ6ZjV0Z2puZ1VQdjJuM2J1WQ==");
                }

                String decodedString = new String(decodedBytes);

                RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
                final String chatURL = "https://api.openai.com/v1/chat/completions";
                final String modelsCompletionURL = "https://api.openai.com/v1/completions";


                return new Request.Builder()
                        .url(chatURL)
                        .header("Authorization", decodedString)
                        .post(body)
                        .build();
        }

        public static void getQuestion(int numberOfQuestions, String subject) {
                //okhttp
                //messageList.add(new Message("Typing... ",Message.SENT_BY_BOT));

                Request request = getRequest(numberOfQuestions, subject);


                client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.d("CALLED", "Failed to load response due to " + e.getMessage());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                if (response.isSuccessful()) {
                                        try {

                                                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
                                                JSONArray jsonArray = jsonObject.getJSONArray("choices");
                                                String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                                                //String result = jsonArray.getJSONObject(0).getString("text");
                                                Log.d("CALLED", result);

                                                //JSONObject t = new JSONObject(result);
                                        } catch (JSONException e) {
                                                e.printStackTrace();
                                        }

//https://github.com/easy-tuto/Android_ChatGPT/blob/main/app/src/main/java/com/example/easychatgpt/MainActivity.java
                                } else {
                                        Log.d("ELSE CALLED", "Failed to load response due to " + Objects.requireNonNull(response.body()).toString());
                                }
                        }


                });

        }


}
