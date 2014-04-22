package com.huayuan.integration.wechat.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 14-4-22.
 */
@Entity
@Table(name = "WECHAT_MENU")
public class Menu implements Serializable {
    private static final long serialVersionUID = -5459343523696195445L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MNEU_KEY")
    private String menu_key;
    @Column(name = "NAME")
    private String name;
    @Column(name = "URL")
    private String url;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "menu")
    private List<MessageTemplate> templates = new ArrayList<>();

    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenu_key() {
        return menu_key;
    }

    public void setMenu_key(String menu_key) {
        this.menu_key = menu_key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MessageTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<MessageTemplate> templates) {
        this.templates = templates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
