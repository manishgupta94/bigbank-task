package com.bigbank.dragonsOfMugloar.application.service;

import com.bigbank.dragonsOfMugloar.application.model.*;
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
/**
 * This Service class is used to make API calls, parse the JSON response into DTO class.
 * This class is used by GameEngine to receive JSON response as a DTO.
 *
 *
 * @author Manish Gupta
 * @version $Id: GameService.java 1.0
 * @since 2020-11-28
 */
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

    /**
     * service method to start a new game.
     *
     * @return a new Game object
     */
    public Game startGame() {
        String startGameURI = baseUri + "game/start";
        ResponseEntity<String> gameResponseEntity =restTemplate
                .exchange(startGameURI , HttpMethod.POST,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(gameResponseEntity.getBody(), Game.class);
    }

    /**
     * A method that makes API call to fetch the list of all the messages
     * that can be attempted.
     *
     * @param gameId game id for which the messages are to be fetched.
     * @return list of messages that can be attempted.
     */
    public List<Ad> fetchAllMessages(String gameId) {
        String fetchMessageURI = baseUri + gameId + "/messages";
        ResponseEntity<String> adListResponseEntity =restTemplate
                .exchange(fetchMessageURI, HttpMethod.GET,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(adListResponseEntity.getBody(), new TypeToken<List<Ad>>(){}.getType());
    }

    /**
     * A method that makes API call to attempt the message.
     *
     * @param gameId current game id.
     * @param adId message id which is to be attempted.
     * @return response from the API after solving the message.
     */
    public SolveMessageResponse solveBestMessage(String gameId, String adId) throws IllegalStateException {
        String executeMessageURI = baseUri + gameId + "/solve/" + adId;
        ResponseEntity<String> solveMessageResponseEntity =restTemplate
                .exchange(executeMessageURI, HttpMethod.POST,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(solveMessageResponseEntity.getBody(), SolveMessageResponse.class);
    }

    /**
     * A method that makes API call to fetch the available shop items.
     *
     * @param gameId current game id.
     * @return shop items available.
     */
    public List<ShopItem> getShopItems(String gameId) {
        String getShopItemsURI = baseUri + gameId + "/shop";
        ResponseEntity<String> shopItemListResponseEntity =restTemplate
                .exchange(getShopItemsURI, HttpMethod.GET,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(shopItemListResponseEntity.getBody(), new TypeToken<List<ShopItem>>(){}.getType());
    }

    /**
     * A method that makes API call to purchase an item.
     *
     * @param gameId current game id.
     * @param itemId item id to be purchased.
     * @return response from the API after buying the item.
     */
    public BuyItemResponse buyShopItem(String gameId, String itemId) throws IllegalStateException{
        String executeMessageURI = baseUri + gameId + "/shop/buy/" + itemId;
        ResponseEntity<String> buyItemResponseEntity = restTemplate
                .exchange(executeMessageURI, HttpMethod.POST,
                        new HttpEntity<>(null, HTTP_HEADERS), String.class);

        return GSON.fromJson(buyItemResponseEntity.getBody(), BuyItemResponse.class);
    }
}
