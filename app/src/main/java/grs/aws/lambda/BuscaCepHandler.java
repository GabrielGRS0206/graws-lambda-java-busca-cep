package grs.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import grs.aws.lambda.client.ViaCepClient;
import grs.aws.lambda.client.request.CepRequest;
import grs.aws.lambda.client.response.EnderecoResponse;

import java.io.IOException;
import java.io.UncheckedIOException;

public class BuscaCepHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static ViaCepClient viaCepClient;
    private static Gson gson;

    static {
        viaCepClient = new ViaCepClient();
        gson = new Gson();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request,
                                                      Context context) {
        var logger = context.getLogger();

        logger.log("Request received - " + request.getBody());


        try {
            CepRequest cepRequest = gson.fromJson(request.getBody(), CepRequest.class);
            EnderecoResponse enderecoResponse = viaCepClient.buscaEnderecoPeloCep(cepRequest.getCep());

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(gson.toJson(enderecoResponse))
                    .withIsBase64Encoded(false);
        } catch (IOException e){
            throw new UncheckedIOException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
