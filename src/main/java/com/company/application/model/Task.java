package com.company.application.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity(name = "tasks")
public class Task {
    private int idTask;
    private int idWorker;
    private String whattodo;
    private Date deadline;
    private boolean status;

    @Id
    @Column(name = "id_task")
    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    @Basic
    @Column(name = "id_worker")
    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    @Basic
    @Column(name = "whattodo")
    public String getWhattodo() {
        return whattodo;
    }

    public void setWhattodo(String whattodo) {
        this.whattodo = whattodo;
    }

    @Basic
    @Column(name = "deadline")
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return idTask == task.idTask &&
                idWorker == task.idWorker &&
                status == task.status &&
                Objects.equals(whattodo, task.whattodo) &&
                Objects.equals(deadline, task.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idWorker, whattodo, deadline, status);
    }
}
