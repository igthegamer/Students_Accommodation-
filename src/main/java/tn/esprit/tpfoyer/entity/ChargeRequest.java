package tn.esprit.tpfoyer.entity;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class ChargeRequest {

    public enum Currency {
        EUR, USD;
    }
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
    private String reservationId;
}