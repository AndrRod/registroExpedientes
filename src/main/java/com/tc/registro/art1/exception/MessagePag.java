package com.tc.registro.art1.exception;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MessagePag {
    @JsonProperty("content")
    private List<Object> content;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("nextPath")
    private String nextPath;
    @JsonProperty("prevPath")
    private String prevPath;
}
