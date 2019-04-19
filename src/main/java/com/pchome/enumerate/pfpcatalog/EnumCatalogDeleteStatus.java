package com.pchome.enumerate.pfpcatalog;

public enum EnumCatalogDeleteStatus {
    UNDELETE("0"),
    DELETE("1");

    private String status;

    private EnumCatalogDeleteStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
