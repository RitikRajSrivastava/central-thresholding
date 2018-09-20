package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import repository.Employee;

import java.io.IOException;

public class JsonFilter {

    private Employee emp;

    public void filter(String recordValue) {

        System.out.println("value: "+recordValue);

        ObjectMapper objectMapper = new ObjectMapper();

        Employee employee = null;

//            JSONObject object = new JSONObject(recordValue);
        try {
            employee = objectMapper.readValue(recordValue, Employee.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("name:" + employee.getName() + " id:" + employee.getEmpId());

    }
}
