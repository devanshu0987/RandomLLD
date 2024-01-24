package org.jupiter.query;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction("1", LocalDateTime.now(), 100));
        transactionList.add(new Transaction("2", LocalDateTime.now().plus(1, ChronoUnit.HOURS), 200));
        transactionList.add(new Transaction("3", LocalDateTime.now(), 200));
        transactionList.add(new Transaction("4", LocalDateTime.now(), 400));
        transactionList.add(new Transaction("5", LocalDateTime.now(), 700));

        EqualityOperation<Transaction> idEquality = new EqualityOperation<>("id", "5");
        EqualityOperation<Transaction> amountEquality = new EqualityOperation<>("amount", 200);
        var idAndAmountEquality = idEquality.and(amountEquality);
        var idOrAmountEquality = idEquality.or(amountEquality);

        List<Transaction> idFilteredList = transactionList.stream().filter(
                x -> idEquality.test(x)).collect(Collectors.toList());
        List<Transaction> amountFilteredList = transactionList.stream().filter(
                x -> amountEquality.test(x)).collect(Collectors.toList());
        List<Transaction> andFilteredList = transactionList.stream().filter(
                x -> idAndAmountEquality.test(x)).collect(Collectors.toList());
        List<Transaction> orFilteredList = transactionList.stream().filter(
                x -> idOrAmountEquality.test(x)).collect(Collectors.toList());

        System.out.println(idFilteredList);
        System.out.println(amountFilteredList);
        System.out.println(andFilteredList);
        System.out.println(orFilteredList);

        InOperation<Transaction> idInEquality = new InOperation<>("id", List.of("1", "2", "3"));
        List<Transaction> idInFilteredList = transactionList.stream().filter(
                x -> idInEquality.test(x)).collect(Collectors.toList());

        System.out.println();
        System.out.println(idInFilteredList);

    }
}
