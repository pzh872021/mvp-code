package com.mvp.base;

import java.lang.ref.SoftReference;
import java.lang.reflect.Proxy;

abstract class XPresenter<V extends XView> {

    private SoftReference<XView> mReferenceView;
    protected V mProxyView;

     void attach(XView view) {
        mReferenceView = new SoftReference<>(view);
        mProxyView = (V) Proxy.newProxyInstance(
                view.getClass().getClassLoader(), view.getClass().getInterfaces(),
                (o, method, objects) -> {
                    if (mReferenceView == null || mReferenceView.get() == null) {
                        return null;
                    }
                    return method.invoke(mReferenceView.get(), objects);
                });

    }

   void detach() {
        mReferenceView.clear();
        mProxyView.cancelNetWork();
        mReferenceView = null;
    }

}
