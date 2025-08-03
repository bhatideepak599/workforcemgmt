package com.yourcompany.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Staff model representing an employee
 * 
 * @author Deepak Bhati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    private Long id;
    private String name;
    private String email;
    private String department;
}
