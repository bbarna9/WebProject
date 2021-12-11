package com.myproject.Repo;

import com.myproject.Model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepo extends CrudRepository<Book, Integer> {
    public Long countById(Integer id);
}
