package com.semantyca.nb.core.dataengine.jpa;


import com.semantyca.nb.ui.view.ViewPage;

public interface ISimpleDAO<T> {

    T findById(String id);

    void delete(T entity);

    ViewPage findAll(int pageNum, int pageSize);
}