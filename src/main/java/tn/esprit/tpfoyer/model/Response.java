package tn.esprit.tpfoyer.model;

import lombok.Data;

@Data
public class Response {
    private String intentID;
    private String clientSecret;

    public Response(String intentID, String clientSecret) {
        this.intentID = intentID;
        this.clientSecret = clientSecret;
    }
}
