package com.eveb.bottlepay.common.service;

import java.util.Map;

import com.eveb.bottlepay.common.entity.PayParams;
import com.eveb.bottlepay.common.entity.PayRecord;
import com.eveb.bottlepay.common.entity.Vendor;
import com.eveb.bottlepay.common.utils.R;

/**
 * 各种支付都要事先这个类,方便管理
 */
public interface CommonPayService {

    /**
     * 获取支付路径
     * @param pp
     * @return
     */
    public Map<String, Object> getPayUrl(PayParams pp, Vendor vendor);

    /**
     * 请求第三方,支付结果,并更新数据库
     * @param billDetail
     * @return
     */
    public R getPayResult(PayRecord payRecord);
}
