package cn.org.miny.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * Created by limingyang on 2018/7/19.
 */
@Data
public class MiniProgramResult implements Serializable {

    /**
     * 错误码
     */
    protected Integer errcode;

    /**
     * 错误信息
     */
    protected String errmsg;


}
