package org.cred.paymentrecommendation.serviceImpl;



import org.cred.paymentrecommendation.enums.Issuer;
import org.cred.paymentrecommendation.enums.PaymentInstrumentType;
import org.cred.paymentrecommendation.models.user.DeviceContext;
import org.cred.paymentrecommendation.models.user.PaymentInstrument;
import org.cred.paymentrecommendation.models.user.UserContext;
import org.cred.paymentrecommendation.models.user.UserPaymentInstrument;
import org.cred.paymentrecommendation.service.CartService;

import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl implements CartService {


    @Override
    public void createCart() {

        //create cart data

        DeviceContext deviceContext = new DeviceContext(true);
        UserContext userContext = new UserContext(deviceContext);
        List<PaymentInstrument> pymentInstrumentsLsit = new ArrayList<>();
        PaymentInstrument paymentInstrument1 = new PaymentInstrument(PaymentInstrumentType.CREDIT_CARD, "SBI devid card", Issuer.SBI, "abc", 0.92);
        pymentInstrumentsLsit.add(paymentInstrument1);
        UserPaymentInstrument userPaymentInstrument = new UserPaymentInstrument(pymentInstrumentsLsit);

//        User user = new User(1, userContext,  userPaymentInstrument);
    }

}
