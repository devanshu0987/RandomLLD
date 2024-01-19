package org.cred.paymentrecommendation.models.user;

import java.util.Comparator;
import java.util.List;
import org.cred.paymentrecommendation.enums.*;

public class PaymentIntrumentComparator implements Comparator<PaymentInstrument> {
    private List<PaymentInstrumentType> relevance;

    public PaymentIntrumentComparator(List<PaymentInstrumentType> relevance) {
        this.relevance = relevance;
    }

    @Override
    public int compare(PaymentInstrument lhs, PaymentInstrument rhs) {
        int lhsIndex = relevance.indexOf(lhs.getPaymentInstrumentType());
        int rhsIndex = relevance.indexOf(rhs.getPaymentInstrumentType());
        if (lhsIndex == rhsIndex) {
            int comp = lhs.getRelevanceScore().compareTo(rhs.getRelevanceScore());
            return -1 * comp;
        }
        if (lhsIndex > rhsIndex)
            return 1;
        return -1;
    }
}
