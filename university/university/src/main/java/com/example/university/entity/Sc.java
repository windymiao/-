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
 * @since 2024-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Sc对象", description="")
public class Sc implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sno;

    private Integer cno;

    private Double score;


}
