package com.rioc.ws.modeles.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class BankDetailDto {

    @NotNull @NotEmpty
    String pseudo;

    @NotNull @NotEmpty
    String iban;
}
