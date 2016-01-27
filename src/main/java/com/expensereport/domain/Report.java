package com.expensereport.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "reportname", length = 255, nullable = false)
    private String reportname;
    
    @ManyToOne
    @JoinColumn(name = "reportid_id")
    private Project reportid;

    @OneToMany(mappedBy = "lineitemid")
    @JsonIgnore
    private Set<Lineitem> lineitemids = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportname() {
        return reportname;
    }
    
    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public Project getReportid() {
        return reportid;
    }

    public void setReportid(Project project) {
        this.reportid = project;
    }

    public Set<Lineitem> getLineitemids() {
        return lineitemids;
    }

    public void setLineitemids(Set<Lineitem> lineitems) {
        this.lineitemids = lineitems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Report report = (Report) o;
        if(report.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Report{" +
            "id=" + id +
            ", reportname='" + reportname + "'" +
            '}';
    }
}
