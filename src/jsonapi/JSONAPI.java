/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonapi;

import com.company.utils.Const;
import com.company.utils.JSONHelper;
import com.company.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author student
 */
public class JSONAPI {

   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JSONException {
        String jsonResult = NetworkUtils.get("http://resources.finance.ua/ua/public/currency-cash.json");     
        //System.out.println("result is" + jsonResult);
        
        
            JSONObject object = new JSONObject(jsonResult);
           // System.out.println("keys -"+JSONHelper.getKeys(object));
        ArrayList<String> keys=JSONHelper.getKeys(object);
           JSONObject currencyObject = object.getJSONObject(keys.get(6));
        System.out.println();
        //System.out.println("currency keys -"+JSONHelper.getKeys(currencyObject));
        JSONArray organizations=object.getJSONArray(keys.get(5));
        System.out.println(organizations.get(0));
        
        HashMap<String,Object> resultMap=new HashMap<>();
        double minCurrency =Double.parseDouble(((JSONObject)organizations.get(0))
                .getJSONObject("currencies")
                .getJSONObject("USD")
                .getString("bid"));
        
        resultMap=JSONHelper.createMapFromJSON((JSONObject)organizations.get(0));
        
          
               
        
        for(int i=0; i<organizations.length();i++)
        {
            JSONObject org=(JSONObject)organizations.getJSONObject(i);
            try {
            
        System.out.println(org.getString("title"));
        JSONObject curr = org.getJSONObject("currencies");
        System.out.println("EUR - "+curr.getString("EUR"));
        System.out.println("USD - "+curr.getString("USD"));
        
        String bidUSDCurrency=curr.getJSONObject("USD").getString("bid");
        if(Double.parseDouble(bidUSDCurrency)<minCurrency){
//            resultMap.put(Const.TITLE_KEY,org.getString("title"));
//            resultMap.put(Const.PHONE_KEY,org.getString("phone"));
//            resultMap.put(Const.CURRENCY_KEY,bidUSDCurrency);
//             resultMap.put(Const.ADDRESS_KEY,org.getString("address"));
         resultMap=JSONHelper.createMapFromJSON(org);
         minCurrency=Double.parseDouble(bidUSDCurrency);
        }
        System.out.println("-----------------------------");
        System.out.println("min currency -"+resultMap.toString());
        }
            
            catch (JSONException ex) {
            System.out.println(org.getString("title")+"error!!");
        }
        } 
        
    }
    
}
