package com.bigbank.dragonsofmugloar;

import com.bigbank.dragonsofmugloar.model.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.util.List;

@Service
public class GameService {

    @Value("${game.baseURI}")
    private String baseUri;

    private static final Gson GSON = new Gson();
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

    private final RestTemplate restTemplate;

    @Autowired
    public GameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Game startGame() {
        String startGameURI = baseUri + "game/start";
        ResponseEntity<String> gameResponseEntity =restTemplate
                .exchange(startGameURI , HttpMethod.POST,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(gameResponseEntity.getBody(), Game.class);
    }

    public List<Ad> fetchAllMessages(String gameId) {
        String fetchMessageURI = baseUri + gameId + "/messages";
        ResponseEntity<String> adListResponseEntity =restTemplate
                .exchange(fetchMessageURI, HttpMethod.GET,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(adListResponseEntity.getBody(), new TypeToken<List<Ad>>(){}.getType());
    }

    public SolveMessageResponse solveBestMessage(String gameId, String adId) throws IllegalStateException {
        String executeMessageURI = baseUri + gameId + "/solve/" + adId;
        ResponseEntity<String> solveMessageResponseEntity =restTemplate
                .exchange(executeMessageURI, HttpMethod.POST,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(solveMessageResponseEntity.getBody(), SolveMessageResponse.class);
    }

    public List<ShopItem> getShopItems(String gameId) {
        String getShopItemsURI = baseUri + gameId + "/shop";
        ResponseEntity<String> shopItemListResponseEntity =restTemplate
                .exchange(getShopItemsURI, HttpMethod.GET,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(shopItemListResponseEntity.getBody(), new TypeToken<List<ShopItem>>(){}.getType());
    }

    public BuyItemResponse buyShopItem(String gameId, String itemId) throws IllegalStateException{
        String executeMessageURI = baseUri + gameId + "/shop/buy/" + itemId;
        ResponseEntity<String> buyItemResponseEntity = restTemplate
                .exchange(executeMessageURI, HttpMethod.POST,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(buyItemResponseEntity.getBody(), BuyItemResponse.class);
    }
}
