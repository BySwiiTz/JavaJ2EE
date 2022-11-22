package com.rioc.ws.services.adress;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rioc.ws.exception.ApiException;
import com.rioc.ws.mapper.IAccountMapper;
import com.rioc.ws.modeles.dto.AccountDto;
import com.rioc.ws.modeles.dto.AdressDto;
import com.rioc.ws.tools.FeatureCollection;
import com.rioc.ws.tools.Properties;

@Service
public class AdressService implements IAdressService {

    public AdressService() {
        super();
    }

    private AdressDto correctionAdressDto(FeatureCollection response) {
        Properties properties = response.getFeatures().get(0).getProperties();
        AdressDto adressDto = new AdressDto();

        if (properties.getCity() != null && !properties.getCity().isEmpty()) {
            adressDto.setCity(properties.getCity());
        }
        if (properties.getHousenumber() != null && !properties.getHousenumber().isEmpty()) {
            adressDto.setHouseNumber(properties.getHousenumber());
        }
        if (properties.getPostcode() != null && !properties.getPostcode().isEmpty()) {
            adressDto.setPostCode(Integer.parseInt(properties.getPostcode()));
        }
        if (properties.getStreet() != null && !properties.getStreet().isEmpty()) {
            adressDto.setStreet(properties.getStreet());
        }
        return adressDto;
    }

    public AdressDto validAdress(AccountDto accountDto) {

        FeatureCollection response = new FeatureCollection();
        AdressDto adressDto = accountDto.getAdress();

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api-adresse.data.gouv.fr")
                .build();

        if (!adressDto.getCity().isEmpty() && adressDto.getHouseNumber() != null) {
            String streetAdress = adressDto.getHouseNumber()+"+"+adressDto.getStreet().replace(' ', '+')+"+"+adressDto.getPostCode()+"+"+adressDto.getCity();
            response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search/")
                            .queryParam("q", streetAdress)
                            .build())
                    .retrieve()
                    .bodyToMono(FeatureCollection.class)
                    .block();
        }
        if (response != null && !response.getFeatures().isEmpty() && response.getFeatures() != null) {
            adressDto = this.correctionAdressDto(response);
            return adressDto;
        }

        throw new ApiException("ADDRESS LABEL INCORRECT", HttpStatus.NOT_FOUND);
    }

}
