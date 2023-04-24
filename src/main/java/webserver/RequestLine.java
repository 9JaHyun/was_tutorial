package webserver;

public class RequestLine {

    private static final String REQUEST_LINE_SEPARATOR = " ";
    private static final String URL_PREFIX = "/";


    private final HttpMethod httpMethod;
    private final String url;
    private final String protocol;

    private RequestLine(HttpMethod httpMethod, String url, String protocol) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
    }

    public static RequestLine parse(String requestLine) {
        String[] requestLineData = requestLine.split(REQUEST_LINE_SEPARATOR);

        if (requestLineData.length <= 2) {
            throw new IllegalArgumentException("This RequestLine doesn't satisfy spec");
        }

        if (!requestLineData[1].startsWith(URL_PREFIX)) {
            throw new IllegalArgumentException("URL must start with '/'");
        }

        return new RequestLine(
            HttpMethod.valueOf(requestLineData[0]),
            requestLineData[1],
            requestLineData[2]);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }
}
