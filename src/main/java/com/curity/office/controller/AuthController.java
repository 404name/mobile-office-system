package com.curity.office.controller;

import cn.hutool.crypto.digest.MD5;
import com.curity.office.common.HttpCode;
import com.curity.office.common.Result;
import com.curity.office.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.prop.EruptAppProp;
import xyz.erupt.core.util.MD5Util;
import xyz.erupt.core.view.EruptApiModel;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.base.LoginModel;
import xyz.erupt.upms.fun.LoginProxy;
import xyz.erupt.upms.model.EruptOrg;
import xyz.erupt.upms.model.EruptPost;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptContextService;
import xyz.erupt.upms.service.EruptSessionService;
import xyz.erupt.upms.service.EruptUserService;
import xyz.erupt.upms.vo.AdminUserinfo;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
@CrossOrigin
@RestController //使用此注解实现依赖注入相关功能（可选）
@Transactional
@RequestMapping("/erupt-api/office/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private EruptDao eruptDao;
    @Autowired
    private EruptUserService eruptUserService;
    @Autowired
    private EruptSessionService sessionService;
    @Autowired
    private EruptContextService eruptContextService;
    @Autowired
    private EruptAppProp eruptAppProp;

    /**
     * 获取当前用户
     * @return
     */
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    @GetMapping("/currentUser")
    public Result currentUser(){
        AdminUserinfo adminUserinfo = null;
        adminUserinfo = userService.currentUser();
        if(adminUserinfo == null){
            return Result.build(HttpCode.UNAUTHORIZED);
        }
        else{
            return Result.success(adminUserinfo);
        }
    }
    /**
     * 获取当前用户详细信息
     * @return
     */
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    @GetMapping("/currentDetailUser")
    public Result currentDetailUser(){
        return Result.success(eruptUserService.getCurrentEruptUser());
    }

    /**
     * 更新用户信息
     * @return
     */
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    @PostMapping("/update")
    public Result updateUser(@RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email){
        EruptUser currentEruptUser = eruptUserService.getCurrentEruptUser();
        currentEruptUser.setName(name);
        currentEruptUser.setPhone(phone);
        currentEruptUser.setEmail(email);
        return Result.success(userService.update(currentEruptUser));
    }

    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestParam("account") String account, @RequestParam("pwd") String pwd, @RequestParam(name = "verifyCode",required = false) String verifyCode){
        try {
            EruptUser eruptUser = new EruptUser();
            eruptUser.setAccount(account);
            eruptUser.setName(account);
            //默认部门 + 默认员工等级
            EruptOrg org = eruptDao.queryEntity(EruptOrg.class, "id = 5");
            EruptPost eruptPost = eruptDao.queryEntity(EruptPost.class, "id = 1");
            eruptUser.setEruptOrg(org);
            eruptUser.setEruptPost(eruptPost);
            eruptUser.setPassword(MD5Util.digest(pwd));
            eruptUser.setIsAdmin(false);
            eruptUser.setCreateTime(new Date());
            eruptUser.setStatus(true);
            eruptUser.setIsMd5(true);
            EruptUser eruptUser1 = null;
            eruptUser1 = eruptDao.queryEntity(EruptUser.class, "account = '" + account + "'");
            if(eruptUser1 != null){
                return Result.errorMsg("该用户已注册");
            }
            eruptDao.persist(eruptUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorMsg("注册失败");
        }
        return Result.success("注册成功");
    }
    /**
     * 注销
     * @return
     */
    @PostMapping({"/logout"})
    @EruptRouter(
            verifyType = EruptRouter.VerifyType.LOGIN
    )
    public EruptApiModel logout() {
        String token = this.eruptContextService.getCurrentToken();
        LoginProxy loginProxy = EruptUserService.findEruptLogin();
        Optional.ofNullable(loginProxy).ifPresent((it) -> {
            it.logout(token);
        });
        this.sessionService.remove("eruptAuth:menu-value-map:" + token);
        this.sessionService.remove("eruptAuth:menu-code-map:" + token);
        this.sessionService.remove("eruptAuth:menu-view:" + token);
        this.sessionService.remove("eruptAuth:token:" + token);
        return EruptApiModel.successApi();
    }
    /**
     * 第三方登录/注册 自动绑定用户
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestParam("account") String account, @RequestParam("pwd") String pwd, @RequestParam(name = "verifyCode",required = false) String verifyCode) {
        MD5 md5 = new MD5();
        pwd = md5.digestHex(md5.digestHex(pwd) + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + account);
        try {
            LoginProxy loginProxy = EruptUserService.findEruptLogin();
            LoginModel loginModel;
            EruptUser eruptUser;
            if (null == loginProxy) {
                loginModel = this.eruptUserService.login(account, pwd);
            } else {
                loginModel = new LoginModel();

                try {
                    eruptUser = loginProxy.login(account, pwd);
                    loginModel.setEruptUser(eruptUser);
                    loginModel.setPass(true);
                } catch (Exception var7) {
                    if (0 == this.eruptAppProp.getVerifyCodeCount()) {
                        loginModel.setUseVerifyCode(true);
                    }
                    loginModel.setReason(var7.getMessage());
                    loginModel.setPass(false);
                }
            }

            if (loginModel.isPass()) {
                eruptUser = loginModel.getEruptUser();
                loginModel.setToken(RandomStringUtils.random(16, true, true));
                loginModel.setExpire(this.eruptUserService.getExpireTime());
                this.eruptUserService.putUserInfo(eruptUser, loginModel.getToken());
                EruptUser finalEruptUser = eruptUser;
                Optional.ofNullable(loginProxy).ifPresent((it) -> {
                    it.loginSuccess(finalEruptUser, loginModel.getToken());
                });
                this.eruptUserService.cacheUserInfo(eruptUser, loginModel.getToken());
                this.eruptUserService.saveLoginLog(eruptUser, loginModel.getToken());
            }

            return Result.success(loginModel);

        } catch (Throwable var8) {
            throw var8;
        }
    }

}
