package com.starsofocean.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


//@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter,Ordered {
    /**
     *
     * @param exchange 请求上下文,可以获取Request,Response等信息
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();
        //获取参数中的authorization参数
        String authorization = params.getFirst("authorization");
        //判断参数是否相等
        if("admin".equals(authorization)){
            //放行
            return chain.filter(exchange);
        }
        //拦截
          //设置状态码
          exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
    //过滤器执行顺序
    @Override
    public int getOrder() {
        return -1;
    }
}
