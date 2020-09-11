package com.yang;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yang.bean.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloJson {

    private Gson gson = new Gson();

    @Test
    public void testString(){
        //JavaBean 和 Json 的相互转换
        Person person = new Person("胡洋", "男", 30);
        String personJson = gson.toJson(person);
        System.out.println(personJson);

        Person personObj = gson.fromJson(personJson, Person.class);
        System.out.println(personObj);
    }

    @Test
    public void testList(){
        // List 和 Json 的相互转换
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("胡洋", "男", 30));
        personList.add(new Person("刘芮彤", "女", 27));

        String personListString = gson.toJson(personList);
        System.out.println(personListString);

        List<Person> personList1 = gson.fromJson(personListString,new TypeToken<List<Person>>(){}.getType());
        System.out.println(personList1);
    }

    @Test
    public void testMap(){
        // Map 和 Json的相互转换
        Map<Integer,Person> personMap = new HashMap<>();
        personMap.put(1,new Person("胡洋", "男", 30));
        personMap.put(2,new Person("刘芮彤", "女", 27));

        String personMapString = gson.toJson(personMap);
        System.out.println(personMapString);

        Map<Integer,Person> personMap1 = gson.fromJson(personMapString,new TypeToken<Map<Integer,Person>>(){}.getType());
        System.out.println(personMap1);
    }
}
