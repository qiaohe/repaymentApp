package com.huayuan.domain.recognizer;

import com.google.common.base.Objects;

/**
 * Created by dell on 14-3-21.
 */
public final class IdCardInfo {
    private String name;
    private String nationality;
    private String address;
    private String idCardNumber;

    public IdCardInfo() {

    }

    public IdCardInfo(String name, String nationality, String address, String idCardNumber) {
        this();
        this.name = name;
        this.nationality = nationality;
        this.address = address;
        this.idCardNumber = idCardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCardNumber);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof IdCardInfo) {
            final IdCardInfo other = (IdCardInfo) obj;
            return Objects.equal(idCardNumber, other.idCardNumber);
        } else {
            return false;
        }
    }
}
