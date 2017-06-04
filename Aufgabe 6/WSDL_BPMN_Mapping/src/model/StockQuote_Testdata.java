package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockQuote_Testdata {

	public Map<String, List<String>> map;

	public StockQuote_Testdata(){
		this.map =  new HashMap<String, List<String>>();
		
		String stockQuoteSoap = "StockQuoteSoap";
		String stockQuoteSoap12 = "StockQuoteSoap12";
		String stockQuoteHTTPGet = "StockQuoteHttpGet";
		String stockQuoteHTTPPost = "StockQuoteHttpPost";
		ArrayList<String> stockQuote =  new ArrayList<String>();
		stockQuote.add(stockQuoteSoap);
		stockQuote.add(stockQuoteSoap12);
		stockQuote.add(stockQuoteHTTPGet);
		stockQuote.add(stockQuoteHTTPPost);
		
		ArrayList<String> temp;
		
		/* ETH */
		String ethStockQuoteHTTPGet = "ETH StockQuoteHttpGet";
		ArrayList<String> ethStockQuoteList = new ArrayList<String>(stockQuote);
		ethStockQuoteList.add(ethStockQuoteHTTPGet);
		map.put("ETH StockQuote Get", ethStockQuoteList);
		//---
		temp = new ArrayList<String>();
		temp.add("Buy ETH");
		map.put("Buy ETH", temp);
		//---
		temp = new ArrayList<String>();
		temp.add("Sell ETH");
		map.put("Sell ETH", temp);
		
		/* XRP */
		String xrpStockQuoteHTTPGet = "XRP StockQuoteHttpGet";
		ArrayList<String> xrpStockQuoteList = new ArrayList<String>(stockQuote);
		xrpStockQuoteList.add(xrpStockQuoteHTTPGet);
		map.put("XRP StockQuote Get", xrpStockQuoteList);
		//---
		temp = new ArrayList<String>();
		temp.add("Buy XRP");
		map.put("Buy XRP", temp);
		//---
		temp = new ArrayList<String>();
		temp.add("Sell XRP");
		map.put("Sell XRP", temp);
		
		/* BTC */
		String btcStockQuoteHTTPGet = "BTC StockQuoteHttpGet";
		ArrayList<String> btcStockQuoteList = new ArrayList<String>(stockQuote);
		btcStockQuoteList.add(btcStockQuoteHTTPGet);
		map.put("BTC StockQuote Get", btcStockQuoteList);
		//---
		temp = new ArrayList<String>();
		temp.add("Buy BTC");
		map.put("Buy BTC", temp);
		//---
		temp = new ArrayList<String>();
		temp.add("Sell BTC");
		map.put("Sell BTC", temp);
	}

}
