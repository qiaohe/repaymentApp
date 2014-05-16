package com.huayuan.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Created by dell on 14-5-10.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -5784755492258616408L;

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}
