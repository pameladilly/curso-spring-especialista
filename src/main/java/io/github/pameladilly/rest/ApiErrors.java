package io.github.pameladilly.rest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String mensagemErro) {
        this.errors = Arrays.asList(mensagemErro);
    }

}
