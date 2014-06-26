package com.huayuan.domain.wechat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 14-4-29.
 */
@Entity
@Table(name = "WECHAT_HINT_MESSAGE")
public class HintMessage implements Serializable {
    private static final long serialVersionUID = -3626919354698433538L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TEMPLATE")
    private String template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
