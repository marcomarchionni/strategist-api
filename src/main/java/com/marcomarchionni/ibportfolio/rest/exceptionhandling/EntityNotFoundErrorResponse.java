package com.marcomarchionni.ibportfolio.rest.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityNotFoundErrorResponse {

        private int status;
        private String message;
        private long timeStamp;
}
