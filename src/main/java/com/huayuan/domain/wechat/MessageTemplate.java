package com.huayuan.domain.wechat;

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

    public boolean isCreditLimit() {
        return getStatuses().equalsIgnoreCase("3.1,3.2,4,13") || getStatuses().equalsIgnoreCase("5.1,5.2,6,7,8,9,10,11,12");
    }

    public boolean isApplicationNoNeeded() {
        return getStatuses().equalsIgnoreCase("5.1") || getStatuses().equalsIgnoreCase("5.2");
    }

    public boolean isUsedCrl() {
        return getStatuses().equalsIgnoreCase("9");
    }

    public boolean isRepay() {
        return getStatuses().equalsIgnoreCase("8,9");
    }

    public boolean isNoRepay() {
        return getStatuses().equalsIgnoreCase("10") || getStatuses().equalsIgnoreCase("12");
    }

    public boolean isUrlNotNeeded() {
        return getStatuses().equalsIgnoreCase("1,2,3.1,3.2,4,5,6,7") || getStatuses().equalsIgnoreCase("12,13");
    }
}
