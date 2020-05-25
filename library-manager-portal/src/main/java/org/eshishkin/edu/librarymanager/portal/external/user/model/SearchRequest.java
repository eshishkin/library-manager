package org.eshishkin.edu.librarymanager.portal.external.user.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest implements Serializable {

    protected Selector selector;
    protected Integer limit;
    protected Integer skip;

    public static SearchRequest of(Selector selector) {
        SearchRequest request = new SearchRequest();
        request.setSelector(selector);
        return request;
    }

    public interface Selector extends Serializable {}
}
