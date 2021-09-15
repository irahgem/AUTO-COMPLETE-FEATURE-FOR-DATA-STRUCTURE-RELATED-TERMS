package Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class wiki 
{
	
	List<String> getTerms() throws IOException 
	{
		Document list = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_terms_relating_to_algorithms_and_data_structures").get();
        Elements content = list.getElementsByTag("li");
        List<String> terms = new ArrayList<String>();
        for (int i=28; i<content.size()-55; i++)
        	terms.add(content.get(i).text().toLowerCase());
        return terms;
	}
	
    String getInfo(String text) throws IOException 
    {
    	Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/"+text).get();
        Elements content = doc.getElementsByTag("p"); 
        String info = content.get(0).text();
        int i = 1;
        while(info.equals("") && i< 5) 
        {
        	info = content.get(i).text();
        	i++;
        }
        return info;
    }
    
    String moreInfo(String text) throws IOException 
    {
    	Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/"+text).get();
        Elements content = doc.getElementsByClass("mw-parser-output"); 
        String info = content.text();
        
        return info;
    }
}