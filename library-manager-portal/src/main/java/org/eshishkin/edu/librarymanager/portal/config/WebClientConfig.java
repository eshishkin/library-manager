package org.eshishkin.edu.librarymanager.portal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.channel.BootstrapHandlers;
import reactor.netty.http.client.HttpClient;
import static java.lang.Integer.max;

@Configuration
public class WebClientConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .wiretap(true)
                .tcpConfiguration(tc ->
                        tc.bootstrap(b -> BootstrapHandlers.updateLogSupport(b, new CustomLogger(HttpClient.class)))
                );

        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs()
                            .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));

                    clientDefaultCodecsConfigurer.defaultCodecs()
                            .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                }).build();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(strategies);
    }

    public static class CustomLogger extends LoggingHandler {
        public CustomLogger(Class<?> clazz) {
            super(clazz);
        }

        @Override
        protected String format(ChannelHandlerContext ctx, String event, Object arg) {
            if (arg instanceof ByteBuf) {
                ByteBuf msg = (ByteBuf) arg;
                return decode(
                        msg, msg.readerIndex(), msg.readableBytes(), StandardCharsets.UTF_8);
            }
            return super.format(ctx, event, arg);
        }

        private String decode(ByteBuf src, int readerIndex, int len, Charset charset) {
            if (len != 0) {
                byte[] array;
                int offset;
                if (src.hasArray()) {
                    array = src.array();
                    offset = src.arrayOffset() + readerIndex;
                } else {
                    array = new byte[max(len, 1024)];
                    offset = 0;
                    src.getBytes(readerIndex, array, 0, len);
                }
                return new String(array, offset, len, charset);
            }
            return "";
        }
    }
}
