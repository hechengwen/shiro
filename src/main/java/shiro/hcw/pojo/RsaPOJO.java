package shiro.hcw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/12/11 16:25
 * Description:
 * Others:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsaPOJO {
    private String sign;
    private String encodeStr;
    private String cipher;
}
