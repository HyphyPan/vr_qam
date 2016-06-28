package com.nd.khan.entity;

public class Case {
    /**
     * 主键
     */
    private String id;
 
    /**
     * 教案所有者
     */
    private String caseOwner;
 
    /**
     * 教案名称
     */
    private String name;
 
    /**
     * 教案内容
     */
    private String content;
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getCaseOwner() {
        return caseOwner;
    }
 
    public void setCaseOwner(String caseOwner) {
        this.caseOwner = caseOwner;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
}
