package com.huayuan.domain.credit;

import javax.persistence.*;

/**
 * Created by dell on 14-5-15.
 */
@Entity
@Table(name = "PBOC_OUT")
public class PbocOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_NO")
    private String id_No;
    @Column(name = "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_No() {
        return id_No;
    }

    public void setId_No(String id_No) {
        this.id_No = id_No;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
