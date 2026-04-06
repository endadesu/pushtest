package com.memapp.todo;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoMapper {

    @Select("SELECT * FROM todo")
    List<Todo> findAll();

    @Select("SELECT * FROM todo WHERE id = #{id}")
    Optional<Todo> findById(long id);

    @Insert("INSERT INTO todo (text, completed) VALUES (#{text}, #{completed})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Todo todo);

    @Update("UPDATE todo SET completed = #{completed} WHERE id = #{id}")
    void update(Todo todo);

    @Delete("DELETE FROM todo WHERE id = #{id}")
    int deleteById(long id);
}
