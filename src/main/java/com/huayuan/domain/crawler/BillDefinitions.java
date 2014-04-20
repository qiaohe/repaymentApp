package com.huayuan.domain.crawler;

import com.huayuan.domain.dictionary.Dictionary;
import com.huayuan.repository.DictionaryRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 14-4-6.
 */
@XmlRootElement(name = "bills")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillDefinitions {
    @Inject
    private DictionaryRepository dictionaryRepository;
    private ConcurrentHashMap<Integer, String> BANK_MAP = new ConcurrentHashMap<>();

    public BillDefinitions() {

    }

    @PostConstruct
    private void init() {
        List<Dictionary> banks = dictionaryRepository.findByType("BANK");
        for (Dictionary dictionary : banks) {
            BANK_MAP.put(Integer.valueOf(dictionary.getValue()), dictionary.getName());
        }
    }

    @XmlElement(name = "bill")
    private List<BillDefinition> billDefinitions = null;

    public List<BillDefinition> getBillDefinitions() {
        return billDefinitions;
    }

    public void setBillDefinitions(List<BillDefinition> billDefinitions) {
        this.billDefinitions = billDefinitions;
    }

    public void addBillDefinition(BillDefinition billDefinition) {
        if (!billDefinitions.contains(billDefinition)) {
            billDefinitions.add(billDefinition);
        }
    }

    public BillDefinition getDefinitionBy(final Integer bankNo) {
        for (BillDefinition bd : billDefinitions) {
            if (bd.getBank().equalsIgnoreCase(BANK_MAP.get(bankNo))) return bd;
        }
        return null;
    }
}
