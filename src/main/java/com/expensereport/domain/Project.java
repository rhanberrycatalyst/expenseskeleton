package com.expensereport.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "projectname", length = 255, nullable = false)
    private String projectname;
    
    @ManyToOne
    @JoinColumn(name = "userid_id")
    private User userid;

    @OneToMany(mappedBy = "reportid")
    @JsonIgnore
    private Set<Report> reportids = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectname() {
        return projectname;
    }
    
    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User user) {
        this.userid = user;
    }

    public Set<Report> getReportids() {
        return reportids;
    }

    public void setReportids(Set<Report> reports) {
        this.reportids = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if(project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", projectname='" + projectname + "'" +
            '}';
    }
}
