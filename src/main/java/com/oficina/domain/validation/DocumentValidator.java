package com.oficina.domain.validation;

public class DocumentValidator {

    public static boolean isValidCPF(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int digit1 = 11 - (sum % 11);
        if (digit1 >= 10) digit1 = 0;
        if ((cpf.charAt(9) - '0') != digit1) return false;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int digit2 = 11 - (sum % 11);
        if (digit2 >= 10) digit2 = 0;
        return (cpf.charAt(10) - '0') == digit2;
    }

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null) return false;
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.length() != 14) return false;
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (cnpj.charAt(i) - '0') * weight1[i];
        }
        int digit1 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        if ((cnpj.charAt(12) - '0') != digit1) return false;

        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += (cnpj.charAt(i) - '0') * weight2[i];
        }
        int digit2 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        return (cnpj.charAt(13) - '0') == digit2;
    }

    public static boolean isValidDocument(String document) {
        if (document == null) return false;
        String cleaned = document.replaceAll("[^0-9]", "");
        if (cleaned.length() == 11) return isValidCPF(document);
        if (cleaned.length() == 14) return isValidCNPJ(document);
        return false;
    }
}
