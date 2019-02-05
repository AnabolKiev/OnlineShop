package com.anabol.onlineshop.dao.jdbc.mapper;

import com.anabol.onlineshop.entity.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapperTest {

    @Test
    public void testParse() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getInt("id")).thenReturn(1);
        when(resultSetMock.getString("firstName")).thenReturn("Alex");
        when(resultSetMock.getString("lastName")).thenReturn("Kovalchuk");
        when(resultSetMock.getDouble("salary")).thenReturn(1000.50);
        when(resultSetMock.getString("dateOfBirth")).thenReturn("1984-07-29");

        User actualUser = UserMapper.mapRow(resultSetMock);

        assertEquals(1, actualUser.getId());
        assertEquals("Alex", actualUser.getFirstName());
        assertEquals("Kovalchuk", actualUser.getLastName());
        assertEquals(1000.50, actualUser.getSalary(), 0.0001);
        assertEquals(LocalDate.of(1984, 7, 29), actualUser.getDateOfBirth());
    }

//    @Test
//    public void testParseList() throws SQLException {
//        ResultSet resultSetMock = mock(ResultSet.class);
//
//        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
//        when(resultSetMock.getInt("id")).thenReturn(1);
//        when(resultSetMock.getString("firstName")).thenReturn("Alex");
//        when(resultSetMock.getString("lastName")).thenReturn("Kovalchuk");
//        when(resultSetMock.getDouble("salary")).thenReturn(1000.50);
//        when(resultSetMock.getString("dateOfBirth")).thenReturn("1984-07-29");
//
//        List<User> list = UserMapper.parseList(resultSetMock);
//
//        assertFalse(list.isEmpty());
//        assertEquals(1, list.size());
//
//        User user = list.get(0);
//        assertEquals("Alex", user.getFirstName());
//    }

}