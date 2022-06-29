package com.mvp.net.callback;

public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
