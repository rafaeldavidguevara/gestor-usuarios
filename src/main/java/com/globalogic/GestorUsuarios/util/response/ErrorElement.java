package com.globalogic.GestorUsuarios.util.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ErrorElement {
    private Timestamp timestamp;
    private int codigo;
    private String detail;
}
