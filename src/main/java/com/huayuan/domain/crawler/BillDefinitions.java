package com.huayuan.domain.crawler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dell on 14-4-6.
 */
@XmlRootElement(name = "bills")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillDefinitions {
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

    public BillDefinition getDefinitionBy(final String bank) {
        for (BillDefinition bd : billDefinitions) {
            if (bd.getBank().equalsIgnoreCase(bank)) return bd;
        }
        return null;
    }
}
