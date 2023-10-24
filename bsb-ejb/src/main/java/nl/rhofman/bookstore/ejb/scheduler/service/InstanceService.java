package nl.rhofman.bookstore.ejb.scheduler.service;

import java.util.function.Consumer;

public interface InstanceService {

    <T> void executeBean(Class<T> clazz, Consumer<T> action);
}
