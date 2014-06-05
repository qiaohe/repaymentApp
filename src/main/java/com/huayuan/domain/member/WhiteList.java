package com.huayuan.domain.member;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 14-6-4.
 */
@Entity
@Table(name = "WHITE_LIST")
public class WhiteList implements Serializable {
    private static final long serialVersionUID = 6838197224744684727L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MEMBER_ID")
    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
