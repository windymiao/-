package com.example.university.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author university
 * @since 2024-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Clazz对象", description="")
public class Clazz implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clazzId;

    private String clazzName;

    private Integer deptId;


}
