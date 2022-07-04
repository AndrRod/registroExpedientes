package com.tc.registro.art1.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageGeneral {
    @JsonProperty("message")
    private String message;
    @JsonProperty("path")
    private String path;
}
