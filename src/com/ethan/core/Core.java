package com.ethan.core;

import com.ethan.exchange.RSExchange;
import com.ethan.exchange.RSItem;
import com.ethan.parsing.XMLParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 1/9/2018.
 */
public class Core {
    private RSExchange rsExchange;
    private Map<String, RSItem> exampleMap = new HashMap<>();

    public Core() {
        rsExchange = new RSExchange("https://rsbuddy.com/exchange/summary.json");
        new XMLParser("https://ghostbin.com/paste/ua7pz/raw", exampleMap);
        iterateMap();
    }


    public static void main(String[] args) {
        new Core();
    }

    private void iterateMap() {
        for (Map.Entry<String, RSItem> item : exampleMap.entrySet()) {
            System.out.println("Name: " + item.getValue().getName() + " Sell_Average: " + item.getValue().getSellAverage());
        }
    }
}
