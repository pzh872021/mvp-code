package com.mvp.base;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class XProxyImpl implements XProxy {

    private XView mView;
    private List<XPresenter> mInjectPresenters;

    public XProxyImpl(XView view) {
        this.mView = view;
        mInjectPresenters = new ArrayList<>();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void bindPresenter() {
        //获得已经申明的变量，包括私有的
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            XInjectPresenter injectPresenter = field.getAnnotation(XInjectPresenter.class);
            if (injectPresenter != null) {
                try {
                    Class<? extends XPresenter> type = (Class<? extends XPresenter>) field.getType();
                    XPresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attach(mView);
                    field.setAccessible(true);
                    field.set(mView, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new RuntimeException("注解的Presenter类要继承BasePresenter");
                }
            }
        }
    }

    @Override
    public void unbindPresenter() {
        for (XPresenter presenter : mInjectPresenters) {
            presenter.detach();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;
    }
}
