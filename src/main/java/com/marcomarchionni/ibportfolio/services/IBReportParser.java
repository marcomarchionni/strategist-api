package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class IBReportParser {
    List<Position> positions;
    List<Trade> trades;

    public IBReportParser() {}

    public void parse(String fileName){

        SAXParserFactory factory = SAXParserFactory.newInstance();

        try (InputStream is = getXMLFileAsStream(fileName)) {

            SAXParser saxParser = factory.newSAXParser();

            // parse XML and map to object, it works, but not recommend, try JAXB
            IBHandler handler = new IBHandler();

            saxParser.parse(is, handler);

            // save all
            positions = handler.getPositions();
            trades = handler.getTrades();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public void printAll() {
        positions.forEach(x -> System.out.println(x.toString()));
        trades.forEach(x -> System.out.println(x.toString()));
    }

    // get XML file from resources folder.
    private static InputStream getXMLFileAsStream(String fileName) {
        return IBReportParser.class.getClassLoader().getResourceAsStream(fileName);
    }

}