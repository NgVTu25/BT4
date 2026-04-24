package org.example.bt5.repository;

import java.util.List;

public interface CrudRepository<T, ID> {
    void saveBook(T book);

    Boolean updateBook(ID id, T book);

    Boolean deleteBooks(List<ID> ids);

    void saveAll(List<T> books);

    Object findById(ID id);
}
