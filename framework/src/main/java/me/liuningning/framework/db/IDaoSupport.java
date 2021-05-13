package me.liuningning.framework.db;

import java.util.List;

public interface IDaoSupport<T> {

    int insert(T entry);

    void insert(List<T> entries);


    int update(T entry, String where, String... value);

    int delete(String where, String... value);

    QuerySupport<T> getQuerySupport();
}
