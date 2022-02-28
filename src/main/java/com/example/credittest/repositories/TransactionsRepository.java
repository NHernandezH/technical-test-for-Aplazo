package com.example.credittest.repositories;

import com.example.credittest.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TransactionsRepository extends CrudRepository<RequestEntity,Integer> {

}
