package database;

import java.sql.SQLException;
import java.util.List;

import exceptions.EntityNotFoundException;

public interface DAOInterface<T, U> {
  void insert(T t) throws SQLException;

  void delete(U identifier) throws EntityNotFoundException, SQLException;

  void update(T T) throws EntityNotFoundException, SQLException;

  T get(U identifier) throws EntityNotFoundException, SQLException;

  List<T> getAll() throws SQLException;
}
