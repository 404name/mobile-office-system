package com.curity.office.controller;

import cn.hutool.core.bean.BeanUtil;
import com.curity.office.common.Result;
import com.curity.office.model.vo.DictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptDictItem;
import xyz.erupt.upms.model.EruptPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@PropertySource("classpath:application.yml")//读取application.yml文件
@RequestMapping("/erupt-api/office")
public class SystemInfoController {
    //TODO: 将用户类别 字典  返回
    @Autowired
    private EruptDao eruptDao;
    @Value("${erupt-app.version}")
    private String version;
    @Value("${erupt-app.versionMsg}")
    private String versionMsg;
    @GetMapping()
    public Result systemInfo() {
        HashMap<String, Object> map = new HashMap<>();
        Integer integer = eruptDao.getJdbcTemplate().queryForObject("select id from e_dict where code = 'state'", Integer.class);
        List<EruptDictItem> stateItems = eruptDao.queryEntityList(EruptDictItem.class,
                "erupt_dict_id = " + integer);
        integer = eruptDao.getJdbcTemplate().queryForObject("select id from e_dict where code = 'estate'", Integer.class);
        List<EruptDictItem> estateItems = eruptDao.queryEntityList(EruptDictItem.class,
                "erupt_dict_id = " + integer);
        List<EruptPost> posts = eruptDao.queryEntityList(EruptPost.class,
                "1 = 1");
        List<Map<String, Object>> orgs = eruptDao.getJdbcTemplate()
                .queryForList("select * from e_upms_org");
        List<Map<String, Object>> list1 = eruptDao.getJdbcTemplate()
                .queryForList("select erupt_post_id,count(*) from e_upms_user GROUP BY erupt_post_id having erupt_post_id > 0");
        List<Map<String, Object>> list2 = eruptDao.getJdbcTemplate()
                .queryForList("select erupt_org_id,count(*) from e_upms_user GROUP BY erupt_org_id having erupt_org_id > 0");
        List<DictVo> stateVo = new ArrayList<>();
        List<DictVo> estateVo = new ArrayList<>();
        for(EruptDictItem eruptDictItem:stateItems) {
            stateVo.add(BeanUtil.toBean(eruptDictItem,DictVo.class));
        }
        for(EruptDictItem eruptDictItem:estateItems) {
            estateVo.add(BeanUtil.toBean(eruptDictItem,DictVo.class));
        }
        for(EruptPost post:posts){
            post.setWeight(0);
            for(Map<String, Object> map1:list1){
                if(post.getId().equals((Long) map1.get("erupt_post_id"))){
                    post.setWeight(((Long) map1.get("count(*)")).intValue());
                    break;
                }
            }
        }
        for(Map<String, Object> org:orgs){
            org.put("sort",0);
            for(Map<String, Object> map1:list2){
                if(((Long)org.get("id")).equals((Long) map1.get("erupt_org_id"))){
                    org.put("sort",((Long) map1.get("count(*)")).intValue());
                    break;
                }
            }
        }
        map.put("stateItems",stateVo);
        map.put("estateItems",estateVo);
        map.put("posts",posts);
        map.put("orgs",orgs);
        map.put("version",version);
        map.put("versionMsg",versionMsg);
        return Result.success(map);
    }

}
