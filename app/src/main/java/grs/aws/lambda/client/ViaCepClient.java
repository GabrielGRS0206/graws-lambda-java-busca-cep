package grs.aws.lambda.client;


import com.google.gson.Gson;
import grs.aws.lambda.client.response.EnderecoResponse;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepClient {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    public EnderecoResponse buscaEnderecoPeloCep(String cep) throws Exception {
        String webService = "http://viacep.com.br/ws/";
        String urlParaChamada = webService + cep + "/json";

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(urlParaChamada))
                    .header("Content-Type", "text/plain;charset=UTF-8")
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200){
                return gson.fromJson(response.body(), EnderecoResponse.class);
            } else {
                return EnderecoResponse.builder().build();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
