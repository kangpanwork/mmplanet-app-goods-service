package com.mmplanet.cloud.app.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 10:29 <br>
 * @Author: niujiao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryTreeVo implements Serializable {

    /**
     * 主键
     **/
    private String id;
    /**
     * 父主键
     **/
    private String pid;
    /**
     * 目录名称
     **/
    private String name;

    /**
     * 目录路径
     */
    private String idPath;

    /**
     * 分类排序
     **/
    private Integer sort;

    private Date createTime;

    /**
     * 子节点
     */
    private List<CategoryTreeVo> children;
}
