package com.example.university.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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
@ApiModel(value="TMajor对象", description="")
public class TMajor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "major_id", type = IdType.AUTO)
    private Integer majorId;

    private String majorName;

    private Integer deptId;

    @TableField(exist = false)
    private List<TStudent> students;


}
