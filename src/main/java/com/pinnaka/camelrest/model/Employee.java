package com.pinnaka.camelrest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Employee {
    @JsonProperty("employee_id")
    private Integer empId;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("hire_date")
    private Date hireDate;
}
