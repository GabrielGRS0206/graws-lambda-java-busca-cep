package grs.aws.lambda.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoResponse {
    private String logradouro;
    private String bairro;
    private String ibge;
    private String cep;
    private String localidade;
}