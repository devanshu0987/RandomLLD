package org.cred.paymentrecommendation.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cred.paymentrecommendation.enums.*;
import org.cred.paymentrecommendation.models.cart.Cart;
import org.cred.paymentrecommendation.models.config.LineOfBusinessRule;
import org.cred.paymentrecommendation.models.config.PaymentIntrumentRule;
import org.cred.paymentrecommendation.models.user.PaymentInstrument;
import org.cred.paymentrecommendation.models.user.PaymentIntrumentComparator;
import org.cred.paymentrecommendation.models.user.User;
import org.cred.paymentrecommendation.service.PaymentRecommender;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentRecommenderImpl implements PaymentRecommender {

    public PaymentRecommenderImpl() {

    }

    Map<LineOfBusiness, LineOfBusinessRule> readRules() {
        HashMap<LineOfBusiness, LineOfBusinessRule> rules;
        File configFile = new File("C:\\Users\\devansm\\Downloads\\Boiler Plate Codes-20240119T032739Z-001\\Boiler Plate Codes\\Java- Unsolved Boiler plate\\src\\main\\java\\com\\paymentrecommendation\\models\\config\\config.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            String total = "";
            String res;
            while ((res = br.readLine()) != null) {
                total += res;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            rules = objectMapper.readValue(total, new TypeReference<HashMap<LineOfBusiness, LineOfBusinessRule>>() {
            });
            ;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rules;
    }

    @Override
    public List<PaymentInstrument> recommendPaymentInstruments(User user, Cart cart) {
        Map<LineOfBusiness, LineOfBusinessRule> config = readRules();
        List<PaymentInstrument> res = new ArrayList<>();
        if(user.getUserPaymentInstrument() == null)
            throw new RuntimeException("The line of business is not supported");
        List<PaymentInstrument> currList = user.getUserPaymentInstrument().getPaymentInstruments();
        LineOfBusiness currLob = cart.getLineOfBusiness();
        if(cart.getCartDetail() == null)
            return res;
        Double currCartValue = cart.getCartDetail().getCartAmount();
        if (!config.containsKey(currLob)) {
            throw new RuntimeException("The line of business is not supported");
        }

        // upi filtering
        boolean isUPIEnabled = false;
        if(user.getUserContext() != null)
            isUPIEnabled = user.getUserContext().getDeviceContext().isUpiEnabled();
        if (!isUPIEnabled) {
            currList = currList.stream().filter(x -> (x.getPaymentInstrumentType() != PaymentInstrumentType.UPI))
                    .collect(Collectors.toList());
        }
        // allowlist filtering.
        // remove all payment type which are not allowed for this lob.
        Map<PaymentInstrumentType, PaymentIntrumentRule> lobRules = config.get(currLob).rules;
        // transaction limit filtering.
        currList = currList.stream().filter(x -> {
            PaymentInstrumentType paymentInstrumentType = x.getPaymentInstrumentType();
            // if payment instrument type is not configured, we ignore it.
            if (!lobRules.containsKey(paymentInstrumentType))
                return false;
            PaymentIntrumentRule paymentIntrumentRule = lobRules.get(paymentInstrumentType);
            if(!paymentIntrumentRule.isAllowed)
                return false;
            if (currCartValue > paymentIntrumentRule.limit)
                return false;
            else
                return true;
        }).collect(Collectors.toList());

        // order the elements
        // relevance based on the line of business
        PaymentIntrumentComparator comparator = new PaymentIntrumentComparator(config.get(currLob).relevance);
        // relevance if multiple payment instruments for a particular type.
        currList.sort(comparator.thenComparing(PaymentInstrument::getRelevanceScore));
        return currList;
    }

}
