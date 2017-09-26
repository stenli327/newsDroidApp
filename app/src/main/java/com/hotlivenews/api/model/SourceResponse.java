package com.hotlivenews.api.model;

import com.hotlivenews.api.Source;

import java.util.List;

public class SourceResponse extends Response {

    private List<Source> sources;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
