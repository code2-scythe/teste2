package com.example.gestao_de_estoque.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementação da lógica de validação de CNPJ.
 * Esta classe valida um CNPJ utilizando os critérios de validade padrão definidos pela Receita Federal.
 */
public class CnpjValidator implements ConstraintValidator<CNPJ, String> {

    /**
     * Verifica se o CNPJ fornecido é válido.
     *
     * @param cnpj    o CNPJ a ser validado
     * @param context o contexto de validação
     * @return true se o CNPJ for válido, caso contrário, false
     */
    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.isEmpty()) {
            // Caso o CNPJ seja nulo ou vazio, considera válido se não for obrigatório
            return true;
        }

        // Remove tudo que não for dígito (como pontos, barras e traços)
        String somenteDigitos = cnpj.replaceAll("\\D", "");

        // Verifica se o CNPJ possui exatamente 14 dígitos e não é uma sequência repetida
        if (somenteDigitos.length() != 14 || somenteDigitos.matches("(\\d)\\1{13}")) {
            return false;  // CNPJ inválido se não tiver 14 dígitos ou se for uma sequência repetida (ex: 111.111.111.11111)
        }

        // Pesos para cálculo dos dois dígitos verificadores
        int[] pesos1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] pesos2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

        // Verifica se o primeiro e segundo dígitos verificadores são válidos
        return calcularDigito(somenteDigitos, pesos1) == Character.getNumericValue(somenteDigitos.charAt(12))
                && calcularDigito(somenteDigitos, pesos2) == Character.getNumericValue(somenteDigitos.charAt(13));
    }

    /**
     * Calcula o dígito verificador de um CNPJ utilizando os pesos passados.
     *
     * @param cnpj  o CNPJ em formato numérico (apenas dígitos)
     * @param pesos os pesos a serem utilizados no cálculo do dígito
     * @return o dígito calculado
     */
    private int calcularDigito(String cnpj, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        // Se o resto for menor que 2, o dígito é 0; caso contrário, é 11 - resto
        return resto < 2 ? 0 : 11 - resto;
    }
}