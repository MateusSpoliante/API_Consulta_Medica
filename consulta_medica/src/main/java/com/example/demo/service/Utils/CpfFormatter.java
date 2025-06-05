package com.example.demo.service.Utils;

public class CpfFormatter {

    public static String formatarCpf(String cpf) {
        if (cpf == null) return null;

        // Remove tudo que não for número
        cpf = cpf.replaceAll("[^\\d]", "");

        // Verifica se tem 11 digitos
        if (cpf.length() != 11) return cpf;

        // Formata com pontos e traço
        return cpf.substring(0, 3) + "." +
               cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" +
               cpf.substring(9);
    }
}
