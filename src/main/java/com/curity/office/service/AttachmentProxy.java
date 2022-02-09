package com.curity.office.service;

import java.io.InputStream;

public interface AttachmentProxy {

    /**
     * @param inputStream 数据流
     * @param path        上传位置
     * @return 存储路径，正常情况下直接返回path参数即可
     */
    String upLoad(InputStream inputStream, String path);

    /**
     * 附件网络根地址
     *
     * @return
     */
    String fileDomain();

    /**
     * 是否同时保存到本地
     *
     * @return
     */
    default boolean isLocalSave() {
        return true;
    }
}
