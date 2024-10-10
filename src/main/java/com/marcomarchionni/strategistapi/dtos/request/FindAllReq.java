package com.marcomarchionni.strategistapi.dtos.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuppressWarnings("unused")
public class FindAllReq {

    private String inlineCount;
    @Min(value = 0, message = "Skip must be greater or equal to 0")
    @Builder.Default
    private int skip = 0;
    @Min(value = 1, message = "Top must be greater or equal to 1")
    @Builder.Default
    private int top = 5;
    private String orderBy;

    public void set$inlinecount(String $inlineCount) {
        this.inlineCount = $inlineCount;
    }

    public void set$skip(@Min(value = 0, message = "Skip must be greater or equal to 0") int $skip) {
        this.skip = $skip;
    }

    public void set$top(@Min(value = 1, message = "Top must be greater or equal to 1") int $top) {
        this.top = $top;
    }

    public void set$orderby(String $orderby) {
        this.orderBy = $orderby;
    }
}
