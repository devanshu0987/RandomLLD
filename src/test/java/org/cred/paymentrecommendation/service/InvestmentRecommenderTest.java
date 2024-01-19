package org.cred.paymentrecommendation.service;

import com.google.common.collect.Lists;
import org.cred.paymentrecommendation.enums.Issuer;
import org.cred.paymentrecommendation.enums.LineOfBusiness;
import org.cred.paymentrecommendation.enums.PaymentInstrumentType;
import org.cred.paymentrecommendation.models.cart.Cart;
import org.cred.paymentrecommendation.models.cart.CartDetail;
import org.cred.paymentrecommendation.models.cart.CartItem;
import org.cred.paymentrecommendation.models.user.*;
import org.cred.paymentrecommendation.serviceImpl.PaymentRecommenderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InvestmentRecommenderTest {
    private PaymentRecommender paymentRecommender;

    @BeforeEach
    void setup() {
        //TODO: Setup paymentRecommender
        paymentRecommender = new PaymentRecommenderImpl();
    }

    @Test
    void testInvestmentWithNoInstruments() {
        final User user = new User(UUID.randomUUID().toString(), null, new UserPaymentInstrument(new ArrayList<>()));
        final Cart cart = new Cart(LineOfBusiness.INVESTMENT, null);
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testInvestmentWithCreditCardInstrument() {
        final PaymentInstrument creditCardPaymentInstrument = createCreditCardPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), null,
                new UserPaymentInstrument(Collections.singletonList(creditCardPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.INVESTMENT, new CartDetail(1000d, Collections.singletonList(getCartItem(1000d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testInvestmentWithUPIInstrumentButNotUPIEnabled() {
        final PaymentInstrument upiPaymentInstrument = createUPIPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(false)),
                new UserPaymentInstrument(Collections.singletonList(upiPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.INVESTMENT, new CartDetail(1000d, Collections.singletonList(getCartItem(1000d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testInvestmentWithUPIInstrumentButLimitBreached() {
        final PaymentInstrument upiPaymentInstrument = createUPIPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(true)),
                new UserPaymentInstrument(Collections.singletonList(upiPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.INVESTMENT, new CartDetail(1000001d, Collections.singletonList(getCartItem(1000001d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testInvestmentWithNetBankingButLimitBreached() {
        final PaymentInstrument netBankingPaymentInstrument = createNetBankingPaymentInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(false)),
                new UserPaymentInstrument(Collections.singletonList(netBankingPaymentInstrument)));
        final Cart cart = new Cart(LineOfBusiness.INVESTMENT, new CartDetail(2000001d, Collections.singletonList(getCartItem(2000001d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testInvestmentWithDebitCardButLimitBreached() {
        final PaymentInstrument debitCardInstrument = createDebitCardInstrument();
        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(false)),
                new UserPaymentInstrument(Collections.singletonList(debitCardInstrument)));
        final Cart cart = new Cart(LineOfBusiness.INVESTMENT, new CartDetail(2000001d, Collections.singletonList(getCartItem(2000001d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testInvestmentHappyScenario() {
        final PaymentInstrument sbiDebitCard = createDebitCardInstrument();
        final PaymentInstrument sbiUPI = createUPIPaymentInstrument();
        final PaymentInstrument hdfcUPI = createUPIPaymentInstrument();
        hdfcUPI.setIssuer(Issuer.HDFC);
        hdfcUPI.setRelevanceScore(0.88);
        final PaymentInstrument sbiNetBanking = createNetBankingPaymentInstrument();
        final PaymentInstrument hdfcCreditCard = createCreditCardPaymentInstrument();

        final User user = new User(UUID.randomUUID().toString(), new UserContext(new DeviceContext(true)),
                new UserPaymentInstrument(Arrays.asList(hdfcCreditCard, sbiUPI, hdfcUPI, sbiNetBanking, sbiDebitCard)));
        final List<PaymentInstrument> expectedInstrumentList = Lists.newArrayList(hdfcUPI, sbiUPI, sbiNetBanking, sbiDebitCard);
        final Cart cart = new Cart(LineOfBusiness.INVESTMENT, new CartDetail(10000d, Collections.singletonList(getCartItem(10000d))));
        final List<PaymentInstrument> actualList = paymentRecommender.recommendPaymentInstruments(user, cart);
        assertEquals(4, actualList.size());
        assertEquals(expectedInstrumentList, actualList);
    }

    private PaymentInstrument createCreditCardPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.CREDIT_CARD, "credit_card_number", Issuer.HDFC, "HDFC Diner", 0.7);
    }

    private PaymentInstrument createUPIPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.UPI, "upi_handle", Issuer.SBI, "SBI UPI", 0.85);
    }

    private PaymentInstrument createNetBankingPaymentInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.NETBANKING, "bank_account_number", Issuer.SBI, "Savings Account", 0.86);
    }

    private PaymentInstrument createDebitCardInstrument() {
        return new PaymentInstrument(PaymentInstrumentType.DEBIT_CARD, "debit_card_number", Issuer.SBI, "SBI Debit Card", 0.91);
    }

    private CartItem getCartItem(final Double amount) {
        return new CartItem(UUID.randomUUID().toString(), "Reliance Stocks", amount);
    }
}
