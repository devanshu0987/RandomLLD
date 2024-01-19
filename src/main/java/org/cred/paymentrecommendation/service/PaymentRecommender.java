package org.cred.paymentrecommendation.service;

import org.cred.paymentrecommendation.models.cart.Cart;
import org.cred.paymentrecommendation.models.user.PaymentInstrument;
import org.cred.paymentrecommendation.models.user.User;

import java.util.List;

public interface PaymentRecommender {
    List<PaymentInstrument> recommendPaymentInstruments(final User user, final Cart cart);
}
