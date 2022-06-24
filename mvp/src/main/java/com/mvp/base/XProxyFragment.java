package com.mvp.base;

class XProxyFragment<V extends XView> extends XProxyImpl {
    public XProxyFragment(V view) {
        super(view);
    }
}
