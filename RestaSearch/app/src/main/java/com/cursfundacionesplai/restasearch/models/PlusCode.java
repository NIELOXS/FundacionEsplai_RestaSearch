package com.cursfundacionesplai.restasearch.models;

public class PlusCode {
    private String compound_code;
    private String global_code;

    public PlusCode(String compoundCode, String globalCode) {
        this.compound_code = compoundCode;
        this.global_code = globalCode;
    }

    public String getCompound_code() {
        return compound_code;
    }

    public void setCompound_code(String compound_code) {
        this.compound_code = compound_code;
    }

    public String getGlobal_code() {
        return global_code;
    }

    public void setGlobal_code(String global_code) {
        this.global_code = global_code;
    }
}
