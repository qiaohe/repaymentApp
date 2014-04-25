package com.huayuan.integration.wechat.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 14-4-22.
 */
@Entity
@Table(name = "WECHAT_MESSAGE_TEMPLATE")
public class MessageTemplate implements Serializable {
    private static final long serialVersionUID = 1405562078652620415L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;
    @Column(name = "STATUSES")
    private String statuses;
    @Column(name = "TEMPLATE")
    private String template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
