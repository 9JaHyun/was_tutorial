package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RequestLineTest {

    @DisplayName("RequestLine.parse() 는")
    @Nested
    class Parse_Context {

        @DisplayName("완전하게 데이터가 존재하는 경우 성공한다.")
        @Test
        void willSuccess(){
            // given
            String request = "GET /app/data HTTP/1.1";

            // when
            RequestLine requestLine = RequestLine.parse(request);

            // then
            Assertions.assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
            Assertions.assertThat(requestLine.getUrl()).isEqualTo("/app/data");
            Assertions.assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
        }

        @DisplayName("HTTP METHOD가 누락된 경우 실패한다.")
        @Test
        void no_HttpMethod_willFail(){
            // given
            String request = "/app/data HTTP/1.1";

            // when then
            Assertions.assertThatThrownBy(() -> RequestLine.parse(request))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("URL이 누락된 경우 실패한다.")
        @Test
        void no_url_will_fail(){
            // given
            String request = "GET HTTP/1.1";

            // when then
            Assertions.assertThatThrownBy(() -> RequestLine.parse(request))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("URL이 '/' 로 시작하지 않으면 실패한다.")
        @Test
        void url_not_startWith_slash_will_fail(){
            // given
            String request = "GET app/data HTTP/1.1";

            // when then
            Assertions.assertThatThrownBy(() -> RequestLine.parse(request))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("프로토콜이 누락된 경우 실패한다.")
        @Test
        void no_protocol_will_fail(){
            // given
            String request = "GET app/data HTTP/1.1";

            // when then
            Assertions.assertThatThrownBy(() -> RequestLine.parse(request))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

}
