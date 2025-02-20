package telran.dailyFarm.accounting.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import telran.dailyFarm.accounting.dto.farmer.AddressDto;
import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.dto.farmer.LocationDto;

@Service
@RequiredArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";
    private final WebClient webClient = WebClient.create();

    @Override
    public LocationDto getCoordinates(FarmerDto farmerDto) {
        String address = formatAddress(farmerDto);
        String url = buildUrl(address);

        String response = fetchCoordinatesJson(url);
        return parseCoordinates(response, address);
    }

    private String buildUrl(String address) {
        return UriComponentsBuilder.fromUriString(NOMINATIM_API_URL)
                .queryParam("format", "json")
                .queryParam("q", address)
                .toUriString();
    }

    private String formatAddress(FarmerDto farmerDto) {
        if (farmerDto.getAddress() == null) {
            throw new IllegalArgumentException("Address is required for geolocation.");
        }

        AddressDto address = farmerDto.getAddress();

        return String.join(" ", 
            address.getCountry(), 
            address.getCity(), 
            address.getStreet(), 
            address.getHouseNumber(), 
            address.getZipCode()
        );
    }

    private String fetchCoordinatesJson(String url) {
        return webClient.get()
                .uri(url)
                .header("User-Agent","DailyFarmGeolocationService/1.0") // OpenStreetMap 
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private LocationDto parseCoordinates(String response, String address) {
        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
        if (jsonArray.isEmpty()) {
            throw new NoSuchElementException("Address not found: " + address);
        }
        JsonObject location = jsonArray.get(0).getAsJsonObject();
        return new LocationDto(location.get("lat").getAsDouble(), location.get("lon").getAsDouble());
    }
}