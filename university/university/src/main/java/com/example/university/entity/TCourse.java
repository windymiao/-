package com.example.university.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="TCourse对象", description="")
public class TCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cno")
    private Integer cno;

    private String cname;

    private Integer deptId;

    @TableField(exist = false)
    private List<TStudent> students;


}
