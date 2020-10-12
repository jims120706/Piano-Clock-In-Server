package com.piano.net;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommonRsp2<TData> {
    @Schema(description = "返回码")
    public int code;

    @Schema(description = "返回信息")
    public String msg;

    private TData data;
}
