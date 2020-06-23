package com.watson.demo.work;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SortTest {

    @Data
    static class Bank {

        // 银行名称
        private String bankName;
        // 贷款额度(万)
        private Integer loanQuota;
        // 贷款期限(月)
        private Integer loanTime;
        // 贷款利息
        private BigDecimal loanInterest;


        Bank(String bankName,
             Integer loanQuota,
             Integer loanTime,
             BigDecimal loanInterest) {
            this.bankName = bankName;
            this.loanQuota = loanQuota;
            this.loanTime = loanTime;
            this.loanInterest = loanInterest;
        }

        @Override
        public String toString() {
            return "银行名称:" + this.bankName +
                    ", 贷款额度(万):" + this.loanQuota +
                    ", 贷款期限(月):" + this.loanTime +
                    ", 贷款利息:" + this.loanInterest;
        }
    }

    // 额度从高到低, 期限从长到短, 利息从低到高
    private static List<Bank> sort(List<Bank> bankList) {

        return bankList.stream().sorted((o1, o2) -> {
            int cr = 0;
            int i = o2.getLoanQuota() - o1.getLoanQuota();
            if (i != 0) {
                cr = i > 0 ? 1 : -1;
            } else {
                int j = o2.getLoanTime() - o1.getLoanTime();
                if (j != 0) {
                    cr = j > 0 ? 1 : -1;
                } else {
                    BigDecimal k = o1.getLoanInterest().subtract(o2.getLoanInterest());
                    if (k.intValue() != 0) {
                        cr = k.compareTo(BigDecimal.ZERO) > 0 ? 1 : -1;
                    }
                }
            }
            return cr;
        }).collect(Collectors.toList());

    }

    public static void main(String[] args) {

        List<Bank> bankList = new ArrayList<Bank>() {{
            add(new Bank("中国银行", 100, 4, new BigDecimal("5.88")));
            add(new Bank("平安银行", 90, 6, new BigDecimal("5.88")));
            add(new Bank("建设银行", 110, 6, new BigDecimal("5.88")));
            add(new Bank("工商银行", 150, 3, new BigDecimal("5.88")));
            add(new Bank("招商银行", 150, 3, new BigDecimal("5.88")));
            add(new Bank("人民银行", 180, 6, new BigDecimal("5.66")));
            add(new Bank("农业银行", 120, 6, new BigDecimal("5.68")));
            add(new Bank("宁波银行", 120, 6, new BigDecimal("4.88")));
        }};

        for (Bank bank : sort(bankList)) {
            System.out.println(bank);
        }

    }


}
