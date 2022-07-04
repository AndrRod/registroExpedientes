package com.tc.registro.art1.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PaginationMessage {
        public MessagePag messageInfo(Page page, List<Object> dtoPageList){
        List <Object> content = dtoPageList;
        String nextPath = null;
        String prevPath = null;
        if(!page.isLast()) nextPath= "?page=" + (page.getNumber()+1);
        if(!page.isFirst()) prevPath= "?page=" + (page.getNumber()-1);
        if(page.getContent().isEmpty()) content = Collections.singletonList("page empty");
        return new MessagePag(content, HttpStatus.OK.value(), nextPath, prevPath);
    }
}
