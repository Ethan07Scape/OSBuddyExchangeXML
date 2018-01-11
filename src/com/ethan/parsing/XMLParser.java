package com.ethan.parsing;


import com.ethan.exchange.RSItem;
import com.ethan.utils.NetUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Ethan on 1/11/2018.
 */
public class XMLParser {
    private String url;
    private Map map;

    public XMLParser(String url, Map map) {
        this.url = url;
        this.map = map;
        init();
    }

    public void init() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            URLConnection connection = NetUtils.createURLConnection(url);
            Document doc = dBuilder.parse(connection.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList names = doc.getElementsByTagName("entry");
            for (int i = 0; i < names.getLength(); i++) {
                Node n = names.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    String name = e.getElementsByTagName("Name").item(0).getTextContent();
                    long id = Long.parseLong(e.getElementsByTagName("ID").item(0).getTextContent());
                    long sell = Long.parseLong(e.getElementsByTagName("Sell_Average").item(0).getTextContent());
                    long buy = Long.parseLong(e.getElementsByTagName("Buy_Average").item(0).getTextContent());
                    long overall = Long.parseLong(e.getElementsByTagName("Overall_Average").item(0).getTextContent());
                    map.put(name, new RSItem(name, sell, buy, overall, id));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}