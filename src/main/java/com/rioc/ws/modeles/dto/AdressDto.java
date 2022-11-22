package com.rioc.ws.modeles.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class AdressDto {

    @NotNull @NotEmpty
    private String houseNumber;

    @NotNull @NotEmpty
    private String street;

    @DecimalMin(value = "10000")
    @DecimalMax(value = "99999")
    private Integer postCode;

    @NotNull @NotEmpty
    private String city;

    /*
     * @NotNull @NotEmpty
     * private String label;
     */

}
