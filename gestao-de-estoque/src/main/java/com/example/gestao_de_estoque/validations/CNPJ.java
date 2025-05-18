package com.example.gestao_de_estoque.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação personalizada para validar o CNPJ.
 * A validação é feita pelo CnpjValidator, que é o validador responsável por garantir que o CNPJ informado seja válido.
 */
@Constraint(validatedBy = CnpjValidator.class)  // Indica que a validação é feita pela classe CnpjValidator.
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })  // Define onde a anotação pode ser aplicada (campo, parâmetro ou método).
@Retention(RetentionPolicy.RUNTIME)  // A anotação estará disponível em tempo de execução para ser processada.
public @interface CNPJ {

    /**
     * A mensagem padrão caso o CNPJ não seja válido.
     *
     * @return a mensagem de erro
     */
    String message() default "CNPJ inválido";

    /**
     * Grupo de validação. Pode ser usado para organizar as validações em diferentes grupos.
     *
     * @return os grupos de validação
     */
    Class<?>[] groups() default {};

    /**
     * Carregar dados adicionais sobre a validação. Pode ser usado para informações extras.
     *
     * @return o payload
     */
    Class<? extends Payload>[] payload() default {};
}