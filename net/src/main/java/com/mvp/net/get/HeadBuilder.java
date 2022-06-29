package com.mvp.net.get;


import com.mvp.net.other.OtherRequest;
import com.mvp.net.other.OtherMethod;
import com.mvp.net.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OtherMethod.HEAD, url, tag, params, headers, id).build();
    }
}
