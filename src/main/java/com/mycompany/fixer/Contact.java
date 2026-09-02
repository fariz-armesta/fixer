/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fixer;

/**
 *
 * @author FARIZ-T14
 */
public class Contact {
    private final int id;
    private final String name;
    private final String company;
    private final String email;
    private final String phone;
    private final String tag;
    private final String social;
    private final String desc;

    public Contact(int id, String name, String company, String email,
                    String phone, String tag, String social, String desc) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.tag = tag;
        this.social = social;
        this.desc = desc;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCompany() { return company; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getTag() { return tag; }
    public String getSocial() { return social; }
    public String getDesc() { return desc; }
}
