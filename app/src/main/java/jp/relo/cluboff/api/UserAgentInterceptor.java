package jp.relo.cluboff.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tonkhanh on 1/5/18.
 */

public class UserAgentInterceptor  implements Interceptor {

    private final String userAgent;

    public UserAgentInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        Request requestWithUserAgent = originRequest.newBuilder()
                .header("User-Agent", userAgent)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}