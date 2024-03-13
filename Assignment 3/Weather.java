//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 27, 2024
//  Title:  Assignment 3: Weather
//  Descr: 
//
//          Return weather readings using an API for 6 locations.
//
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {

    //CONSTS
    //API URL/KEY Strings
    private static String API_KEY = "c02821d30d7e79335c7d9f6d4cc8a3cf";
    private static String API_URL = "http://api.openweathermap.org/data/3.0/onecall?lat=%1$s&lon=%2$s&appid=%3$s"; //lat/long is str formatted

    //VARS
    //Six GTA cities by lat/long
    private static String scarborough[] = {"43.746786", "-79.211276", "\nSCARBOROUGH\n"}; //they look diff because they were defaults for me in scarborough
    private static String missisauga[] = {"43.5926303282077", "-79.64740542901852", "\nMISSISSAUGA\n"};
    private static  String toronto[] = {"43.6540723324646", "-79.38180246044165", "\nTORONTO\n"};
    private static String richmondHill[] = {"43.881735633032726", "-79.44185182792931", "\nRICHMOND HILL\n"};
    private static String markham[] = {"43.857592756515025", "-79.34369420910741", "\nMARKHAM\n"};
    private static String ajax[] = {"43.85090468251506", "-79.02273737605887", "\nAJAX\n"};

    //Hottest city
    private static double tempHOT = 0;
    private static String cityHOT;

    //Coldest city
    private static double tempCOLD = 0;
    private static String cityCOLD;

    //PROGRAM
    public static void main(String args[]) throws Exception {        
        //Create urls for each city
        URL scarbURL = urlGTA(scarborough);
        URL saugaURL = urlGTA(missisauga);
        URL torontoURL = urlGTA(toronto);
        URL richHillURL = urlGTA(richmondHill);
        URL markhamURL = urlGTA(markham);
        URL ajaxURL = urlGTA(ajax);

        //Calls the weather API 6 times.
        callWeatherAPI(scarbURL, scarborough);
        callWeatherAPI(saugaURL, missisauga);
        callWeatherAPI(torontoURL, toronto);
        callWeatherAPI(richHillURL, richmondHill);
        callWeatherAPI(markhamURL, markham);
        callWeatherAPI(ajaxURL, ajax);

        //Print out temp ranking conclusions
        System.out.println("\nThe hottest city was:" + cityHOT + "\nThe coldest city was: " + cityCOLD + "\n");
    }

    public static URL urlGTA(String city[]) throws Exception {
        URL url = new URL(String.format(API_URL, city[0], city[1], API_KEY));
        return url;
    }

    public static void callWeatherAPI(URL url, String cities[]) throws Exception {        
        //Break it up for readability
        System.out.println(cities[2]);

        //setup connection using URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        //Response code handling (only works on 200)
        int responseCode =  connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader((connection.getInputStream()))); //Open reader
            String inputLine; //init string container
            StringBuffer response = new StringBuffer(); //init buffer

            //loops as long as there is still stuff left to read for output portion of assignment 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine); //adding to our buffer
                
            }

            //parse for temps for temp ranking portion of assignment
            double temp = Double.parseDouble(response.toString().split("\"temp\":")[1].split(",")[0]);

            tempHOT = (tempHOT == 0) ? temp : tempHOT;
            tempCOLD = (tempCOLD == 0) ? temp : tempCOLD;

            if (temp > tempHOT) {
                tempHOT = temp;
                cityHOT = cities[2];
            }

            if (temp < tempCOLD) {
                tempCOLD = temp;
                cityCOLD = cities[2];
            }

            in.close(); //close our reader
            System.out.println("Response: " + response.toString()); //print it all out
        }
    }
}