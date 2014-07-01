package zm.hashcode.android.mshengu.connection;

import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zm.hashcode.android.mshengu.resources.GeoplotResource;
import zm.hashcode.android.mshengu.resources.SiteResource;
import zm.hashcode.android.mshengu.resources.UnitDeliveryResource;
import zm.hashcode.android.mshengu.resources.UnitServiceResource;

/**
 * Created by hashcode on 2014/07/01.
 */
public class Connection {

    public HttpHeaders getContentType() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        return requestHeaders;

    }

    public MobileResponseMessage postDeployment(UnitDeliveryResource unitDeliveryResource) {
        final String url =getURL()+"tagunit";
        HttpEntity<UnitDeliveryResource> requestEntity = new HttpEntity<UnitDeliveryResource>(unitDeliveryResource, getContentType());
        ResponseEntity<MobileResponseMessage> response = getConnection().exchange(url, HttpMethod.POST, requestEntity, MobileResponseMessage.class);
        return response.getBody();
    }


    public MobileResponseMessage postUnitService(UnitServiceResource unitServiceResource) {
        final String url =getURL()+"serviceunit";
        HttpEntity<UnitServiceResource> requestEntity = new HttpEntity<UnitServiceResource>(unitServiceResource, getContentType());
        ResponseEntity<MobileResponseMessage> response = getConnection().exchange(url, HttpMethod.POST, requestEntity, MobileResponseMessage.class);
        return response.getBody();
    }



    public MobileResponseMessage postGeoPlot(GeoplotResource geoplotResource) {
        final String url =getURL()+"geoplot";
        HttpEntity<GeoplotResource> requestEntity = new HttpEntity<GeoplotResource>(geoplotResource, getContentType());
        ResponseEntity<MobileResponseMessage> response = getConnection().exchange(url, HttpMethod.POST, requestEntity, MobileResponseMessage.class);
        return response.getBody();
    }

    public RestTemplate getConnection() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        return restTemplate;
    }


    public List<SiteResource> getSites(int size) {
        String siteSize=String.valueOf(size);
        final String url =getURL()+"sites/"+siteSize;
        Log.d("url",url);
        List<SiteResource> sites = new ArrayList<SiteResource>();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        ResponseEntity<SiteResource[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, SiteResource[].class);
        SiteResource[] events = responseEntity.getBody();

        for (SiteResource event : events) {
            sites.add(event);
        }
        Log.d("sites", sites.toString());
        return sites;
    }

    public String getURL()
    {
        String url_val="";

        return url_val;

    }

}
