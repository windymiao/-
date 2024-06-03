package com.example.university.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value="TStudent对象", description="")
public class TStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sno")
    private String sno;

    private String sname;

    private Integer age;

    private String gender;

    private String grade;

    private Integer deptId;

    private Integer majorId;

    private String clazzId;

    @TableField(exist = false)
    private Integer roleId = 2;

    @TableField(exist = false)
    private TDept dept;

    @TableField(exist = false)
    private TMajor major;

    @TableField(exist = false)
    private TCourse course;


}
