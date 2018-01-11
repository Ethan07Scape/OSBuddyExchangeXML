package com.ethan.exchange;

import com.ethan.utils.NetUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 1/9/2018.
 */
public class RSExchange {
    private String json;
    private Map<String, RSItem> itemMap = new HashMap();

    public RSExchange(String link) {
        json = NetUtils.readUrl(link);
        fillMap(getDatabase());
        writeToXML(itemMap);
    }

    public JSONObject getDatabase() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            return (JSONObject) obj;
        } catch (Exception e) {

        }
        return null;
    }

    public void fillMap(JSONObject obj) {
        for (Object key : obj.keySet()) {
            String keyStr = (String) key;
            JSONObject nestedValue = (JSONObject) obj.get(keyStr);
            RSItem item = new RSItem((String) nestedValue.get("name"), (long) nestedValue.get("sell_average"), (long) nestedValue.get("buy_average"), (long) nestedValue.get("overall_average"), (long) nestedValue.get("id"));
            itemMap.put(item.getName(), new RSItem(item.getName(), item.getSellAverage(), item.getBuyAverage(), item.getOverallAverage(), item.getId()));
        }
    }

    private void writeToXML(Map<String, RSItem> map) {
        try {
            DocumentBuilder builder;
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("OSBuddy_Prices");
            for (String key : map.keySet()) {

                RSItem keyValue = map.get(key);

                Element node = document.createElement("entry");
                Element keyName = document.createElement("Name");
                Element overallAverage = document.createElement("Overall_Average");
                Element buyAverage = document.createElement("Buy_Average");
                Element sellAverage = document.createElement("Sell_Average");
                Element itemID = document.createElement("ID");


                keyName.setTextContent(key);
                overallAverage.setTextContent(Long.toString(keyValue.getOverallAverage()));
                sellAverage.setTextContent(Long.toString(keyValue.getSellAverage()));
                buyAverage.setTextContent(Long.toString(keyValue.getBuyAverage()));
                itemID.setTextContent(Long.toString(keyValue.getId()));

                node.appendChild(keyName);
                node.appendChild(itemID);
                node.appendChild(sellAverage);
                node.appendChild(overallAverage);
                node.appendChild(buyAverage);

                root.appendChild(node);

            }
            document.appendChild(root);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(document);
            File file = new File(System.getProperty("user.home") + "/Desktop/summary.xml");
            Result result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
