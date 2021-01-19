package gateway.filter;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 定义全局路由过滤器
 */
//@Component
public class UserGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //写业务逻辑
        //获得header中的token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        //TODO 根据业务开发对应的鉴权规则
        if (StringUtils.isBlank(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); //返回未授权
            return exchange.getResponse().setComplete();  //返回前端
        }
        //继续往下执行

        return chain.filter(exchange);
    }

    /**
     * 过滤器的优先级，数字越小优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
