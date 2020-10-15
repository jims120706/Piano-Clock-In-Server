/*
package com.piano.net;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;

*/
/**
 * 腾讯云的七层负载均衡不支持长连接，每次和后端服务通信完就会断开连接。这里暂时强行修改让服务也只支持短连接。否则每次回包后都会抛出一个reset by peer异常
 *//*

@Filter("/**")
public class TencentCloudFilter implements HttpServerFilter {
    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        return Publishers.map(chain.proceed(request), mutableHttpResponse -> {
            mutableHttpResponse.getHeaders().remove(HttpHeaders.CONNECTION);
            mutableHttpResponse.getHeaders().add(HttpHeaders.CONNECTION, "close");
            return mutableHttpResponse;
        });
    }

}
*/
