/*
 * Created on 2005-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.esite.framework.util;

/**
 * @author zhangzf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CompareResultBean {

    private String itemName;
    private Object origValue;
    private Object currValue;
    /**
     * @return Returns the currValue.
     */
    public Object getCurrValue() {
        return currValue;
    }
    /**
     * @param currValue The currValue to set.
     */
    public void setCurrValue(Object currValue) {
        this.currValue = currValue;
    }
    /**
     * @return Returns the itemName.
     */
    public String getItemName() {
        return itemName;
    }
    /**
     * @param itemName The itemName to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    /**
     * @return Returns the origValue.
     */
    public Object getOrigValue() {
        return origValue;
    }
    /**
     * @param origValue The origValue to set.
     */
    public void setOrigValue(Object origValue) {
        this.origValue = origValue;
    }
}
