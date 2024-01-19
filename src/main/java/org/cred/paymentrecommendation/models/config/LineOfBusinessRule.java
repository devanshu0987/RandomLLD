package org.cred.paymentrecommendation.models.config;


import org.cred.paymentrecommendation.enums.PaymentInstrumentType;

import java.util.List;
import java.util.Map;

public class LineOfBusinessRule {
    public List<PaymentInstrumentType> relevance;
    public Map<PaymentInstrumentType, PaymentIntrumentRule> rules;
}
