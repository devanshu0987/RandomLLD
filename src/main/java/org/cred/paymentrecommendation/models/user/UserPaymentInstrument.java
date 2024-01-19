package org.cred.paymentrecommendation.models.user;

import java.util.ArrayList;
import java.util.List;

public class UserPaymentInstrument {
    private List<PaymentInstrument> paymentInstruments = new ArrayList<>();

    public List<PaymentInstrument> getPaymentInstruments() {
        return paymentInstruments;
    }

    public UserPaymentInstrument(List<PaymentInstrument> paymentInstruments) {
        this.paymentInstruments = paymentInstruments;
    }

    public void setPaymentInstruments(List<PaymentInstrument> paymentInstruments) {
        this.paymentInstruments = paymentInstruments;
    }
}
