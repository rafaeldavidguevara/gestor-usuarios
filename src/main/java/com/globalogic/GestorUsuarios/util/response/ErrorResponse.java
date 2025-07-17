package com.globalogic.GestorUsuarios.util.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private List<ErrorElement> error;
}
