package de.sinqularo.bungee.mysql;

public interface Callback<T> {

    void onSuccess(final T result);

    void onFailure(final Throwable cause);

}