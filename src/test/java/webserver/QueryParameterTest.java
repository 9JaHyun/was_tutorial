package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class QueryParameterTest {
    @DisplayName("QueryParameter.parse() 는")
    @Nested
    class Parse_Context {

        @DisplayName("완전하게 데이터가 존재하는 경우 성공한다.")
        @Test
        void willSuccess() {
            // given
            String request = "userId=userId1&password=password&name=username&email=email@google.com";

            // when
            QueryParameter queryParameter = QueryParameter.parse(request);

            Assertions.assertThat(queryParameter.get("userId")).isEqualTo("userId1");
            Assertions.assertThat(queryParameter.get("password")).isEqualTo("password");
            Assertions.assertThat(queryParameter.get("name")).isEqualTo("username");
            Assertions.assertThat(queryParameter.get("email")).isEqualTo("email@google.com");
            Assertions.assertThat(queryParameter.getKeys().size()).isSameAs(4);
        }

        @DisplayName("UTF-8 디코딩에 성공한다.")
        @Test
        void url_decoding_willSuccess() {
            // given
            String request = "userId=%EC%95%84%EC%9D%B4%EB%94%98%EB%8D%B0%EC%9A%A91&password=password&name=%ED%95%9C%EA%B8%80%EC%9D%B4%EB%A6%84&email=email%40google.com";

            QueryParameter queryParameter = QueryParameter.parse(request);

            Assertions.assertThat(queryParameter.get("userId")).isEqualTo("아이딘데용1");
            Assertions.assertThat(queryParameter.get("password")).isEqualTo("password");
            Assertions.assertThat(queryParameter.get("name")).isEqualTo("한글이름");
            Assertions.assertThat(queryParameter.get("email")).isEqualTo("email@google.com");
            Assertions.assertThat(queryParameter.getKeys().size()).isSameAs(4);
        }

        @DisplayName("'='이 여러개 있는 경우 가장 앞의 것만 짤린다.")
        @Test
        void only_first_equals_will_success() {
            // given
            String request = "userId=user=Id1&password=pa=s==s=word";

            // when
            QueryParameter queryParameter = QueryParameter.parse(request);

            Assertions.assertThat(queryParameter.get("userId")).isEqualTo("user=Id1");
            Assertions.assertThat(queryParameter.get("password")).isEqualTo("pa=s==s=word");
            Assertions.assertThat(queryParameter.getKeys().size()).isSameAs(2);
        }

        @DisplayName("빈 '&'의 경우 무시한다.")
        @Test
        void empty_Ampersand_ignore_success() {
            // given
            String request = "userId=username1&&password=pw1";

            // when
            QueryParameter queryParameter = QueryParameter.parse(request);

            Assertions.assertThat(queryParameter.get("userId")).isEqualTo("username1");
            Assertions.assertThat(queryParameter.get("password")).isEqualTo("pw1");

            Assertions.assertThat(queryParameter.getKeys().size()).isSameAs(2);
        }
    }
}
