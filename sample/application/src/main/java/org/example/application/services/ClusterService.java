package org.example.application.services;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.application.microservices.model.MicroService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClusterService {

    @Value("${KUBERNETES_SERVICE_HOST}")
    private String serviceHost;

    @Value("${KUBERNETES_PORT_443_TCP_PORT}")
    private String servicePort;

    Logger LOGGER = LogManager.getLogger(ClusterService.class);

    public List<MicroService> getAvailableMicroservices() throws Exception {

        List<MicroService> microServices = new ArrayList<>();

        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        BufferedReader reader = new BufferedReader(new FileReader("/var/run/secrets/kubernetes.io/serviceaccount/token"));
        String bearerToken = reader.readLine();
        reader.close();

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        HttpsURLConnection con = (HttpsURLConnection) new URL("https://" + serviceHost+":" + servicePort +"/apis/apps/v1/namespaces/default/deployments?labelSelector=microserviceId").openConnection();
        con.setRequestProperty("Authorization","Bearer "+ bearerToken);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type","application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output;

        StringBuilder response = new StringBuilder();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        if (ObjectUtils.isEmpty(response)) {
            throw new RuntimeException("Deployments weren't found");
        }

        JsonElement jsonElement = JsonParser.parseString(response.toString());
        jsonElement.getAsJsonObject().getAsJsonArray("items").forEach(e -> {
            JsonObject metadata = e.getAsJsonObject().getAsJsonObject("metadata");
            JsonPrimitive id = metadata.getAsJsonObject("labels").getAsJsonPrimitive("microserviceId");
            JsonPrimitive name = metadata.getAsJsonObject("annotations").getAsJsonPrimitive("microserviceName");
            microServices.add(new MicroService(id.getAsString(), name.getAsString()));
        });

        LOGGER.info("Got list of microservices {}", microServices);
        return microServices;
    }

}
