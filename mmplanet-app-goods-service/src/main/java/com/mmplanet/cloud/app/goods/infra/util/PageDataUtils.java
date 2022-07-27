package com.mmplanet.cloud.app.goods.infra.util;

import com.mmplanet.cloud.app.common.page.Page;
import com.mmplanet.cloud.app.common.page.PageData;

import java.util.List;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/5 16:59 <br>
 * @Author: niujiao
 */
public final class PageDataUtils {

    public static <T> PageData<T> build(long pageNum, long pageSize, Long total, Long totalPage, List<T> data) {
        Page page = new Page();
        page.setTotal(total);
        page.setPageNum((int) pageNum);
        page.setPageSize((int) pageSize);
        page.setHasNextPage(pageNum < totalPage);
        return new PageData<T>(page, data);
    }

    public static <T> PageData<T> build(long pageNum, long pageSize, Long total, boolean hasNextPage, List<T> data) {
        Page page = new Page();
        page.setTotal(total);
        page.setPageNum((int) pageNum);
        page.setPageSize((int) pageSize);
        page.setHasNextPage(hasNextPage);
        return new PageData<T>(page, data);
    }
}
