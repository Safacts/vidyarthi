
package com.aadi.learner;

public class HistoryItem {
    private String url;
    private boolean isPinned;

    public HistoryItem(String url, boolean isPinned) {
        this.url = url;
        this.isPinned = isPinned;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }
}