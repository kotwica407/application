package com.company.application.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity(name = "workers")
public class Worker {
    private int idWorker;
    private long pesel;
    private String name;
    private String lastname;
    private String position;
    private String department;
    private BigDecimal salary;
    private Date hiredate;
    private String login;
    private String password;
    private String email;

    public Worker(){}

    public Worker(Worker worker) {
        this.idWorker = worker.idWorker;
        this.pesel = worker.pesel;
        this.name = worker.name;
        this.lastname = worker.lastname;
        this.position = worker.position;
        this.department = worker.department;
        this.salary = worker.salary;
        this.hiredate = worker.hiredate;
        this.login = worker.login;
        this.password = worker.password;
        this.email = worker.email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_worker")
    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    @Basic
    @Column(name = "PESEL")
    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    @Basic
    @NotEmpty
    @Column(name = "login", nullable = false, unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Email
    @NotEmpty
    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "salary")
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Basic
    @Column(name = "hiredate")
    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return idWorker == worker.idWorker &&
                pesel == worker.pesel &&
                Objects.equals(name, worker.name) &&
                Objects.equals(lastname, worker.lastname) &&
                Objects.equals(position, worker.position) &&
                Objects.equals(department, worker.department) &&
                Objects.equals(salary, worker.salary) &&
                Objects.equals(hiredate, worker.hiredate) &&
                Objects.equals(login, worker.login) &&
                Objects.equals(email, worker.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWorker, pesel, name, lastname, position, department, salary, hiredate, login, email);
    }
}
