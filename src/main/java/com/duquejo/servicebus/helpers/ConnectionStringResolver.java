/* (C)2023 */
package com.duquejo.servicebus.helpers;

import com.duquejo.servicebus.model.ServiceBusKey;

import java.util.HashMap;

/** The type Connection string resolver. */
public class ConnectionStringResolver {

    private static final String ENDPOINT = "Endpoint";
    private static final String HOST = "host";
    private static final String SAS_KEY_NAME = "SharedAccessKeyName";
    private static final String SAS_KEY = "SharedAccessKey";

    /** Instantiates a new Connection string resolver. */
    public ConnectionStringResolver() {}

    /**
     * Gets service bus key.
     *
     * @param connectionString the connection string
     * @return the service bus key
     */
    public static ServiceBusKey getServiceBusKey(String connectionString) {
        final String[] segments = connectionString.split(";");
        final HashMap<String, String> hashMap = new HashMap<>();

        for (final String segment : segments) {
            final int indexOfEqualSign = segment.indexOf("=");
            final String key = segment.substring(0, indexOfEqualSign);
            final String value = segment.substring(indexOfEqualSign + 1);
            hashMap.put(key, value);
        }

        final String endpoint = hashMap.get(ENDPOINT);
        final String[] segmentsOfEndpoint = endpoint.split("/");
        final String host = segmentsOfEndpoint[segmentsOfEndpoint.length - 1];
        hashMap.put(HOST, host);

        return new ServiceBusKey(
                hashMap.get(HOST), hashMap.get(SAS_KEY_NAME), hashMap.get(SAS_KEY));
    }
}
