package com.expensereport.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lineitem.
 */
@Entity
@Table(name = "lineitem")
public class Lineitem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @NotNull
    @Size(max = 255)
    @Column(name = "type", length = 255, nullable = false)
    private String type;
    
    @ManyToOne
    @JoinColumn(name = "lineitemid_id")
    private Report lineitemid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public Report getLineitemid() {
        return lineitemid;
    }

    public void setLineitemid(Report report) {
        this.lineitemid = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lineitem lineitem = (Lineitem) o;
        if(lineitem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lineitem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lineitem{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
