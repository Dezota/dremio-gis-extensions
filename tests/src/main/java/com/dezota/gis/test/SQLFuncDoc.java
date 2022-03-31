package com.dezota.gis.test;

public class SQLFuncDoc {

    private String name;
    private String args;
    private String returnType;
    private String description;
    private String[] tags;
    private String example;

    public SQLFuncDoc() {

    }

    public SQLFuncDoc(String name, String args, String returnType, String description, String[] tags, String example) {
        this.name = name;
        this.args = args;
        this.returnType = returnType;
        this.description = description;
        this.tags = tags;
        this.example = example;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "SQLFuncDoc{" +
                "name='" + name + '\'' +
                ", args='" + args + '\'' +
                ", returnType='" + returnType + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + java.util.Arrays.toString(tags) +
                ", example='" + example + '\'' +
                '}';
    }
}
