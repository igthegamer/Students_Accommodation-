package tn.esprit.tpfoyer.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;
import tn.esprit.tpfoyer.model.Response;

public interface IPaymentService {

    PaymentIntent createPaymentIntent(Response paymentInfoRequest) throws StripeException;

    ResponseEntity<String> stripePayment(String userEmail) throws Exception;
}
