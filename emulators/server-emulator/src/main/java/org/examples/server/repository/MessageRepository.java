package org.examples.server.repository;


import org.examples.server.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {

}
