package com.janekim.gif.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

// Supposed to model data object with list of gif objects
@JsonRootName(value = "data")
public class Data {

    private List<Result> data;
    
    public Data(List<Result> data) {
        this.data = data;
    }

    public Data() {
    }

    public List<Result> getData() {
        return data;
    }

    public void setData(List<Result> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data [data=" + data + "]";
    }

    
}
