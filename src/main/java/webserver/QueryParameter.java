package webserver;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class QueryParameter {

    private static final String QUERY_PARAMETER_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private final Map<String, String> queryParamMap;

    private QueryParameter(Map<String, String> queryParamMap) {
        this.queryParamMap = queryParamMap;
    }

    public static QueryParameter parse(String queryString) {
        String decodedQueryString = URLDecoder.decode(queryString);
        String[] queryParams = decodedQueryString.split(QUERY_PARAMETER_SEPARATOR);
        ConcurrentHashMap<String, String> paramMap = Arrays.stream(queryParams)
            .map(keyValueString -> keyValueString.split(KEY_VALUE_SEPARATOR, 2))
            .filter(keyValueFair -> keyValueFair.length == 2)
            .collect(Collectors.toMap(keyValueFair -> keyValueFair[0],
                keyValueFair -> keyValueFair[1], (x, y) -> y, ConcurrentHashMap::new));

        return new QueryParameter(paramMap);
    }

    public String get(String key) {
        return queryParamMap.get(key);
    }

    public Set<String> getKeys() {
        return new HashSet<>(queryParamMap.keySet());
    }
}
